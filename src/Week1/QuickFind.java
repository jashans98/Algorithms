package Week1;

/**
 * Created by Jashan Shewakramani
 * Description: Rudimentary Quick Find implementation based on connected components
 *
 * Notes:
 * -> This is a eager solution to the dymanic connectivity problem
 * -> The running time of each "union" operation is O(N), where N is the number of nodes in the network
 * -> This is highly expensive. N unions on the network (which is not unreasonable) gets you to O(N^2)
 * -> The good thing with this implementation is that checking connectivity can be done in O(1)
 */
public class QuickFind {
    private int[] id;

    public QuickFind(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid)
                id[i] = qid;
        }
    }
}
