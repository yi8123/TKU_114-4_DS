import java.util.*;

public class Q10_UnweightedShortestPath {
    public static List<String> shortestPath(Map<String, List<String>> graph, String start, String target) {
        if (graph == null || start == null || target == null) {
            return new ArrayList<>();
        }
        if (!graph.containsKey(start) || !graph.containsKey(target)) {
            return new ArrayList<>();
        }
        if (start.equals(target)) {
            List<String> path = new ArrayList<>();
            path.add(start);
            return path;
        }

        Map<String, String> predecessor = new HashMap<>();
        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();

        queue.offer(start);
        visited.add(start);

        boolean found = false;
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            if (curr.equals(target)) {
                found = true;
                break;
            }

            List<String> neighbors = graph.get(curr);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (neighbor != null && visited.add(neighbor)) {
                        predecessor.put(neighbor, curr);
                        queue.offer(neighbor);
                    }
                }
            }
        }

        if (!found) {
            return new ArrayList<>();
        }

        LinkedList<String> path = new LinkedList<>();
        String step = target;
        while (step != null) {
            path.addFirst(step);
            step = predecessor.get(step);
        }
        return path;
    }
}