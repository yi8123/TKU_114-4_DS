import java.util.PriorityQueue;

public class SupportTicketQueue {

    public static class Ticket implements Comparable<Ticket> {
        private final String id;
        private final int severity;
        private final int createdOrder;

        public Ticket(String id, int severity, int createdOrder) {
            this.id = id;
            this.severity = severity;
            this.createdOrder = createdOrder;
        }

        @Override
        public int compareTo(Ticket other) {
            if (this.severity != other.severity) {
                return Integer.compare(other.severity, this.severity);
            }
            return Integer.compare(this.createdOrder, other.createdOrder);
        }

        @Override
        public String toString() {
            return id + "|" + severity + "|" + createdOrder;
        }
    }

    private final PriorityQueue<Ticket> queue = new PriorityQueue<>();

    public void addTicket(Ticket ticket) {
        queue.offer(ticket);
    }

    public void processAll() {
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }

    public static void main(String[] args) {
        SupportTicketQueue stq = new SupportTicketQueue();
        stq.addTicket(new Ticket("TCK-001", 3, 1));
        stq.addTicket(new Ticket("TCK-002", 5, 2));
        stq.addTicket(new Ticket("TCK-003", 5, 3));
        stq.addTicket(new Ticket("TCK-004", 1, 4));
        stq.addTicket(new Ticket("TCK-005", 3, 5));

        stq.processAll();
    }
}