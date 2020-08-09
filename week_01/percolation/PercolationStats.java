/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final int n;
    private final int trials;
    private final double[] X;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        this.n = n;
        this.trials = trials;
        this.X = new double[trials];
        // Run Experiment
        for (int trial = 0; trial < trials; trial++) {
            Percolation percolation = new Percolation(n);

            int[] indices = new int[n*n];
            for (int i = 0; i < indices.length ; i++) {
                indices[i] = i ;
            }
            StdRandom.shuffle(indices);
            int r, c;
            for (int index : indices) {
                r = (index / n) + 1 ;
                c = (index % n) + 1 ;
                if (!percolation.percolates()) {
                    if (!percolation.isOpen(r, c)) {
                        this.X[trial] += 1.0;
                        percolation.open(r, c);
                    }
                } else {
                    break ;
                }
            }

            this.X[trial] /= (n*n);
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.printf("95%% confidence interval  = [%f, %f]\n", stats.confidenceLo(),
                          stats.confidenceHi());
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(X);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(X);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / java.lang.Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / java.lang.Math.sqrt(trials);
    }

}
