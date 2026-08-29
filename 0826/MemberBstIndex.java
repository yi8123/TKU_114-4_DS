public class MemberBstIndex {

    static class Member {
        int memberId;
        String name;
        String email;

        Member(int memberId, String name, String email) {
            this.memberId = memberId;
            this.name = name;
            this.email = email;
        }

        @Override
        public String toString() {
            return String.format("[ID: %d | Name: %s | Email: %s]", memberId, name, email);
        }
    }

    static class Node {
        Member member;
        Node left, right;
        Node(Member member) { this.member = member; }
    }

    private Node root;

    public boolean add(Member member) {
        if (member == null || member.email == null || member.email.trim().isEmpty()) {
            System.out.println("Add failed: Invalid member data or blank email.");
            return false;
        }
        if (find(member.memberId) != null) {
            System.out.println("Add failed: Duplicate memberId " + member.memberId);
            return false;
        }
        root = insertRec(root, member);
        return true;
    }

    private Node insertRec(Node node, Member member) {
        if (node == null) return new Node(member);
        if (member.memberId < node.member.memberId) node.left = insertRec(node.left, member);
        else node.right = insertRec(node.right, member);
        return node;
    }

    public Member find(int memberId) {
        return findRec(root, memberId);
    }

    private Member findRec(Node node, int memberId) {
        if (node == null) return null;
        if (node.member.memberId == memberId) return node.member;
        return memberId < node.member.memberId ? findRec(node.left, memberId) : findRec(node.right, memberId);
    }

    public boolean updateEmail(int memberId, String newEmail) {
        if (newEmail == null || newEmail.trim().isEmpty()) {
            System.out.println("Update failed: Email cannot be blank.");
            return false;
        }
        Member target = find(memberId);
        if (target == null) {
            System.out.println("Update failed: Member " + memberId + " not found.");
            return false;
        }
        target.email = newEmail;
        return true;
    }

    public boolean remove(int memberId) {
        if (find(memberId) == null) {
            System.out.println("Remove failed: Member " + memberId + " not found.");
            return false;
        }
        root = removeRec(root, memberId);
        return true;
    }

    private Node removeRec(Node node, int memberId) {
        if (node == null) return null;
        if (memberId < node.member.memberId) {
            node.left = removeRec(node.left, memberId);
        } else if (memberId > node.member.memberId) {
            node.right = removeRec(node.right, memberId);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node successor = findMin(node.right);
            node.member = successor.member;
            node.right = removeRec(node.right, successor.member.memberId);
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public void inorderReport() {
        System.out.println("--- Member Inorder Report ---");
        inorderRec(root);
        System.out.println("-----------------------------");
    }

    private void inorderRec(Node node) {
        if (node != null) {
            inorderRec(node.left);
            System.out.println(node.member);
            inorderRec(node.right);
        }
    }

    public static void main(String[] args) {
        MemberBstIndex index = new MemberBstIndex();
        
        index.add(new Member(105, "Alice", "alice@test.com"));
        index.add(new Member(102, "Bob", "bob@test.com"));
        index.add(new Member(108, "Charlie", "charlie@test.com"));
        
        index.add(new Member(102, "Duplicate Bob", "bob2@test.com"));
        index.add(new Member(109, "Dave", "   "));
        index.inorderReport();
        
        index.updateEmail(102, "bob_updated@test.com");
        index.updateEmail(102, "");
        
        index.remove(105);
        index.remove(999);
        index.inorderReport();
    }
}