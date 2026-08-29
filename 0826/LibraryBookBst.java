import java.util.ArrayList;
import java.util.List;

public class LibraryBookBst {

    static class Book {
        String isbn;
        String title;
        String author;
        boolean available;

        Book(String isbn, String title, String author, boolean available) {
            this.isbn = isbn;
            this.title = title;
            this.author = author;
            this.available = available;
        }

        @Override
        public String toString() {
            return String.format("[ISBN: %-13s | Title: %-25s | Author: %-12s | Status: %s]",
                    isbn, title, author, available ? "Available" : "BORROWED");
        }
    }

    static class Node {
        Book book;
        Node left, right;
        Node(Book book) { this.book = book; }
    }

    private Node root;

    public boolean add(Book book) {
        if (book == null || book.isbn == null || book.isbn.trim().isEmpty()) {
            System.out.println("[ADD FAIL] Invalid book data or empty ISBN.");
            return false;
        }
        if (find(book.isbn) != null) {
            System.out.println("[ADD FAIL] Duplicate ISBN: " + book.isbn);
            return false;
        }
        root = insertRec(root, book);
        return true;
    }

    private Node insertRec(Node node, Book book) {
        if (node == null) return new Node(book);
        int cmp = book.isbn.compareTo(node.book.isbn);
        if (cmp < 0) node.left = insertRec(node.left, book);
        else if (cmp > 0) node.right = insertRec(node.right, book);
        return node;
    }

    public Book find(String isbn) {
        if (isbn == null) return null;
        return findRec(root, isbn);
    }

    private Book findRec(Node node, String isbn) {
        if (node == null) return null;
        int cmp = isbn.compareTo(node.book.isbn);
        if (cmp == 0) return node.book;
        return (cmp < 0) ? findRec(node.left, isbn) : findRec(node.right, isbn);
    }

    public boolean borrow(String isbn) {
        Book book = find(isbn);
        if (book == null) {
            System.out.println("[BORROW FAIL] Book not found: " + isbn);
            return false;
        }
        if (!book.available) {
            System.out.println("[BORROW FAIL] Book is already borrowed: " + isbn);
            return false;
        }
        book.available = false;
        System.out.println("[BORROW SUCCESS] Borrowed: " + book.title);
        return true;
    }

    public boolean returnBook(String isbn) {
        Book book = find(isbn);
        if (book == null) {
            System.out.println("[RETURN FAIL] Book not found: " + isbn);
            return false;
        }
        if (book.available) {
            System.out.println("[RETURN FAIL] Book was not borrowed: " + isbn);
            return false;
        }
        book.available = true;
        System.out.println("[RETURN SUCCESS] Returned: " + book.title);
        return true;
    }

    public boolean remove(String isbn) {
        Book target = find(isbn);
        if (target == null) {
            System.out.println("[REMOVE FAIL] Book not found: " + isbn);
            return false;
        }
        if (!target.available) {
            System.out.println("[REMOVE REJECTED] Cannot remove borrowed book: " + isbn);
            return false;
        }
        root = removeRec(root, isbn);
        System.out.println("[REMOVE SUCCESS] Book removed: " + isbn);
        return true;
    }

    private Node removeRec(Node node, String isbn) {
        if (node == null) return null;
        int cmp = isbn.compareTo(node.book.isbn);
        if (cmp < 0) {
            node.left = removeRec(node.left, isbn);
        } else if (cmp > 0) {
            node.right = removeRec(node.right, isbn);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node successor = findMin(node.right);
            node.book = successor.book;
            node.right = removeRec(node.right, successor.book.isbn);
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public List<Book> rangeQuery(String lowIsbn, String highIsbn) {
        List<Book> list = new ArrayList<>();
        if (lowIsbn == null || highIsbn == null || lowIsbn.compareTo(highIsbn) > 0) {
            return list;
        }
        rangeRec(root, lowIsbn, highIsbn, list);
        return list;
    }

    private void rangeRec(Node node, String low, String high, List<Book> list) {
        if (node == null) return;
        if (node.book.isbn.compareTo(low) > 0) rangeRec(node.left, low, high, list);
        if (node.book.isbn.compareTo(low) >= 0 && node.book.isbn.compareTo(high) <= 0) list.add(node.book);
        if (node.book.isbn.compareTo(high) < 0) rangeRec(node.right, low, high, list);
    }

    public void inorderReport() {
        System.out.println("================ Library Inventory Report ================");
        inorderRec(root);
        System.out.println("==========================================================");
    }

    private void inorderRec(Node node) {
        if (node != null) {
            inorderRec(node.left);
            System.out.println(node.book);
            inorderRec(node.right);
        }
    }

    public static void main(String[] args) {
        LibraryBookBst library = new LibraryBookBst();

        library.add(new Book("978-0131103627", "The C Programming Language", "K&R", true));
        library.add(new Book("978-0201633610", "Design Patterns", "GoF", true));
        library.add(new Book("978-0321573513", "Algorithms 4th", "Sedgewick", true));
        library.add(new Book("978-0132350884", "Clean Code", "Robert Martin", true));
        library.borrow("978-0201633610");
        library.remove("978-0201633610");
        library.returnBook("978-0201633610");
        library.remove("978-0201633610");

        System.out.println("\n--- ISBN Range Query [978-0130000000 ~ 978-0200000000] ---");
        for (Book b : library.rangeQuery("978-0130000000", "978-0200000000")) {
            System.out.println(b);
        }
        library.inorderReport();
    }
}