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

    public TroopSpellCast getStrongestTroopWithVulnerabilityList(List<Spell> spellList){
        TroopSpellCast strongestTroop = null;
        for (TroopRule troop : troops) {
            for (Spell spell : spellList){
                if (troop.getVulnerabilities() != null && troop.getVulnerabilities().contains(spell.getName()) && (strongestTroop == null || troop.getTotalHealthPoints() > strongestTroop.getTroopHealthPoints()) && (strongestTroop == null || strongestTroop.getSpellDmg() < spell.getDamage())) {
                    if(strongestTroop == null){
                        strongestTroop = new TroopSpellCast();
                    }
                    strongestTroop.setTroopName(troop.getName());
                    strongestTroop.setTroopHealthPoints(troop.getTotalHealthPoints());
                    strongestTroop.setSpellName(spell.getName());
                    strongestTroop.setSpellDmg(spell.getDamage());
                    break;
                }
            }
        }
        return strongestTroop;
    }
}
