import java.util.*;

public class CampusNavigationSystem {
    private final Map<String, String> locations = new HashMap<>();
    private final Map<String, List<String>> roadGraph = new HashMap<>();

    public void addLocation(String id, String name) {
        locations.put(id, name);
        roadGraph.putIfAbsent(id, new ArrayList<>());
    }

    public void addRoad(String idA, String idB) {
        if (locations.containsKey(idA) && locations.containsKey(idB)) {
            roadGraph.get(idA).add(idB);
            roadGraph.get(idB).add(idA);
        }
    }

    public List<String> findShortestPath(String startId, String targetId) {
        if (!locations.containsKey(startId) || !locations.containsKey(targetId)) return Collections.emptyList();
        if (startId.equals(targetId)) return Collections.singletonList(locations.get(startId));

        Map<String, String> prev = new HashMap<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();

        queue.offer(startId);
        visited.add(startId);

        boolean reached = false;
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            if (curr.equals(targetId)) {
                reached = true;
                break;
            }
            for (String next : roadGraph.getOrDefault(curr, Collections.emptyList())) {
                if (!visited.contains(next)) {
                    visited.add(next);
                    prev.put(next, curr);
                    queue.offer(next);
                }
            }
        }

        if (!reached) return Collections.emptyList();

        List<String> path = new ArrayList<>();
        for (String at = targetId; at != null; at = prev.get(at)) {
            path.add(locations.get(at));
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        CampusNavigationSystem nav = new CampusNavigationSystem();
        nav.addLocation("A", "Main Gate");
        nav.addLocation("B", "Library");
        nav.addLocation("C", "Engineering Hall");
        nav.addLocation("D", "Student Center");

        nav.addRoad("A", "B");
        nav.addRoad("B", "C");
        nav.addRoad("A", "D");
        nav.addRoad("D", "C");

        System.out.println("Path: " + nav.findShortestPath("A", "C"));
        System.out.println("Missing Path: " + nav.findShortestPath("A", "Z"));
    }
}