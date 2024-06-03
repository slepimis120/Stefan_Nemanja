package stefan.nemanja.model.models.dto;

public class UnitDTO {
    private Long id;
    private int troopCount;
    private int health;
    private int iPosition;
    private int jPosition;
    private boolean hasWaited = true;
    private boolean onMove = false;

    public UnitDTO() {
    }

    public UnitDTO(Long id, int troopCount, int health, int iPosition, int jPosition, boolean hasWaited, boolean onMove) {
        this.id = id;
        this.troopCount = troopCount;
        this.health = health;
        this.iPosition = iPosition;
        this.jPosition = jPosition;
        this.hasWaited = hasWaited;
        this.onMove = onMove;
    }

    public int getTroopCount() {
        return troopCount;
    }

    public void setTroopCount(int troopCount) {
        this.troopCount = troopCount;
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

    public boolean isHasWaited() {
        return hasWaited;
    }

    public void setHasWaited(boolean hasWaited) {
        this.hasWaited = hasWaited;
    }

    public boolean isOnMove() {
        return onMove;
    }

    public void setOnMove(boolean onMove) {
        this.onMove = onMove;
    }
}