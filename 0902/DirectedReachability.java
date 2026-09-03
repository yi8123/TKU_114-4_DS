import java.util.*;

public class DirectedReachability {
    private final Map<String, List<String>> adjList = new HashMap<>();

    public void addEdge(String u, String v) {
        adjList.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
        adjList.putIfAbsent(v, new ArrayList<>());
    }

    public boolean isReachable(String from, String to) {
        if (!adjList.containsKey(from) || !adjList.containsKey(to)) return false;
        if (from.equals(to)) return true;

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(from);
        visited.add(from);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            if (curr.equals(to)) return true;

            for (String neighbor : adjList.getOrDefault(curr, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        DirectedReachability dr = new DirectedReachability();
        dr.addEdge("A", "B");
        dr.addEdge("B", "C");
        dr.addEdge("C", "D");
        dr.addEdge("D", "B");

        System.out.println("A -> D: " + dr.isReachable("A", "D"));
        System.out.println("D -> A: " + dr.isReachable("D", "A"));
        System.out.println("A -> A: " + dr.isReachable("A", "A"));
        System.out.println("A -> Z: " + dr.isReachable("A", "Z"));
    }
}
