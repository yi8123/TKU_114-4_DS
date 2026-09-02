import java.util.*;

public class LowestKPriceTracker {

    public static List<Integer> getLowestKPrices(List<Integer> prices, int k) {
        if (k <= 0 || prices == null) {
            return Collections.emptyList();
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (Integer price : prices) {
            if (price == null || price < 0) {
                continue;
            }

            if (maxHeap.size() < k) {
                maxHeap.offer(price);
            } else if (price < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.offer(price);
            }
        }

        List<Integer> result = new ArrayList<>(maxHeap);
        Collections.sort(result);
        return result;
    }

    public static void main(String[] args) {
        List<Integer> prices = Arrays.asList(120, 50, null, -10, 80, 20, 300, 10, 50);
        int k = 4;
        List<Integer> lowestK = getLowestKPrices(prices, k);
        System.out.println("Lowest " + k + " prices: " + lowestK);
    }
}