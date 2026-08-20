class Book {
    private final String isbn;
    private final String title;
    private final int price;
    private final int stock;

    Book(String isbn, String title, int price, int stock) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getTotalValue() {
        return price * stock;
    }

    @Override
    public String toString() {
        return String.format("[%s] %-16s | Price: %4d | Stock: %2d", isbn, title, price, stock);
    }
}

public class BookArrayReport {
    public static void main(String[] args) {
        Book[] books = {
            new Book("978-01", "Java Basics", 500, 10),
            new Book("978-02", "Effective Java", 800, 2),
            new Book("978-03", "Design Patterns", 650, 3),
            new Book("978-04", "Clean Code", 700, 5)
        };

        System.out.println("=== 所有書籍列表 ===");
        int totalInventoryValue = 0;
        Book highestPriceBook = books[0];

        for (Book book : books) {
            System.out.println(book);
            totalInventoryValue += book.getTotalValue();

            if (book.getPrice() > highestPriceBook.getPrice()) {
                highestPriceBook = book;
            }
        }

        System.out.println("\n庫存總價值: $" + totalInventoryValue);
        System.out.println("單價最高書籍: " + highestPriceBook);

        System.out.println("\n=== 庫存小於或等於 3 的書籍 ===");
        for (Book book : books) {
            if (book.getStock() <= 3) {
                System.out.println(book);
            }
        }
    }
}