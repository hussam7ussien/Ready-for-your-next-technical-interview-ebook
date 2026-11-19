import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.*;

/** Runnable local examples for the guide's clarified Java sections. Java 21. */
public class JavaEssentialsExamples {
    private static int checks;
    static void check(boolean result) {
        checks++;
        if (!result) throw new AssertionError("Check " + checks + " failed");
    }
    interface ThrowingAction { void run() throws Exception; }
    static void expect(Class<? extends Throwable> type, ThrowingAction action) {
        checks++;
        try { action.run(); }
        catch (Throwable error) {
            if (type.isInstance(error)) return;
            throw new AssertionError("Expected " + type + ", got " + error, error);
        }
        throw new AssertionError("Expected " + type);
    }
    static class Box<T> {
        private final T value;
        Box(T value) { this.value = value; }
        T get() { return value; }
    }
    static <T> List<T> filter(List<T> input, Predicate<T> condition) {
        List<T> result = new ArrayList<>();
        for (T item : input) if (condition.test(item)) result.add(item);
        return result;
    }
    // Validates and prints only; it does not read actual file contents.
    static void readFile(String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            throw new IOException("File path cannot be empty.");
        }
        System.out.println("Reading file: " + filePath);
    }
    static void forwardRequest() throws IOException { readFile(""); }
    static void checkQuantity(int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("Quantity cannot be negative");
    }
    public static void main(String[] args) throws Exception {
        List<String> fixed = Arrays.asList("Ali", "Mona");
        fixed.set(0, "Omar");
        check(fixed.equals(List.of("Omar", "Mona")));
        expect(UnsupportedOperationException.class, () -> fixed.add("Sara"));
        expect(UnsupportedOperationException.class, () -> fixed.remove("Mona"));
        List<String> immutable = List.of("Ali", "Mona");
        expect(UnsupportedOperationException.class, () -> immutable.set(0, "Omar"));
        expect(NullPointerException.class, () -> List.of("Ali", null));
        check(Arrays.asList("Ali", null).get(1) == null);
        String[] array = {"Ali", "Mona"};
        List<String> view = Arrays.asList(array);
        view.set(0, "Omar"); check(array[0].equals("Omar"));
        array[1] = "Sara"; check(view.equals(List.of("Omar", "Sara")));
        List<String> copy = new ArrayList<>(view);
        copy.add("Hussam"); copy.remove("Omar"); copy.set(0, "Mona");
        check(array[1].equals("Sara"));
        check(copy.equals(List.of("Mona", "Hussam")));
        int[] primitiveArray = {1, 2};
        check(Arrays.asList(primitiveArray).size() == 1);
        String first = new String("Ali"), second = new String("Ali");
        check(first != second); check(first.equals(second));
        check(!Objects.equals(null, "Ali")); check(Objects.equals(null, null));
        check(new Box<>("Hello").get().equals("Hello"));
        check(new Box<>(10).get() == 10);
        check(3_000_000_000L > Integer.MAX_VALUE);
        check(0.1 + 0.2 != 0.3);
        check(new BigDecimal("0.1").add(new BigDecimal("0.2"))
            .equals(new BigDecimal("0.3")));
        check(new BigDecimal("99.955").setScale(2, RoundingMode.HALF_UP)
            .equals(new BigDecimal("99.96")));
        check(BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE)
            .compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0);
        expect(IOException.class, () -> readFile(null));
        expect(IOException.class, () -> readFile(""));
        expect(IOException.class, JavaEssentialsExamples::forwardRequest);
        expect(IllegalArgumentException.class, () -> checkQuantity(-1));
        checkQuantity(0);
        List<String> names = new ArrayList<>(); names.add("Ali"); names.add("Ali");
        check(names.size() == 2);
        Set<String> unique = new HashSet<>(names); check(unique.size() == 1);
        Map<Integer, String> users = new HashMap<>();
        check(users.put(1, "Ali") == null);
        check(users.put(1, "Omar").equals("Ali"));
        check(users.putIfAbsent(1, "Sara").equals("Omar"));
        users.put(2, null); users.putIfAbsent(2, "Mona");
        check(users.get(2).equals("Mona"));
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(10); stack.push(20);
        check(stack.peek() == 20 && stack.size() == 2);
        check(stack.pop() == 20 && stack.size() == 1);
        check(stack.peek() == 10); stack.pop(); check(stack.peek() == null);
        expect(NoSuchElementException.class, stack::pop);
        expect(NullPointerException.class, () -> { int value = stack.peek(); });
        Queue<Integer> queue = new ArrayDeque<>();
        check(queue.offer(10)); check(queue.offer(20));
        check(queue.poll() == 10); check(queue.poll() == 20); check(queue.poll() == null);
        List<Integer> numbers = new ArrayList<>(List.of(10, 20, 30));
        ListIterator<Integer> cursor = numbers.listIterator();
        check(cursor.next() == 10); check(cursor.next() == 20);
        check(cursor.previous() == 20); check(cursor.previous() == 10);
        ListIterator<Integer> edit = numbers.listIterator();
        expect(IllegalStateException.class, () -> edit.set(1));
        edit.next(); edit.set(15); edit.add(17);
        check(numbers.equals(List.of(15, 17, 20, 30)));
        expect(IllegalStateException.class, edit::remove);
        expect(IllegalStateException.class, () -> edit.set(1));
        check(edit.next() == 20); edit.remove();
        check(numbers.equals(List.of(15, 17, 30)));
        expect(IllegalStateException.class, edit::remove);
        ListIterator<Integer> empty = List.<Integer>of().listIterator();
        expect(NoSuchElementException.class, empty::next);
        expect(NoSuchElementException.class, empty::previous);
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Predicate<Integer> positive = n -> n > 0;
        check(isEven.test(4)); check(!isEven.test(5));
        check(positive.and(isEven).test(4)); check(!positive.and(isEven).test(-4));
        check(positive.or(isEven).test(-4)); check(positive.or(isEven).test(3));
        check(!positive.or(isEven).test(-3)); check(isEven.negate().test(3));
        Predicate<String> hasText = t -> t != null && !t.isBlank();
        check(hasText.test("Hussam")); check(!hasText.test("   ")); check(!hasText.test(null));
        List<Integer> input = List.of(1, 2, 3, 4, 5, 6);
        check(filter(input, positive.and(isEven)).equals(List.of(2, 4, 6)));
        check(filter(List.<Integer>of(), isEven).isEmpty());
        check(input.stream().anyMatch(isEven)); check(!input.stream().allMatch(isEven));
        check(!input.stream().noneMatch(isEven));
        check(Stream.<Integer>empty().allMatch(isEven));
        check(Stream.<Integer>empty().noneMatch(isEven));
        List<Integer> editable = new ArrayList<>(input); editable.removeIf(isEven);
        check(editable.equals(List.of(1, 3, 5)));
        check(input.size() == 6); // filtering/removal of the copy did not change source
        List<Integer> evens = List.of(2, 4, 6);
        check(evens.stream().count() == 3);
        check(evens.stream().findFirst().orElse(-1) == 2);
        check(Stream.<Integer>empty().findFirst().orElse(-1) == -1);
        check(evens.stream().reduce(0, Integer::sum) == 12);
        check(evens.stream().mapToInt(Integer::intValue).sum() == 12);
        check(evens.stream().mapToInt(Integer::intValue).average().orElse(0) == 4);
        IntSummaryStatistics stats = evens.stream().mapToInt(Integer::intValue).summaryStatistics();
        check(stats.getCount() == 3 && stats.getSum() == 12 && stats.getMin() == 2 && stats.getMax() == 6);
        List<Integer> result = input.stream().filter(isEven).toList();
        expect(UnsupportedOperationException.class, () -> result.add(8));
        List<Integer> mutable = input.stream().collect(Collectors.toCollection(ArrayList::new));
        mutable.add(7); check(mutable.size() == 7);
        Stream<Integer> once = input.stream(); once.count();
        expect(IllegalStateException.class, once::count);
        check("ali".toUpperCase(Locale.ROOT).equals("ALI"));
        check("ali".toUpperCase(Locale.forLanguageTag("tr")).equals("AL\u0130"));
        // Argument evaluation vs deferred computation; no network or external I/O.
        AtomicInteger loads = new AtomicInteger();
        Map<Integer, String> cache = new HashMap<>(); cache.put(1, "saved");
        cache.putIfAbsent(1, "load-" + loads.incrementAndGet());
        check(loads.get() == 1 && cache.get(1).equals("saved"));
        cache.computeIfAbsent(1, k -> "load-" + loads.incrementAndGet());
        check(loads.get() == 1);
        cache.computeIfAbsent(2, k -> "load-" + loads.incrementAndGet());
        check(loads.get() == 2 && cache.get(2).equals("load-2"));
        ConcurrentHashMap<Integer, String> concurrent = new ConcurrentHashMap<>();
        expect(NullPointerException.class, () -> concurrent.put(1, null));
        System.out.println("Java essentials passed " + checks + " behavior checks");
    }
}
