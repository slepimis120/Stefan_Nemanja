package stefan.nemanja.model.models;

import jakarta.persistence.*;

import java.util.EnumSet;
import java.util.Set;

@Entity
public class Spell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ElementCollection(targetClass = School.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "spell_school")
    @Column(nullable = false, name = "school")
    private Set<School> school;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private int cost;

    @Column(nullable = false)
    private int damage;

    public enum School {
        AIR, EARTH, FIRE, WATER
    }

    public Spell() {
    }

    public Spell(Long id, String name, Set<School> school, int level, int cost, int damage) {
        this.id = id;
        this.name = name;
        this.school = school;
        this.level = level;
        this.cost = cost;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<School> getSchool() {
        return school;
    }

    public void setSchool(Set<School> school) {
        this.school = school;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
