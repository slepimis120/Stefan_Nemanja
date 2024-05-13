package stefan.nemanja.model.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MELEEUNIT")
public class MeleeUnit extends Unit
{
    public MeleeUnit(Long id, String name, TroopType type, int healthPoints, int attackRange, int speed, float fightValue) {
        super(id, name, type, healthPoints, attackRange, speed, fightValue);
    }

    public MeleeUnit(Long id){
        super(id);
    }

    public MeleeUnit(){

    }

}
