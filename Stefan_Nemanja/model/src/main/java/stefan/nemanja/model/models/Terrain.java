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

    @ElementCollection
    @CollectionTable(name = "unavailable_tiles", joinColumns = @JoinColumn(name = "terrain_id"))
    @Column(name = "coordinate")
    private List<Tile> unavailable_tiles;

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

    public Terrain(Long id, TerrainType terrain_type, List<Tile> unavailable_tiles) {
        this.id = id;
        this.terrain_type = terrain_type;
        this.unavailable_tiles = unavailable_tiles;
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

    public List<Tile> getUnavailable_tiles() {
        return unavailable_tiles;
    }

    public void setUnavailable_tiles(List<Tile> unavailable_tiles) {
        this.unavailable_tiles = unavailable_tiles;
    }
}
