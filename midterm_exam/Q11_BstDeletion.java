import java.util.ArrayList;
import java.util.List;

public class Q11_BstDeletion {
    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    private Node root;
    private int size = 0;

    public boolean add(int value) {
        if (root == null) {
            root = new Node(value);
            size++;
            return true;
        }
        Node curr = root;
        while (true) {
            if (value == curr.value) {
                return false;
            } else if (value < curr.value) {
                if (curr.left == null) {
                    curr.left = new Node(value);
                    size++;
                    return true;
                }
                curr = curr.left;
            } else {
                if (curr.right == null) {
                    curr.right = new Node(value);
                    size++;
                    return true;
                }
                curr = curr.right;
            }
        }
    }

    public boolean contains(int value) {
        Node curr = root;
        while (curr != null) {
            if (value == curr.value) {
                return true;
            } else if (value < curr.value) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return false;
    }

    public boolean remove(int value) {
        if (!contains(value)) {
            return false;
        }
        root = deleteNode(root, value);
        size--;
        return true;
    }

    private Node deleteNode(Node node, int target) {
        if (node == null) return null;
        if (target < node.value) {
            node.left = deleteNode(node.left, target);
        } else if (target > node.value) {
            node.right = deleteNode(node.right, target);
        } else {
            // Case 1 & 2: Leaf or One Child
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // Case 3: Two Children (Right subtree minimum)
            Node successor = findMin(node.right);
            node.value = successor.value;
            node.right = deleteNode(node.right, successor.value);
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public int size() {
        return size;
    }

    public List<Integer> inorder() {
        List<Integer> list = new ArrayList<>();
        inorderHelper(root, list);
        return list;
    }

    private void inorderHelper(Node node, List<Integer> list) {
        if (node == null) return;
        inorderHelper(node.left, list);
        list.add(node.value);
        inorderHelper(node.right, list);
    }

    public boolean isValid() {
        return isValidBst(root, null, null);
    }

    private boolean isValidBst(Node node, Integer min, Integer max) {
        if (node == null) return true;
        if (min != null && node.value <= min) return false;
        if (max != null && node.value >= max) return false;
        return isValidBst(node.left, min, node.value) && isValidBst(node.right, node.value, max);
    }
}