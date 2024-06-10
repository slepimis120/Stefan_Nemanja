package stefan.nemanja.model.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Terrain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private TerrainType terrain_type;

    public enum TerrainType {
        GRASS,
        DIRT,
        LAVA,
        SUBTERRANEAN,
        ROUGH,
        SAND,
        SNOW,
        SWAMP
    }

    public Terrain(Long id, TerrainType terrain_type) {
        this.id = id;
        this.terrain_type = terrain_type;
    }

    public Terrain() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TerrainType getTerrain_type() {
        return terrain_type;
    }

    public void setTerrain_type(TerrainType terrain_type) {
        this.terrain_type = terrain_type;
    }
}
