package stefan.nemanja.service.services;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import stefan.nemanja.model.models.Node;
import stefan.nemanja.model.models.Tile;
import stefan.nemanja.model.models.Troop;
import stefan.nemanja.model.models.dto.UnitDTO;
import stefan.nemanja.model.models.dto.CurrentUserDTO;
import stefan.nemanja.model.models.rules.NodeRule;
import stefan.nemanja.model.models.rules.ResultRules;
import stefan.nemanja.model.models.rules.TroopRule;
import stefan.nemanja.model.models.rules.UserTroopRule;
import stefan.nemanja.service.repositories.TroopRepository;

import java.util.*;

@Service
public class TroopService {
    private final TreeService treeService;
    private final TroopRepository troopRepository;
    private final KieContainer kieContainer;

    public TroopService(TreeService treeService, TroopRepository troopRepository, KieContainer kieContainer) {
        this.treeService = treeService;
        this.troopRepository = troopRepository;
        this.kieContainer = kieContainer;
    }

    public List<Troop> getAllTroops() {
        return troopRepository.findAll();
    }

    public ResultRules bestMove(CurrentUserDTO currentUserDTO) {
        UserTroopRule enemyUserTroopRule = createUserTroopRule(currentUserDTO, false);
        UserTroopRule ownUserTroopRule = createUserTroopRule(currentUserDTO, true);
        ResultRules resultRules = new ResultRules();

        if (canAttack(ownUserTroopRule, enemyUserTroopRule)) {
            resultRules.setResult("You should definitely attack enemy that's closest to you.");
            return resultRules;
        } else {
            resultRules.setResult("You should definitely move closer to enemy.");
            return resultRules;
            //return calculateOptimalMove(currentUserDTO, enemyUserTroopRule, ownUserTroopRule, resultRules);
        }
    }

    public boolean canAttack(UserTroopRule ownTroops, UserTroopRule enemyTroops) {
        TroopRule activeTroop = null;
        for (TroopRule troops : ownTroops.getTroops()) {
            if (troops.isOnMove()) {
                activeTroop = troops;
                break;
            }
        }
        assert activeTroop != null;

        Node userTree = treeService.createTree(activeTroop.getiPosition(), activeTroop.getjPosition(), activeTroop.getSpeed());
        Set<String> insertedNodes = new HashSet<>();

        KieSession kieSession = kieContainer.getKieBase("bwBase").newKieSession();
        insertTreeIntoSession(userTree, kieSession, insertedNodes);

        for (TroopRule troop : enemyTroops.getTroops()) {
            Tile enemyNode = new Tile(troop.getiPosition(), troop.getjPosition());
            kieSession.insert(enemyNode);
        }

        int firedRules = kieSession.fireAllRules();

        kieSession.dispose();

        return firedRules > 0;
    }

    private void insertTreeIntoSession(Node node, KieSession kieSession, Set<String> insertedNodes) {
        for (Node child : node.getChildren()) {
            String childCoord = child.getX() + "," + child.getY();
            if (!insertedNodes.contains(childCoord)) {
                NodeRule nodeRule = new NodeRule(node.getX(), node.getY(), child.getX(), child.getY());
                kieSession.insert(nodeRule);
                System.out.println("Node: " + nodeRule.getI() + " " + nodeRule.getJ());
                insertedNodes.add(childCoord);
            }
            insertTreeIntoSession(child, kieSession, insertedNodes);
        }
    }


    private ResultRules calculateOptimalMove(CurrentUserDTO currentUserDTO, UserTroopRule ownTroops, UserTroopRule enemyTroops, ResultRules resultRules) {

        KieSession kieSession = kieContainer.getKieBase("bwBase").newKieSession();
        kieSession.insert(currentUserDTO);
        kieSession.insert(ownTroops);
        kieSession.insert(enemyTroops);

        kieSession.insert(resultRules);
        kieSession.fireAllRules();
        kieSession.dispose();

        return resultRules;
    }

    public UserTroopRule createUserTroopRule(CurrentUserDTO currentUserDTO, boolean isFriendly) {
        List<TroopRule> troops = new ArrayList<>();
        List<UnitDTO> unitDTOs = isFriendly ? currentUserDTO.getOurTroops() : currentUserDTO.getEnemyTroops();

        for (UnitDTO unitDTO : unitDTOs) {
            Optional<Troop> optionalTroop = troopRepository.findById(unitDTO.getId());
            if (optionalTroop.isPresent()) {
                Troop troop = optionalTroop.get();
                TroopRule troopRule = new TroopRule(unitDTO, troop);
                troops.add(troopRule);
            }
        }

        return new UserTroopRule(troops, isFriendly);
    }

