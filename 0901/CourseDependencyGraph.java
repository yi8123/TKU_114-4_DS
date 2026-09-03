import java.util.*;

public class CourseDependencyGraph {
    private final Map<String, Set<String>> inEdges;
    private final Map<String, Set<String>> outEdges;

    public CourseDependencyGraph() {
        this.inEdges = new HashMap<>();
        this.outEdges = new HashMap<>();
    }

    public void addCourse(String courseId) {
        inEdges.putIfAbsent(courseId, new TreeSet<>());
        outEdges.putIfAbsent(courseId, new TreeSet<>());
    }

    public void addDependency(String prereq, String target) {
        addCourse(prereq);
        addCourse(target);
        outEdges.get(prereq).add(target);
        inEdges.get(target).add(prereq);
    }

    public Set<String> getPrerequisites(String courseId) {
        return inEdges.getOrDefault(courseId, Collections.emptySet());
    }

    public Set<String> getSuccessors(String courseId) {
        return outEdges.getOrDefault(courseId, Collections.emptySet());
    }

    public int getInDegree(String courseId) {
        return inEdges.containsKey(courseId) ? inEdges.get(courseId).size() : 0;
    }

    public int getOutDegree(String courseId) {
        return outEdges.containsKey(courseId) ? outEdges.get(courseId).size() : 0;
    }

    public void printDependencyReport() {
        List<String> allCourses = new ArrayList<>(inEdges.keySet());
        Collections.sort(allCourses);

        System.out.printf("%-12s %-10s %-10s %-25s %-25s%n", 
                          "Course", "In-Degree", "Out-Degree", "Prerequisites", "Successors");
        System.out.println("-----------------------------------------------------------------------------------------");

        for (String course : allCourses) {
            System.out.printf("%-12s %-10d %-10d %-25s %-25s%n",
                    course,
                    getInDegree(course),
                    getOutDegree(course),
                    getPrerequisites(course),
                    getSuccessors(course));
        }
    }
}