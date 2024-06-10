package stefan.nemanja.model.models.dto;

public class TerrainDTO {
    private String type;

    public TerrainDTO() {
    }

    public TerrainDTO(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}