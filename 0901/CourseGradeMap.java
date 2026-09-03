import java.util.*;

public class CourseGradeMap {
    private final Map<String, List<Integer>> courseGrades;

    public CourseGradeMap() {
        this.courseGrades = new HashMap<>();
    }

    public void addGrade(String courseId, int score) {
        courseGrades.computeIfAbsent(courseId, k -> new ArrayList<>()).add(score);
    }

    public double getAverage(String courseId) {
        List<Integer> grades = courseGrades.get(courseId);
        if (grades == null || grades.isEmpty()) return 0.0;

        int sum = 0;
        for (int score : grades) sum += score;
        return (double) sum / grades.size();
    }

    public int getMaxScore(String courseId) {
        List<Integer> grades = courseGrades.get(courseId);
        if (grades == null || grades.isEmpty()) return -1;

        int max = Integer.MIN_VALUE;
        for (int score : grades) {
            if (score > max) max = score;
        }
        return max;
    }

    public void printSortedReport() {
        Map<String, List<Integer>> sortedMap = new TreeMap<>(courseGrades);

        System.out.printf("%-12s %-8s %-10s %-10s%n", "Course ID", "Count", "Average", "Max Score");
        System.out.println("----------------------------------------------");
        for (Map.Entry<String, List<Integer>> entry : sortedMap.entrySet()) {
            String courseId = entry.getKey();
            List<Integer> list = entry.getValue();
            double avg = getAverage(courseId);
            int max = getMaxScore(courseId);
            System.out.printf("%-12s %-8d %-10.2f %-10d%n", courseId, list.size(), avg, max);
        }
    }
}