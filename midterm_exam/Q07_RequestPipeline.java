import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class Q07_RequestPipeline {
    public static boolean isBalanced(String text) {
        if (text == null) {
            return false;
        }
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.pop();
                if ((c == ')' && top != '(') ||
                    (c == ']' && top != '[') ||
                    (c == '}' && top != '{')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static List<String> process(String[] commands) {
        if (commands == null) {
            return Collections.emptyList();
        }
        Deque<String> normalQueue = new ArrayDeque<>();
        Deque<String> urgentQueue = new ArrayDeque<>();
        List<String> result = new ArrayList<>();

        for (String cmd : commands) {
            if (cmd == null || cmd.isBlank()) {
                continue;
            }
            cmd = cmd.trim();
            if (cmd.equals("PROCESS")) {
                if (!urgentQueue.isEmpty()) {
                    result.add(urgentQueue.poll());
                } else if (!normalQueue.isEmpty()) {
                    result.add(normalQueue.poll());
                } else {
                    result.add("EMPTY");
                }
            } else if (cmd.startsWith("URGENT ")) {
                String id = cmd.substring(7).trim();
                if (!id.isEmpty()) {
                    urgentQueue.offer(id);
                }
            } else if (cmd.startsWith("NORMAL ")) {
                String id = cmd.substring(7).trim();
                if (!id.isEmpty()) {
                    normalQueue.offer(id);
                }
            }
        }
        return result;
    }
}