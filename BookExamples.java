import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BookExamples {
    private static int checks;

    static int[] twoSum(int[] numbers, int target) {
        Map<Long, Integer> seen = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            long needed = (long) target - numbers[i];
            Integer previous = seen.get(needed);
            if (previous != null) {
                return new int[]{previous, i};
            }
            seen.put((long) numbers[i], i);
        }
        throw new IllegalArgumentException("No solution");
    }

    static boolean containsDuplicate(int[] numbers) {
        Set<Integer> seen = new HashSet<>();
        for (int number : numbers) {
            if (!seen.add(number)) return true;
        }
        return false;
    }

    static boolean validParentheses(String input) {
        Deque<Character> stack = new ArrayDeque<>();
        Map<Character, Character> pairs =
            Map.of(')', '(', ']', '[', '}', '{');
        for (char character : input.toCharArray()) {
            if (character == '(' || character == '[' || character == '{') {
                stack.push(character);
            } else {
                Character expected = pairs.get(character);
                if (expected == null || stack.isEmpty()
                        || stack.pop() != expected.charValue()) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    static void check(boolean condition, String label) {
        checks++;
        if (!condition) throw new AssertionError(label);
    }

    static void expect(Class<? extends Throwable> type,
                       Runnable action, String label) {
        try {
            action.run();
        } catch (Throwable error) {
            check(type.isInstance(error), label + " wrong exception");
            return;
        }
        throw new AssertionError(label + " no exception");
    }

    public static void main(String[] args) throws Exception {
        List<Integer> even = List.of(5, 2, 8, 3, 2, 10, 5, 4).stream()
            .filter(n -> n % 2 == 0).distinct().sorted().toList();
        check(even.equals(List.of(2, 4, 8, 10)), "even distinct sorted");
        expect(UnsupportedOperationException.class,
            () -> even.add(12), "Stream.toList is unmodifiable");

        List<String> names = List.of("Ahmed", "Ali", "Sara", "Amr", "Mona", "");
        List<String> transformed = names.stream()
            .filter(n -> n.startsWith("A") || n.startsWith("a"))
            .map(n -> n.toUpperCase(Locale.ROOT)).sorted().toList();
        check(transformed.equals(List.of("AHMED", "ALI", "AMR")), "names");
        int squareSum = List.of(3, 5, 2, 8, 1, 4).stream()
            .filter(n -> n % 2 == 0).mapToInt(n -> n * n).sum();
        check(squareSum == 84, "square sum");
        check(List.of(2, 3, 4).stream().reduce(0, Integer::sum) == 9,
            "reduce addition");
        check(List.of(2, 3, 4).stream().reduce(1, (a, b) -> a * b) == 24,
            "reduce multiplication");
        check(List.<Integer>of().stream().reduce(1, (a, b) -> a * b) == 1,
            "empty reduction identity");

        int[] primitive = {1, 2, 4};
        check(Arrays.asList(primitive).size() == 1, "primitive array is one object");
        List<Integer> boxed = Arrays.stream(primitive).boxed()
            .collect(Collectors.toCollection(ArrayList::new));
        boxed.add(8);
        check(boxed.equals(List.of(1, 2, 4, 8)), "boxed mutable list");
        Integer[] reference = {1, 2, 4};
        List<Integer> view = Arrays.asList(reference);
        view.set(0, 9);
        check(reference[0] == 9, "array backed list");
        expect(UnsupportedOperationException.class,
            () -> view.add(8), "fixed size list");
        expect(NullPointerException.class,
            () -> List.of(1, null), "List.of rejects null");
        List<Integer> removal = new ArrayList<>(List.of(10, 20, 10));
        check(removal.remove(1) == 20, "remove by index");
        check(removal.remove(Integer.valueOf(10)), "remove by value");
        check(removal.equals(List.of(10)), "remaining list");

        Set<Integer> a = new HashSet<>(List.of(1, 2, 3));
        check(!a.add(2), "duplicate add returns false");
        a.retainAll(Set.of(2, 3, 4));
        check(a.equals(Set.of(2, 3)), "set intersection");
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Ali", 90);
        check(scores.putIfAbsent("Ali", 80) == 90, "putIfAbsent prior value");
        check(scores.get("Ali") == 90, "putIfAbsent preserves existing");
        check(scores.put("Ali", 80) == 90, "put prior value");
        check(scores.get("Ali") == 80, "put replaces");
        check(scores.getOrDefault("Sara", 0) == 0, "default lookup");

        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(10); stack.push(20);
        check(stack.peek() == 20 && stack.pop() == 20, "stack LIFO");
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(10); queue.offer(20);
        check(queue.peek() == 10 && queue.poll() == 10, "queue FIFO");
        queue.poll();
        check(queue.poll() == null && queue.peek() == null, "empty queue");
        String text = "";
        text.concat("a");
        check(text.indexOf("a") == -1, "String immutable");
        text = text.concat("a");
        check(text.indexOf("a") == 0, "String result assignment");

        check(containsDuplicate(new int[]{1, 2, 3, 1}), "duplicate true");
        check(!containsDuplicate(new int[]{}), "empty has no duplicate");
        check(!containsDuplicate(new int[]{1, 2, 3}), "unique values");
        check(validParentheses("([{}])"), "nested brackets");
        check(validParentheses("()[]{}"), "adjacent brackets");
        check(validParentheses(""), "empty brackets");
        check(!validParentheses("([)]"), "crossed brackets");
        check(!validParentheses(")"), "unmatched closing bracket");
        check(!validParentheses("("), "unmatched opening bracket");
        check(!validParentheses("x"), "reject non bracket");
        expect(IllegalArgumentException.class,
            () -> twoSum(new int[]{1, 2}, 99), "no two sum solution");
        Random random = new Random(17);
        for (int run = 0; run < 1000; run++) {
            int[] input = new int[2 + random.nextInt(20)];
            for (int i = 0; i < input.length; i++) input[i] = random.nextInt(201) - 100;
            int target = input[0] + input[1];
            int[] pair = twoSum(input, target);
            check(pair[0] != pair[1]
                    && (long) input[pair[0]] + input[pair[1]] == target,
                "random two sum " + run);
        }
        int[] extreme = twoSum(new int[]{Integer.MIN_VALUE, 0}, Integer.MIN_VALUE);
        check(extreme[0] == 0 && extreme[1] == 1, "extreme complement arithmetic");

        ExecutorService executor = Executors.newFixedThreadPool(8);
        try {
            AtomicInteger counter = new AtomicInteger();
            List<Future<?>> jobs = new ArrayList<>();
            for (int worker = 0; worker < 8; worker++) {
                jobs.add(executor.submit(() -> {
                    for (int i = 0; i < 10000; i++) counter.incrementAndGet();
                }));
            }
            for (Future<?> job : jobs) job.get();
            check(counter.get() == 80000, "atomic increments");

            ConcurrentHashMap<Integer, String> cache = new ConcurrentHashMap<>();
            AtomicInteger loads = new AtomicInteger();
            CountDownLatch start = new CountDownLatch(1);
            List<Future<String>> lookups = new ArrayList<>();
            for (int worker = 0; worker < 8; worker++) {
                lookups.add(executor.submit(() -> {
                    start.await();
                    return cache.computeIfAbsent(1, key -> {
                        loads.incrementAndGet();
                        return "user";
                    });
                }));
            }
            start.countDown();
            for (Future<String> result : lookups) check("user".equals(result.get()), "lookup");
            check(loads.get() == 1, "one successful absent mapping computation");
        } finally {
            executor.shutdown();
        }

        List<Integer> parallel = java.util.stream.IntStream.range(0, 10000)
            .parallel().map(n -> n * 2).boxed().toList();
        check(parallel.size() == 10000 && parallel.get(9999) == 19998,
            "parallel result collection");
        AtomicInteger observedFailures = new AtomicInteger();
        CompletableFuture<String> failure = CompletableFuture.supplyAsync(() -> {
            throw new IllegalStateException("simulated");
        });
        CompletableFuture<String> observed = failure.whenComplete((value, error) -> {
            if (error != null) observedFailures.incrementAndGet();
        });
        expect(CompletionException.class, observed::join, "future failure");
        check(observedFailures.get() == 1, "completion observed failure");
        System.out.println("Passed " + checks + " checks");
    }
}
