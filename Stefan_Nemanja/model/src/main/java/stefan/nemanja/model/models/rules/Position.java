package stefan.nemanja.model.models.rules;

public class Position {
    private int i;
    private int j;
    private int depth;

    public Position() {
    }

    public Position(int i, int j, int depth) {
        this.i = i;
        this.j = j;
        this.depth = depth;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
