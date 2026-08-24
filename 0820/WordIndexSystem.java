import java.util.*;

public class WordIndexSystem {
    public static void main(String[] args) {
        String[] sentences = {
            "Hello world, welcome to Java world.",
            "Java is great, and programming in Java is fun.",
            "Welcome back, happy coding!"
        };

        Map<String, Integer> wordFrequency = new HashMap<>();
        Set<String> uniqueWords = new HashSet<>();

        for (String sentence : sentences) {
            String cleaned = sentence.replaceAll("[,.]", "").toLowerCase();
            String[] tokens = cleaned.split("\\s+");
            for (String token : tokens) {
                if (token.isEmpty()) continue;
                uniqueWords.add(token);
                wordFrequency.put(token, wordFrequency.getOrDefault(token, 0) + 1);
            }
        }

        System.out.println("=== 所有不重複單字 (Total: " + uniqueWords.size() + ") ===");
        System.out.println(uniqueWords);

        System.out.println("\n=== 出現至少兩次的單字 ===");
        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            if (entry.getValue() >= 2) {
                System.out.println(entry.getKey() + " -> " + entry.getValue() + " 次");
            }
        }
    }
}