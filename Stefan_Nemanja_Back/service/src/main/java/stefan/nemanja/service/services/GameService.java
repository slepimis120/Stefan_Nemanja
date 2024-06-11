package stefan.nemanja.service.services;

import org.drools.decisiontable.ExternalSpreadsheetCompiler;
import org.kie.api.KieBase;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.stereotype.Service;
import stefan.nemanja.model.models.Spell;
import stefan.nemanja.model.models.dto.CurrentUserDTO;
import stefan.nemanja.model.models.rules.ResultRules;
import stefan.nemanja.model.models.rules.UserTroopRule;

import java.io.InputStream;
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
        List<Spell> spells = spellService.getSpellsByIds(currentUserDTO.getAvailableSpells());

        KieSession kieSession = kieContainer.getKieBase("forwardKbase").newKieSession();
        kieSession.getAgenda().getAgendaGroup("spell-check").setFocus();
        kieSession.insert(currentUserDTO);

        for (Spell spell : spells) {
            kieSession.insert(spell);
        }

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

        InputStream template = getClass().getResourceAsStream("/rules/template/troopStrength.drt");
        InputStream data = getClass().getResourceAsStream("/rules/template/troopStrength.xls");

        ExternalSpreadsheetCompiler converter = new ExternalSpreadsheetCompiler();
        String drl = converter.compile(data, template, 2, 1);

        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);

        Results results = kieHelper.verify();

        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)){
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.println("Error: "+message.getText());
            }

            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }

        KieSession kieSession = kieHelper.build().newKieSession();
        kieSession.insert(resultRules);
        kieSession.insert(ourTroops);
        kieSession.insert(enemyTroops);
        kieSession.fireAllRules();

        return resultRules;
    }
}