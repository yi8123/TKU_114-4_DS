import java.util.*;

public class CoursePlanningGraph {
    private final Map<String, List<String>> prereqGraph = new HashMap<>();

    public void addCourse(String course) {
        prereqGraph.putIfAbsent(course, new ArrayList<>());
    }

    public void addPrerequisite(String preCourse, String targetCourse) {
        addCourse(preCourse);
        addCourse(targetCourse);
        prereqGraph.get(preCourse).add(targetCourse);
    }

    public boolean canReach(String start, String target) {
        if (!prereqGraph.containsKey(start) || !prereqGraph.containsKey(target)) return false;
        Set<String> visited = new HashSet<>();
        return dfsCheck(start, target, visited);
    }

    private boolean dfsCheck(String curr, String target, Set<String> visited) {
        if (curr.equals(target)) return true;
        visited.add(curr);
        for (String next : prereqGraph.getOrDefault(curr, Collections.emptyList())) {
            if (!visited.contains(next) && dfsCheck(next, target, visited)) {
                return true;
            }
        }
        return false;
    }

    public Set<String> getAllImpactedCourses(String modifiedCourse) {
        if (!prereqGraph.containsKey(modifiedCourse)) return Collections.emptySet();
        Set<String> impacted = new LinkedHashSet<>();
        dfsCollect(modifiedCourse, impacted);
        impacted.remove(modifiedCourse);
        return impacted;
    }

    private void dfsCollect(String curr, Set<String> visited) {
        visited.add(curr);
        for (String next : prereqGraph.getOrDefault(curr, Collections.emptyList())) {
            if (!visited.contains(next)) {
                dfsCollect(next, visited);
            }
        }
    }

    public static void main(String[] args) {
        CoursePlanningGraph cpg = new CoursePlanningGraph();
        cpg.addPrerequisite("CS101", "CS102");
        cpg.addPrerequisite("CS102", "CS201");
        cpg.addPrerequisite("CS102", "CS205");
        cpg.addPrerequisite("CS201", "CS301");

        System.out.println("Is CS101 prerequisite of CS301: " + cpg.canReach("CS101", "CS301"));
        System.out.println("Courses impacted if CS102 syllabus changes: " + cpg.getAllImpactedCourses("CS102"));
        System.out.println("Impact of unknown course: " + cpg.getAllImpactedCourses("MATH999"));
    }
}