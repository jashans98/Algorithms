package Week1.A1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by Jashan Shewakramani
 * Description: Class for running Monte Carlo Simulations on the Percolation Model
 */
public class PercolationStats {

    private double[] dataset;
    private int dimension;
    private int numTrials;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("arguments must be greater than zero");
        dimension = n;
        numTrials = trials;
        dataset = new double[numTrials];
        for (int i = 0; i < numTrials; i++) {
            dataset[i] = runTrial();
        }
    }

    // check if you can select stuff in O(1)
    private double runTrial() {

        Percolation percolation = new Percolation(dimension);
        int numIters = 0;

        while (!percolation.percolates()) {
            int encodedSite = StdRandom.uniform(0, dimension * dimension);
            int row = getRow(encodedSite);
            int col = getCol(encodedSite);

            if (!percolation.isOpen(row, col)) {
                percolation.open(row, col);
                numIters++;
            }
        }

        return ((double) numIters) / ((double) (dimension * dimension));
    }

    private int getRow(int encodedSite) {
        return (encodedSite / dimension) + 1;
    }

    private int getCol(int encodedSite) {
        return (encodedSite % dimension) + 1;
    }

    public double mean() {
        return StdStats.mean(dataset);
    }

    public double stddev() {
        return StdStats.stddev(dataset);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(numTrials);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(numTrials);
    }

    public static void main(String[] args) {
        int n;
        int t;
        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            t = Integer.parseInt(args[1]);
        } else {
            n = 50;
            t = 500;
        }

        PercolationStats p = new PercolationStats(n, t);
        System.out.println(p.mean());
        System.out.println(p.stddev());
        System.out.println(p.confidenceLo() + ", " + p.confidenceHi());
    }
}
