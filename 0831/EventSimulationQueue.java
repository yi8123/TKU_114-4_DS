import java.util.*;

public class EventSimulationQueue {

    public static class Event implements Comparable<Event> {
        private final String eventId;
        private final long timestamp;
        private final String eventType;
        private final int sequence;

        public Event(String eventId, long timestamp, String eventType, int sequence) {
            this.eventId = eventId;
            this.timestamp = timestamp;
            this.eventType = eventType;
            this.sequence = sequence;
        }

        public String getEventId() {
            return eventId;
        }

        @Override
        public int compareTo(Event other) {
            if (this.timestamp != other.timestamp) {
                return Long.compare(this.timestamp, other.timestamp);
            }
            return Integer.compare(this.sequence, other.sequence);
        }

        @Override
        public String toString() {
            return "Event{" + "id='" + eventId + '\'' + ", time=" + timestamp +
                   ", type='" + eventType + '\'' + ", seq=" + sequence + '}';
        }
    }

    private final PriorityQueue<Event> queue = new PriorityQueue<>();
    private final Set<String> cancelledIds = new HashSet<>();
    private int seqGenerator = 0;

    public void schedule(String eventId, long timestamp, String eventType) {
        queue.offer(new Event(eventId, timestamp, eventType, ++seqGenerator));
    }

    public boolean cancel(String eventId) {
        cancelledIds.add(eventId);
        return true;
    }

    public void runSimulation() {
        System.out.println("=== Starting Simulation Trace ===");
        while (!queue.isEmpty()) {
            Event current = queue.poll();
            if (cancelledIds.contains(current.getEventId())) {
                System.out.println("[CANCELLED] Skipping " + current);
            } else {
                System.out.println("[EXECUTED] " + current);
            }
        }
        System.out.println("=== Simulation Finished ===");
    }

    public static void main(String[] args) {
        EventSimulationQueue sim = new EventSimulationQueue();

        sim.schedule("EV-001", 100, "TIMER_EXPIRED");
        sim.schedule("EV-002", 50,  "PACKET_RECEIVED");
        sim.schedule("EV-003", 100, "UI_CLICK");
        sim.schedule("EV-004", 75,  "HEARTBEAT");
        sim.schedule("EV-005", 50,  "SENSOR_TRIGGER");

        sim.cancel("EV-004");

        sim.runSimulation();
    }
}