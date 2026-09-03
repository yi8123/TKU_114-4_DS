import java.util.*;

public class CampusMatrixGraph {
    private final Map<String, Integer> vertexToIndex;
    private final List<String> indexToVertex;
    private int[][] adjMatrix;
    private int edgeCount;

    public CampusMatrixGraph(List<String> vertices) {
        int n = vertices.size();
        this.vertexToIndex = new HashMap<>();
        this.indexToVertex = new ArrayList<>(vertices);
        this.adjMatrix = new int[n][n];
        this.edgeCount = 0;

        for (int i = 0; i < n; i++) {
            vertexToIndex.put(vertices.get(i), i);
        }
    }

    public boolean addEdge(String u, String v) {
        if (!vertexToIndex.containsKey(u) || !vertexToIndex.containsKey(v) || u.equals(v)) {
            return false;
        }
        int i = vertexToIndex.get(u);
        int j = vertexToIndex.get(v);

        if (adjMatrix[i][j] == 1) {
            return false;
        }

        adjMatrix[i][j] = 1;
        adjMatrix[j][i] = 1;
        edgeCount++;
        return true;
    }

    public boolean removeEdge(String u, String v) {
        if (!vertexToIndex.containsKey(u) || !vertexToIndex.containsKey(v)) {
            return false;
        }
        int i = vertexToIndex.get(u);
        int j = vertexToIndex.get(v);

        if (adjMatrix[i][j] == 0) {
            return false;
        }

        adjMatrix[i][j] = 0;
        adjMatrix[j][i] = 0;
        edgeCount--;
        return true;
    }

    public int getDegree(String vertex) {
        if (!vertexToIndex.containsKey(vertex)) return -1;
        int idx = vertexToIndex.get(vertex);
        int degree = 0;
        for (int val : adjMatrix[idx]) {
            if (val == 1) degree++;
        }
        return degree;
    }

    public List<String> getNeighbors(String vertex) {
        if (!vertexToIndex.containsKey(vertex)) return Collections.emptyList();
        int idx = vertexToIndex.get(vertex);
        List<String> neighbors = new ArrayList<>();
        for (int j = 0; j < adjMatrix[idx].length; j++) {
            if (adjMatrix[idx][j] == 1) {
                neighbors.add(indexToVertex.get(j));
            }
        }
        return neighbors;
    }

    public int getEdgeCount() {
        return edgeCount;
    }
}