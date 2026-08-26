public class RecursiveDigitReport {

    // 計算各位數字總和（遞迴，不使用 loop 或 String 轉換）
    public static int digitSum(int n) {
        n = Math.abs(n);
        return digitSumHelper(n);
    }
    private static int digitSumHelper(int n) {
        if (n < 10) return n;
        return n % 10 + digitSumHelper(n / 10);
    }

    // 計算位數（digitCount(0) 回傳 1）
    public static int digitCount(int n) {
        n = Math.abs(n);
        return digitCountHelper(n);
    }
    private static int digitCountHelper(int n) {
        if (n < 10) return 1;
        return 1 + digitCountHelper(n / 10);
    }

    // 計算特定數字出現次數
    public static int countDigit(int n, int digit) {
        n = Math.abs(n);
        return countDigitHelper(n, digit);
    }
    private static int countDigitHelper(int n, int digit) {
        if (n < 10) return (n == digit) ? 1 : 0;
        int last = n % 10;
        return (last == digit ? 1 : 0) + countDigitHelper(n / 10, digit);
    }

    public static void main(String[] args) {
        int[] tests = {50205, 0, -731};
        for (int t : tests) {
            System.out.println("=== n = " + t + " ===");
            System.out.println("digitSum: " + digitSum(t));
            System.out.println("digitCount: " + digitCount(t));
            for (int d = 0; d <= 9; d++) {
                int c = countDigit(t, d);
                if (c > 0) {
                    System.out.println("countDigit(" + d + "): " + c);
                }
            }
            System.out.println();
        }
    }
}