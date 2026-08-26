public class RecursiveTextTools {

    public static String reverse(String s) {
        if (s == null || s.length() <= 1) return s;
        return reverse(s.substring(1)) + s.charAt(0);
    }

    // 忽略英文大小寫與空白
    public static boolean isPalindrome(String s) {
        if (s == null) return false;
        String cleaned = cleanForPalindrome(s);
        return isPalindromeHelper(cleaned, 0, cleaned.length() - 1);
    }
    private static String cleanForPalindrome(String s) {
        return removeSpacesAndLower(s, 0);
    }
    private static String removeSpacesAndLower(String s, int index) {
        if (index == s.length()) return "";
        char c = s.charAt(index);
        if (c == ' ') {
            return removeSpacesAndLower(s, index + 1);
        }
        return Character.toLowerCase(c) + removeSpacesAndLower(s, index + 1);
    }
    private static boolean isPalindromeHelper(String s, int left, int right) {
        if (left >= right) return true;
        if (s.charAt(left) != s.charAt(right)) return false;
        return isPalindromeHelper(s, left + 1, right - 1);
    }

    public static int countCharacter(String s, char target) {
        if (s == null || s.isEmpty()) return 0;
        int rest = countCharacter(s.substring(1), target);
        return (s.charAt(0) == target ? 1 : 0) + rest;
    }

    public static void main(String[] args) {
        // 測試 empty、single character、Level 與一般字串
        String[] tests = {"", "a", "Level", "A man a plan a canal Panama", "hello world"};
        for (String t : tests) {
            System.out.println("=== \"" + t + "\" ===");
            System.out.println("reverse: \"" + reverse(t) + "\"");
            System.out.println("isPalindrome: " + isPalindrome(t));
            System.out.println("countCharacter('l'): " + countCharacter(t, 'l'));
            System.out.println();
        }
    }
}