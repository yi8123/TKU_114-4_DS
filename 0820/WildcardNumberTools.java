import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WildcardNumberTools {

    public static double average(List<? extends Number> values) {
        if (values == null || values.isEmpty()) return 0.0;
        double sum = 0.0;
        for (Number num : values) {
            if (num != null) sum += num.doubleValue();
        }
        return sum / values.size();
    }

    public static double maximum(List<? extends Number> values) {
        if (values == null || values.isEmpty()) return Double.NaN;
        double max = -Double.MAX_VALUE;
        boolean hasValidNum = false;
        for (Number num : values) {
            if (num != null) {
                max = Math.max(max, num.doubleValue());
                hasValidNum = true;
            }
        }
        return hasValidNum ? max : Double.NaN;
    }

    public static void addRange(List<? super Integer> target, int start, int end) {
        if (target == null || start > end) return;
        for (int i = start; i <= end; i++) {
            target.add(i);
        }
    }

    public static void main(String[] args) {
        List<Integer> intList = Arrays.asList(10, 20, 30, 40);
        List<Double> doubleList = Arrays.asList(1.5, 2.5, 3.5);
        List<Integer> emptyList = new ArrayList<>();

        System.out.println("Int Avg: " + average(intList) + ", Max: " + maximum(intList));
        System.out.println("Double Avg: " + average(doubleList) + ", Max: " + maximum(doubleList));
        System.out.println("Empty Avg: " + average(emptyList) + ", Max: " + maximum(emptyList));

        List<Number> numList = new ArrayList<>();
        addRange(numList, 1, 5);
        addRange(numList, 10, 5); // start > end 不加入
        System.out.println("addRange Result: " + numList);
    }
}