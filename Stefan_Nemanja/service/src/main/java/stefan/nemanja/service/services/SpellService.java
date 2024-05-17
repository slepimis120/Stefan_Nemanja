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
import stefan.nemanja.service.repositories.SpellRepository;
import stefan.nemanja.service.repositories.TroopRepository;

import java.util.List;
import java.util.stream.Collectors;

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

    public ResultRules getRecommendedSpell(CurrentUserDTO currentUserDTO){
        List<Spell> spells = getSpellsByIds(currentUserDTO.getAvailableSpells());
        List<Troop> enemyTroops = troopRepository.findAllById(currentUserDTO.getEnemyTroops().stream()
                .map(UnitDTO::getId)
                .collect(Collectors.toList()));
        List<Troop> ownTroops = troopRepository.findAllById(currentUserDTO.getOurTroops().stream()
                .map(UnitDTO::getId)
                .collect(Collectors.toList()));

        TroopRule enemyTroopRule = new TroopRule(enemyTroops, false);
        TroopRule ownTroopRule = new TroopRule(ownTroops, true);
        ResultRules resultRules = new ResultRules();

        kieSession.insert(currentUserDTO);
        for (Spell spell : spells) {
            kieSession.insert(spell);
        }

        kieSession.insert(enemyTroopRule);
        kieSession.insert(ownTroopRule);
        kieSession.insert(resultRules);

        kieSession.getAgenda().getAgendaGroup("spell-casting").setFocus();
        kieSession.fireAllRules();

        return resultRules;
    }

    public List<Spell> getAllSpells(){
        return spellRepository.findAll();
    }

    public List<Spell> getSpellsByIds(List<Long> spellIds){
        return spellRepository.findAllById(spellIds);
    }
}
