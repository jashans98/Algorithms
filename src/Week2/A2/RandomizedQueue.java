package Week2.A2;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Jashan Shewakramani
 * Description: Resizing Array implementation of randomized queue
 */
public class RandomizedQueue<Item> implements Iterable<Item>{
    private Item[] values;
    private Deque<Integer> slackSpots;
    private int size;
    private int n;

    public RandomizedQueue() {
        values = (Item[]) new Object[1];
        slackSpots = new Deque<>();
        size = 0;
        n = 0;
    }

    private int arrSize() {
        return values.length;
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
            values[slackSpots.removeFirst()] = item;
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
        slackSpots.addFirst(removeIndex);

        size--;

        if (size == values.length / 3)
            resizeAndDefragment(values.length / 2);

        return toDequeue;

    }

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty");
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
        slackSpots = new Deque<>(); // we don't have anymore slack spots after de-fragmentation
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
            if (!hasNext())
                throw new NoSuchElementException("No more elements left in iterator");

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

        int n = 64;

        for (int i = 0; i < 2*n + 1; i++) {
            queue.enqueue(i);
            if (prevSize == -1 || queue.arrSize() > prevSize) {
                prevSize = queue.arrSize();
                System.out.println(prevSize);
            }
        }

        for (int i = 0; i < n; i++) {
            queue.dequeue();
            if (prevSize != queue.arrSize()) {
                prevSize = queue.arrSize();
                System.out.println(prevSize);
            }
        }
    }
}
