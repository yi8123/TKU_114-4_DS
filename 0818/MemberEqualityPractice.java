import java.util.Objects;

class LibraryMember {
    private final String memberId;
    private final String name;
    private final String email;

    LibraryMember(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryMember that = (LibraryMember) o;
        return Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }

    @Override
    public String toString() {
        return "LibraryMember{id='" + memberId + "', name='" + name + "', email='" + email + "'}";
    }
}

public class MemberEqualityPractice {
    public static void main(String[] args) {
        LibraryMember m1 = new LibraryMember("M001", "Alice", "alice@test.com");
        LibraryMember m2 = new LibraryMember("M001", "Alice", "alice_new@test.com");

        System.out.println("m1: " + m1);
        System.out.println("m2: " + m2);

        System.out.println("\n--- 比較結果 ---");
        System.out.println("m1 == m2: " + (m1 == m2));
        System.out.println("m1.equals(m2): " + m1.equals(m2));
        System.out.println("m1.equals(null): " + m1.equals(null));
    }
}