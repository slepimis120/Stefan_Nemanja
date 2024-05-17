package stefan.nemanja.model.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "RANGEDTROOPS")
public class RangedTroop extends Troop {
    @Column(nullable = false)
    private int ammo;

    public RangedTroop(Long id, String name, TroopType type, City city, int healthPoints, int minDmg, int maxDmg, int speed, int size, String vulnerabilities, float fightValue, int ammo) {
        super(id, name, type, city, healthPoints, minDmg, maxDmg, speed, size, vulnerabilities, fightValue);
        this.ammo = ammo;
    }

    public RangedTroop(){

    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
}
