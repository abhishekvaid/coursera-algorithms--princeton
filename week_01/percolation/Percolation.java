/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private int openSites;
    private final boolean[] isOpen;
    private final WeightedQuickUnionUF quickUF;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.n  = n;
        this.isOpen = new boolean[n*n+2];
        this.quickUF = new WeightedQuickUnionUF(n*n+2);
    }

    private int makeIndex(int r, int c) {
        r-- ; c-- ;
        return c + r * n + 1;
    }

    private boolean validate(int r, int c) {
        return r >= 1 && c >= 1 && r <= n && c <= n;
    }

    private void validateSite(int r, int c) {
        if (!validate(r, c))
            throw new IllegalArgumentException();
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateSite(row, col);
        int index = makeIndex(row, col);
        if (!isOpen[index]) {
            isOpen[index] = true;
            openSites++;
            for (int i : new int[]{1, -1}) {
                if (validate(row+i, col)) {
                    int n1 = makeIndex(row + i, col);
                    if (isOpen[n1])
                        quickUF.union(index, n1);
                }
                if (validate(row, col+i)) {
                    int n2 = makeIndex(row, col + i);
                    if (isOpen[n2])
                        quickUF.union(index, n2);
                }
            }
            if (row == 1)
                quickUF.union(index, 0);
            if (row == n)
                quickUF.union(index, n*n+1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateSite(row, col);
        return isOpen[makeIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateSite(row, col);
        return quickUF.find(0) == quickUF.find(makeIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return quickUF.find(0) == quickUF.find(n*n+1);
    }

    // test client (optional)
    public static void main(String[] args) {}
}
