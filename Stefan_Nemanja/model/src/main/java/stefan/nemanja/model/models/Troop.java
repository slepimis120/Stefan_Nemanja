package stefan.nemanja.model.models;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "TROOPTYPE")
public abstract class Troop {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TroopType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private City city;

    @Column(nullable = false)
    private int healthPoints;

    @Column(nullable = false)
    private int minDmg;

    @Column(nullable = false)
    private int maxDmg;

    @Column(nullable = false)
    private int speed;

    @Column(nullable = false)
    private int size;

    @Column(nullable = true)
    private String vulnerabilities;

    @Column(nullable = false)
    private float fightValue;

    public enum TroopType {
        MELEE, RANGED
    }

    public enum City {
        CASTLE,
        RAMPART,
        TOWER,
        CONFLUX,
        NECROPOLIS,
        FORTRESS,
        DUNGEON,
        STRONGHOLD,
        INFERNO,
        NEUTRAL
    }

    public Troop(Long id, String name, TroopType type, City city, int healthPoints, int minDmg, int maxDmg, int speed, int size, String vulnerabilities, float fightValue) {
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
    }

    public Troop(){

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

    public TroopType getType() {
        return type;
    }

    public void setType(TroopType type) {
        this.type = type;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
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

    public float getFightValue() {
        return fightValue;
    }

    public void setFightValue(float fightValue) {
        this.fightValue = fightValue;
    }

    public String getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(String vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }
}