package stefan.nemanja.model.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "MELEETROOPS")
public class MeleeTroop extends Troop
{
    public MeleeTroop(Long id, String name, TroopType type, City city, int healthPoints, int minDmg, int maxDmg, int speed, int size, String vulnerabilities, float fightValue) {
        super(id, name, type, city, healthPoints, minDmg, maxDmg, speed, size, vulnerabilities, fightValue);
    }

    public MeleeTroop(){

    }

}
