package stefan.nemanja.service.services;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import stefan.nemanja.model.models.Troop;
import stefan.nemanja.model.models.dto.UnitDTO;
import stefan.nemanja.model.models.dto.CurrentUserDTO;
import stefan.nemanja.model.models.rules.ResultRules;
import stefan.nemanja.model.models.rules.TroopRule;
import stefan.nemanja.model.models.rules.UserTroopRule;
import stefan.nemanja.service.repositories.TroopRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TroopService {
    private final TroopRepository troopRepository;
    private final KieSession kieSession;

    public TroopService(TroopRepository troopRepository, KieContainer kieContainer) {
        this.troopRepository = troopRepository;
        this.kieSession = kieContainer.getKieBase("bwBase").newKieSession();
    }

    public ResultRules bestMove(CurrentUserDTO currentUserDTO) {
        List<Troop> enemyTroops = troopRepository.findAllById(
                currentUserDTO.getEnemyTroops().stream()
                        .map(UnitDTO::getId)
                        .collect(Collectors.toList())
        );
        List<Troop> ownTroops = troopRepository.findAllById(
                currentUserDTO.getOurTroops().stream()
                        .map(UnitDTO::getId)
                        .collect(Collectors.toList())
        );

        UserTroopRule enemyUserTroopRule = createUserTroopRule(currentUserDTO, false);
        UserTroopRule ownUserTroopRule = createUserTroopRule(currentUserDTO, true);
        ResultRules resultRules = new ResultRules();

        if (canAttack(currentUserDTO, enemyTroops)) {
            return null;
        } else {
            return calculateOptimalMove(currentUserDTO, enemyUserTroopRule, ownUserTroopRule, resultRules);
        }
    }

    // Placeholder
    private boolean canAttack(CurrentUserDTO currentUserDTO, List<Troop> enemyTroops) {
        return false;
    }


    private ResultRules calculateOptimalMove(CurrentUserDTO currentUserDTO, UserTroopRule ownTroops, UserTroopRule enemyTroops, ResultRules resultRules) {

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
}
