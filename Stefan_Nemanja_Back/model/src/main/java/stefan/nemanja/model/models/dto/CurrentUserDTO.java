package stefan.nemanja.model.models.dto;

import java.util.List;

public class CurrentUserDTO {
    private List<UnitDTO> ourTroops;
    private List<UnitDTO> enemyTroops;
    private List<Long> availableSpells;
    private TerrainDTO terrain;
    private Integer spellPoints;
    private boolean usedSpell;
    private boolean canCastSpell;

    public Integer getSpellPoints() {
        return spellPoints;
    }

    public void setSpellPoints(Integer spellPoints) {
        this.spellPoints = spellPoints;
    }

    public List<Long> getAvailableSpells() {
        return availableSpells;
    }

    public void setAvailableSpells(List<Long> availableSpells) {
        this.availableSpells = availableSpells;
    }

    public boolean isUsedSpell() {
        return usedSpell;
    }

    public void setUsedSpell(boolean usedSpell) {
        this.usedSpell = usedSpell;
    }

    public boolean isCanCastSpell() {
        return canCastSpell;
    }

    public void setCanCastSpell(boolean canCastSpell) {
        this.canCastSpell = canCastSpell;
    }

    public List<UnitDTO> getEnemyTroops() {
        return enemyTroops;
    }

    public void setEnemyTroops(List<UnitDTO> enemyTroops) {
        this.enemyTroops = enemyTroops;
    }

    public List<UnitDTO> getOurTroops() {
        return ourTroops;
    }

    public void setOurTroops(List<UnitDTO> ourTroops) {
        this.ourTroops = ourTroops;
    }

    public TerrainDTO getTerrain() {
        return terrain;
    }

    public void setTerrain(TerrainDTO terrain) {
        this.terrain = terrain;
    }
}
