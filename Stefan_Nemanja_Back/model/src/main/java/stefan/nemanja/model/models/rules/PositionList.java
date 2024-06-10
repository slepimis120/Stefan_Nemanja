package stefan.nemanja.model.models.rules;

import java.util.List;

public class PositionList {
    private List<Position> positions;

    public PositionList(List<Position> positions) {
        this.positions = positions;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }
}