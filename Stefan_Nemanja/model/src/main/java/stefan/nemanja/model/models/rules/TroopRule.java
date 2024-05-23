package stefan.nemanja.model.models.rules;

import stefan.nemanja.model.models.Troop;
import stefan.nemanja.model.models.dto.UnitDTO;

public class TroopRule {
    private Long id;
    private String name;
    private Troop.TroopType type;
    private Troop.City city;
    private int healthPoints;
    private int minDmg;
    private int maxDmg;
    private int speed;
    private int size;
    private String vulnerabilities;
    private float fightValue;
    private int iPosition;
    private int jPosition;
    private boolean onMove = false;

    public TroopRule() {
    }

    public TroopRule(Long id, String name, Troop.TroopType type, Troop.City city, int healthPoints, int minDmg, int maxDmg, int speed, int size, String vulnerabilities, float fightValue, int iPosition, int jPosition, boolean onMove) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.city = city;
        this.healthPoints = healthPoints;
        this.minDmg = minDmg;
        this.maxDmg = maxDmg;
        this.speed = speed;
        this.size = size;
        this.vulnerabilities = vulnerabilities;
        this.fightValue = fightValue;
        this.iPosition = iPosition;
        this.jPosition = jPosition;
        this.onMove = onMove;
    }

    public TroopRule(UnitDTO unitDTO, Troop troop) {
        this.id = unitDTO.getId();
        this.name = troop.getName();
        this.type = troop.getType();
        this.city = troop.getCity();
        this.healthPoints = unitDTO.getHealth();
        this.minDmg = troop.getMinDmg();
        this.maxDmg = troop.getMaxDmg();
        this.speed = troop.getSpeed();
        this.size = troop.getSize();
        this.vulnerabilities = troop.getVulnerabilities();
        this.fightValue = troop.getFightValue();
        this.iPosition = unitDTO.getiPosition();
        this.jPosition = unitDTO.getjPosition();
        this.onMove = unitDTO.isOnMove();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Troop.TroopType getType() {
        return type;
    }

    public void setType(Troop.TroopType type) {
        this.type = type;
    }

    public Troop.City getCity() {
        return city;
    }

    public void setCity(Troop.City city) {
        this.city = city;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getMinDmg() {
        return minDmg;
    }

    public void setMinDmg(int minDmg) {
        this.minDmg = minDmg;
    }

    public int getMaxDmg() {
        return maxDmg;
    }

    public void setMaxDmg(int maxDmg) {
        this.maxDmg = maxDmg;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(String vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }

    public float getFightValue() {
        return fightValue;
    }

    public void setFightValue(float fightValue) {
        this.fightValue = fightValue;
    }

    public int getiPosition() {
        return iPosition;
    }

    public void setiPosition(int iPosition) {
        this.iPosition = iPosition;
    }

    public int getjPosition() {
        return jPosition;
    }

    public void setjPosition(int jPosition) {
        this.jPosition = jPosition;
    }

    public boolean isOnMove() {
        return onMove;
    }

    public void setOnMove(boolean onMove) {
        this.onMove = onMove;
    }
}
