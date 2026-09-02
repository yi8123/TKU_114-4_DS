import java.util.PriorityQueue;

public class EmergencyTriageQueue {

    public static class Patient implements Comparable<Patient> {
        private final String medicalId;
        private final int triageLevel;
        private final int arrivalOrder;

        public Patient(String medicalId, int triageLevel, int arrivalOrder) {
            this.medicalId = medicalId;
            this.triageLevel = triageLevel;
            this.arrivalOrder = arrivalOrder;
        }

        @Override
        public int compareTo(Patient other) {
            if (this.triageLevel != other.triageLevel) {
                return Integer.compare(this.triageLevel, other.triageLevel);
            }
            if (this.arrivalOrder != other.arrivalOrder) {
                return Integer.compare(this.arrivalOrder, other.arrivalOrder);
            }
            return this.medicalId.compareTo(other.medicalId);
        }

        @Override
        public String toString() {
            return "[" + medicalId + " | Triage: " + triageLevel + " | Arrival: " + arrivalOrder + "]";
        }
    }

    private final PriorityQueue<Patient> queue = new PriorityQueue<>();
    private int orderCounter = 0;

    public void checkIn(String medicalId, int triageLevel) {
        Patient p = new Patient(medicalId, triageLevel, ++orderCounter);
        queue.offer(p);
        System.out.println("Check-in: " + p);
    }

    public Patient peekNext() {
        return queue.peek();
    }

    public Patient callNext() {
        return queue.poll();
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public static void main(String[] args) {
        EmergencyTriageQueue triage = new EmergencyTriageQueue();

        triage.checkIn("P001", 3);
        triage.checkIn("P002", 1);
        triage.checkIn("P003", 2);
        triage.checkIn("P004", 1);
        triage.checkIn("P005", 3);

        System.out.println("\n--- Current Queue Size: " + triage.size() + " ---");
        System.out.println("Next up: " + triage.peekNext());

        System.out.println("\n--- Calling Patients ---");
        while (!triage.isEmpty()) {
            System.out.println("Called: " + triage.callNext() + " (Remaining: " + triage.size() + ")");
        }

        System.out.println("Calling on empty: " + triage.callNext());
    }
}