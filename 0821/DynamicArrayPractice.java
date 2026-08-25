import java.util.Arrays;

@SuppressWarnings("unchecked")
class DynamicArray<T> {
    private Object[] data;
    private int size = 0;

    public DynamicArray() {
        this(4); // 預設初始容量
    }

    public DynamicArray(int capacity) {
        this.data = new Object[capacity];
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return data.length;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            int newCapacity = data.length * 2;
            data = Arrays.copyOf(data, newCapacity);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    public void add(T value) {
        ensureCapacity();
        data[size++] = value;
    }

    public void add(int index, T value) {
        checkIndexForAdd(index);
        ensureCapacity();
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = value;
        size++;
    }

    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    public T set(int index, T value) {
        checkIndex(index);
        T oldValue = (T) data[index];
        data[index] = value;
        return oldValue;
    }

    public T remove(int index) {
        checkIndex(index);
        T oldValue = (T) data[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(data, index + 1, data, index, numMoved);
        }
        data[--size] = null; // 移除後最後一個無效格要設為 null
        return oldValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}

public class DynamicArrayPractice {
    public static void main(String[] args) {
        System.out.println("=== 測試 String 型別與動態擴充 ===");
        DynamicArray<String> strArray = new DynamicArray<>(2);
        strArray.add("A");
        strArray.add("B");
        System.out.println("擴充前容量: " + strArray.capacity()); // 2
        strArray.add("C"); // 觸發擴充為 4
        System.out.println("擴充後容量: " + strArray.capacity()); // 4
        System.out.println("內容: " + strArray);

        strArray.add(1, "INSERT");
        System.out.println("插入後內容: " + strArray);
        strArray.remove(1);
        System.out.println("刪除後內容: " + strArray);

        System.out.println("\n=== 測試 Integer 型別 ===");
        DynamicArray<Integer> intArray = new DynamicArray<>();
        intArray.add(10);
        intArray.add(20);
        intArray.add(30);
        System.out.println("Integer Array: " + intArray);

        System.out.println("\n=== 邊界與異常測試 ===");
        try {
            System.out.println("測試刪除 index -1:");
            intArray.remove(-1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("捕獲預期例外: " + e.getMessage());
        }

        try {
            System.out.println("測試存取 index == size:");
            intArray.get(intArray.size());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("捕獲預期例外: " + e.getMessage());
        }

        DynamicArray<String> emptyArray = new DynamicArray<>();
        try {
            System.out.println("測試空結構刪除:");
            emptyArray.remove(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("捕獲預期例外: " + e.getMessage());
        }
    }
}