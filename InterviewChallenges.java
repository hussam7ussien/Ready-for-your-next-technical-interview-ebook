import java.util.*;

public class InterviewChallenges {
    // BEGIN twoSum
    static int[] twoSum(int[] values, int target) {
        Map<Long, Integer> seen = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            long needed = (long) target - values[i];
            if (seen.containsKey(needed)) {
                return new int[]{seen.get(needed), i};
            }
            seen.put((long) values[i], i);
        }
        throw new IllegalArgumentException("No solution");
    }
    // END twoSum

    // BEGIN containsDuplicate
    static boolean containsDuplicate(int[] values) {
        Set<Integer> seen = new HashSet<>();
        for (int value : values) {
            if (!seen.add(value)) return true;
        }
        return false;
    }
    // END containsDuplicate

    // BEGIN validParentheses
    static boolean validParentheses(String text) {
        Deque<Character> stack = new ArrayDeque<>();
        Map<Character, Character> matching =
            Map.of(')', '(', ']', '[', '}', '{');
        for (char c : text.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') stack.push(c);
            else {
                Character expected = matching.get(c);
                if (expected == null || stack.isEmpty()
                        || stack.pop() != expected.charValue()) return false;
            }
        }
        return stack.isEmpty();
    }
    // END validParentheses

    // BEGIN groupAnagrams
    static List<List<String>> groupAnagrams(String[] words) {
        Map<String, List<String>> groups = new HashMap<>();
        for (String word : words) {
            int[] counts = new int[26];
            for (char c : word.toCharArray()) counts[c - 'a']++;
            String key = Arrays.toString(counts);
            groups.computeIfAbsent(key, ignored -> new ArrayList<>())
                .add(word);
        }
        return new ArrayList<>(groups.values());
    }
    // END groupAnagrams

    // BEGIN longestSubstring
    static int longestSubstring(String text) {
        Map<Character, Integer> lastPosition = new HashMap<>();
        int left = 0, best = 0;
        for (int right = 0; right < text.length(); right++) {
            char c = text.charAt(right);
            Integer previous = lastPosition.put(c, right);
            if (previous != null) left = Math.max(left, previous + 1);
            best = Math.max(best, right - left + 1);
        }
        return best;
    }
    // END longestSubstring

    // BEGIN maximumSubarray
    static long maximumSubarray(int[] values) {
        if (values.length == 0) throw new IllegalArgumentException("Empty input");
        long endingHere = values[0], best = values[0];
        for (int i = 1; i < values.length; i++) {
            endingHere = Math.max(values[i], endingHere + values[i]);
            best = Math.max(best, endingHere);
        }
        return best;
    }
    // END maximumSubarray

    // BEGIN productExceptSelf
    static long[] productExceptSelf(int[] values) {
        long[] result = new long[values.length];
        long prefix = 1;
        for (int i = 0; i < values.length; i++) {
            result[i] = prefix;
            prefix = Math.multiplyExact(prefix, values[i]);
        }
        long suffix = 1;
        for (int i = values.length - 1; i >= 0; i--) {
            result[i] = Math.multiplyExact(result[i], suffix);
            suffix = Math.multiplyExact(suffix, values[i]);
        }
        return result;
    }
    // END productExceptSelf

    // BEGIN mergeIntervals
    static int[][] mergeIntervals(int[][] intervals) {
        int[][] sorted = Arrays.stream(intervals)
            .map(int[]::clone).toArray(int[][]::new);
        Arrays.sort(sorted, Comparator.comparingInt(a -> a[0]));
        List<int[]> result = new ArrayList<>();
        for (int[] interval : sorted) {
            if (result.isEmpty()
                    || interval[0] > result.get(result.size() - 1)[1]) {
                result.add(interval);
            } else {
                int[] previous = result.get(result.size() - 1);
                previous[1] = Math.max(previous[1], interval[1]);
            }
        }
        return result.toArray(int[][]::new);
    }
    // END mergeIntervals

    // BEGIN topKFrequent
    static int[] topKFrequent(int[] values, int k) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int value : values) counts.merge(value, 1, Integer::sum);
        if (k < 0 || k > counts.size()) {
            throw new IllegalArgumentException("Invalid k");
        }
        if (k == 0) return new int[0];
        PriorityQueue<Integer> heap = new PriorityQueue<>(
            Comparator.comparingInt(counts::get));
        for (int value : counts.keySet()) {
            heap.offer(value);
            if (heap.size() > k) heap.poll();
        }
        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) result[i] = heap.poll();
        return result;
    }
    // END topKFrequent

    // BEGIN searchRotated
    static int searchRotated(int[] values, int target) {
        int left = 0, right = values.length - 1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (values[middle] == target) return middle;
            if (values[left] <= values[middle]) {
                if (values[left] <= target && target < values[middle]) {
                    right = middle - 1;
                } else left = middle + 1;
            } else {
                if (values[middle] < target && target <= values[right]) {
                    left = middle + 1;
                } else right = middle - 1;
            }
        }
        return -1;
    }
    // END searchRotated

    // BEGIN ListNode
    static class ListNode {
        int value;
        ListNode next;
        ListNode(int value) { this.value = value; }
    }
    // END ListNode

    // BEGIN reverseList
    static ListNode reverseList(ListNode head) {
        ListNode previous = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = previous;
            previous = head;
            head = next;
        }
        return previous;
    }
    // END reverseList

    // BEGIN hasCycle
    static boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }
    // END hasCycle

    // BEGIN mergeLists
    static ListNode mergeLists(ListNode first, ListNode second) {
        ListNode dummy = new ListNode(0), tail = dummy;
        while (first != null && second != null) {
            if (first.value <= second.value) {
                tail.next = first;
                first = first.next;
            } else {
                tail.next = second;
                second = second.next;
            }
            tail = tail.next;
        }
        tail.next = first != null ? first : second;
        return dummy.next;
    }
    // END mergeLists

    // BEGIN TreeNode
    static class TreeNode {
        int value;
        TreeNode left, right;
        TreeNode(int value) { this.value = value; }
    }
    // END TreeNode

    // BEGIN levelOrder
    static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                level.add(node.value);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            result.add(level);
        }
        return result;
    }
    // END levelOrder

    // BEGIN countIslands
    static int countIslands(char[][] grid) {
        if (grid.length == 0) return 0;
        int rows = grid.length, columns = grid[0].length, islands = 0;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Deque<int[]> queue = new ArrayDeque<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (grid[r][c] != '1') continue;
                islands++;
                grid[r][c] = '0';
                queue.offer(new int[]{r, c});
                while (!queue.isEmpty()) {
                    int[] cell = queue.poll();
                    for (int[] direction : directions) {
                        int nr = cell[0] + direction[0];
                        int nc = cell[1] + direction[1];
                        if (nr >= 0 && nr < rows && nc >= 0 && nc < columns
                                && grid[nr][nc] == '1') {
                            grid[nr][nc] = '0';
                            queue.offer(new int[]{nr, nc});
                        }
                    }
                }
            }
        }
        return islands;
    }
    // END countIslands

    // BEGIN climbStairs
    static long climbStairs(int steps) {
        if (steps < 0 || steps > 91) {
            throw new IllegalArgumentException("Use steps from 0 through 91");
        }
        long previous = 1, current = 1;
        for (int step = 2; step <= steps; step++) {
            long next = previous + current;
            previous = current;
            current = next;
        }
        return current;
    }
    // END climbStairs

    // BEGIN coinChange
    static int coinChange(int[] coins, int amount) {
        if (amount < 0) throw new IllegalArgumentException("Negative amount");
        for (int coin : coins) {
            if (coin <= 0) throw new IllegalArgumentException("Nonpositive coin");
        }
        int missing = Math.addExact(amount, 1);
        int[] best = new int[missing];
        Arrays.fill(best, missing);
        best[0] = 0;
        for (int value = 1; value <= amount; value++) {
            for (int coin : coins) {
                if (coin <= value && best[value - coin] != missing) {
                    best[value] = Math.min(best[value], best[value - coin] + 1);
                }
            }
        }
        return best[amount] == missing ? -1 : best[amount];
    }
    // END coinChange

    // BEGIN LruCache
    static class LruCache extends LinkedHashMap<Integer, Integer> {
        private final int capacity;
        LruCache(int capacity) {
            super(16, 0.75f, true);
            if (capacity < 0) throw new IllegalArgumentException("Negative capacity");
            this.capacity = capacity;
        }
        int getValue(int key) { return getOrDefault(key, -1); }
        void putValue(int key, int value) { put(key, value); }
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > capacity;
        }
    }
    // END LruCache

    private static int checks;
    private static void check(boolean condition, String label) {
        checks++;
        if (!condition) throw new AssertionError(label);
    }
    private static ListNode list(int... values) {
        ListNode dummy = new ListNode(0), tail = dummy;
        for (int value : values) {
            tail.next = new ListNode(value); tail = tail.next;
        }
        return dummy.next;
    }
    private static List<Integer> values(ListNode node) {
        List<Integer> result = new ArrayList<>();
        while (node != null) { result.add(node.value); node = node.next; }
        return result;
    }
    public static void main(String[] args) {
        check(Arrays.equals(twoSum(new int[]{2, 7, 11, 15}, 9), new int[]{0, 1}), "two sum");
        check(Arrays.equals(twoSum(new int[]{3, 3}, 6), new int[]{0, 1}), "two equal indices");
        check(Arrays.equals(twoSum(new int[]{8, -3}, 5), new int[]{0, 1}), "negative complement");
        check(!containsDuplicate(new int[]{}), "empty duplicate");
        check(containsDuplicate(new int[]{1, 2, 1}), "duplicate");
        check(validParentheses("{[()]}"), "nested brackets");
        check(!validParentheses("([)]"), "crossed brackets");
        check(validParentheses("") && !validParentheses("a"), "bracket contract");
        var groups = groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat", ""});
        Set<Set<String>> normalized = new HashSet<>();
        for (var group : groups) normalized.add(new HashSet<>(group));
        check(normalized.equals(Set.of(Set.of("eat", "tea", "ate"), Set.of("tan", "nat"), Set.of("bat"), Set.of(""))), "anagrams");
        check(longestSubstring("abba") == 2 && longestSubstring("") == 0, "window never goes backward");
        check(maximumSubarray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}) == 6, "max subarray");
        check(maximumSubarray(new int[]{-5, -2, -7}) == -2, "all negative");
        check(Arrays.equals(productExceptSelf(new int[]{1, 2, 3, 4}), new long[]{24, 12, 8, 6}), "products");
        check(Arrays.equals(productExceptSelf(new int[]{0, 2, 0}), new long[]{0, 0, 0}), "two zeros");
        check(Arrays.equals(productExceptSelf(new int[]{0, 2, 3}), new long[]{6, 0, 0}), "one zero");
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {10, 12}};
        check(Arrays.deepEquals(mergeIntervals(intervals), new int[][]{{1, 6}, {8, 12}}), "intervals");
        check(intervals[0][1] == 3 && mergeIntervals(new int[][]{}).length == 0, "input unchanged");
        check(Arrays.equals(topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2), new int[]{1, 2}), "top k");
        check(topKFrequent(new int[]{1}, 0).length == 0, "zero k");
        check(searchRotated(new int[]{4, 5, 6, 7, 0, 1, 2}, 0) == 4, "rotated search");
        check(searchRotated(new int[]{}, 1) == -1, "empty rotated search");
        for (int n = 1; n <= 30; n++) {
            for (int rotation = 0; rotation < n; rotation++) {
                int[] rotated = new int[n];
                for (int i = 0; i < n; i++) rotated[i] = (i + rotation) % n;
                for (int target = -1; target <= n; target++) {
                    int position = searchRotated(rotated, target);
                    check(target < 0 || target >= n ? position == -1
                        : position >= 0 && rotated[position] == target, "generated rotated search");
                }
            }
        }
        check(values(reverseList(list(1, 2, 3))).equals(List.of(3, 2, 1)), "reverse");
        check(reverseList(null) == null && !hasCycle(list(1, 2, 3)), "empty and acyclic");
        ListNode cyclic = list(1, 2, 3); cyclic.next.next.next = cyclic.next;
        check(hasCycle(cyclic), "cycle identity");
        check(values(mergeLists(list(1, 3, 5), list(1, 2, 6))).equals(List.of(1, 1, 2, 3, 5, 6)), "merge lists");
        TreeNode root = new TreeNode(3); root.left = new TreeNode(9); root.right = new TreeNode(20);
        root.right.left = new TreeNode(15); root.right.right = new TreeNode(7);
        check(levelOrder(root).equals(List.of(List.of(3), List.of(9, 20), List.of(15, 7))), "tree bfs");
        check(levelOrder(null).isEmpty(), "empty tree");
        check(countIslands(new char[][]{"11000".toCharArray(), "11000".toCharArray(), "00100".toCharArray(), "00011".toCharArray()}) == 3, "islands");
        check(countIslands(new char[][]{}) == 0, "empty grid");
        check(climbStairs(0) == 1 && climbStairs(5) == 8, "stairs");
        check(climbStairs(91) == 7540113804746346429L, "stairs long limit");
        check(coinChange(new int[]{1, 2, 5}, 11) == 3, "coin change");
        check(coinChange(new int[]{2}, 3) == -1 && coinChange(new int[]{}, 0) == 0, "unreachable amount");
        check(coinChange(new int[]{1, 3, 4}, 6) == 2, "greedy fails");
        LruCache cache = new LruCache(2); cache.putValue(1, 10); cache.putValue(2, 20);
        check(cache.getValue(1) == 10, "lru read"); cache.putValue(3, 30);
        check(cache.getValue(2) == -1 && cache.getValue(3) == 30, "lru eviction");
        LruCache empty = new LruCache(0); empty.putValue(1, 1);
        check(empty.getValue(1) == -1, "zero capacity");
        Random random = new Random(29);
        for (int run = 0; run < 400; run++) {
            int n = 1 + random.nextInt(12); int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = random.nextInt(9) - 4;
            long best = Long.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                long sum = 0;
                for (int j = i; j < n; j++) { sum += a[j]; best = Math.max(best, sum); }
            }
            check(maximumSubarray(a) == best, "generated max subarray");
            long[] products = productExceptSelf(a);
            for (int i = 0; i < n; i++) {
                long expected = 1;
                for (int j = 0; j < n; j++) if (j != i) expected *= a[j];
                check(products[i] == expected, "generated product");
            }
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < n; i++) text.append((char) ('a' + random.nextInt(4)));
            int longest = 0;
            for (int i = 0; i < n; i++) {
                Set<Character> unique = new HashSet<>();
                for (int j = i; j < n && unique.add(text.charAt(j)); j++) longest = Math.max(longest, j - i + 1);
            }
            check(longestSubstring(text.toString()) == longest, "generated window");
        }
        System.out.println("Interview challenges passed " + checks + " checks");
    }
}
