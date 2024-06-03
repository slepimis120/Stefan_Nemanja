package stefan.nemanja.service.services;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import stefan.nemanja.model.models.Troop;
import stefan.nemanja.model.models.dto.CurrentUserDTO;
import stefan.nemanja.model.models.rules.ResultRules;
import stefan.nemanja.model.models.rules.TroopRule;
import stefan.nemanja.model.models.rules.UserTroopRule;

import java.util.List;

@Service
public class GameService {
    private final SpellService spellService;
    private final TroopService troopService;
    private final KieContainer kieContainer;

    public GameService(SpellService spellService, TroopService troopService, KieContainer kieContainer) {
        this.spellService = spellService;
        this.troopService = troopService;
        this.kieContainer = kieContainer;
    }

    public ResultRules getBestMove(CurrentUserDTO currentUserDTO) {
        KieSession kieSession = kieContainer.getKieBase("forwardKbase").newKieSession();
        kieSession.insert(currentUserDTO);
        kieSession.getAgenda().getAgendaGroup("spell-check").setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();
        if (currentUserDTO.isCanCastSpell()) {
            return spellService.castSpell(currentUserDTO);
        }
        else{
            return troopService.bestMove(currentUserDTO);
        }
    }

    public ResultRules getTroopStrength(CurrentUserDTO currentUserDTO) {
        ResultRules resultRules = new ResultRules();
        UserTroopRule ourTroops = troopService.createUserTroopRule(currentUserDTO, true);
        UserTroopRule enemyTroops = troopService.createUserTroopRule(currentUserDTO, false);

        KieSession kieSession = kieContainer.getKieBase("forwardKbase").newKieSession();
        kieSession.insert(resultRules);
        kieSession.insert(ourTroops);
        kieSession.insert(enemyTroops);
        kieSession.getAgenda().getAgendaGroup("troop-strength").setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();

        return resultRules;
    }
}