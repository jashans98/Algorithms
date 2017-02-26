package Week4.A4;

import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;

/**
 * Created by Jashan Shewakramani
 * Description: Base Board class for A* search algorithm
 */
public class Board {

    private int[][] blocks;
    private int dimension;

    private int zeroIndex;

    public Board(int[][] config) {
        if (config == null)
            throw new NullPointerException("Board cannot be null");

        blocks = config.clone();
        dimension = blocks.length;
        zeroIndex = findZero();

        assert zeroIndex != -1;
    }

    private int findZero() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0)
                    return i * dimension + j;
            }
        }

        return -1;
    }

    public int dimension() {
        return dimension;
    }

    // number of blocks out of place
    public int hamming() {
        int result = 0;

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int expectedVal;
                if (i == dimension() - 1 && j == dimension() - 1)
                    continue;

                else
                    expectedVal = i * dimension() + j + 1;

                if (blocks[i][j] != expectedVal)
                    result++;
            }
        }

        return result;
    }


    // manhattan priority function
    public int manhattan() {
        int result = 0;

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int val = blocks[i][j];

                if (val != 0) {
                    int expectedRow = (val - 1) / dimension();
                    int expectedCol = (val - 1) % dimension();
                    result += Math.abs(i - expectedRow) + Math.abs(j - expectedCol);
                }


            }
        }

        return result;
    }


    // checks if the given board is equal to the goal board
    public boolean isGoal() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int expectedVal;
                if (i == dimension-1 && j == dimension-1)
                    expectedVal = 0;
                else
                    expectedVal = i * dimension + j + 1;

                if (blocks[i][j] != expectedVal)
                    return false;
            }
        }

        return true;
    }

    // checks if two board objects are equal
    public boolean equals(Object y) {
        if (!(y instanceof Board))
            return false;

        Board b = (Board) y;
        if (b.dimension() != this.dimension())
            return false;

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (b.blocks[i][j] != this.blocks[i][j])
                    return false;
            }
        }

        return true;
    }

    public static int[][] copy(int[][] init) {
        int dim = init.length;

        int[][] res = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                res[i][j] = init[i][j];
            }
        }

        return res;
    }

    // return a new board with any two blocks swapped
    public Board twin() {
        int[][] twin = Board.copy(blocks);
        int a;
        int b;

        do {
            a = StdRandom.uniform(dimension * dimension);
            b = StdRandom.uniform(dimension * dimension);
        } while (twin[a / dimension][a % dimension] == 0 ||
                 twin[b / dimension][b % dimension] == 0 ||
                 a == b);

        int temp = twin[a / dimension][a % dimension];
        twin[a / dimension][a % dimension] = twin[b / dimension][b % dimension];
        twin[b / dimension][b % dimension] = temp;

        return new Board(twin);
    }

    // convenience for visualization
    public String toString() {
        StringBuilder result = new StringBuilder((2 * dimension) * dimension);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                result.append(blocks[i][j]);
                if (j == dimension - 1)
                    result.append("\n");
                else
                    result.append(" ");
            }
        }

        return result.toString();
    }

    public Iterable<Board> neighbours() {
        ArrayList<Board> result = new ArrayList<>();
        int zeroRow = zeroIndex / dimension;
        int zeroCol = zeroIndex % dimension;

        ArrayList<Integer> candidates = new ArrayList<>();
        if (zeroRow < dimension - 1) {
            candidates.add((zeroRow + 1) * dimension + zeroCol);
        }

        if (zeroRow > 0) {
            candidates.add((zeroRow - 1) * dimension + zeroCol);
        }

        if (zeroCol > 0) {
            candidates.add(zeroRow * dimension + (zeroCol - 1));
        }

        if (zeroCol < dimension - 1) {
            candidates.add(zeroRow * dimension + (zeroCol + 1));
        }

        for (Integer candidate : candidates) {
            result.add(new Board(exch(blocks, zeroRow, zeroCol, candidate / dimension, candidate % dimension)));
        }

        return result;

    }

    public int[][] exch(int init[][], int i, int j, int x, int y) {
        int[][] result = Board.copy(init);
        int temp = result[i][j];
        result[i][j] = result[x][y];
        result[x][y] = temp;

        return result;
    }

    // unit tests
    public static void main(String[] args) {
        int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board test = new Board(blocks);

        System.out.println(test);

        for (Board n: test.neighbours())
            System.out.println(n);
    }

}
