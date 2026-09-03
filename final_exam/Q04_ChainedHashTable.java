import java.util.*;

public class Q04_ChainedHashTable {
    private static class Entry {
        int key;
        String value;

        Entry(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private final List<List<Entry>> buckets;
    private final int bucketCount;
    private int size;

    public Q04_ChainedHashTable(int bucketCount) {
        if (bucketCount <= 0) {
            throw new IllegalArgumentException("bucketCount must be positive");
        }
        this.bucketCount = bucketCount;
        this.buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            this.buckets.add(new LinkedList<>());
        }
        this.size = 0;
    }

    private int getIndex(int key) {
        return Math.floorMod(key, bucketCount);
    }

    public void put(int key, String value) {
        int idx = getIndex(key);
        List<Entry> chain = buckets.get(idx);
        for (Entry entry : chain) {
            if (entry.key == key) {
                entry.value = value;
                return;
            }
        }
        chain.add(new Entry(key, value));
        size++;
    }

    public String get(int key) {
        int idx = getIndex(key);
        List<Entry> chain = buckets.get(idx);
        for (Entry entry : chain) {
            if (entry.key == key) {
                return entry.value;
            }
        }
        return null;
    }

    public boolean remove(int key) {
        int idx = getIndex(key);
        List<Entry> chain = buckets.get(idx);
        Iterator<Entry> it = chain.iterator();
        while (it.hasNext()) {
            if (it.next().key == key) {
                it.remove();
                size--;
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public int longestChain() {
        int max = 0;
        for (List<Entry> chain : buckets) {
            if (chain.size() > max) {
                max = chain.size();
            }
        }
        return max;
    }
}