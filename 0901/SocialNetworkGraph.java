import java.util.*;

public class SocialNetworkGraph {
    private final Map<String, Set<String>> adjList;

    public SocialNetworkGraph() {
        this.adjList = new HashMap<>();
    }

    public boolean addUser(String user) {
        if (adjList.containsKey(user)) return false;
        adjList.put(user, new HashSet<>());
        return true;
    }

    public boolean addFriendship(String u, String v) {
        if (!adjList.containsKey(u) || !adjList.containsKey(v) || u.equals(v)) {
            return false;
        }
        boolean added = adjList.get(u).add(v);
        adjList.get(v).add(u);
        return added;
    }

    public boolean removeFriendship(String u, String v) {
        if (!adjList.containsKey(u) || !adjList.containsKey(v)) {
            return false;
        }
        boolean removed = adjList.get(u).remove(v);
        adjList.get(v).remove(u);
        return removed;
    }

    public Set<String> getCommonFriends(String u, String v) {
        if (!adjList.containsKey(u) || !adjList.containsKey(v)) {
            return Collections.emptySet();
        }
        Set<String> common = new HashSet<>(adjList.get(u));
        common.retainAll(adjList.get(v));
        return common;
    }

    public List<String> getIsolatedUsers() {
        List<String> isolated = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : adjList.entrySet()) {
            if (entry.getValue().isEmpty()) {
                isolated.add(entry.getKey());
            }
        }
        return isolated;
    }

    public Set<String> getFriends(String user) {
        return adjList.getOrDefault(user, Collections.emptySet());
    }
}