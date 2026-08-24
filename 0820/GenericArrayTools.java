import java.util.Arrays;
import java.util.Objects;

public class GenericArrayTools {

    public static <T> int countMatches(T[] data, T target) {
        if (data == null || data.length == 0) return 0;
        int count = 0;
        for (T item : data) {
            if (Objects.equals(item, target)) {
                count++;
            }
        }
        return count;
    }

    public static <T> T last(T[] data) {
        if (data == null || data.length == 0) return null;
        return data[data.length - 1];
    }

    public static <T> void swap(T[] data, int first, int second) {
        if (data == null || data.length == 0) return;
        if (first < 0 || first >= data.length || second < 0 || second >= data.length) return;
        T temp = data[first];
        data[first] = data[second];
        data[second] = temp;
    }

    public static void main(String[] args) {
        String[] words = {"apple", null, "banana", "apple", "cherry"};
        System.out.println("Matches 'apple': " + countMatches(words, "apple"));
        System.out.println("Matches null: " + countMatches(words, null));
        System.out.println("Last element: " + last(words));

        swap(words, 0, 4);
        System.out.println("After swap (0, 4): " + Arrays.toString(words));

        // 邊界測試
        Integer[] empty = new Integer[0];
        System.out.println("Empty last: " + last(empty));
        swap(words, -1, 10); // 不合法 index 不崩潰
    }
}