import Week1.QuickFind;
import Week1.QuickUnion;
import Week1.WeightedQuickUnion;

/**
 * Created by: Jashan Shewakramani
 * Description: Dump for testing all algorithms here on small cases
 */

public class Main {

    public static void main(String[] args) {
        QuickFind uf = new QuickFind(5);
        uf.union(1, 4);
        uf.union(1, 3);

        System.out.println(uf.connected(3, 4));
        System.out.println(uf.connected(2, 4));

        QuickUnion quickUnion = new QuickUnion(5);
        quickUnion.union(1, 4);
        quickUnion.union(1, 3);

        System.out.println(quickUnion.connected(3, 4));
        System.out.println(quickUnion.connected(2, 4));

        WeightedQuickUnion w = new WeightedQuickUnion(5);
        w.union(1, 4);
        w.union(1, 3);

        System.out.println(w.connected(3, 4));
        System.out.println(w.connected(2, 4));
    }
}
