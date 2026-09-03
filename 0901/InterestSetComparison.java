import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class InterestSetComparison {

    public static <T> Set<T> getUnion(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(set1);
        result.addAll(set2);
        return Collections.unmodifiableSet(result);
    }

    public static <T> Set<T> getIntersection(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(set1);
        result.retainAll(set2);
        return Collections.unmodifiableSet(result);
    }

    public static <T> Set<T> getFirstOnly(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(set1);
        result.removeAll(set2);
        return Collections.unmodifiableSet(result);
    }

    public static <T> Set<T> getSecondOnly(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<>(set2);
        result.removeAll(set1);
        return Collections.unmodifiableSet(result);
    }
}