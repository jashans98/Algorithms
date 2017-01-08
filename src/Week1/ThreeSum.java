package Week1;


/**
 * Created by Jashan Shewakramani
 * Description: ThreeSum code from lecture
 *
 * Notes:
 * Returns the number of triples in an array that sums to zero
 */
public class ThreeSum {

    // naive implementation runs in O(N^3)
    public static int countBruteForce(int[] a) {
        int N = a.length;
        int count = 0;

        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                for (int k = j+1; k < N; k++) {
                    if (a[i] + a[j] + a[k] == 0)
                        count++;
                }
            }
        }

        return count;
    }

    // TODO: write the more efficient implementation that makes use of sorting
}