    public Map<TroopRule, Integer> rankEnemiesByDistance(List<TroopRule> ourTroops, List<TroopRule> enemyTroops){
        Map<TroopRule, Integer> enemyDistanceMap = new HashMap<>();

        for (TroopRule ourTroop : ourTroops) {
            if (ourTroop.getType() == Troop.TroopType.RANGED){
                for(TroopRule enemyTroop : enemyTroops) {
                    int distance = calculateDistance(ourTroop.getiPosition(), ourTroop.getjPosition(), enemyTroop.getiPosition(), enemyTroop.getjPosition());
                    enemyDistanceMap.putIfAbsent(enemyTroop, distance);
                    if(distance < enemyDistanceMap.get(enemyTroop)){
                        enemyDistanceMap.put(enemyTroop, distance);
                    }
                }
            }
        }
        return enemyDistanceMap;
    }

    public TroopRule rankTroops(List<TroopRule> enemyTroops, List<TroopRule> ourTroops){
        TroopRule attackingTroop = ourTroops.stream()
                .filter(TroopRule::isOnMove)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No troop is on move"));

        //Rangiranje po tome cije unite cemo brojcano najvise ubiti
        Map<TroopRule, Integer> killedUnitsMap = new HashMap<>();

        for(TroopRule troop: enemyTroops){
            int totalHealth = (troop.getTroopCount() - 1) * troop.getTroopHealthPoints() + troop.getTotalHealthPoints();
            int remainingHealth = totalHealth - ((attackingTroop.getMaxDmg() + attackingTroop.getMinDmg()) / 2);
            int remainingUnits = remainingHealth > 0 ? remainingHealth / troop.getTroopHealthPoints() : 0;
            int killedUnits = troop.getTroopCount() - remainingUnits;
            killedUnitsMap.put(troop, killedUnits);
        }

        List<TroopRule> rankedTroops = enemyTroops.stream()
                .sorted(Comparator.comparing(killedUnitsMap::get).reversed())
                .toList();

        int maxKilledUnits = killedUnitsMap.get(rankedTroops.getFirst());
        int finalMaxKilledUnits = maxKilledUnits;
        List<TroopRule> topRankedTroops = rankedTroops.stream()
                .filter(troop -> killedUnitsMap.get(troop) == finalMaxKilledUnits)
                .toList();

        //Ako ima vise trupa cijih unita ubijamo najvise, brisemo sve trupe cije bi unite ubili sa retaliationom
        if(topRankedTroops.size() != 1){
            for(Iterator<TroopRule> iterator = topRankedTroops.iterator(); iterator.hasNext();){
                TroopRule troop = iterator.next();
                int totalHealth = (troop.getTroopCount() - 1) * troop.getTroopHealthPoints() + troop.getTotalHealthPoints();
                int remainingHealth = totalHealth - ((attackingTroop.getMaxDmg() + attackingTroop.getMinDmg()) / 2);
                if(remainingHealth <= 0){
                    iterator.remove();
                }
            }

            rankedTroops = topRankedTroops.stream()
                    .sorted(Comparator.comparing(killedUnitsMap::get).reversed())
                    .toList();

            maxKilledUnits = killedUnitsMap.get(rankedTroops.getFirst());
            int finalMaxKilledUnits1 = maxKilledUnits;
            topRankedTroops = rankedTroops.stream()
                    .filter(troop -> killedUnitsMap.get(troop) == finalMaxKilledUnits1)
                    .toList();
        }

        if(topRankedTroops.size() == 1){
            return topRankedTroops.getFirst();
        }

        //Ako i dalje imamo isti broj trupa na #1, rangiramo po tome kolko su daleko od ranged trupa
        Map<TroopRule, Integer> enemyDistanceMap = rankEnemiesByDistance(ourTroops, enemyTroops);

        List<Map.Entry<TroopRule, Integer>> entries = new ArrayList<>(enemyDistanceMap.entrySet());

        int minDistance = Collections.min(enemyDistanceMap.values());

        for (Iterator<Map.Entry<TroopRule, Integer>> iterator = entries.iterator(); iterator.hasNext();) {
            Map.Entry<TroopRule, Integer> entry = iterator.next();
            if (entry.getValue() > minDistance) {
                iterator.remove();
            }
        }

        entries.sort(Map.Entry.comparingByValue());

        if (entries.size() > 1) {
            Map.Entry<TroopRule, Integer> maxHealthEntry = null;
            int maxHealth = -1;

            for (Map.Entry<TroopRule, Integer> entry : entries) {
                TroopRule troop = entry.getKey();
                int health = (troop.getTroopCount() - 1) * troop.getTroopHealthPoints() + troop.getTotalHealthPoints();
                if (health > maxHealth) {
                    maxHealth = health;
                    maxHealthEntry = entry;
                }
            }

            return maxHealthEntry.getKey();
        } else {
            return entries.getFirst().getKey();
        }
    }

    public int calculateDistance(int i1, int j1, int i2, int j2){
        int x1 = j1 - (i1 - (i1 & 1)) / 2;
        int y1 = -x1 - i1;

        int x2 = j2 - (i2 - (i2 & 1)) / 2;
        int y2 = -x2 - i2;

        return (Math.abs(x1 - x2) + Math.abs(y1 - y2) + Math.abs(i1 - i2)) / 2;
    }
}
