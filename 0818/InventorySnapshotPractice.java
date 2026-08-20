import java.util.Arrays;

final class InventorySnapshot {
    private final String warehouseId;
    private final int[] quantities;

    InventorySnapshot(String warehouseId, int[] quantities) {
        this.warehouseId = warehouseId;
        if (quantities == null) {
            this.quantities = new int[0];
        } else {
            this.quantities = Arrays.copyOf(quantities, quantities.length);
        }
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public int[] getQuantities() {
        return Arrays.copyOf(quantities, quantities.length);
    }

    public int totalQuantity() {
        int sum = 0;
        for (int q : quantities) {
            sum += q;
        }
        return sum;
    }

    public int outOfStockCount() {
        int count = 0;
        for (int q : quantities) {
            if (q == 0) {
                count++;
            }
        }
        return count;
    }
}

public class InventorySnapshotPractice {
    public static void main(String[] args) {
        int[] data = {5, 0, 3, 0};
        InventorySnapshot snapshot = new InventorySnapshot("WH-A", data);

        System.out.println("總數量: " + snapshot.totalQuantity());       // 8
        System.out.println("缺貨品項數: " + snapshot.outOfStockCount()); // 2

        data[0] = 999;
        int[] getterArray = snapshot.getQuantities();
        getterArray[1] = 999;

        System.out.println("\n外部修改後再次計算：");
        System.out.println("總數量（應維持 8）: " + snapshot.totalQuantity());
        System.out.println("缺貨數（應維持 2）: " + snapshot.outOfStockCount());
    }
}