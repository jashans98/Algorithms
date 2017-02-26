package Week4;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by Jashan Shewakramani
 * binary heap implementation of maximum priority queue
 * todo: rewrite using a neater, object oriented binary tree
 */
public class MaxPQ<Key extends Comparable<Key>> {
    private Key[] values;
    private int size;

    private boolean less(int i, int j) {
        return values[i].compareTo(values[j]) == -1;
    }

    private void swap(int i, int j) {
        Key temp = values[i];
        values[i] = values[j];
        values[j] = temp;
    }

    public MaxPQ() {
        values = (Key[]) new Comparable[1];
        size = 0;
        insert(null); // we waste the 0 index for simplicity
    }

    public void insert(Key k) {
        if (size == values.length)
            resize();

        values[size++] = k;
        swim(size - 1); // adjust k so that it's at the top of the PQ
    }

    // maintain invariant
    private void swim(int k) {
        // we've reached the top
        while (k > 1 && less(k/2, k)) {
            swap(k, k/2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= size - 1) {
            int j = 2*k;
            if (j < size - 1 && less(j, j+1))
                j++;
            if (!less(k, j))
                break;

            swap(k, j);
            k = j;
        }
    }

    public Key delMax() {
        Key max = values[1];
        swap(1, --size); // swap with the last element
        sink(1);
        values[size] = null; // empty for the garbage collector
        return max;
    }

    public Key getMax() {
        return values[1];
    }

    // resize for efficient memory allocation
    private void resize() {
        Key[] copy;
        if (size > values.length / 4 && size < values.length)
            return;
        else if (size == values.length)
            copy = (Key[]) new Comparable[2 * values.length];
        else
            copy = (Key[]) new Comparable[values.length / 2];

        for (int i = 0; i < size; i++)
            copy[i] = values[i];

        values = copy;
    }

    private void printPQ() {
        for (int i = 1; i < values.length; i++) {
            System.out.println(values[i]);
        }
    }

    private boolean validPQ(int k) {
        if (2 * k >= size) // no more children
            return true;
        else
            return less(2*k, k) && less(2*k + 1, k) && validPQ(2 * k) && validPQ(2 * k + 1);
    }

    public static void main(String[] args) {
        MaxPQ<Integer> pq = new MaxPQ<>();
        for (int i = 0; i < 5000000; i++) {
            pq.insert(StdRandom.uniform(500000));
        }

        for (int i = 0; i < 7; i++) {
            System.out.println(pq.delMax());
        }
    }
}


