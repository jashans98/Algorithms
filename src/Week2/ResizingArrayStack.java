package Week2;

import java.util.Iterator;

/**
 * Created by Jashan Shewakramani
 * Description: Stack implementation using resizing arrays
 */
public class ResizingArrayStack<T> implements Iterable<T>{

    private int n = 0;

    // pretty disgusting implementation, but Java gives us no other option
    private T[] container = (T[]) new Object[1];

    public void push(T item) {
        container[n] = item;
        n++;
        if (n == container.length)
            resize(container.length * 2);
    }

    public T pop() {
        n--;
        T item = container[n];
        container[n] = null; // delete the unnecessary reference for the garbage collector

        // shrink when it's small enough
        if (n == container.length/4)
            resize(container.length / 2);

        return item;
    }

    private void resize(int size) {
        T[] copy = (T[]) new Object[size];
        for (int i = 0; i < container.length; i++) {
            copy[i] = container[i];
        }
        container = copy;
    }

    @Override
    public Iterator<T> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<T> {

        private int index = n - 1;

        @Override
        public boolean hasNext() {
            return index != -1;
        }

        @Override
        public T next() {
            T item = container[index];
            index--;
            return item;
        }
    }
}
