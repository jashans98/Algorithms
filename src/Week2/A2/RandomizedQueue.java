package Week2.A2;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Jashan Shewakramani
 * Description: Resizing Array implementation of randomized queue
 */
public class RandomizedQueue<Item> implements Iterable<Item>{
    private Item[] values;
    private Stack<Integer> slackSpots;
    private int size;
    private int n;

    public RandomizedQueue() {
        values = (Item[]) new Object[1];
        slackSpots = new Stack<>();
        size = 0;
        n = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException("Do not add null items");

        if (!slackSpots.isEmpty()) {
            values[slackSpots.pop()] = item;
        } else {
            values[n] = item;
            n++;
        }

        size++;

        if (n == values.length)
            resizeAndDefragment(2 * values.length);
    }

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty");

        int removeIndex = getRandomIndex();

        Item toDequeue = values[removeIndex];
        values[removeIndex] = null;
        slackSpots.push(removeIndex);

        size--;

        if (size == values.length / 4)
            resizeAndDefragment(values.length / 2);

        return toDequeue;

    }

    public Item sample() {
        return values[getRandomIndex()];
    }

    private int getRandomIndex() {
        if (size == 1)
            return 0;

        int index;
        do {
            index = StdRandom.uniform(n);
        } while (values[index] == null);

        return index;
    }

    private void resizeAndDefragment(int newSize) {
        Item[] copy = (Item[]) new Object[newSize];
        int copyIndex = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                copy[copyIndex] = values[i];
                copyIndex++;
            }
        }

        n = copyIndex;
        values = copy;
        slackSpots = new Stack<>(); // we don't have anymore slack spots after de-fragmentation
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    private class RandomArrayIterator implements Iterator<Item> {

        int[] seq;
        int index;

        RandomArrayIterator() {
            seq = new int[size];
            resizeAndDefragment(2 * size);
            for (int i = 0; i < size; i++) {
                seq[i] = i;
            }

            StdRandom.shuffle(seq);

            index = 0;
        }

        @Override
        public boolean hasNext() {
            return !(index == seq.length);
        }

        @Override
        public Item next() {
            Item item = values[seq[index]];
            index++;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove method is not supported");
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        int prevSize = -1;
    }
}
