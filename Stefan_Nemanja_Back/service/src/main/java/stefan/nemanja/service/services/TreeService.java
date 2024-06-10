package stefan.nemanja.service.services;

import org.springframework.stereotype.Service;
import stefan.nemanja.model.models.Node;

import java.util.*;

@Service
public class TreeService {

    public Node createTree(int start_i, int start_j, int maxDepth) {
        Node root = new Node(start_i, start_j);
        Map<String, Integer> visited = new HashMap<>();
        visited.put(root.getX() + "," + root.getY(), maxDepth);
        generateChildren(root, maxDepth, visited);
        return root;
    }

    private void generateChildren(Node node, int depth, Map<String, Integer> visited) {
        if (depth == 0) {
            return;
        }
        int x = node.getX();
        int y = node.getY();
        List<Node> neighbors = new ArrayList<>();

        if (x % 2 == 0) {
            addNeighborIfValid(neighbors, x - 1, y, depth, visited);     // gornje levo
            addNeighborIfValid(neighbors, x - 1, y + 1, depth, visited); // donje levo
            addNeighborIfValid(neighbors, x + 1, y, depth, visited);     // gornje desno
            addNeighborIfValid(neighbors, x + 1, y + 1, depth, visited); // donje desno
        } else {
            addNeighborIfValid(neighbors, x - 1, y - 1, depth, visited); // gornje levo
            addNeighborIfValid(neighbors, x - 1, y, depth, visited);     // donje levo
            addNeighborIfValid(neighbors, x + 1, y - 1, depth, visited); // gornje desno
            addNeighborIfValid(neighbors, x + 1, y, depth, visited);     // donje desno
        }

        addNeighborIfValid(neighbors, x, y - 1, depth, visited); // levo
        addNeighborIfValid(neighbors, x, y + 1, depth, visited); // desno

        for (Node neighbor : neighbors) {
            String coord = neighbor.getX() + "," + neighbor.getY();
            if (!visited.containsKey(coord) || visited.get(coord) < depth) {
                if (visited.containsKey(coord)) {
                    removeNodeWithCoordinates(node, neighbor.getX(), neighbor.getY());
                }
                System.out.println("Node: " + coord + ", depth: " + depth);
                visited.put(coord, depth);  // Ažuriraj `visited` sa novom dubinom
                node.addChild(neighbor);
                generateChildren(neighbor, depth - 1, visited);
            }
        }
    }

    private void addNeighborIfValid(List<Node> neighbors, int x, int y, int depth, Map<String, Integer> visited) {
        if (x >= 0 && y >= 0 && x <= 10 && y <= 15 && (!visited.containsKey(x + "," + y) || visited.get(x + "," + y) < depth)) {
            neighbors.add(new Node(x, y));
        }
    }

    private void removeNodeWithCoordinates(Node node, int x, int y) {
        List<Node> childrenToRemove = new ArrayList<>();
        for (Node child : node.getChildren()) {
            if (child.getX() == x && child.getY() == y) {
                childrenToRemove.add(child);
            }
        }
        for (Node childToRemove : childrenToRemove) {
            node.removeChild(childToRemove);
        }
    }
}