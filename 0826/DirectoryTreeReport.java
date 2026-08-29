import java.util.ArrayList;
import java.util.List;

public class DirectoryTreeReport {

    enum NodeType {
        FILE, DIRECTORY
    }

    static class FsNode {
        String name;
        NodeType type;
        long size;
        List<FsNode> children;

        public static FsNode createFile(String name, long size) {
            FsNode node = new FsNode();
            node.name = name;
            node.type = NodeType.FILE;
            node.size = size;
            node.children = new ArrayList<>();
            return node;
        }

        public static FsNode createDirectory(String name) {
            FsNode node = new FsNode();
            node.name = name;
            node.type = NodeType.DIRECTORY;
            node.size = 0;
            node.children = new ArrayList<>();
            return node;
        }

        public void addChild(FsNode child) {
            if (this.type == NodeType.DIRECTORY && child != null) {
                this.children.add(child);
            }
        }
    }

    public static long calculateDirectorySizes(FsNode node) {
        if (node == null) return 0;

        if (node.type == NodeType.FILE) {
            return node.size;
        }

        long totalSize = 0;
        for (FsNode child : node.children) {
            totalSize += calculateDirectorySizes(child);
        }
        node.size = totalSize;
        return node.size;
    }

    public static int countTotalNodes(FsNode node) {
        if (node == null) return 0;
        int count = 1;
        for (FsNode child : node.children) {
            count += countTotalNodes(child);
        }
        return count;
    }

    public static int countFiles(FsNode node) {
        if (node == null) return 0;
        if (node.type == NodeType.FILE) return 1;
        int count = 0;
        for (FsNode child : node.children) {
            count += countFiles(child);
        }
        return count;
    }

    public static int countDirectories(FsNode node) {
        if (node == null) return 0;
        int count = (node.type == NodeType.DIRECTORY) ? 1 : 0;
        for (FsNode child : node.children) {
            count += countDirectories(child);
        }
        return count;
    }

    public static int calculateHeight(FsNode node) {
        if (node == null) return -1;
        if (node.children.isEmpty()) return 0;
        int maxChildHeight = 0;
        for (FsNode child : node.children) {
            maxChildHeight = Math.max(maxChildHeight, calculateHeight(child));
        }
        return 1 + maxChildHeight;
    }

    public static FsNode findLargestFile(FsNode node) {
        if (node == null) return null;
        FsNode largest = (node.type == NodeType.FILE) ? node : null;
        for (FsNode child : node.children) {
            FsNode candidate = findLargestFile(child);
            if (candidate != null) {
                if (largest == null || candidate.size > largest.size) {
                    largest = candidate;
                }
            }
        }
        return largest;
    }

    public static void printTree(FsNode node, String indent) {
        if (node == null) return;
        String typeTag = (node.type == NodeType.DIRECTORY) ? "[DIR] " : "[FILE]";
        System.out.printf("%s%s %-18s (%d bytes)%n", indent, typeTag, node.name, node.size);
        for (FsNode child : node.children) {
            printTree(child, indent + "  ");
        }
    }

    public static void main(String[] args) {
        FsNode root = FsNode.createDirectory("root");

        FsNode home = FsNode.createDirectory("home");
        FsNode user = FsNode.createDirectory("alice");
        user.addChild(FsNode.createFile("resume.pdf", 2048));
        user.addChild(FsNode.createFile("photo.jpg", 10485760));
        home.addChild(user);

        FsNode etc = FsNode.createDirectory("etc");
        etc.addChild(FsNode.createFile("hosts", 512));
        etc.addChild(FsNode.createFile("nginx.conf", 4096));

        FsNode var = FsNode.createDirectory("var");
        FsNode log = FsNode.createDirectory("log");
        log.addChild(FsNode.createFile("syslog.log", 52428800));
        log.addChild(FsNode.createFile("access.log", 20971520));
        var.addChild(log);

        root.addChild(home);
        root.addChild(etc);
        root.addChild(var);
        root.addChild(FsNode.createFile("boot.ini", 128));

        calculateDirectorySizes(root);

        System.out.println("================ 檔案系統結構報表 ================");
        printTree(root, "");

        System.out.println("\n================ 統計資訊報告 ================");
        System.out.println("Total Nodes     : " + countTotalNodes(root));
        System.out.println("File Count      : " + countFiles(root));
        System.out.println("Directory Count : " + countDirectories(root));
        System.out.println("Tree Height     : " + calculateHeight(root));
        
        FsNode largest = findLargestFile(root);
        if (largest != null) {
            System.out.printf("Largest File    : %s (%d bytes)%n", largest.name, largest.size);
        }
    }
}