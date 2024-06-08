package stefan.nemanja.service.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import stefan.nemanja.model.models.Troop;
import stefan.nemanja.service.services.TroopService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/troop")
public class TroopController {
    private final TroopService troopService;


    public TroopController(TroopService troopService) {
        this.troopService = troopService;
    }

    @GetMapping("/allTroops")
    public ResponseEntity<List<Troop>> getAllTroops() {
        return ResponseEntity.ok(troopService.getAllTroops());
    }
}
