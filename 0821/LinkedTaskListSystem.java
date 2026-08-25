class Task {
    String id;
    String name;

    public Task(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "[" + id + ": " + name + "]";
    }
}

class TaskNode {
    Task task;
    TaskNode next;

    public TaskNode(Task task) {
        this.task = task;
        this.next = null;
    }
}

class TaskLinkedList {
    private TaskNode head;
    private int size = 0;

    public int size() {
        return size;
    }

    public boolean containsId(String id) {
        TaskNode curr = head;
        while (curr != null) {
            if (curr.task.id.equals(id)) return true;
            curr = curr.next;
        }
        return false;
    }

    public boolean addFirst(Task task) {
        if (containsId(task.id)) {
            System.out.println("新增失敗，ID 已存在: " + task.id);
            return false;
        }
        TaskNode newNode = new TaskNode(task);
        newNode.next = head;
        head = newNode;
        size++;
        return true;
    }

    public boolean addLast(Task task) {
        if (containsId(task.id)) {
            System.out.println("新增失敗，ID 已存在: " + task.id);
            return false;
        }
        TaskNode newNode = new TaskNode(task);
        if (head == null) {
            head = newNode;
        } else {
            TaskNode curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = newNode;
        }
        size++;
        return true;
    }

    public Task findById(String id) {
        TaskNode curr = head;
        while (curr != null) {
            if (curr.task.id.equals(id)) return curr.task;
            curr = curr.next;
        }
        return null;
    }

    public boolean removeById(String id) {
        if (head == null) return false;

        // 刪除 head
        if (head.task.id.equals(id)) {
            head = head.next;
            size--;
            return true;
        }

        TaskNode curr = head;
        while (curr.next != null && !curr.next.task.id.equals(id)) {
            curr = curr.next;
        }

        // 找到要刪除的節點 (curr.next)
        if (curr.next != null) {
            curr.next = curr.next.next; //涵蓋 middle 與 tail
            size--;
            return true;
        }

        return false; // 找不到 id
    }

    public boolean insertAfter(String existingId, Task task) {
        if (containsId(task.id)) {
            System.out.println("插入失敗，新 ID 已存在: " + task.id);
            return false;
        }
        TaskNode curr = head;
        while (curr != null && !curr.task.id.equals(existingId)) {
            curr = curr.next;
        }
        if (curr == null) {
            System.out.println("插入失敗，找不到目標 ID: " + existingId);
            return false;
        }
        TaskNode newNode = new TaskNode(task);
        newNode.next = curr.next;
        curr.next = newNode;
        size++;
        return true;
    }

    public void printAll() {
        System.out.print("List 內容 (size=" + size + "): ");
        TaskNode curr = head;
        while (curr != null) {
            System.out.print(curr.task + " -> ");
            curr = curr.next;
        }
        System.out.println("null");
    }
}

public class LinkedTaskListSystem {
    public static void main(String[] args) {
        TaskLinkedList list = new TaskLinkedList();

        System.out.println("=== 測試 1: 空 list 刪除與搜尋 ===");
        System.out.println("刪除是否存在: " + list.removeById("T1"));
        System.out.println("搜尋是否存在: " + list.findById("T1"));

        System.out.println("\n=== 測試 2: 建立列表與重複 ID 阻擋 ===");
        list.addLast(new Task("T1", "Task 1"));
        list.addLast(new Task("T2", "Task 2"));
        list.addLast(new Task("T3", "Task 3"));
        list.addFirst(new Task("T0", "Task 0"));
        list.addLast(new Task("T1", "Duplicate Task")); // 應阻擋
        list.printAll();

        System.out.println("\n=== 測試 3: insertAfter ===");
        list.insertAfter("T2", new Task("T2.5", "Inserted Task"));
        list.printAll();

        System.out.println("\n=== 測試 4: 刪除 Middle (T2.5) ===");
        list.removeById("T2.5");
        list.printAll();

        System.out.println("\n=== 測試 5: 刪除 Head (T0) ===");
        list.removeById("T0");
        list.printAll();

        System.out.println("\n=== 測試 6: 刪除 Tail (T3) ===");
        list.removeById("T3");
        list.printAll();

        System.out.println("\n=== 測試 7: 刪除不存在 ID ===");
        System.out.println("刪除 T999: " + list.removeById("T999"));
    }
}
