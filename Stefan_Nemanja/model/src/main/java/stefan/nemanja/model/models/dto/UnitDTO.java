package stefan.nemanja.model.models.dto;

public class UnitDTO {
    private Long id;
    private int health;
    private int iPosition;
    private int jPosition;

    public UnitDTO() {
    }

    public UnitDTO(Long id, int health, int iPosition, int jPosition) {
        this.id = id;
        this.health = health;
        this.iPosition = iPosition;
        this.jPosition = jPosition;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}