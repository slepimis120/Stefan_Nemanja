package stefan.nemanja.model.models;

import jakarta.persistence.*;

@Entity
public class Spell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int spellCost;

    @Column(nullable = false)
    private int damage;

    public Spell() {
    }

    public Spell(Long id, String name, int spellCost, int damage) {

        this.id = id;
        this.name = name;
        this.spellCost = spellCost;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpellCost() {
        return spellCost;
    }

    public void setSpellCost(int spellCost) {
        this.spellCost = spellCost;
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
}
