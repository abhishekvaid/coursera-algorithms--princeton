/* *****************************************************************************
 *  Name: Abhishek Vaid
 *  Date: 9th August 2020
 *  Description: Week 4 Assignment of Algorithms I (Coursera)
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private int[][] tiles;
    private int blankI, blankJ;
    private int n;

    private int manhattan;
    private int hamming;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        this.tiles = Arrays.stream(tiles).map(int[]::clone).toArray(int[][]::new);
        n = this.tiles.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] == 0){
                    blankI = i;
                    blankJ = j;
                    break ;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != (i * n + j + 1)) {
                    manhattan += Math.abs(i - (tiles[i][j] - 1) / n);
                    manhattan += Math.abs(j - (tiles[i][j] - 1) % n);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0)
                    hamming += tiles[i][j] == (i * n + j + 1) ? 0 : 1;
            }
        }

    }


    private Board(Board board, int newBlankI, int newBlankJ){

        this.tiles = Arrays.stream(board.tiles).map(int[]::clone).toArray(int[][]::new);

        this.blankI = newBlankI;
        this.blankJ = newBlankJ;

        this.tiles[board.blankI][board.blankJ] = this.tiles[blankI][blankJ];
        this.tiles[blankI][blankJ] = 0;

        n = board.n;

        // if (tiles[i][j] != 0 && tiles[i][j] != (i * n + j + 1)) {
        //     dist += Math.abs(i - (tiles[i][j] - 1) / n);
        //     dist += Math.abs(j - (tiles[i][j] - 1) % n);
        // }

        int val = board.tiles[newBlankI][newBlankJ];

        manhattan = board.manhattan;
        manhattan -= (Math.abs(newBlankI-(val-1)/n) + Math.abs(newBlankJ-(val-1)%n));
        manhattan += (Math.abs(board.blankI-(val-1)/n) + Math.abs(board.blankJ-(val-1)%n));

        // int trueManhattan = _manhattan();
        //
        // if (manhattan != trueManhattan) {
        //     System.out.println("Fucked");
        // }
        //
        // manhattan = trueManhattan;

        // dist += tiles[i][j] == (i * n + j + 1) ? 0 : 1;
        hamming = board.hamming;
        hamming -= (val == (newBlankI * n + newBlankJ + 1) ? 0 : 1);
        hamming += (val == (board.blankI * n + board.blankJ + 1) ? 0 : 1);

    }

    // unit testing (not graded)
    public static void main(String[] args) {

        Board board1 = new Board(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } });
        System.out.println(board1);
        System.out.println("------------------");
        System.out.println(board1.twin());
        System.out.println(board1.hamming());
        System.out.println(board1.manhattan());

        Board board2 = new Board(new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } });
        System.out.println(board2);
        System.out.println("------------------");
        System.out.println(board2.twin());
        System.out.println(board2.hamming());
        System.out.println(board2.manhattan());

        Board board3 = new Board(new int[][] { { 1, 0 }, { 2, 3 } });
        System.out.println(board3);
        System.out.println("------------------");
        System.out.println(board3.twin());
        System.out.println(board3.hamming());
        System.out.println(board3.manhattan());

        Board board4 = new Board(new int[][] { { 2, 0 }, { 1, 3 } });
        Board board5 = new Board(new int[][] { { 3, 0 }, { 1, 2 } });

        System.out.println(board4.equals(board5));
    }

    // number of tiles out of place
    // private int _hamming() {
    //     int dist = 0, n = dimension();
    //     for (int i = 0; i < n; i++) {
    //         for (int j = 0; j < n; j++) {
    //             if (tiles[i][j] != 0)
    //                 dist += tiles[i][j] == (i * n + j + 1) ? 0 : 1;
    //         }
    //     }
    //     return dist;
    // }

    //
    // sum of Manhattan distances between tiles and goal
    // private int _manhattan() {
    //     int dist = 0, n = dimension(), i_, j_;
    //     for (int i = 0; i < n; i++) {
    //         for (int j = 0; j < n; j++) {
    //             if (tiles[i][j] != 0 && tiles[i][j] != (i * n + j + 1)) {
    //                 dist += abs(i - (tiles[i][j] - 1) / n);
    //                 dist += abs(j - (tiles[i][j] - 1) % n);
    //             }
    //         }
    //     }
    //     return dist;
    // }

    public int manhattan() {
        return manhattan;
    }

    public int hamming() {
        return hamming;
    }

    // // board dimension n
    public int dimension() {
        return n;
    }


    //
    // // is this board the goal board?
    public boolean isGoal() {
        return hamming == 0;
    }


    // // does this board equal y?
    public boolean equals(Object y) {
        // System.out.println("Fucked");
        if (y == null)
            return false;
        if (this == y)
            return true;
        if (!(y instanceof Board))
            return false;
        Board that = (Board) y;

        boolean unequal = (this.hamming != that.hamming) ||
                (this.manhattan != that.manhattan) ||
                (this.blankJ != that.blankJ) ||
                (this.blankI != that.blankI);

        return unequal ? false : Arrays.deepEquals(this.tiles, that.tiles);

    }

    // // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();
        for (int delta : new int[]{1, -1}) {
            if (blankI + delta < n && blankI + delta >= 0) {
                neighbors.add(new Board(this, blankI+delta, blankJ));
            }
            if (blankJ + delta < n && blankJ + delta >= 0) {
                neighbors.add(new Board(this, blankI, blankJ+delta));
            }
        }
        return neighbors;
    }

    // // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        int[][] twinTiles = Arrays.stream(tiles).map(int[]::clone).toArray(int[][]::new);

        // Board twin = new Board(this.tiles);
        int[][] directions = {{-1, -1}, {1, 1}, {-1, 1}, {1, -1}};
        int i_, j_;
        for (int i = 0; i < directions.length; i++) {
            i_ = this.blankI+directions[i][0];
            j_ = this.blankJ+directions[i][1];
            if (i_ >= 0 && i_ < n && j_ >= 0 && j_ < n){
                int tmp = twinTiles[i_][j_];
                twinTiles[i_][j_] = twinTiles[i_][blankJ];
                twinTiles[i_][blankJ] = tmp;
                return new Board(twinTiles);
            }
        }
        throw new IllegalArgumentException("This statemenet is unreachable");
    }

    // string representation of this board
    public String toString() {

        StringBuilder s = new StringBuilder();
        // s.append(String.format("Man: %3d | Hamming: %d\n", this.manhattan, this.hamming));
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();

        // return "\n" + Arrays.stream(tiles)
        //              .map(row -> Arrays.stream(row).mapToObj(i -> String.format("%2d", i))
        //                                .collect(Collectors.joining(" | ")))
        //              .collect(Collectors.joining("\n")) + "\n";
    }

}
