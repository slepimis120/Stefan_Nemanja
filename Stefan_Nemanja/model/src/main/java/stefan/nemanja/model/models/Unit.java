package stefan.nemanja.model.models;

import jakarta.persistence.*;

@Entity
@Table(name = "UNITS")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TROOPTYPE")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TroopType type;

    @Column(nullable = false)
    private int healthPoints;

    @Column(nullable = false)
    private int attackRange;

    @Column(nullable = false)
    private int speed;

    @Column(nullable = false)
    private float fightValue;

    public enum TroopType {
        MELEE, RANGED
    }

    public Unit(Long id, String name, TroopType type, int healthPoints, int attackRange, int speed, float fightValue) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.healthPoints = healthPoints;
        this.attackRange = attackRange;
        this.speed = speed;
        this.fightValue = fightValue;
    }

    public Unit(Long id) {
        this.id = id;
    }

    public Unit() {
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

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public float getFightValue() {
        return fightValue;
    }

    public void setFightValue(float fightValue) {
        this.fightValue = fightValue;
    }
}