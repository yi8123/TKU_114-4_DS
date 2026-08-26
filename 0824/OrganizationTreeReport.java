import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class OrganizationTreeReport {

    static class OrgNode {
        String title;
        OrgNode left, right;
        OrgNode(String title) { this.title = title; }
    }

    // 找不到單位時回傳 null，不拋例外
    static OrgNode findParent(OrgNode root, String target) {
        if (root == null) return null;
        if ((root.left != null && root.left.title.equals(target)) ||
            (root.right != null && root.right.title.equals(target))) {
            return root;
        }
        OrgNode leftResult = findParent(root.left, target);
        if (leftResult != null) return leftResult;
        return findParent(root.right, target);
    }

    // 找不到時回傳 -1
    static int findDepth(OrgNode root, String target) {
        return findDepthHelper(root, target, 0);
    }
    private static int findDepthHelper(OrgNode node, String target, int depth) {
        if (node == null) return -1;
        if (node.title.equals(target)) return depth;
        int leftResult = findDepthHelper(node.left, target, depth + 1);
        if (leftResult != -1) return leftResult;
        return findDepthHelper(node.right, target, depth + 1);
    }

    // 找不到單位時回傳空 list，不發生例外
    static List<String> pathFromRoot(OrgNode root, String target) {
        List<String> path = new ArrayList<>();
        boolean found = pathHelper(root, target, path);
        if (!found) {
            path.clear();
        }
        return path;
    }
    private static boolean pathHelper(OrgNode node, String target, List<String> path) {
        if (node == null) return false;
        path.add(node.title);
        if (node.title.equals(target)) return true;
        if (pathHelper(node.left, target, path)) return true;
        if (pathHelper(node.right, target, path)) return true;
        path.remove(path.size() - 1);
        return false;
    }

    static void printByLevel(OrgNode root) {
        if (root == null) {
            System.out.println("(empty organization)");
            return;
        }
        Queue<OrgNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int level = 0;
        while (!queue.isEmpty()) {
            int countInLevel = queue.size();
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < countInLevel; i++) {
                OrgNode current = queue.poll();
                line.append(current.title).append(" ");
                if (current.left != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);
            }
            System.out.println("Level " + level + ": " + line.toString().trim());
            level++;
        }
    }

    public static void main(String[] args) {
        OrgNode ceo = new OrgNode("執行長");
        OrgNode cto = new OrgNode("技術長");
        OrgNode cfo = new OrgNode("財務長");
        OrgNode devLead = new OrgNode("開發主管");
        OrgNode qaLead = new OrgNode("測試主管");
        OrgNode accountant = new OrgNode("會計");

        ceo.left = cto;
        ceo.right = cfo;
        cto.left = devLead;
        cto.right = qaLead;
        cfo.left = accountant;

        printByLevel(ceo);

        OrgNode parent = findParent(ceo, "開發主管");
        System.out.println("findParent(開發主管): " + (parent == null ? "null" : parent.title));

        System.out.println("findDepth(測試主管): " + findDepth(ceo, "測試主管"));
        System.out.println("findDepth(不存在): " + findDepth(ceo, "不存在的職位"));

        System.out.println("pathFromRoot(會計): " + pathFromRoot(ceo, "會計"));
        System.out.println("pathFromRoot(不存在): " + pathFromRoot(ceo, "不存在的職位"));

        OrgNode notFoundParent = findParent(ceo, "不存在的職位");
        System.out.println("findParent(不存在): " + (notFoundParent == null ? "null" : notFoundParent.title));
    }
}