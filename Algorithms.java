import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Algorithms {
    // BEGIN linearSearch
    static int linearSearch(int[] values, int target) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == target) return i;
        }
        return -1;
    }
    // END linearSearch

    // BEGIN binarySearch
    static int binarySearch(int[] sorted, int target) {
        int left = 0;
        int right = sorted.length - 1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (sorted[middle] == target) return middle;
            if (sorted[middle] < target) left = middle + 1;
            else right = middle - 1;
        }
        return -1;
    }
    // END binarySearch

    // BEGIN lowerBound
    static int lowerBound(int[] sorted, int target) {
        int left = 0;
        int right = sorted.length;
        while (left < right) {
            int middle = left + (right - left) / 2;
            if (sorted[middle] < target) left = middle + 1;
            else right = middle;
        }
        return left;
    }
    // END lowerBound

    // BEGIN bubbleSort
    static void bubbleSort(int[] values) {
        for (int end = values.length - 1; end > 0; end--) {
            boolean changed = false;
            for (int i = 0; i < end; i++) {
                if (values[i] > values[i + 1]) {
                    swap(values, i, i + 1);
                    changed = true;
                }
            }
            if (!changed) return;
        }
    }
    // END bubbleSort

    // BEGIN selectionSort
    static void selectionSort(int[] values) {
        for (int i = 0; i < values.length - 1; i++) {
            int smallest = i;
            for (int j = i + 1; j < values.length; j++) {
                if (values[j] < values[smallest]) smallest = j;
            }
            swap(values, i, smallest);
        }
    }
    // END selectionSort

    // BEGIN insertionSort
    static void insertionSort(int[] values) {
        for (int i = 1; i < values.length; i++) {
            int current = values[i];
            int j = i - 1;
            while (j >= 0 && values[j] > current) {
                values[j + 1] = values[j];
                j--;
            }
            values[j + 1] = current;
        }
    }
    // END insertionSort

    // BEGIN mergeSort
    static void mergeSort(int[] values) {
        int[] buffer = new int[values.length];
        mergeSort(values, buffer, 0, values.length);
    }

    static void mergeSort(int[] a, int[] buffer, int from, int to) {
        if (to - from < 2) return;
        int middle = from + (to - from) / 2;
        mergeSort(a, buffer, from, middle);
        mergeSort(a, buffer, middle, to);
        int left = from, right = middle, out = from;
        while (left < middle && right < to) {
            buffer[out++] = a[left] <= a[right]
                ? a[left++] : a[right++];
        }
        while (left < middle) buffer[out++] = a[left++];
        while (right < to) buffer[out++] = a[right++];
        System.arraycopy(buffer, from, a, from, to - from);
    }
    // END mergeSort

    // BEGIN quickSort
    static void quickSort(int[] values) {
        quickSort(values, 0, values.length - 1);
    }

    static void quickSort(int[] a, int left, int right) {
        if (left >= right) return;
        int chosen = ThreadLocalRandom.current()
            .nextInt(left, right + 1);
        swap(a, chosen, right);
        int pivot = a[right];
        int boundary = left;
        for (int i = left; i < right; i++) {
            if (a[i] <= pivot) swap(a, i, boundary++);
        }
        swap(a, boundary, right);
        quickSort(a, left, boundary - 1);
        quickSort(a, boundary + 1, right);
    }
    // END quickSort

    // BEGIN heapSort
    static void heapSort(int[] values) {
        for (int root = values.length / 2 - 1; root >= 0; root--) {
            siftDown(values, root, values.length);
        }
        for (int end = values.length - 1; end > 0; end--) {
            swap(values, 0, end);
            siftDown(values, 0, end);
        }
    }

    static void siftDown(int[] a, int root, int size) {
        while (root < size / 2) {
            int child = root * 2 + 1;
            if (child + 1 < size && a[child + 1] > a[child]) {
                child++;
            }
            if (a[root] >= a[child]) return;
            swap(a, root, child);
            root = child;
        }
    }
    // END heapSort

    // BEGIN swap
    static void swap(int[] a, int first, int second) {
        int temporary = a[first];
        a[first] = a[second];
        a[second] = temporary;
    }
    // END swap

    private static int checks;
    private static void check(boolean condition) {
        checks++;
        if (!condition) throw new AssertionError("Check " + checks);
    }

    public static void main(String[] args) {
        Random random = new Random(42);
        List<java.util.function.Consumer<int[]>> sorts = List.of(
            Algorithms::bubbleSort, Algorithms::selectionSort,
            Algorithms::insertionSort, Algorithms::mergeSort,
            Algorithms::quickSort, Algorithms::heapSort);
        List<int[]> cases = new ArrayList<>(List.of(
            new int[]{}, new int[]{1}, new int[]{2, 1},
            new int[]{1, 2, 3, 4}, new int[]{4, 3, 2, 1},
            new int[]{2, 2, 2, 2}, new int[]{-3, 0, -3, 7},
            new int[]{Integer.MAX_VALUE, 0, Integer.MIN_VALUE}));
        for (int run = 0; run < 300; run++) {
            int[] a = new int[random.nextInt(70)];
            for (int i = 0; i < a.length; i++) a[i] = random.nextInt(81) - 40;
            cases.add(a);
        }
        for (int[] input : cases) {
            int[] expected = input.clone();
            Arrays.sort(expected);
            for (var sort : sorts) {
                int[] actual = input.clone();
                sort.accept(actual);
                check(Arrays.equals(actual, expected));
            }
            for (int target : new int[]{-41, -3, 0, 2, 40, 41}) {
                int linear = linearSearch(expected, target);
                int binary = binarySearch(expected, target);
                check((linear == -1) == (binary == -1));
                if (binary >= 0) check(expected[binary] == target);
                int lower = lowerBound(expected, target);
                check(lower >= 0 && lower <= expected.length);
                check(lower == 0 || expected[lower - 1] < target);
                check(lower == expected.length || expected[lower] >= target);
            }
        }
        System.out.println("Algorithms passed " + checks + " checks");
    }
}
