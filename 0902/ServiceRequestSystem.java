import java.util.*;

public class ServiceRequestSystem {
    public static class Request implements Comparable<Request> {
        public final String id;
        public final int priority;
        public boolean cancelled = false;

        public Request(String id, int priority) {
            this.id = id;
            this.priority = priority;
        }

        @Override
        public int compareTo(Request o) {
            return Integer.compare(this.priority, o.priority);
        }

        @Override
        public String toString() {
            return String.format("[%s (Pri:%d)]", id, priority);
        }
    }

    private final Map<String, Request> idMap = new HashMap<>();
    private final PriorityQueue<Request> pq = new PriorityQueue<>();

    public void addRequest(String id, int priority) {
        if (idMap.containsKey(id)) cancelRequest(id);
        Request req = new Request(id, priority);
        idMap.put(id, req);
        pq.offer(req);
    }

    public Request getRequestById(String id) {
        Request r = idMap.get(id);
        return (r != null && !r.cancelled) ? r : null;
    }

    public boolean cancelRequest(String id) {
        Request req = idMap.remove(id);
        if (req != null && !req.cancelled) {
            req.cancelled = true;
            return true;
        }
        return false;
    }

    public Request processNext() {
        while (!pq.isEmpty() && pq.peek().cancelled) {
            pq.poll();
        }
        if (pq.isEmpty()) return null;

        Request top = pq.poll();
        idMap.remove(top.id);
        return top;
    }

    public static void main(String[] args) {
        ServiceRequestSystem srs = new ServiceRequestSystem();
        srs.addRequest("REQ-1", 3);
        srs.addRequest("REQ-2", 1);
        srs.addRequest("REQ-3", 2);

        System.out.println("Query REQ-2: " + srs.getRequestById("REQ-2"));
        srs.cancelRequest("REQ-2");

        System.out.println("Next processed: " + srs.processNext());
        System.out.println("Next processed: " + srs.processNext());
        System.out.println("Empty test: " + srs.processNext());
    }
}