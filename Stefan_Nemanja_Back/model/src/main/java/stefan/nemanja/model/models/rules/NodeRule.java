package stefan.nemanja.model.models.rules;

import org.kie.api.definition.type.Position;

public class NodeRule {
    @Position(0)
    private int i;

    @Position(1)
    private int j;

    @Position(2)
    private int childI;

    @Position(3)
    private int childJ;

    public NodeRule(int i, int j, int childI, int childJ) {
        this.i = i;
        this.j = j;
        this.childI = childI;
        this.childJ = childJ;
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

    public int getChildI() {
        return childI;
    }

    public void setChildI(int childI) {
        this.childI = childI;
    }

    public int getChildJ() {
        return childJ;
    }

    public void setChildJ(int childJ) {
        this.childJ = childJ;
    }

    @Override
    public int hashCode() {
        int result = i;
        result = 31 * result + j;
        result = 31 * result + childI;
        result = 31 * result + childJ;
        return result;
    }

    @Override
    public String toString() {
        return "NodeRule{" +
                "i=" + i +
                ", j=" + j +
                ", childI=" + childI +
                ", childJ=" + childJ +
                '}';
    }
}