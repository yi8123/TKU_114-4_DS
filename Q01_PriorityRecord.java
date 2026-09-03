import java.util.*;

record Job(String id, int priority, long sequence) {}

public class Q01_PriorityRecord {

    public static List<String> processOrder(List<Job> jobs) {

        List<String> result = new ArrayList<>();

        if (jobs == null || jobs.isEmpty()) {
            return result;
        }

        Comparator<Job> comparator = Comparator
                .comparingInt(Job::priority)
                .thenComparingLong(Job::sequence)
                .thenComparing(job -> job.id() == null ? "" : job.id());

        PriorityQueue<Job> pq = new PriorityQueue<>(comparator);

        for (Job job : jobs) {
            if (job != null) {
                pq.offer(job);
            }
        }

        while (!pq.isEmpty()) {
            result.add(pq.poll().id());
        }

        return result;
    }
}