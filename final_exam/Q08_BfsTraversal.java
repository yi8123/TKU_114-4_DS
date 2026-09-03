import java.util.*;

public class Q08_BfsTraversal {
    public static List<String> bfs(Map<String, List<String>> graph, String start) {
        if (graph == null || start == null || !graph.containsKey(start)) {
            return new ArrayList<>();
        }

        List<String> order = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();

        visited.add(start);
        queue.offer(start);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            order.add(curr);

            List<String> neighbors = graph.get(curr);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (neighbor != null && visited.add(neighbor)) {
                        queue.offer(neighbor);
                    }
                }
            }
        }
        return order;
    }

    public static Map<String, Integer> distanceFrom(Map<String, List<String>> graph, String start) {
        if (graph == null || start == null || !graph.containsKey(start)) {
            return new HashMap<>();
        }

        Map<String, Integer> dist = new HashMap<>();
        Queue<String> queue = new ArrayDeque<>();

        dist.put(start, 0);
        queue.offer(start);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            int currentDist = dist.get(curr);

            List<String> neighbors = graph.get(curr);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (neighbor != null && !dist.containsKey(neighbor)) {
                        dist.put(neighbor, currentDist + 1);
                        queue.offer(neighbor);
                    }
                }
            }
        }
        return dist;
    }
}