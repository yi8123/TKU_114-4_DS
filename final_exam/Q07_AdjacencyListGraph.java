import java.util.*;

public class Q07_AdjacencyListGraph {
    private final Map<String, LinkedHashSet<String>> adj = new HashMap<>();
    private int edgeTotal = 0;

    public boolean addVertex(String vertex) {
        if (vertex == null || adj.containsKey(vertex)) {
            return false;
        }
        adj.put(vertex, new LinkedHashSet<>());
        return true;
    }

    public boolean addEdge(String from, String to) {
        if (from == null || to == null || from.equals(to)) {
            return false;
        }
        if (!adj.containsKey(from) || !adj.containsKey(to)) {
            return false;
        }
        if (adj.get(from).add(to)) {
            edgeTotal++;
            return true;
        }
        return false;
    }

    public boolean removeEdge(String from, String to) {
        if (from == null || to == null) {
            return false;
        }
        LinkedHashSet<String> neighbors = adj.get(from);
        if (neighbors != null && neighbors.remove(to)) {
            edgeTotal--;
            return true;
        }
        return false;
    }

    public List<String> outgoing(String vertex) {
        if (vertex == null || !adj.containsKey(vertex)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(adj.get(vertex));
    }

    public int inDegree(String vertex) {
        if (vertex == null || !adj.containsKey(vertex)) {
            return 0;
        }
        int inDeg = 0;
        for (LinkedHashSet<String> targets : adj.values()) {
            if (targets.contains(vertex)) {
                inDeg++;
            }
        }
        return inDeg;
    }

    public int edgeCount() {
        return edgeTotal;
    }
}