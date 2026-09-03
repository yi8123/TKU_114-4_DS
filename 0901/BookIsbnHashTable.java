import java.util.Objects;

public class BookIsbnHashTable {
    private static class Entry {
        String isbn;
        String title;
        Entry next;

        Entry(String isbn, String title, Entry next) {
            this.isbn = isbn;
            this.title = title;
            this.next = next;
        }
    }

    private Entry[] buckets;
    private int size;
    private static final double RESIZE_THRESHOLD = 0.75;

    public BookIsbnHashTable() {
        this(11);
    }

    public BookIsbnHashTable(int capacity) {
        this.buckets = new Entry[Math.max(capacity, 1)];
        this.size = 0;
    }

    private int hash(String isbn) {
        if (isbn == null) return 0;
        return Math.abs(isbn.hashCode()) % buckets.length;
    }

    public void put(String isbn, String title) {
        if ((double) (size + 1) / buckets.length > RESIZE_THRESHOLD) {
            resize(buckets.length * 2 + 1);
        }

        int index = hash(isbn);
        Entry curr = buckets[index];
        while (curr != null) {
            if (Objects.equals(curr.isbn, isbn)) {
                curr.title = title;
                return;
            }
            curr = curr.next;
        }

        buckets[index] = new Entry(isbn, title, buckets[index]);
        size++;
    }

    public String get(String isbn) {
        int index = hash(isbn);
        Entry curr = buckets[index];
        while (curr != null) {
            if (Objects.equals(curr.isbn, isbn)) {
                return curr.title;
            }
            curr = curr.next;
        }
        return null;
    }

    public boolean remove(String isbn) {
        int index = hash(isbn);
        Entry curr = buckets[index];
        Entry prev = null;

        while (curr != null) {
            if (Objects.equals(curr.isbn, isbn)) {
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
                put(curr.isbn, curr.title);
                curr = curr.next;
            }
        }
    }

    public int size() {
        return size;
    }

    public double getLoadFactor() {
        return (double) size / buckets.length;
    }

    public void printBucketReport() {
        System.out.printf("=== Bucket Report (Size: %d, Buckets: %d, Load Factor: %.2f) ===%n", 
                          size, buckets.length, getLoadFactor());
        for (int i = 0; i < buckets.length; i++) {
            System.out.printf("Bucket [%2d]: ", i);
            Entry curr = buckets[i];
            if (curr == null) {
                System.out.println("(empty)");
            } else {
                StringBuilder sb = new StringBuilder();
                while (curr != null) {
                    sb.append(String.format("[%s: %s] -> ", curr.isbn, curr.title));
                    curr = curr.next;
                }
                sb.append("null");
                System.out.println(sb);
            }
        }
    }
}