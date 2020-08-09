/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solver {

    private List<Board> path;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null)
            throw new IllegalArgumentException();

        Comparator<SearchNode> comparator = (o1, o2) ->  o1.priority - o2.priority;

        List<MinPQ<SearchNode>> pqList = new ArrayList<>(2);
        pqList.add(new MinPQ<SearchNode>(1 << 10, comparator));
        pqList.add(new MinPQ<SearchNode>(1 << 10, comparator));

        MinPQ<SearchNode> minPq;
        SearchNode curNode;

        pqList.get(0).insert(new SearchNode(initial, null, 0));
        pqList.get(1).insert(new SearchNode(initial.twin(), null, 0));

        for (int i = 0; ; i++) {
            minPq = pqList.get(i % 2);
            curNode = minPq.delMin();
            if (curNode.board.isGoal()) {
                if (i % 2 != 0) {
                    path = null;
                }
                else {
                    path = new ArrayList<>(1 << 7);
                    while (curNode != null) {
                        path.add(curNode.board);
                        curNode = curNode.parent;
                    }
                    Collections.reverse(path);
                }
                break;
            }
            else {
                for (Board neighbour : curNode.board.neighbors()) {
                    if (curNode.parent == null || !neighbour.equals(curNode.parent.board)) {
                        minPq.insert(new SearchNode(neighbour, curNode, curNode.distance+1));
                    }
                }
            }
        }

    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return path != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return path == null ? -1 : path.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return path;
    }

    private class SearchNode {

        public int distance;
        public int priority;
        Board board;
        SearchNode parent;

        public SearchNode(Board board, SearchNode parent, int distance) {
            this.board = board;
            this.parent = parent;
            this.distance = distance;
            this.priority = distance + board.manhattan();
        }

    }

}
