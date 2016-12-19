package Week1;

/**
 * Created by Jashan Shewakramani
 * Description: WeightedQuickUnion for cleaner solution to Dynamic Connectivity
 *
 * Notes:
 * Instead of the naive implementation, the smaller tree is always made a child of the larger one
 * Thus, trees don't usually get too tall (and the algorithm becomes more efficient)
 *
 * This means that the running time of finding the root becomes O(log(n)) instead of O(n) (pretty sweet eh?)
 */
public class WeightedQuickUnion {
    private int forest[];
    private int sizes[];

    public WeightedQuickUnion(int N) {
        forest = new int[N];
        sizes = new int[N];

        for (int i = 0; i < N; i++) {
            forest[i] = i;
            sizes[i] = 1; // all trees are initially of size 1
        }
    }

    // this implementation adds some path compression
    // after computing the root, we just set each node to point to the root directly
    // the next time the root is called we'll access it directly (instead of traversing through all over again)
    private int getParent(int p) {
        if (forest[p] == p)
            return p;
        else {
            forest[p] = getParent(forest[p]);
            return forest[p];
        }
    }

    public boolean connected(int p, int q) {
        return forest[p] == forest[q];
    }

    public void union(int p, int q) {
        int pParent = getParent(p);
        int qParent = getParent(q);

        // don't make any changes if they're already connected
        if (pParent == qParent)
            return;

        // make the smaller tree a child of the larger tree
        if (sizes[pParent] < sizes[qParent]) {
            forest[pParent] = qParent;
            sizes[qParent] += sizes[pParent];
        } else {
            forest[qParent] = pParent;
            sizes[pParent] += sizes[qParent];
        }
    }
}
