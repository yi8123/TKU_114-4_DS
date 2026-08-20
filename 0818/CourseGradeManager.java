class CourseGrade {
    private final String studentId;
    private final String name;
    private final int regularScore;
    private final int midtermScore;
    private final int finalScore;
    private final int attendanceScore;

    CourseGrade(String studentId, String name, int regular, int midterm, int finalExam, int attendance) {
        this.studentId = studentId;
        this.name = name;
        this.regularScore = clamp(regular);
        this.midtermScore = clamp(midterm);
        this.finalScore = clamp(finalExam);
        this.attendanceScore = clamp(attendance);
    }

    private int clamp(int val) {
        return Math.max(0, Math.min(100, val));
    }

    public double calculateFinalScore() {
        return (regularScore * 0.50) + (midtermScore * 0.20) + (finalScore * 0.20) + (attendanceScore * 0.10);
    }

    public String getLevel() {
        double score = calculateFinalScore();
        if (score >= 90.0) return "A";
        if (score >= 80.0) return "B";
        if (score >= 70.0) return "C";
        if (score >= 60.0) return "D";
        return "F";
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("[%s] %-6s | Final: %5.1f | Grade: %s (Reg: %d, Mid: %d, Fin: %d, Att: %d)",
                studentId, name, calculateFinalScore(), getLevel(),
                regularScore, midtermScore, finalScore, attendanceScore);
    }
}

public class CourseGradeManager {
    public static void main(String[] args) {
        CourseGrade[] records = {
            new CourseGrade("S01", "Amy", 90, 85, 95, 100),
            new CourseGrade("S02", "Bob", 70, 60, 65, 80),
            new CourseGrade("S03", "Carl", 40, 50, 45, 60),
            new CourseGrade("S04", "Daisy", 85, 90, 88, 90),
            new CourseGrade("S05", "Evan", 30, 20, 40, 50)
        };

        double totalSum = 0;
        CourseGrade highest = records[0];

        System.out.println("=== 所有學生成績列表 ===");
        for (CourseGrade record : records) {
            System.out.println(record);
            double currentFinal = record.calculateFinalScore();
            totalSum += currentFinal;
            if (currentFinal > highest.calculateFinalScore()) {
                highest = record;
            }
        }

        System.out.println("\n全班平均成績: " + String.format("%.2f", (totalSum / records.length)));
        System.out.println("最高分學生: " + highest.getName() + " (" + highest.calculateFinalScore() + ")");

        System.out.println("\n=== 不及格名單 (Grade F / <60) ===");
        for (CourseGrade record : records) {
            if (record.calculateFinalScore() < 60.0) {
                System.out.println(record);
            }
        }
    }
}