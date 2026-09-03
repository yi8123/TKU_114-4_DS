import java.util.LinkedList;

public class IntegerStringHashTable {

    private static class Entry {
        int key;
        String value;

        Entry(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private final LinkedList<Entry>[] table;
    private final int capacity;
    private int size;

    @SuppressWarnings("unchecked")
    public IntegerStringHashTable(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be positive");
        this.capacity = capacity;
        this.table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            this.table[i] = new LinkedList<>();
        }
        this.size = 0;
    }

    public IntegerStringHashTable() {
        this(11);
    }

    private int hash(int key) {
        return Math.floorMod(key, capacity);
    }

    public void put(int key, String value) {
        int idx = hash(key);
        LinkedList<Entry> bucket = table[idx];
        for (Entry entry : bucket) {
            if (entry.key == key) {
                entry.value = value;
                return;
            }
        }
        bucket.add(new Entry(key, value));
        size++;
    }

    public String get(int key) {
        int idx = hash(key);
        LinkedList<Entry> bucket = table[idx];
        for (Entry entry : bucket) {
            if (entry.key == key) return entry.value;
        }
        return null;
    }

    public boolean containsKey(int key) {
        return get(key) != null;
    }

    public String remove(int key) {
        int idx = hash(key);
        LinkedList<Entry> bucket = table[idx];
        for (int i = 0; i < bucket.size(); i++) {
            Entry entry = bucket.get(i);
            if (entry.key == key) {
                bucket.remove(i);
                size--;
                return entry.value;
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    public void bucketReport() {
        System.out.println("=== Hash Table Bucket Report (Size: " + size + ") ===");
        for (int i = 0; i < capacity; i++) {
            System.out.print("Bucket [" + i + "]: ");
            for (Entry entry : table[i]) {
                System.out.print("(" + entry.key + " -> " + entry.value + ") ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        IntegerStringHashTable ht = new IntegerStringHashTable(5);

        ht.put(1, "Alpha");
        ht.put(6, "Beta");
        ht.put(-4, "Gamma");
        ht.put(2, "Delta");
        ht.put(6, "Beta-Updated");

        ht.bucketReport();
        System.out.println("Get -4: " + ht.get(-4));
        System.out.println("Remove 1: " + ht.remove(1));
        System.out.println("Contains 1? " + ht.containsKey(1));
        ht.bucketReport();
    }
}