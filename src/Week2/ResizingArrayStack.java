package Week2;

/**
 * Created by Jashan Shewakramani
 * Description: Stack implementation using resizing arrays
 */
public class ResizingArrayStack {
    private int n = 0;
    private String[] container = new String[1];

    public void push(String item) {
        container[n] = item;
        n++;
        if (n == container.length)
            resize(container.length * 2);
    }

    public String pop() {
        n--;
        String item = container[n];
        container[n] = null; // delete the unnecessary reference for the garbage collector

        // shrink when it's small enough
        if (n == container.length/4)
            resize(container.length / 2);

        return item;
    }

    private void resize(int size) {
        String[] copy = new String[size];
        for (int i = 0; i < container.length; i++) {
            copy[i] = container[i];
        }
        container = copy;
    }
}
