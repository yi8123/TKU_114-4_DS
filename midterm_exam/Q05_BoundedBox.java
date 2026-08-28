import java.util.ArrayList;
import java.util.List;

public class Q05_BoundedBox<T extends Comparable<T>> {
    private final int capacity;
    private final List<T> elements;

    public Q05_BoundedBox(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be at least 1");
        }
        this.capacity = capacity;
        this.elements = new ArrayList<>(capacity);
    }

    public boolean add(T value) {
        if (value == null || elements.size() >= capacity) {
            return false;
        }
        elements.add(value);
        return true;
    }

    public int size() {
        return elements.size();
    }

    public boolean isFull() {
        return elements.size() >= capacity;
    }

    public T minimum() {
        if (elements.isEmpty()) {
            return null;
        }
        T min = elements.get(0);
        for (int i = 1; i < elements.size(); i++) {
            if (elements.get(i).compareTo(min) < 0) {
                min = elements.get(i);
            }
        }
        return min;
    }

    public T maximum() {
        if (elements.isEmpty()) {
            return null;
        }
        T max = elements.get(0);
        for (int i = 1; i < elements.size(); i++) {
            if (elements.get(i).compareTo(max) > 0) {
                max = elements.get(i);
            }
        }
        return max;
    }

    public int countGreaterThan(T threshold) {
        if (threshold == null || elements.isEmpty()) {
            return 0;
        }
        int count = 0;
        for (T item : elements) {
            if (item.compareTo(threshold) > 0) {
                count++;
            }
        }
        return count;
    }

    public List<T> snapshot() {
        return new ArrayList<>(elements);
    }
}