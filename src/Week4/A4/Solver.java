package Week4.A4;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

/**
 * Created by Jashan Shewakramani
 * Use A* algorithm to solve 8 puzzle
 */
public class Solver {

    private MinPQ<SearchNode> currState = new MinPQ<>();
    private MinPQ<SearchNode> twinState = new MinPQ<>();

    // inner class helps us deal with search nodes individually
    private class SearchNode implements Comparable<SearchNode> {
        SearchNode previous;
        Board board;
        int numMoves;

        SearchNode(SearchNode prev, Board curr, int moves) {
            this.previous = prev;
            this.board = curr;
            this.numMoves = moves;
        }


        @Override
        public int compareTo(SearchNode other) {
            Integer otherPriority = other.board.manhattan() + other.numMoves;
            Integer currentPriority = this.board.manhattan() + this.numMoves;

            return currentPriority.compareTo(otherPriority);
        }
    }


    // define main constructor; solve everything here
    public Solver(Board initial) {
        SearchNode initNode = new SearchNode(null, initial, 0);
        SearchNode twinNode = new SearchNode(null, initial.twin(), 0);

        currState.insert(initNode);
        twinState.insert(twinNode);

        solve();
    }

    private void solve() {
        while(!currState.min().board.isGoal() && !twinState.min().board.isGoal()) {
            iterate(currState);
            iterate(twinState);
        }
    }

    private void iterate(MinPQ<SearchNode> state) {
        SearchNode toProcess = state.delMin();
        Iterable<Board> neighbours = toProcess.board.neighbors();
        Board prevBoard = null;

        if (toProcess.previous != null)
            prevBoard = toProcess.previous.board;

        int movesSoFar = toProcess.numMoves;

        for (Board neighbour: neighbours) {
            if (!neighbour.equals(prevBoard)) {
                SearchNode toAdd = new SearchNode(toProcess, neighbour, movesSoFar + 1);
                state.insert(toAdd);
            }
        }
    }

    public boolean isSolvable() {
        return currState.min().board.isGoal();
    }

    public int moves() {
        if (!isSolvable())
            return -1;
        else
            return currState.min().numMoves;
    }

    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;

        ArrayList<Board> result = new ArrayList<>();

        SearchNode lastNode = currState.min();
        while (lastNode != null) {
            result.add(0, lastNode.board);
            lastNode = lastNode.previous;
        }

        return result;
    }

    // unit tests (these will be hard)
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
