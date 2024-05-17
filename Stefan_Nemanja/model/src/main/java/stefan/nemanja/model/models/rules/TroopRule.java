package stefan.nemanja.model.models.rules;

import stefan.nemanja.model.models.Spell;
import stefan.nemanja.model.models.Troop;

import java.util.List;

public class TroopRule {
    private List<Troop> troops;
    private boolean isFriendly;

    public TroopRule(List<Troop> troops, boolean isFriendly) {
        this.troops = troops;
        this.isFriendly = isFriendly;
    }

    public TroopRule() {
    }

    public List<Troop> getTroops() {
        return troops;
    }

    public void setTroops(List<Troop> troops) {
        this.troops = troops;
    }

    public boolean getIsFriendly() {
        return isFriendly;
    }

    public void setFriendly(boolean friendly) {
        isFriendly = friendly;
    }

    public Troop getStrongestTroopWithVulnerabilityList(List<Spell> spellList){
        System.out.println(spellList.toString());
        Troop strongestTroop = null;
        for (Troop troop : troops) {
            for (Spell spell : spellList){
                if (troop.getVulnerabilities() != null && troop.getVulnerabilities().contains(spell.getName()) && (strongestTroop == null || troop.getHealthPoints() > strongestTroop.getHealthPoints())) {
                    strongestTroop = troop;
                    break;
                }
            }
        }
        System.out.println(strongestTroop.toString());
        return strongestTroop;
    }
}
