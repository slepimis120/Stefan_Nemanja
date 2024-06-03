package stefan.nemanja.model.models.rules;

import stefan.nemanja.model.models.Spell;

import java.util.List;

public class UserTroopRule {
    private List<TroopRule> troops;
    private boolean isFriendly;

    public UserTroopRule(List<TroopRule> troops, boolean isFriendly) {
        this.troops = troops;
        this.isFriendly = isFriendly;
    }

    public UserTroopRule() {
    }

    public List<TroopRule> getTroops() {
        return troops;
    }

    public void setTroops(List<TroopRule> troops) {
        this.troops = troops;
    }

    public boolean getIsFriendly() {
        return isFriendly;
    }

    public void setFriendly(boolean friendly) {
        isFriendly = friendly;
    }

    public TroopRule getStrongestTroopWithVulnerabilityList(List<Spell> spellList){
        System.out.println(spellList.toString());
        TroopRule strongestTroop = null;
        for (TroopRule troop : troops) {
            for (Spell spell : spellList){
                if (troop.getVulnerabilities() != null && troop.getVulnerabilities().contains(spell.getName()) && (strongestTroop == null || troop.getTotalHealthPoints() > strongestTroop.getTotalHealthPoints())) {
                    strongestTroop = troop;
                    break;
                }
            }
        }
        System.out.println(strongestTroop.toString());
        return strongestTroop;
    }
}
