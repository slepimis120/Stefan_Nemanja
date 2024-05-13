package stefan.nemanja.model.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int spellPoints;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Spell> spells;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Troop> troops;

    public Hero(Integer id, String name, int spellPoints, List<Spell> spells, List<Troop> troops) {
        this.id = id;
        this.name = name;
        this.spellPoints = spellPoints;
        this.spells = spells;
        this.troops = troops;
    }

    public Hero() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpellPoints() {
        return spellPoints;
    }

    public void setSpellPoints(int spellPoints) {
        this.spellPoints = spellPoints;
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public void setSpells(List<Spell> spells) {
        this.spells = spells;
    }

    public List<Troop> getTroops() {
        return troops;
    }

    public void setTroops(List<Troop> troops) {
        this.troops = troops;
    }
}
