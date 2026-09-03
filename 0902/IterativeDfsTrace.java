import java.util.*;

public class IterativeDfsTrace {
    public static void traceDfs(Map<String, List<String>> graph, String start) {
        System.out.println("--- Starting DFS Trace for start: " + start + " ---");
        if (graph == null || start == null || !graph.containsKey(start)) {
            System.out.println("Graph is empty or start vertex does not exist.");
            return;
        }

        Deque<String> stack = new ArrayDeque<>();
        Set<String> visited = new LinkedHashSet<>();

        stack.push(start);
        System.out.printf("[PUSH] Stack: %-20s | Visited: %s%n", stack, visited);

        while (!stack.isEmpty()) {
            String curr = stack.pop();
            System.out.printf("[POP ] Stack: %-20s | Processing: %s%n", stack, curr);

            if (!visited.contains(curr)) {
                visited.add(curr);
                System.out.printf("[VISIT] Node: %-19s | Visited: %s%n", curr, visited);

                List<String> neighbors = graph.getOrDefault(curr, Collections.emptyList());
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    String neighbor = neighbors.get(i);
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                        System.out.printf("[PUSH] Stack: %-20s | Visited: %s%n", stack, visited);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Map<String, List<String>> g = new LinkedHashMap<>();
        g.put("A", Arrays.asList("B", "C"));
        g.put("B", Arrays.asList("D", "E"));
        g.put("C", Collections.singletonList("F"));
        g.put("D", Collections.emptyList());
        g.put("E", Collections.emptyList());
        g.put("F", Collections.emptyList());

        traceDfs(g, "A");
        traceDfs(g, "MISSING_NODE");
    }
}