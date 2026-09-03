import java.util.*;

public class WebsiteLinkGraph {
    private final Map<String, Set<String>> outgoingMap;
    private final Map<String, Integer> incomingCounts;

    public WebsiteLinkGraph() {
        this.outgoingMap = new HashMap<>();
        this.incomingCounts = new HashMap<>();
    }

    public void addPage(String page) {
        outgoingMap.putIfAbsent(page, new TreeSet<>());
        incomingCounts.putIfAbsent(page, 0);
    }

    public boolean addLink(String fromPage, String toPage) {
        addPage(fromPage);
        addPage(toPage);

        if (outgoingMap.get(fromPage).add(toPage)) {
            incomingCounts.put(toPage, incomingCounts.get(toPage) + 1);
            return true;
        }
        return false;
    }

    public Set<String> getOutgoingLinks(String page) {
        return outgoingMap.getOrDefault(page, Collections.emptySet());
    }

    public int getIncomingCount(String page) {
        return incomingCounts.getOrDefault(page, 0);
    }

    public List<String> getPagesWithoutIncoming() {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : incomingCounts.entrySet()) {
            if (entry.getValue() == 0) {
                result.add(entry.getKey());
            }
        }
        Collections.sort(result);
        return result;
    }

    public List<String> getPagesWithoutOutgoing() {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : outgoingMap.entrySet()) {
            if (entry.getValue().isEmpty()) {
                result.add(entry.getKey());
            }
        }
        Collections.sort(result);
        return result;
    }

    public void printReport() {
        List<String> pages = new ArrayList<>(outgoingMap.keySet());
        Collections.sort(pages);

        System.out.printf("%-20s %-15s %-30s%n", "Page", "Incoming Count", "Outgoing Links");
        System.out.println("------------------------------------------------------------------");
        for (String page : pages) {
            System.out.printf("%-20s %-15d %-30s%n",
                    page,
                    getIncomingCount(page),
                    getOutgoingLinks(page));
        }

        System.out.println("\nSource Pages (No Incoming Links): " + getPagesWithoutIncoming());
        System.out.println("Sink Pages (No Outgoing Links)    : " + getPagesWithoutOutgoing());
    }
}