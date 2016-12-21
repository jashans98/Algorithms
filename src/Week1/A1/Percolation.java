package Week1.A1;


/**
 * Created by Jashan Shewakramani
 *
 * Description: Completely rewritten model with cleaner implementation and path compression
 */
public class Percolation {
    private int virtualTopIndex;
    private int dimension;
    private int virtualBottomIndex;
    private int[] forest; // we'll be adding our own path compression
    private int[] sizes;
    private boolean[] openSites;

    public Percolation(int n) {
        dimension = n;
        virtualTopIndex = n * n;
        virtualBottomIndex = n * n + 1;

        forest = new int[n * n + 2];
        openSites = new boolean[n * n + 2];
        sizes = new int[n * n + 2];

        openSites[virtualTopIndex] = true;
        openSites[virtualBottomIndex] = true;

        for (int i = 0; i < forest.length; i++) {
            forest[i] = i;
            sizes[i] = 1;
        }
    }

    private int siteIndex(int row, int col) {
        if (row <= 0 || col <= 0 || row > dimension || col > dimension)
            throw new IndexOutOfBoundsException("Invalid coordinates!");

        return (row - 1) * dimension + (col - 1);
    }

    public boolean isOpen(int row, int col) {
        return openSites[siteIndex(row, col)];
    }

    public void open(int row, int col) {
        if (isOpen(row, col))
            return;

        int index = siteIndex(row, col);
        openSites[index] = true;

        if (row == 1)
            union(index, virtualTopIndex);


        // make possible adjacent unions
        makePossibleUnion(index, row+1, col);
        makePossibleUnion(index, row-1, col);
        makePossibleUnion(index, row, col+1);
        makePossibleUnion(index, row, col-1);

    }

    public boolean isFull(int row, int col) {
        return connected(siteIndex(row, col), virtualTopIndex);
    }

    public boolean percolates() {
        for (int i = 1; i <= dimension; i++) {
            if (connected(siteIndex(dimension, i), virtualTopIndex))
                return true;
        }

        return false;
    }

    private void makePossibleUnion(int index, int tRow, int tCol) {
        if (tRow <= 0 || tCol <= 0 || tCol > dimension || tRow > dimension)
            return;

        if (isOpen(tRow, tCol))
            union(index, siteIndex(tRow, tCol));
    }

    private int getRoot(int p) {
        if (forest[p] == p) {
            return p;
        } else {
            forest[p] = getRoot(forest[p]);
            return forest[p];
        }
    }

    private boolean connected(int p, int q) {
        return getRoot(p) == getRoot(q);
    }

    private void union(int p, int q) {
        int pParent = getRoot(p);
        int qParent = getRoot(q);

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

    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(1, 1);
        p.open(2, 2);
        p.open(2, 1);
        p.open(3, 1);
        p.open(3, 3);
        System.out.println(p.percolates());
        System.out.println(p.isFull(3, 3));
    }
}
