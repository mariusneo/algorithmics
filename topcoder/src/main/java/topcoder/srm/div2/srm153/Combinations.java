package topcoder.srm.div2.srm153;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Combinations {
    public static List<int[]> combinations(int N, int K) {
        // get the combination by index
        // e.g. 01 --> AB , 23 --> CD
        int combination[] = new int[K];

        // position of current index
        //  if (r = 1)              r*
        //  index ==>        0   |   1   |   2
        //  element ==>      A   |   B   |   C
        int r = 0;
        int index = 0;

        List<int[]> combinations = new ArrayList<>();
        while (r >= 0) {
            // possible indexes for 1st position "r=0" are "0,1,2" --> "A,B,C"
            // possible indexes for 2nd position "r=1" are "1,2,3" --> "B,C,D"

            // for r = 0 ==> index < (4+ (0 - 2)) = 2
            if (index <= (N + (r - K))) {
                combination[r] = index;

                // if we are at the last position print and increase the index
                if (r == K - 1) {

                    //do something with the combination e.g. add to list or print
                    combinations.add(Arrays.copyOf(combination, combination.length));
                    index++;
                } else {
                    // select index for next position
                    index = combination[r] + 1;
                    r++;
                }
            } else {
                r--;
                if (r > 0)
                    index = combination[r] + 1;
                else
                    index = combination[0] + 1;
            }
        }

        return combinations;
    }

    public static void main(String[] args) {
        combinations(5, 3).forEach(arr -> {
            Arrays.stream(arr).forEach(i -> {
                System.out.print(i + " ");
            });
            System.out.println();
        });
    }
}
