/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final boolean[] isOpen;
    private final boolean[] isConnectedToBottom;
    private final WeightedQuickUnionUF quickUF;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        this.n = n;
        this.isOpen = new boolean[n * n + 1];
        this.isConnectedToBottom = new boolean[n * n + 1];
        this.quickUF = new WeightedQuickUnionUF(n * n + 1);
    }

    // test client (optional)
    public static void main(String[] args) {

    }

    private int makeIndex(int r, int c) {
        return --c + --r * n + 1;
    }

    private boolean withinBounds(int r, int c) {
        return r >= 1 && c >= 1 && r <= n && c <= n;
    }

    private void validateIndices(int r, int c) {
        if (!withinBounds(r, c))
            throw new IllegalArgumentException();
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateIndices(row, col);
        int index = makeIndex(row, col);
        if (!isOpen[index]) {
            boolean bottomFlag = (row == n);
            isOpen[index] = true;
            openSites++;
            for (int i : new int[]{1, -1}) {
                if (withinBounds(row + i, col)) {
                    int neighborIndex = makeIndex(row + i, col);
                    if (isOpen[neighborIndex]) {
                        bottomFlag = bottomFlag || isConnectedToBottom[quickUF.find(neighborIndex)];
                        quickUF.union(index, neighborIndex);
                    }
                }
                if (withinBounds(row, col + i)) {
                    int neighborIndex = makeIndex(row, col + i);
                    if (isOpen[neighborIndex]) {
                        bottomFlag = bottomFlag || isConnectedToBottom[quickUF.find(neighborIndex)];
                        quickUF.union(index, neighborIndex);
                    }
                }
            }

            if (row == 1)
                quickUF.union(index, 0);
            if (bottomFlag)
                isConnectedToBottom[quickUF.find(index)] = true;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateIndices(row, col);
        return isOpen[makeIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateIndices(row, col);
        return quickUF.find(0) == quickUF.find(makeIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return isConnectedToBottom[quickUF.find(0)];
    }
}
