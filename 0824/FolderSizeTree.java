public class FolderSizeTree {

    static class FolderNode {
        String name;
        int ownSize;
        FolderNode left, right;
        FolderNode(String name, int ownSize) {
            this.name = name;
            this.ownSize = ownSize;
        }
    }

    // 使用 postorder 計算 subtree size
    static int subtreeSize(FolderNode node) {
        if (node == null) return 0;
        int leftSize = subtreeSize(node.left);
        int rightSize = subtreeSize(node.right);
        return node.ownSize + leftSize + rightSize;
    }

    // 回傳 {name, size} 找出最大 subtree
    private static String[] largestSubtreeHelper(FolderNode node) {
        if (node == null) return new String[]{"(none)", "-1"};
        String[] best = new String[]{node.name, String.valueOf(subtreeSize(node))};
        String[] leftBest = largestSubtreeHelper(node.left);
        if (Integer.parseInt(leftBest[1]) > Integer.parseInt(best[1])) {
            best = leftBest;
        }
        String[] rightBest = largestSubtreeHelper(node.right);
        if (Integer.parseInt(rightBest[1]) > Integer.parseInt(best[1])) {
            best = rightBest;
        }
        return best;
    }

    static void printLeafFolders(FolderNode node) {
        if (node == null) return;
        if (node.left == null && node.right == null) {
            System.out.print(node.name + " ");
            return;
        }
        printLeafFolders(node.left);
        printLeafFolders(node.right);
    }

    public static void main(String[] args) {
        FolderNode root = new FolderNode("根目錄", 10);
        FolderNode docs = new FolderNode("文件", 20);
        FolderNode pics = new FolderNode("圖片", 15);
        FolderNode work = new FolderNode("工作", 5);
        FolderNode personal = new FolderNode("個人", 8);
        FolderNode vacation = new FolderNode("旅遊", 50);
        FolderNode screenshots = new FolderNode("截圖", 12);

        root.left = docs;
        root.right = pics;
        docs.left = work;
        docs.right = personal;
        pics.left = vacation;
        pics.right = screenshots;

        System.out.println("total size: " + subtreeSize(root));
        String[] largest = largestSubtreeHelper(root);
        System.out.println("largest subtree: " + largest[0] + " (size=" + largest[1] + ")");
        System.out.print("leaf folders: ");
        printLeafFolders(root);
        System.out.println();
    }
}