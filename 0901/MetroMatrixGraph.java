import java.util.*;

public class MetroMatrixGraph {
    private final List<String> stations;
    private final Map<String, Integer> stationIndexMap;
    private final int[][] adjMatrix;
    private int edgeCount;

    public MetroMatrixGraph(List<String> stations) {
        this.stations = new ArrayList<>(stations);
        this.stationIndexMap = new HashMap<>();
        int n = stations.size();
        this.adjMatrix = new int[n][n];
        this.edgeCount = 0;

        for (int i = 0; i < n; i++) {
            stationIndexMap.put(stations.get(i), i);
        }
    }

    public boolean addTrack(String u, String v) {
        if (!stationIndexMap.containsKey(u) || !stationIndexMap.containsKey(v) || u.equals(v)) {
            return false;
        }
        int i = stationIndexMap.get(u);
        int j = stationIndexMap.get(v);

        if (adjMatrix[i][j] == 1) {
            return false;
        }

        adjMatrix[i][j] = 1;
        adjMatrix[j][i] = 1;
        edgeCount++;
        return true;
    }

    public List<String> getNeighbors(String station) {
        if (!stationIndexMap.containsKey(station)) return Collections.emptyList();
        int idx = stationIndexMap.get(station);
        List<String> neighbors = new ArrayList<>();
        for (int j = 0; j < stations.size(); j++) {
            if (adjMatrix[idx][j] == 1) {
                neighbors.add(stations.get(j));
            }
        }
        return neighbors;
    }

    public int getDegree(String station) {
        return getNeighbors(station).size();
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public void printMatrixReport() {
        System.out.println("=== Metro Adjacency Matrix Report ===");
        System.out.printf("Total Stations: %d, Total Track Segments (Edges): %d%n%n", stations.size(), edgeCount);

        System.out.printf("%-15s", "");
        for (String s : stations) {
            System.out.printf("%-10s", s);
        }
        System.out.println();

        for (int i = 0; i < stations.size(); i++) {
            System.out.printf("%-15s", stations.get(i));
            for (int j = 0; j < stations.size(); j++) {
                System.out.printf("%-10d", adjMatrix[i][j]);
            }
            System.out.printf(" (Degree: %d)%n", getDegree(stations.get(i)));
        }
    }
}