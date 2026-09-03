import java.util.*;

public class NetworkComponents {
    public static class ComponentReport {
        public final List<List<String>> components;
        public final int totalComponents;
        public final List<String> largestComponent;

        public ComponentReport(List<List<String>> components) {
            this.components = components;
            this.totalComponents = components.size();
            this.largestComponent = components.stream()
                    .max(Comparator.comparingInt(List::size))
                    .orElse(Collections.emptyList());
        }

        @Override
        public String toString() {
            return String.format("Total: %d, Largest Size: %d%nComponents: %s",
                    totalComponents, largestComponent.size(), components);
        }
    }

    public static ComponentReport analyze(Map<String, List<String>> graph) {
        if (graph == null || graph.isEmpty()) {
            return new ComponentReport(Collections.emptyList());
        }

        Set<String> visited = new HashSet<>();
        List<List<String>> allComponents = new ArrayList<>();

        for (String node : graph.keySet()) {
            if (!visited.contains(node)) {
                List<String> comp = new ArrayList<>();
                Queue<String> queue = new ArrayDeque<>();
                queue.offer(node);
                visited.add(node);

                while (!queue.isEmpty()) {
                    String curr = queue.poll();
                    comp.add(curr);
                    for (String neighbor : graph.getOrDefault(curr, Collections.emptyList())) {
                        if (!visited.contains(neighbor)) {
                            visited.add(neighbor);
                            queue.offer(neighbor);
                        }
                    }
                }
                allComponents.add(comp);
            }
        }
        return new ComponentReport(allComponents);
    }

    public static void main(String[] args) {
        Map<String, List<String>> g = new HashMap<>();
        g.put("A", Arrays.asList("B"));
        g.put("B", Arrays.asList("A", "C"));
        g.put("C", Arrays.asList("B"));
        g.put("D", Arrays.asList("E"));
        g.put("E", Arrays.asList("D"));
        g.put("F", Collections.emptyList());

        System.out.println(analyze(g));
        System.out.println("Empty Test: " + analyze(Collections.emptyMap()));
    }
}