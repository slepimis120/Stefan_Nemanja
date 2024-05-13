package stefan.nemanja.model.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RANGEDUNIT")
public class RangedUnit extends Unit{
    @Column(nullable = false)
    private int ammo;

    public RangedUnit(Long id, String name, TroopType type, int healthPoints, int attackRange, int speed, float fightValue, int ammo) {
        super(id, name, type, healthPoints, attackRange, speed, fightValue);
        this.ammo = ammo;
    }

    public RangedUnit(Long id){
        super(id);
    }

    public RangedUnit(){

    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
}
