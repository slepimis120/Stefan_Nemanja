package stefan.nemanja.service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stefan.nemanja.model.models.Spell;
import stefan.nemanja.service.services.SpellService;

import java.util.List;

@RestController
@RequestMapping("/spell")
public class SpellController {
    private final SpellService spellService;


    public SpellController(SpellService spellService) {
        this.spellService = spellService;
    }

    @GetMapping("/allSpells")
    public ResponseEntity<List<Spell>> getAllSpells() {
        return ResponseEntity.ok(spellService.getAllSpells());
    }
}