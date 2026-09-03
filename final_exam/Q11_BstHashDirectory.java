import java.util.*;

public class Q11_BstHashDirectory {
    private static class Node {
        int id;
        Node left, right;

        Node(int id) {
            this.id = id;
        }
    }

    private Node root;
    private final Map<Integer, String> hashMap = new HashMap<>();

    public boolean add(int id, String name) {
        if (id <= 0 || name == null) {
            return false;
        }
        String trimmed = name.trim();
        if (trimmed.isEmpty() || hashMap.containsKey(id)) {
            return false;
        }

        root = insertBst(root, id);
        hashMap.put(id, trimmed);
        return true;
    }

    private Node insertBst(Node node, int id) {
        if (node == null) {
            return new Node(id);
        }
        if (id < node.id) {
            node.left = insertBst(node.left, id);
        } else if (id > node.id) {
            node.right = insertBst(node.right, id);
        }
        return node;
    }

    public String findName(int id) {
        return hashMap.get(id);
    }

    public boolean remove(int id) {
        if (id <= 0 || !hashMap.containsKey(id)) {
            return false;
        }
        root = removeBst(root, id);
        hashMap.remove(id);
        return true;
    }

    private Node removeBst(Node node, int id) {
        if (node == null) {
            return null;
        }
        if (id < node.id) {
            node.left = removeBst(node.left, id);
        } else if (id > node.id) {
            node.right = removeBst(node.right, id);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            Node minNode = findMin(node.right);
            node.id = minNode.id;
            node.right = removeBst(node.right, minNode.id);
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public List<Integer> idsBetween(int low, int high) {
        List<Integer> result = new ArrayList<>();
        if (low > high) {
            return result;
        }
        rangeHelper(root, low, high, result);
        return result;
    }

    private void rangeHelper(Node node, int low, int high, List<Integer> result) {
        if (node == null) return;
        if (node.id > low) {
            rangeHelper(node.left, low, high, result);
        }
        if (node.id >= low && node.id <= high) {
            result.add(node.id);
        }
        if (node.id < high) {
            rangeHelper(node.right, low, high, result);
        }
    }

    public int size() {
        return hashMap.size();
    }
}