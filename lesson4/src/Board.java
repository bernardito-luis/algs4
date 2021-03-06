import java.lang.Math;
import java.util.ArrayList;
import java.lang.NullPointerException;

public class Board {
    private int n;
    private final int[][] current_board;
    private int blank_x;
    private int blank_y;

    public Board(int[][] blocks) {
        if (blocks == null) throw new NullPointerException();
        n = blocks.length;
        this.current_board = new int[this.n][this.n];
        for (int i = 0; i < this.n; i++)
            for (int j = 0; j < this.n; j++) {
                this.current_board[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    this.blank_x = i;
                    this.blank_y = j;
                }
            }
    }

    public int dimension() {
        return this.n;
    }

    public int hamming() {
        int out_of_place = 0;
        for (int i = 0; i < this.n; i++)
            for (int j = 0; j < this.n; j++)
                if (i * n + j + 1 != this.current_board[i][j] &&
                        this.current_board[i][j] != 0)
                    out_of_place++;

        return out_of_place;
    }

    public int manhattan() {
        int out_of_place = 0;
        int goal_x;
        int goal_y;
        int val;
        for (int i = 0; i < this.n; i++)
            for (int j = 0; j < this.n; j++) {
                val = this.current_board[i][j];
                if (val == 0)
                    continue;
                goal_x = val / this.n;
                goal_y = val % this.n - 1;
                if (goal_y < 0) {
                    goal_x -= 1;
                    goal_y += this.n;
                }
                out_of_place += Math.abs(i - goal_x) + Math.abs(j - goal_y);
            }
        return out_of_place;
    }

    public boolean isGoal() {
        return this.hamming() == 0;
    }

    public Board twin() {
        int x1 = -1, y1 = -1;
        int x2 = -1, y2 = -1;

        int[][] twin_blocks = new int[this.n][this.n];
        for (int i = 0; i < this.n; i++)
            for (int j = 0; j < this.n; j++) {
                twin_blocks[i][j] = this.current_board[i][j];
                if (this.current_board[i][j] != 0 && x1 == -1) {
                    x1 = i;
                    y1 = j;
                } else if (this.current_board[i][j] != 0 && x2 == -1) {
                    x2 = i;
                    y2 = j;
                }
            }
        int buf = twin_blocks[x1][y1];
        twin_blocks[x1][y1] = twin_blocks[x2][y2];
        twin_blocks[x2][y2] = buf;
        return new Board(twin_blocks);
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++)
                if (this.current_board[i][j] != that.current_board[i][j])
                    return false;
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> neighborhood = new ArrayList<Board>();
        if (this.blank_x + 1 != this.n) {
            int [][] new_board = new int [n][n];
            for (int i = 0; i < this.n; i++)
                for (int j = 0; j < this.n; j++)
                    new_board[i][j] = current_board[i][j];
            new_board[blank_x][blank_y] = new_board[blank_x+1][blank_y];
            new_board[blank_x+1][blank_y] = 0;
            neighborhood.add(new Board(new_board));
        }
        if (this.blank_y + 1 != this.n) {
            int [][] new_board = new int [n][n];
            for (int i = 0; i < this.n; i++)
                for (int j = 0; j < this.n; j++)
                    new_board[i][j] = current_board[i][j];
            new_board[blank_x][blank_y] = new_board[blank_x][blank_y+1];
            new_board[blank_x][blank_y+1] = 0;
            neighborhood.add(new Board(new_board));
        }
        if (this.blank_x > 0) {
            int [][] new_board = new int [n][n];
            for (int i = 0; i < this.n; i++)
                for (int j = 0; j < this.n; j++)
                    new_board[i][j] = current_board[i][j];
            new_board[blank_x][blank_y] = new_board[blank_x-1][blank_y];
            new_board[blank_x-1][blank_y] = 0;
            neighborhood.add(new Board(new_board));
        }
        if (this.blank_y > 0) {
            int [][] new_board = new int [n][n];
            for (int i = 0; i < this.n; i++)
                for (int j = 0; j < this.n; j++)
                    new_board[i][j] = current_board[i][j];
            new_board[blank_x][blank_y] = new_board[blank_x][blank_y-1];
            new_board[blank_x][blank_y-1] = 0;
            neighborhood.add(new Board(new_board));
        }
        return neighborhood;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", current_board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {

    }
}
