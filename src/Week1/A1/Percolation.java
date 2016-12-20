package Week1.A1;


import java.util.ArrayList;

/**
 * Created by Jashan Shewakramani
 * Description: API for running statistical simulations on a Percolation network model
 *
 * Notes:
 * I could've just used Princeton's nice library instead of recreating the API (but this is better for learning)
 * It would also be a good idea to write a more object-oriented implementation of this algorithm
 * This would be highly counter-intuitive when reading from scratch
 */

public class Percolation {

    private int dimension;
    private boolean[] openSites;
    private int[] forest;
    private int[] sizes;

    // useful variable to determine connections from top to bottom
    private int virtualTopIndex;
    private int virtualBottomIndex;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be greater than 0");

        dimension = n;
        openSites = new boolean[n * n + 2];
        forest = new int[n * n + 2];
        sizes = new int[n * n + 2];

        virtualTopIndex = n * n;
        virtualBottomIndex = n * n + 1;

        // this array uses the same implementation we saw in the lectures (path compression +
        for (int i = 0; i < forest.length; i++) {
            forest[i] = i;
            sizes[i] = 1;
        }
    }


    public void open(int row, int col) {
        if (row <= 0 || col <= 0 || row > dimension || col > dimension)
            throw new IllegalArgumentException("Coordinates out of bounds");
        // Open up the site at the given coordinate
        int siteIndex = (row - 1) * dimension + (col - 1);
        openSites[siteIndex] = true;

        // if the row is on the top or bottom, make according unions
        if (row == 1)
            union(siteIndex, virtualTopIndex);

        if (row == dimension)
            union(siteIndex, virtualBottomIndex);

        int[] leftRight;
        int[] upDown;

        leftRight = oneDimensionAdjacency(col);
        upDown = oneDimensionAdjacency(row);

        ArrayList<Integer> finalAdjacencies = new ArrayList<>();

        for (int elem : leftRight)
            finalAdjacencies.add((row - 1) * dimension + elem);

        for (int elem: upDown)
            finalAdjacencies.add(elem * dimension + (col - 1));

        // now finally, check if the adjacent cells are open, and make necessary unions
        for (Integer i: finalAdjacencies) {
            if (openSites[i]) {
                union(siteIndex, i);
            }
        }

    }

    private int[] oneDimensionAdjacency(int rc) {
        int[] result;
        if (rc == 1)
            result = new int[]{1};
        else if (rc == dimension)
            result = new int[]{dimension - 2};
        else
            result = new int[]{(rc - 1) + 1, (rc - 1) - 1};

        return result;
    }


    private int getRoot(int p) {
        if (forest[p] == p) {
            return p;
        } else {
            forest[p] = getRoot(forest[p]);
            return forest[p];
        }
    }

    // makes a union between two connected classes
    private void union(int p, int q) {
        int pParent = getRoot(p);
        int qParent = getRoot(q);

        if (pParent == qParent)
            return;

        if (sizes[pParent] < sizes[qParent]) {
            forest[pParent] = qParent;
            sizes[qParent] += sizes[pParent];
        } else {
            forest[qParent] = pParent;
            sizes[pParent] += sizes[qParent];
        }
    }

    private boolean isConnected(int p, int q) {
        return getRoot(p) == getRoot(q);
    }

    public boolean isOpen(int row, int col) {
        return openSites[row * dimension + col];
    }

    public boolean isFull(int row, int col) {
        return !isOpen(row, col);
    }

    public boolean percolates() {
        return isConnected(virtualTopIndex, virtualBottomIndex);
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(1, 1);
        p.open(1, 3);
        p.open(2, 2);
        p.open(3, 2);

        System.out.println(p.percolates());
    }
}
