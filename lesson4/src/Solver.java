import java.lang.String;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;


public class Solver {
    private ArrayList<Board> solution_boards = new ArrayList<Board>();
    private int solution_moves;
    private boolean solvable = false;

    private class SearchNode {
        private final Board board;
        private final SearchNode prev;
        private final int moves;

        public SearchNode(Board board, SearchNode prev, int moves) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;
        }

        public int priority() {
            return moves + board.manhattan();
        }

        public int compareTo(SearchNode that) {
            if (this.priority() > that.priority()) return +1;
            if (this.priority() < that.priority()) return -1;
            return 0;
        }

        private Stack<Board> path_to_root() {
            Stack<Board> path = new Stack<Board>();
            SearchNode cursor = this;
            path.push(cursor.board);
            while (cursor.prev != null) {
                cursor = cursor.prev;
                path.push(cursor.board);
            }
            return path;
        }
    }

    private class SearchComp implements Comparator<SearchNode> {
//        private final Board board;
//
//        public SearchComp(Board board) {
//            this.board = board;
//        }
        public int compare(SearchNode v, SearchNode w) {
            return v.compareTo(w);
        }
    }

    public Solver(Board initial) {
        Board twin = initial.twin();
        SearchNode init_node = new SearchNode(initial, null, 0);
        SearchNode twin_node = new SearchNode(twin, null, 0);
        SearchComp comp = new SearchComp();
        MinPQ<SearchNode> boards = new MinPQ<SearchNode>(comp);
        MinPQ<SearchNode> twin_boards = new MinPQ<SearchNode>(comp);
        boards.insert(init_node);
        twin_boards.insert(twin_node);
        SearchNode candidate;
        SearchNode twin_candidate;
        int moves = 0;
        int twin_moves = 0;
        while (!boards.isEmpty() || !twin_boards.isEmpty()) {
            if (!boards.isEmpty()) {
                candidate = boards.delMin();
                if (candidate.board.isGoal()) {
                    solvable = true;
                    solution_moves = candidate.path_to_root().size() - 1;
                    Stack<Board> buf = candidate.path_to_root();
                    while (!buf.isEmpty()) {
                        solution_boards.add(buf.pop());
                    }
                    break;
                } else {
                    moves++;
                    for (Board item : candidate.board.neighbors()) {
                        if (candidate.prev != null && item.equals(candidate.prev.board)) {
                            continue;
                        }
                        SearchNode new_node = new SearchNode(item, candidate, moves);
                        boards.insert(new_node);
                    }
                }
            }
            if (!twin_boards.isEmpty()) {
                twin_candidate = twin_boards.delMin();
                if (twin_candidate.board.isGoal()) {
                    solvable = false;
                    solution_moves = -1;
                    break;
                } else {
                    twin_moves++;
                    for (Board item : twin_candidate.board.neighbors()) {
                        if (twin_candidate.prev != null && item.equals(twin_candidate.prev.board)) {
                            continue;
                        }
                        SearchNode new_node = new SearchNode(item, twin_candidate, twin_moves);
                        twin_boards.insert(new_node);
                    }
                }
            }
        }
    }
    public boolean isSolvable() {
        return solvable;
    }
    public int moves() {
        return solution_moves;
    }
    public Iterable<Board> solution() {
        if (!solvable) return null;
        return solution_boards;
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
