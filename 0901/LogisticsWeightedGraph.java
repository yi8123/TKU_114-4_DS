import java.util.*;

public class LogisticsWeightedGraph {
    public static class Edge {
        private final String destination;
        private double weight;

        public Edge(String destination, double weight) {
            this.destination = destination;
            this.weight = weight;
        }

        public String getDestination() { return destination; }
        public double getWeight() { return weight; }
        public void setWeight(double weight) { this.weight = weight; }

        @Override
        public String toString() {
            return String.format("%s (Cost: %.2f)", destination, weight);
        }
    }

    private final Map<String, Map<String, Edge>> adjList;

    public LogisticsWeightedGraph() {
        this.adjList = new HashMap<>();
    }

    public boolean addLocation(String location) {
        if (adjList.containsKey(location)) return false;
        adjList.put(location, new HashMap<>());
        return true;
    }

    public boolean addOrUpdateRoute(String from, String to, double cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Logistic cost cannot be negative: " + cost);
        }
        if (!adjList.containsKey(from) || !adjList.containsKey(to)) {
            return false;
        }

        Map<String, Edge> outgoing = adjList.get(from);
        if (outgoing.containsKey(to)) {
            outgoing.get(to).setWeight(cost);
        } else {
            outgoing.put(to, new Edge(to, cost));
        }
        return true;
    }

    public boolean removeRoute(String from, String to) {
        if (!adjList.containsKey(from) || !adjList.containsKey(to)) {
            return false;
        }
        return adjList.get(from).remove(to) != null;
    }

    public Double getCost(String from, String to) {
        if (!adjList.containsKey(from) || !adjList.containsKey(to)) {
            return null;
        }
        Edge edge = adjList.get(from).get(to);
        return edge != null ? edge.getWeight() : null;
    }

    public List<Edge> getOutgoingRoutes(String location) {
        if (!adjList.containsKey(location)) return Collections.emptyList();
        return new ArrayList<>(adjList.get(location).values());
    }

    public void printNetwork() {
        List<String> nodes = new ArrayList<>(adjList.keySet());
        Collections.sort(nodes);

        System.out.println("=== Logistics Cost Network ===");
        for (String node : nodes) {
            System.out.printf("Location [%s] -> %s%n", node, getOutgoingRoutes(node));
        }
    }
}