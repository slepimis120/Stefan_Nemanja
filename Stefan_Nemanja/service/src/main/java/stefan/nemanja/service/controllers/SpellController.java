package stefan.nemanja.service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stefan.nemanja.model.models.Spell;
import stefan.nemanja.model.models.dto.CurrentUserDTO;
import stefan.nemanja.model.models.rules.ResultRules;
import stefan.nemanja.service.services.SpellService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/spell")
public class SpellController {
    private final SpellService spellService;

    public SpellController(SpellService spellService) {
        this.spellService = spellService;
    }

    @PostMapping("/castspell")
    public ResponseEntity<ResultRules> castSpell(@RequestBody CurrentUserDTO currentUserDTO) {
        ResultRules recommendedSpell = spellService.getRecommendedSpell(currentUserDTO);
        return ResponseEntity.ok(recommendedSpell);
    }
}
