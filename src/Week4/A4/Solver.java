package Week4.A4;


import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;

/**
 * Created by Jashan Shewakramani
 * Use A* algorithm to solve 8 puzzle
 */
public class Solver {

    private MinPQ<SearchNode> currState = new MinPQ<>();
    private MinPQ<SearchNode> twinState = new MinPQ<>();

    // inner class helps us deal with search nodes individually
    private class SearchNode implements Comparable {
        SearchNode previous;
        Board board;
        int numMoves;

        SearchNode(SearchNode prev, Board curr, int moves) {
            this.previous = prev;
            this.board = curr;
            this.numMoves = moves;
        }


        @Override
        public int compareTo(Object o) {
            if (o instanceof SearchNode) {
                SearchNode other = (SearchNode) o;
                Integer otherPriority = other.board.manhattan() + other.numMoves;
                Integer currentPriority = this.board.manhattan() + this.numMoves;

                return currentPriority.compareTo(otherPriority);
            }
            return 0;
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
        Iterable<Board> neighbours = toProcess.board.neighbours();
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
        int[][] initBlocks = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board b = new Board(initBlocks);

        Solver s = new Solver(b);

        for (Board q : s.solution())
            System.out.println(q);
    }

}
