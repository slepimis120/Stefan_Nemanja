package stefan.nemanja.service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stefan.nemanja.model.models.dto.CurrentUserDTO;
import stefan.nemanja.model.models.rules.ResultRules;
import stefan.nemanja.service.services.GameService;

@RestController
@RequestMapping("/strategy")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/bestMove")
    public ResponseEntity<ResultRules> nextMove(@RequestBody CurrentUserDTO currentUserDTO) {
        return ResponseEntity.ok(gameService.getBestMove(currentUserDTO));
    }

    @PostMapping("/troopStrength")
    public ResponseEntity<ResultRules> troopStrength(@RequestBody CurrentUserDTO currentUserDTO) {
        return ResponseEntity.ok(gameService.getTroopStrength(currentUserDTO));
    }
}
