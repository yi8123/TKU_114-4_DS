interface Exportable {
    void exportData(String destination);
}

interface Compressible {
    void compress(int level);
}

class BackupDocument implements Exportable, Compressible {
    private final String filename;

    BackupDocument(String filename) {
        this.filename = (filename == null || filename.isBlank()) ? "Untitled.bak" : filename;
    }

    @Override
    public void exportData(String destination) {
        System.out.println("Exporting document [" + filename + "] to path: " + destination);
    }

    @Override
    public void compress(int level) {
        System.out.println("Compressing document [" + filename + "] with compression level: " + level);
    }
}

public class DocumentCapabilityDemo {
    public static void main(String[] args) {
        BackupDocument document = new BackupDocument("database_dump.sql");

        
        Exportable exporter = document;
        Compressible compressor = document;

        exporter.exportData("/var/backups/");
        compressor.compress(9);

        System.out.println("\n--- Reference 驗證 ---");
        System.out.println("exporter == compressor: " + (exporter == compressor)); 
        
    }
}