import java.util.ArrayList;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;


public class Solver {
    private final MinPQ<Board> boards;

    private class BoardComp implements Comparator<Board> {
        private final Board board;

        public BoardComp(Board board) {
            this.board = board;
        }

        public int compare(Board v, Board w) {
            return v.compareTo(w);
        }

    }

    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        Board twin = initial.twin();
        BoardComp comp = new BoardComp(initial);
        boards = new MinPQ<Board>(comp);
        boards.insert(initial);
        Board candidat;
        while (!boards.isEmpty()) {
            candidat = boards.delMin();
        }
    }
    public boolean isSolvable() {
        // is the initial board solvable?
        return false;
    }
    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
        return -1;
    }
    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        return new ArrayList<Board>();
    }
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        System.out.println(initial.dimension());
        System.out.println(initial.hamming());
        System.out.println(initial.manhattan());

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
}
