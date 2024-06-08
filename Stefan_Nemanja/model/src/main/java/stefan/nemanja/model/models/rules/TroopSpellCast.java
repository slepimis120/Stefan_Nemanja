package stefan.nemanja.model.models.rules;

import stefan.nemanja.model.models.Spell;
import stefan.nemanja.model.models.Troop;

public class TroopSpellCast {
    private String troopName;
    private int troopHealthPoints;
    private String spellName;
    private int spellDmg;

    public TroopSpellCast(String troopName, int troopHealthPoints, String spellName, int SpellDmg) {
        this.troopName = troopName;
        this.troopHealthPoints = troopHealthPoints;
        this.spellName = spellName;
        this.spellDmg = SpellDmg;
    }

    public TroopSpellCast() {
    }

    public TroopSpellCast(Troop troop, Spell spell) {
        this.troopName = troop.getName();
        this.troopHealthPoints = troop.getHealthPoints();
        this.spellName = spell.getName();
        this.spellDmg = spell.getDamage();
    }

    public String getTroopName() {
        return troopName;
    }

    public void setTroopName(String troopName) {
        this.troopName = troopName;
    }

    public int getTroopHealthPoints() {
        return troopHealthPoints;
    }

    public void setTroopHealthPoints(int troopHealthPoints) {
        this.troopHealthPoints = troopHealthPoints;
    }

    public String getSpellName() {
        return spellName;
    }

    public void setSpellName(String spellName) {
        this.spellName = spellName;
    }

    public int getSpellDmg() {
        return spellDmg;
    }

    public void setSpellDmg(int spellDmg) {
        this.spellDmg = spellDmg;
    }
}
