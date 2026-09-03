import java.util.*;

public class BfsLayerReport {
    public static Map<String, Integer> calculateLayers(Map<String, List<String>> graph, String start) {
        Map<String, Integer> distances = new LinkedHashMap<>();
        if (graph == null || start == null || !graph.containsKey(start)) {
            return distances;
        }

        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();

        queue.offer(start);
        visited.add(start);
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            int currDist = distances.get(curr);

            List<String> neighbors = graph.getOrDefault(curr, Collections.emptyList());
            for (String neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    distances.put(neighbor, currDist + 1);
                    queue.offer(neighbor);
                }
            }
        }
        return distances;
    }

    public static void main(String[] args) {
        Map<String, List<String>> g = new HashMap<>();
        g.put("A", Arrays.asList("B", "C"));
        g.put("B", Arrays.asList("A", "D", "E"));
        g.put("C", Arrays.asList("A", "F"));
        g.put("D", Collections.singletonList("B"));
        g.put("E", Collections.singletonList("B"));
        g.put("F", Collections.singletonList("C"));
        System.out.println("Normal Case: " + calculateLayers(g, "A"));
        System.out.println("Missing Start: " + calculateLayers(g, "Z"));
        System.out.println("Empty Graph: " + calculateLayers(Collections.emptyMap(), "A"));
    }
}