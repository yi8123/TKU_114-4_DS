class Instructor {
    private final String id;
    private final String name;

    Instructor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}

class Course {
    private final String courseCode;
    private final String title;
    private final Instructor instructor;
    Course(String courseCode, String title, Instructor instructor) {
        this.courseCode = courseCode;
        this.title = title;
        this.instructor = instructor;
    }

    String summary() {
        String instructorInfo = (instructor != null) 
                ? instructor.getName() + " (" + instructor.getId() + ")" 
                : "No Instructor Assigned";
        return "[" + courseCode + "] " + title + " - Instructor: " + instructorInfo;
    }
}

public class CourseComposition {
    public static void main(String[] args) {
        Instructor profWang = new Instructor("INS-01", "Dr. Wang");

        Course oop = new Course("CS101", "OOP in Java", profWang);
        Course dataStructure = new Course("CS102", "Data Structures", profWang);

        System.out.println(oop.summary());
        System.out.println(dataStructure.summary());
    }
}