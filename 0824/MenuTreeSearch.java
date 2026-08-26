public class MenuTreeSearch {

    static class Node {
        String name;
        Node left, right;
        Node(String name) { this.name = name; }
    }

    static boolean contains(Node root, String target) {
        if (root == null) return false;
        if (root.name.equals(target)) return true;
        return contains(root.left, target) || contains(root.right, target);
    }

    // 找不到時回傳 -1
    static int findDepth(Node root, String target) {
        return findDepthHelper(root, target, 0);
    }
    private static int findDepthHelper(Node node, String target, int currentDepth) {
        if (node == null) return -1;
        if (node.name.equals(target)) return currentDepth;
        int leftResult = findDepthHelper(node.left, target, currentDepth + 1);
        if (leftResult != -1) return leftResult;
        return findDepthHelper(node.right, target, currentDepth + 1);
    }

    static int countLeaves(Node root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        return countLeaves(root.left) + countLeaves(root.right);
    }

    static void preorderDisplay(Node root, StringBuilder sb) {
        if (root == null) return;
        sb.append(root.name).append(" ");
        preorderDisplay(root.left, sb);
        preorderDisplay(root.right, sb);
    }

    public static void main(String[] args) {
        Node main = new Node("主選單");
        Node food = new Node("餐飲");
        Node drink = new Node("飲料");
        Node coffee = new Node("咖啡");
        Node tea = new Node("茶類");
        Node dessert = new Node("甜點");
        Node cake = new Node("蛋糕");

        main.left = food;
        main.right = dessert;
        food.left = drink;
        drink.left = coffee;
        drink.right = tea;
        dessert.left = cake;

        StringBuilder sb = new StringBuilder();
        preorderDisplay(main, sb);
        System.out.println("preorder display: " + sb.toString().trim());

        System.out.println("contains(咖啡): " + contains(main, "咖啡"));
        System.out.println("contains(壽司): " + contains(main, "壽司"));

        System.out.println("findDepth(咖啡): " + findDepth(main, "咖啡"));
        System.out.println("findDepth(壽司): " + findDepth(main, "壽司"));

        System.out.println("countLeaves: " + countLeaves(main));
    }
}