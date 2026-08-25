import java.util.ArrayDeque;
import java.util.Deque;

public class BrowserBackStack {
    private Deque<String> history = new ArrayDeque<>();

    public void visit(String url) {
        history.push(url);
        System.out.println("訪問頁面: " + url);
    }

    public String back() {
        if (history.isEmpty()) {
            System.out.println("警告: 歷程為空，無法返回");
            return null;
        }
        String popped = history.pop();
        System.out.println("返回離開: " + popped);
        return popped;
    }

    public String current() {
        if (history.isEmpty()) {
            System.out.println("當前無開啟頁面");
            return null;
        }
        return history.peek();
    }

    public static void main(String[] args) {
        BrowserBackStack browser = new BrowserBackStack();

        // 測試 1: 空 stack 時操作
        browser.current();
        browser.back();

        // 連續測試至少五個操作
        browser.visit("https://google.com");
        browser.visit("https://github.com");
        browser.visit("https://stackoverflow.com");

        System.out.println("目前頁面: " + browser.current());

        browser.back();
        System.out.println("目前頁面: " + browser.current());

        browser.back();
        browser.back();
        
        // 再次測試空 stack
        browser.back();
    }
}