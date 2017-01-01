package Week2;

/**
 * Created by Jashan Shewakramani
 * Description: Exercise to build queue using resizing arrays
 */
public class ResizingArrayQueue<T> {
    private int head;
    private int tail;

    private T[] values;

    public ResizingArrayQueue() {
        head = 0;
        tail = 0;

        values = (T[]) new Object[1];
    }

    public void enqueue(T item) {
        values[head] = item;
        head--;
        if (head < 0)
            doubleSize();
    }

    public T dequeue() {
        T item = values[tail];
        tail--;
        if (tail < values.length / 4)
            shrink();

        return item;
    }

    private void doubleSize() {
        int currSize = values.length;
        T[] copy = (T[]) new Object[2 * currSize];

        for (int i = currSize; i < 2 * currSize; i++) {
            copy[i] = values[i - currSize];
        }

        head = currSize - 1;
        tail = 2 * currSize - 1;
        values = copy;
    }

    private void shrink() {
        int currSize = values.length;
        T[] copy = (T[]) new Object[currSize / 2];

        for (int i = 0; i < currSize / 2; i++) {
            copy[i] = values[i];
        }

        values = copy;
        // we don't need to update the head or the tail in this case
    }
}
