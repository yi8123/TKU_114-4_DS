public class TraversalSelector {

    static class Node {
        String val;
        Node left, right;
        Node(String val) { this.val = val; }
        Node(String val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static String toPrefix(Node root) {
        if (root == null) return "";
        String left = toPrefix(root.left);
        String right = toPrefix(root.right);
        return (root.val + " " + left + " " + right).replaceAll("\\s+", " ").trim();
    }

    public static String toInfix(Node root) {
        if (root == null) return "";
        if (root.left == null && root.right == null) return root.val;
        return "(" + toInfix(root.left) + " " + root.val + " " + toInfix(root.right) + ")";
    }

    public static String toPostfix(Node root) {
        if (root == null) return "";
        String left = toPostfix(root.left);
        String right = toPostfix(root.right);
        return (left + " " + right + " " + root.val).replaceAll("\\s+", " ").trim();
    }

    public static void main(String[] args) {
        Node root = new Node("*",
                new Node("+", new Node("A"), new Node("B")),
                new Node("-", new Node("C"), new Node("/", new Node("D"), new Node("E")))
        );

        System.out.println("Prefix  (Preorder) : " + toPrefix(root));
        System.out.println("Infix   (Inorder)  : " + toInfix(root));
        System.out.println("Postfix (Postorder): " + toPostfix(root));
    }
}