package stefan.nemanja.service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stefan.nemanja.model.models.dto.CurrentUserDTO;
import stefan.nemanja.model.models.rules.ResultRules;
import stefan.nemanja.service.services.TroopService;

@RestController
@RequestMapping("/troop")
public class TroopController {
    private final TroopService troopService;

    public TroopController(TroopService troopService) {
        this.troopService = troopService;
    }

    @PostMapping("/bestMove")
    public ResponseEntity<ResultRules> nextMove(@RequestBody CurrentUserDTO currentUserDTO) {
        ResultRules troopMovement = troopService.bestMove(currentUserDTO);
        return ResponseEntity.ok(troopMovement);
    }
}
