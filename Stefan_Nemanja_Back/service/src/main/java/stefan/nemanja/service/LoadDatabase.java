package stefan.nemanja.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import stefan.nemanja.model.models.MeleeTroop;
import stefan.nemanja.model.models.RangedTroop;
import stefan.nemanja.model.models.Spell;
import stefan.nemanja.model.models.Troop;
import stefan.nemanja.service.repositories.SpellRepository;
import stefan.nemanja.service.repositories.TroopRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(SpellRepository spellRepository, TroopRepository troopRepository) {
        return args -> {
            loadSpells(spellRepository);
            loadTroops(troopRepository);
        };
    }

    private void loadSpells(SpellRepository repository) throws IOException {
        List<Spell> spells = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource("spells.csv").getInputStream()));
        reader.readLine();
        String line;

        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            String name = fields[0].trim();
            EnumSet<Spell.School> school = EnumSet.noneOf(Spell.School.class);
            for (String s : fields[1].trim().replace("\"", "").split("/")) {
                school.add(Spell.School.valueOf(s.trim()));
            }
            int level = Integer.parseInt(fields[2].trim());
            int cost = Integer.parseInt(fields[3].trim());
            int damage = Integer.parseInt(fields[4].trim());

            spells.add(new Spell(null, name, school, level, cost, damage));
        }

        repository.saveAll(spells);
    }

    private void loadTroops(TroopRepository repository) throws IOException {
        List<Troop> troops = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource("troops.csv").getInputStream()));
        reader.readLine();
        String line;

        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            String name = fields[0].trim();
            Troop.TroopType type = Troop.TroopType.valueOf(fields[1].trim().toUpperCase());
            Troop.City city = Troop.City.valueOf(fields[2].trim().toUpperCase());
            int healthPoints = Integer.parseInt(fields[3].trim());
            int minDmg = Integer.parseInt(fields[4].trim());
            int maxDmg = Integer.parseInt(fields[5].trim());
            int speed = Integer.parseInt(fields[6].trim());
            int size = Integer.parseInt(fields[7].trim());
            String vulnerabilities = fields[9].trim();
            float fightValue = Float.parseFloat(fields[10].trim());

            Troop troop;
            if (type == Troop.TroopType.MELEE) {
                troop = new MeleeTroop(null, name, type, city, healthPoints, minDmg, maxDmg, speed, size, vulnerabilities, fightValue);
            } else {
                int ammo = Integer.parseInt(fields[8].trim());
                troop = new RangedTroop(null, name, type, city, healthPoints, minDmg, maxDmg, speed, size, vulnerabilities, fightValue, ammo);
            }

            troops.add(troop);
        }

        repository.saveAll(troops);
    }
}