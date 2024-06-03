package stefan.nemanja.service.services;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import stefan.nemanja.model.models.Spell;
import stefan.nemanja.model.models.Troop;
import stefan.nemanja.model.models.dto.CurrentUserDTO;
import stefan.nemanja.model.models.dto.UnitDTO;
import stefan.nemanja.model.models.rules.ResultRules;
import stefan.nemanja.model.models.rules.TroopRule;
import stefan.nemanja.model.models.rules.UserTroopRule;
import stefan.nemanja.service.repositories.SpellRepository;
import stefan.nemanja.service.repositories.TroopRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SpellService {
    private final SpellRepository spellRepository;
    private final TroopRepository troopRepository;
    private final KieSession kieSession;

    public SpellService(SpellRepository spellRepository, TroopRepository troopRepository, KieContainer kieContainer) {
        this.spellRepository = spellRepository;
        this.troopRepository = troopRepository;
        this.kieSession = kieContainer.getKieBase("forwardKbase").newKieSession();
    }

    public ResultRules castSpell(CurrentUserDTO currentUserDTO) {
        List<Spell> spells = getSpellsByIds(currentUserDTO.getAvailableSpells());

        UserTroopRule enemyUserTroopRule = createUserTroopRule(currentUserDTO, false);
        UserTroopRule ownUserTroopRule = createUserTroopRule(currentUserDTO, true);
        ResultRules resultRules = new ResultRules();

        kieSession.insert(currentUserDTO);
        for (Spell spell : spells) {
            kieSession.insert(spell);
        }

        kieSession.insert(enemyUserTroopRule);
        kieSession.insert(ownUserTroopRule);
        kieSession.insert(resultRules);

        kieSession.getAgenda().getAgendaGroup("spell-casting").setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();

        return resultRules;
    }

    public List<Spell> getSpellsByIds(List<Long> spellIds){
        return spellRepository.findAllById(spellIds);
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
