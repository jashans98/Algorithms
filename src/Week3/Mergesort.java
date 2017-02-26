package Week3;

import com.sun.istack.internal.NotNull;

/**
 * Created by Jashan Shewakramani
 * Description: Efficient Java Mergesort Implementation
 */
public class Mergesort {

    // helper function to check if a sub-array is sorted
    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo; i < hi - 1; i++) {
            if (!less(a[i], a[i+1]))
                return false;
        }

        return true;
    }

    // helper function to check if a[i] < a[j]
    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) == -1;
    }

    // merge step in algorithm with aux array
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {

        assert isSorted(a, lo, mid); // precondition: a[lo...mid] is sorted
        assert isSorted(a, mid + 1, hi); // precondition: a[mid+1..hi] is sorted

        // copy over things from the aux array
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];

        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)
                a[k] = aux[j++];
            else if (j > hi)
                a[k] = aux[i++];
            else if (less(aux[i], aux[j]))
                a[k] = aux[i++];
            else
                a[k] = aux[j++];
        }
    }

    // helper function sorts subarrays
    private static void sortSubarray(Comparable[] a, Comparable[] aux, int lo, int hi) {
        assert lo < hi;
        if (hi - lo < 1)
            return;

        int mid = (lo + hi) / 2;
        sortSubarray(a, aux, lo, mid);
        sortSubarray(a, aux, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }

    // main sorting function
    public static void sort(Comparable[] a) {
        sortSubarray(a, a.clone(), 0, a.length - 1);
    }
}


