import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Nitya Jani
 * @version 1.0
 * @userid njani8
 * @GTID 903598748
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator is null.");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {
                T temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j--;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The data is null or the comparator is null.");
        }
        int first = 0;
        int last = arr.length - 1;
        int swapped;
        while (last > first) {
            swapped = first;
            for (int i = first; i < last; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = i;
                }
            }
            last = swapped;
            for (int j = last; j > first; j--) {
                if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                    T temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    swapped = j;
                }
            }
            first = swapped;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array is null or the comparator is null.");
        }
        if (arr.length == 1 || arr.length == 0) {
            return;
        } else {
            T[] left = (T[]) new Object[arr.length / 2];
            for (int i = 0; i < arr.length / 2; i++) {
                left[i] = arr[i];
            }
            T[] right = (T[]) new Object[arr.length / 2];
            if (arr.length % 2 != 0) {
                right = (T[]) new Object[arr.length / 2 + 1];
            }
            int w = 0;
            for (int j = arr.length / 2; j < arr.length; j++) {
                right[w] = arr[j];
                w++;
            }
            mergeSort(left, comparator);
            mergeSort(right, comparator);
            merge(left, right, comparator, arr);
        }
    }

    /**
     * This merges subarrays
     * @param <T> data type to sort
     * @param left is the left of the array
     * @param right is the right of the array
     * @param comparator is the compare method
     * @param arr is the array
     */
    private static <T> void merge(T[] left, T[] right, Comparator<T> comparator, T[] arr) {
        int i = 0;
        int j = 0;
        int mid = arr.length / 2;
        int numElements = 0;
        while (i < mid && j < right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                arr[numElements] = left[i];
                i++;
            } else {
                arr[numElements] = right[j];
                j++;
            }
            numElements++;
        }
        //in case one of the lists becomes empty
        while (i < mid) {
            arr[numElements] = left[i];
            i++;
            numElements++;
        }
        while (j < right.length) {
            arr[numElements] = right[j];
            j++;
            numElements++;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator, Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("The array, comparator, or random number generator is null.");
        }
        if (arr.length != 0 && arr.length != 1) {
            quickSortHelper(arr, 0, arr.length - 1, comparator, rand);
        }
    }


    /**
     * This sorts with quicksort.
     * @param <T> data type to sort
     * @param i is the left pointer
     * @param j is the right pointer
     * @param comparator is the compare method
     * @param rand is a random
     * @param arr is the array
     */
    private static <T> void quickSortHelper(T[] arr, int i, int j, Comparator<T> comparator, Random rand) {
        if (i >= j) {
            return;
        }
        int pivotIndex = rand.nextInt(j - i + 1) + i;
        T pivot = arr[pivotIndex];
        T temp = arr[i];
        arr[i] = arr[pivotIndex];
        arr[pivotIndex] = temp;
        pivotIndex = i;
        int leftIn = i + 1;
        int rightIn = j;
        while (leftIn <= rightIn) {
            while (leftIn <= rightIn && comparator.compare(arr[leftIn], pivot) <= 0) {
                leftIn++;
            }
            while (leftIn <= rightIn && comparator.compare(arr[rightIn], pivot) >= 0) {
                rightIn--;
            }
            if (leftIn <= rightIn) {
                T the = arr[leftIn];
                arr[leftIn] = arr[rightIn];
                arr[rightIn] = the;
                leftIn++;
                rightIn--;
            }
        }
        T pivotTemp = arr[pivotIndex];
        arr[pivotIndex] = arr[rightIn];
        arr[rightIn] = pivotTemp;
        quickSortHelper(arr, i, rightIn - 1, comparator, rand);
        quickSortHelper(arr, rightIn + 1, j, comparator, rand);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null or nothing.");
        }
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }
        int biggest = Math.abs(arr[0]);
        for (int i = 0; i < arr.length; i++) {
            if (Math.abs(arr[i]) > biggest) {
                biggest = Math.abs(arr[i]);
            }
        }
        int count = 1;
        int dig = biggest / 10;
        while (dig != 0) {
            dig = dig / 10;
            count++;
        }
        int longest = count;
        int power = 1;
        for (int i = 1; i < longest + 1; i++) {
            for (int j = 0; j < arr.length; j++) {
                int digit = arr[j] % 10;
                if (i != 1) {
                    digit = (arr[j] / power) % 10;
                }
                //the second thing is checking if a digit should be negative or not depending on where it is in
                //the number, like in -85, only 8 should be negative, not 5
                if (digit < 0 && arr[j] / (power * 10) != 0) {
                    buckets[9 - digit].addLast(arr[j]);
                } else {
                    buckets[9 + digit].addLast(arr[j]);
                }
            }
            int index = 0;
            for (LinkedList<Integer> l : buckets) {
                while (!l.isEmpty()) {
                    arr[index] = l.removeFirst();
                    index++;
                }
            }
            power = power * 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is nothing.");
        }
        PriorityQueue<Integer> q = new PriorityQueue<>(data);
        int[] arr = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            Integer t = q.remove();
            arr[i] = t;
        }
        return arr;
    }

    /*public static void main(String[] args) {
        Sorting s = new Sorting();
        int arr[] = new int[9];
        arr[0] = -1023;
        arr[1] = 19;
        arr[2] = 2;
        arr[3] = -0;
        arr[4] = -21;
        arr[5] = 302;
        arr[6] = 19374;
        arr[7] = 203;
        arr[8] = -11111;
        s.lsdRadixSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }*/
}
