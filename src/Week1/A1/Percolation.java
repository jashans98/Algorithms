package Week1.A1;

import Week1.WeightedQuickUnion;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by Jashan Shewakramani
 *
 * Description: Percolation without Path Compression to get 100 on assignment
 */
public class Percolation {
    private int virtualTopIndex;
    private int dimension;
    private int virtualBottomIndex;
    private WeightedQuickUnionUF forest;
    private WeightedQuickUnionUF forest2;
    private boolean[] openSites;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("N cannot be less than zero");
        dimension = n;
        virtualTopIndex = n * n;
        virtualBottomIndex = n * n + 1;

        forest = new WeightedQuickUnionUF(n * n + 2);
        forest2 = new WeightedQuickUnionUF(n * n + 2);
        openSites = new boolean[n * n + 2];

        openSites[virtualTopIndex] = true;
        openSites[virtualBottomIndex] = true;

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

        if (row == 1) {
            forest.union(index, virtualTopIndex);
            forest2.union(index, virtualTopIndex);
        }

        if (row == dimension)
            forest.union(index, virtualBottomIndex);


        // make possible adjacent unions
        makePossibleUnion(index, row+1, col, forest);
        makePossibleUnion(index, row-1, col, forest);
        makePossibleUnion(index, row, col+1, forest);
        makePossibleUnion(index, row, col-1, forest);

        // make possible adjacent unions
        makePossibleUnion(index, row+1, col, forest2);
        makePossibleUnion(index, row-1, col, forest2);
        makePossibleUnion(index, row, col+1, forest2);
        makePossibleUnion(index, row, col-1, forest2);

    }

    public boolean isFull(int row, int col) {
        return forest2.connected(siteIndex(row, col), virtualTopIndex);
    }

    public boolean percolates() {
        return forest.connected(virtualTopIndex, virtualBottomIndex);
    }

    private void makePossibleUnion(int index, int tRow, int tCol, WeightedQuickUnionUF forest) {
        if (tRow <= 0 || tCol <= 0 || tCol > dimension || tRow > dimension)
            return;

        if (isOpen(tRow, tCol))
            forest.union(index, siteIndex(tRow, tCol));
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
