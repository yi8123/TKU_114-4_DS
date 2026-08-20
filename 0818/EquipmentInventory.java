class Equipment {
    private final String id;
    private final String name;
    private int availableCount;

    Equipment(String id, String name, int availableCount) {
        this.id = (id == null || id.isBlank()) ? "Unknown" : id;
        this.name = (name == null || name.isBlank()) ? "Unknown" : name;
        this.availableCount = Math.max(0, availableCount);
    }

    boolean borrowOne() {
        if (availableCount <= 0) {
            return false;
        }
        availableCount--;
        return true;
    }

    void returnItems(int quantity) {
        if (quantity > 0) {
            availableCount += quantity;
        }
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Available: " + availableCount;
    }
}

public class EquipmentInventory {
    public static void main(String[] args) {
        Equipment projector = new Equipment("EQ01", "Projector", 1);
        Equipment laptop = new Equipment("   ", null, -5);

        System.out.println("初始狀態：");
        System.out.println(projector);
        System.out.println(laptop);

        System.out.println("\n借用測試：");
        System.out.println("借 Projector 第一次: " + projector.borrowOne());
        System.out.println("借 Projector 第二次: " + projector.borrowOne());
        System.out.println("借 Laptop: " + laptop.borrowOne());

        System.out.println("\n歸還測試：");
        projector.returnItems(2);
        projector.returnItems(-1);
        System.out.println(projector);
    }
}