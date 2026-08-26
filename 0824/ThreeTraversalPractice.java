public class ThreeTraversalPractice {

    static class Node {
        String value;
        Node left, right;
        Node(String value) { this.value = value; }
    }

    // preorder: root -> left -> right
    static void preorder(Node node, StringBuilder sb) {
        if (node == null) return;
        sb.append(node.value).append(" ");
        preorder(node.left, sb);
        preorder(node.right, sb);
    }

    // inorder: left -> root -> right
    static void inorder(Node node, StringBuilder sb) {
        if (node == null) return;
        inorder(node.left, sb);
        sb.append(node.value).append(" ");
        inorder(node.right, sb);
    }

    // postorder: left -> right -> root
    static void postorder(Node node, StringBuilder sb) {
        if (node == null) return;
        postorder(node.left, sb);
        postorder(node.right, sb);
        sb.append(node.value).append(" ");
    }

    public static void main(String[] args) {
        // 建立 M(F(B,null), T(R,Z))
        Node b = new Node("B");
        Node f = new Node("F");
        f.left = b;
        f.right = null;

        Node r = new Node("R");
        Node z = new Node("Z");
        Node t = new Node("T");
        t.left = r;
        t.right = z;

        Node m = new Node("M");
        m.left = f;
        m.right = t;

        StringBuilder pre = new StringBuilder();
        preorder(m, pre);
        System.out.println("preorder: " + pre.toString().trim());

        StringBuilder in = new StringBuilder();
        inorder(m, in);
        System.out.println("inorder: " + in.toString().trim());

        StringBuilder post = new StringBuilder();
        postorder(m, post);
        System.out.println("postorder: " + post.toString().trim());
    }
}
