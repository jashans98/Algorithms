package Week1;

/**
 * Created by Jashan Shewakramani
 * Description: QuickUnion (lazy approach) to the dynamic connectivity problem
 *
 * Notes:
 * This is a lazy (and naive) approach, very different from QuickFind
 * Computations are made only as necessary, rather than continuously
 * Storing trees, and changing fewer elements can benefit on running time significantly
 *
 * But in the worst case, all operations here are O(N)
 * Multiple unions could easily bring things up to O(M * N) where M is the number of Union operations
 * The find operation could get incredibly expensive depending on the height of the tree
 *
 * Check out WeightedQuickUnion too prevent trees from getting too big
 */
public class QuickUnion {
    private int[] forest;

    public QuickUnion(int N) {
        forest = new int[N];

        // initialize the array as necessary
        for (int i = 0; i < N; i++) {
            forest[i] = i;
        }
    }

    // super clean recursive solution to get root parent of an element in the tree
    private int getParent(int p) {
        if (forest[p] == p)
            return p;
        else
            return getParent(forest[p]);
    }

    // two nodes in the network are connected if they have the same parent
    public boolean connected(int p, int q) {
        return getParent(p) == getParent(q);
    }

    // union involves setting the parent of the parent of q, to the parent of p
    public void union(int p, int q) {
        forest[getParent(q)] = getParent(p);
    }
}
