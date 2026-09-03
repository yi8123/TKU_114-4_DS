import java.util.*;

public class Q06_AdjacencyMatrixGraph {
    private final List<String> vertices;
    private final Map<String, Integer> indexMap;
    private final boolean[][] matrix;

    public Q06_AdjacencyMatrixGraph(List<String> vertices) {
        this.vertices = new ArrayList<>();
        this.indexMap = new HashMap<>();
        if (vertices != null) {
            for (String v : vertices) {
                if (v != null && !indexMap.containsKey(v)) {
                    indexMap.put(v, this.vertices.size());
                    this.vertices.add(v);
                }
            }
        }
        int n = this.vertices.size();
        this.matrix = new boolean[n][n];
    }

    public boolean addEdge(String first, String second) {
        if (first == null || second == null || first.equals(second)) {
            return false;
        }
        Integer i = indexMap.get(first);
        Integer j = indexMap.get(second);
        if (i == null || j == null || matrix[i][j]) {
            return false;
        }
        matrix[i][j] = true;
        matrix[j][i] = true;
        return true;
    }

    public boolean removeEdge(String first, String second) {
        if (first == null || second == null) {
            return false;
        }
        Integer i = indexMap.get(first);
        Integer j = indexMap.get(second);
        if (i == null || j == null || !matrix[i][j]) {
            return false;
        }
        matrix[i][j] = false;
        matrix[j][i] = false;
        return true;
    }

    public boolean hasEdge(String first, String second) {
        if (first == null || second == null) {
            return false;
        }
        Integer i = indexMap.get(first);
        Integer j = indexMap.get(second);
        if (i == null || j == null) {
            return false;
        }
        return matrix[i][j];
    }

    public int degree(String vertex) {
        if (vertex == null) return 0;
        Integer i = indexMap.get(vertex);
        if (i == null) return 0;

        int deg = 0;
        for (int j = 0; j < vertices.size(); j++) {
            if (matrix[i][j]) {
                deg++;
            }
        }
        return deg;
    }

    public List<String> neighbors(String vertex) {
        if (vertex == null) return new ArrayList<>();
        Integer i = indexMap.get(vertex);
        if (i == null) return new ArrayList<>();

        List<String> res = new ArrayList<>();
        for (int j = 0; j < vertices.size(); j++) {
            if (matrix[i][j]) {
                res.add(vertices.get(j));
            }
        }
        return res;
    }
}
