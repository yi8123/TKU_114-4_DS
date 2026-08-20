// 檔名：ReportExporterFactory.java
import java.util.Arrays;

interface ReportExporter {
    void export(String title, int[] values);
}

class CsvExporter implements ReportExporter {
    @Override
    public void export(String title, int[] values) {
        int[] safeValues = (values == null) ? new int[0] : values;
        System.out.println("=== CSV EXPORT ===");
        System.out.println("title,value_count,values");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < safeValues.length; i++) {
            sb.append(safeValues[i]).append(i == safeValues.length - 1 ? "" : ";");
        }
        System.out.printf("\"%s\",%d,\"%s\"%n%n", title, safeValues.length, sb);
    }
}

class JsonExporter implements ReportExporter {
    @Override
    public void export(String title, int[] values) {
        int[] safeValues = (values == null) ? new int[0] : values;
        System.out.println("=== JSON EXPORT ===");
        System.out.println("{");
        System.out.println("  \"title\": \"" + title + "\",");
        System.out.println("  \"count\": " + safeValues.length + ",");
        System.out.println("  \"values\": " + Arrays.toString(safeValues));
        System.out.println("}\n");
    }
}

class TextExporter implements ReportExporter {
    @Override
    public void export(String title, int[] values) {
        int[] safeValues = (values == null) ? new int[0] : values;
        System.out.println("=== TEXT REPORT ===");
        System.out.println("Title: " + title);
        System.out.println("Data: " + Arrays.toString(safeValues));
        System.out.println();
    }
}

public class ReportExporterFactory {
    public static ReportExporter createExporter(String format) {
        if (format == null) {
            return new TextExporter();
        }
        return switch (format.trim().toUpperCase()) {
            case "CSV" -> new CsvExporter();
            case "JSON" -> new JsonExporter();
            default -> new TextExporter();
        };
    }

    public static void exportReport(ReportExporter exporter, String title, int[] values) {
        if (exporter != null) {
            exporter.export(title, values);
        }
    }

    public static void main(String[] args) {
        int[] scores = {95, 88, 72, 60, 100};

        String[] requestedFormats = {"CSV", "json", "XML", null};

        for (String format : requestedFormats) {
            ReportExporter exporter = createExporter(format);
            // 只依賴 ReportExporter interface 呼叫，主流程不使用 instanceof
            exportReport(exporter, "Q3 Sales Figures", scores);
        }

        // 測試 null 陣列防禦邊界
        ReportExporter fallback = createExporter("UNKNOWN_TYPE");
        exportReport(fallback, "Empty Report", null);
    }
}