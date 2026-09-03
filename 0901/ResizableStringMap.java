import java.util.Objects;

public class ResizableStringMap {
    private static class Entry {
        String key;
        String value;
        Entry next;

        Entry(String key, String value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Entry[] buckets;
    private int size;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    public ResizableStringMap() {
        this(7);
    }

    public ResizableStringMap(int initialCapacity) {
        this.buckets = new Entry[Math.max(initialCapacity, 1)];
        this.size = 0;
    }

    private int hash(String key) {
        if (key == null) return 0;
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public void put(String key, String value) {
        if ((double) (size + 1) / buckets.length > LOAD_FACTOR_THRESHOLD) {
            resize(buckets.length * 2 + 1);
        }

        int index = hash(key);
        Entry curr = buckets[index];
        while (curr != null) {
            if (Objects.equals(curr.key, key)) {
                curr.value = value;
                return;
            }
            curr = curr.next;
        }

        buckets[index] = new Entry(key, value, buckets[index]);
        size++;
    }

    public String get(String key) {
        int index = hash(key);
        Entry curr = buckets[index];
        while (curr != null) {
            if (Objects.equals(curr.key, key)) {
                return curr.value;
            }
            curr = curr.next;
        }
        return null;
    }

    public boolean remove(String key) {
        int index = hash(key);
        Entry curr = buckets[index];
        Entry prev = null;

        while (curr != null) {
            if (Objects.equals(curr.key, key)) {
                if (prev == null) {
                    buckets[index] = curr.next;
                } else {
                    prev.next = curr.next;
                }
                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    private void resize(int newCapacity) {
        Entry[] oldBuckets = buckets;
        buckets = new Entry[newCapacity];
        size = 0;

        for (Entry head : oldBuckets) {
            Entry curr = head;
            while (curr != null) {
                put(curr.key, curr.value);
                curr = curr.next;
            }
        }
    }

    public int size() {
        return size;
    }

    public int getBucketCount() {
        return buckets.length;
    }
}