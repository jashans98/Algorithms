
import Week2.LinkedStack;
import Week2.ResizingArrayQueue;
import Week2.ResizingArrayStack;
import Week3.Mergesort;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by: Jashan Shewakramani
 * Description: File for testing miscellaneous algorithms
 */

public class Main {

    public static void main(String[] args) {
        Integer[] t = {45, 2, 213, 12, 14, 342, 15};
        System.out.println("Array before sorting:");
        printArray(t);
        Mergesort.sort(t);
        System.out.println("Array after sorting:");
        printArray(t);
    }

    private static void printArray(Object[] a) {
        for (int i = 0; i < a.length; i++) {
            if (i != a.length - 1)
                System.out.print(a[i] + ", ");
            else
                System.out.println(a[i]);
        }
    }

    private static void testResizeQueue() {
        ResizingArrayQueue<String> queue = new ResizingArrayQueue<>();
        for (int i = 0; i < 5000; i++)
            queue.enqueue(String.valueOf(i));

        for (int i = 0; i < 5000; i++) {
            String item = queue.dequeue();
            if (!item.equals(String.valueOf(i)))
                System.out.println("Error: " + item + " expected " + String.valueOf(i));

        }
    }

    private static void runResizeTest() {
        System.out.println("Resizing Array Stack Test");
        System.out.println("=========================");
        ResizingArrayStack<String> s = new ResizingArrayStack<>();
        String toPush = "hello world";

        Stopwatch stopwatch = new Stopwatch();
        for (int i = 0; i < 1000000; i++) {
            s.push(toPush);
        }

        System.out.println("Time after 1,000,000 pushes: " + stopwatch.elapsedTime());
        System.out.println("Average time: " + stopwatch.elapsedTime() / 1000000);

        for (int i = 0; i < 7890; i++) {
            s.pop();
        }

        System.out.println("Time after 1007890 operations: " + stopwatch.elapsedTime());
        System.out.println("Average time per operation: " + stopwatch.elapsedTime() / 1007890);
        System.out.println("\n");

    }

    private static void runLinkedTest() {
        System.out.println("LinkedList Stack test");
        System.out.println("=========================");
        LinkedStack<String> s = new LinkedStack<>();
        String toPush = "hello world";

        Stopwatch stopwatch = new Stopwatch();
        for (int i = 0; i < 1000000; i++) {
            s.push(toPush);
        }

        System.out.println("Time after 1,000,000 pushes: " + stopwatch.elapsedTime());
        System.out.println("Average time: " + stopwatch.elapsedTime() / 1000000);

        for (int i = 0; i < 7890; i++) {
            s.pop();
        }

        System.out.println("Time after 1007890 operations: " + stopwatch.elapsedTime());
        System.out.println("Average time per operation: " + stopwatch.elapsedTime() / 1007890);
        System.out.println("\n");
    }
}
