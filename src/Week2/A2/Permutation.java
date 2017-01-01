package Week2.A2;

import edu.princeton.cs.algs4.StdIn;

/**
 * Created by Jashan Shewakramani
 *
 */
public class Permutation {
    public static void main(String[] args) {
        int k;
        if (args.length == 0)
            k = 7;
        else
            k = Integer.parseInt(args[0]);

        RandomizedQueue<String> elems = new RandomizedQueue<>();
        String[] inputs = StdIn.readAllStrings();
        // String[] inputs = {"hello", "world", "my", "name", "is", "Jashan", "and", "I", "am", "awesome"};

        for (String input: inputs)
            elems.enqueue(input);

        for (int i = 0; i < k; i++) {
            System.out.println(elems.dequeue());
        }
    }
}
