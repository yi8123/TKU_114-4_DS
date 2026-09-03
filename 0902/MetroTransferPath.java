import java.util.*;

public class MetroTransferPath {
    public static class PathResult {
        public final List<String> path;
        public final int edgeCount;

        public PathResult(List<String> path, int edgeCount) {
            this.path = path;
            this.edgeCount = edgeCount;
        }

        @Override
        public String toString() {
            return "Path: " + path + ", Edge Count: " + edgeCount;
        }
    }

    public static PathResult findShortestRoute(Map<String, List<String>> metro, String start, String target) {
        if (metro == null || start == null || target == null) return new PathResult(Collections.emptyList(), -1);
        if (!metro.containsKey(start) || !metro.containsKey(target)) return new PathResult(Collections.emptyList(), -1);

        if (start.equals(target)) {
            return new PathResult(Collections.singletonList(start), 0);
        }

        Map<String, String> prev = new HashMap<>();
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

            for (String next : metro.getOrDefault(curr, Collections.emptyList())) {
                if (!visited.contains(next)) {
                    visited.add(next);
                    prev.put(next, curr);
                    queue.offer(next);
                }
            }
        }

        if (!found) return new PathResult(Collections.emptyList(), -1);

        List<String> path = new ArrayList<>();
        for (String at = target; at != null; at = prev.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return new PathResult(path, path.size() - 1);
    }

    public static void main(String[] args) {
        Map<String, List<String>> metro = new HashMap<>();
        metro.put("R1", Arrays.asList("R2", "G1"));
        metro.put("R2", Arrays.asList("R1", "R3"));
        metro.put("R3", Collections.singletonList("R2"));
        metro.put("G1", Arrays.asList("R1", "G2"));
        metro.put("G2", Arrays.asList("G1", "R3"));
        metro.put("ISO", Collections.emptyList());

        System.out.println(findShortestRoute(metro, "R1", "R3"));
        System.out.println(findShortestRoute(metro, "R1", "R1"));
        System.out.println(findShortestRoute(metro, "R1", "ISO"));
        System.out.println(findShortestRoute(metro, "R1", "NONE"));
    }
}