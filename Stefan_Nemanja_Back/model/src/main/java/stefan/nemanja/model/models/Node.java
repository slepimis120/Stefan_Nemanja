package stefan.nemanja.model.models;

import org.kie.api.definition.type.Position;

import java.util.ArrayList;
import java.util.List;

public class Node {
    @Position(0)
    private int x;

    @Position(1)
    private int y;
    private List<Node> children;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.children = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public void removeChild(Node child) {
        children.remove(child);
    }
}
