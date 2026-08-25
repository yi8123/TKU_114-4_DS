import java.util.ArrayDeque;
import java.util.Deque;

public class TextEditorHistory {
    private Deque<String> undoStack = new ArrayDeque<>();
    private Deque<String> redoStack = new ArrayDeque<>();

    // 新增操作
    public void performAction(String action) {
        undoStack.push(action);
        redoStack.clear(); // 新增操作後清空 redo
        System.out.println("執行操作: " + action);
        printStatus();
    }

    // Undo
    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("無法 Undo: Undo stack 為空");
            return;
        }
        String action = undoStack.pop();
        redoStack.push(action);
        System.out.println("復原 (Undo): " + action);
        printStatus();
    }

    // Redo
    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("無法 Redo: Redo stack 為空");
            return;
        }
        String action = redoStack.pop();
        undoStack.push(action);
        System.out.println("重做 (Redo): " + action);
        printStatus();
    }

    public void printStatus() {
        System.out.println("  [Undo Stack]: " + undoStack);
        System.out.println("  [Redo Stack]: " + redoStack);
        System.out.println("----------------------------------------");
    }

    public static void main(String[] args) {
        TextEditorHistory editor = new TextEditorHistory();

        // 邊界測試
        editor.undo();
        editor.redo();

        // 操作流程
        editor.performAction("打字 'Hello'");
        editor.performAction("打字 ' World'");
        editor.performAction("刪除字元");

        editor.undo();
        editor.undo();

        editor.redo();

        editor.performAction("插入標點 '!'"); // 應清空 redoStack
        editor.redo(); // 應失敗
    }
}