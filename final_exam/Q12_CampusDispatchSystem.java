import java.util.*;

public class Q12_CampusDispatchSystem {
    public record Request(String id, String location, int priority, long sequence) {}

    private final Map<String, Set<String>> graph = new HashMap<>();
    private final Map<String, Request> requests = new HashMap<>();
    private final PriorityQueue<Request> pq = new PriorityQueue<>(
            Comparator.comparingInt(Request::priority)
                      .thenComparingLong(Request::sequence)
    );

    public boolean addLocation(String location) {
        if (location == null || graph.containsKey(location)) {
            return false;
        }
        graph.put(location, new HashSet<>());
        return true;
    }

    public boolean addRoad(String first, String second) {
        if (first == null || second == null || first.equals(second)) {
            return false;
        }
        if (!graph.containsKey(first) || !graph.containsKey(second)) {
            return false;
        }
        graph.get(first).add(second);
        graph.get(second).add(first);
        return true;
    }

    public boolean submit(Request request) {
        if (request == null || request.id() == null || request.location() == null) {
            return false;
        }
        if (!graph.containsKey(request.location())) {
            return false;
        }
        if (requests.containsKey(request.id())) {
            return false;
        }
        requests.put(request.id(), request);
        pq.offer(request);
        return true;
    }

    public Request nextReachable(String serviceCenter) {
        if (serviceCenter == null || !graph.containsKey(serviceCenter)) {
            return null;
        }

        Set<String> reachableNodes = getReachableLocations(serviceCenter);
        List<Request> skipped = new ArrayList<>();
        Request matched = null;

        while (!pq.isEmpty()) {
            Request candidate = pq.poll();
            if (reachableNodes.contains(candidate.location())) {
                matched = candidate;
                requests.remove(matched.id());
                break;
            } else {
                skipped.add(candidate);
            }
        }

        pq.addAll(skipped);
        return matched;
    }

    private Set<String> getReachableLocations(String start) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            for (String neighbor : graph.getOrDefault(curr, Collections.emptySet())) {
                if (visited.add(neighbor)) {
                    queue.offer(neighbor);
                }
            }
        }
        return visited;
    }

    public List<String> route(String start, String target) {
        if (start == null || target == null || !graph.containsKey(start) || !graph.containsKey(target)) {
            return new ArrayList<>();
        }
        if (start.equals(target)) {
            List<String> res = new ArrayList<>();
            res.add(start);
            return res;
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

            for (String neighbor : graph.getOrDefault(curr, Collections.emptySet())) {
                if (visited.add(neighbor)) {
                    predecessor.put(neighbor, curr);
                    queue.offer(neighbor);
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

    public int pendingCount() {
        return pq.size();
    }
}
