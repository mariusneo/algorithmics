package topcoder.srm.div2.srm153;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PickTeam {

    public List<int[]> combination(int N, int K) {
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

    public String[] pickPeople(int teamSize, String[] people) {

        String[] names = new String[people.length];
        int[][] likes = new int[people.length][people.length];
        for (int i = 0; i < people.length; i++) {
            String[] components = people[i].split(" ");
            names[i] = components[0];
            for (int j = 1; j < components.length; j++) {
                likes[i][j - 1] = Integer.parseInt(components[j]);
            }
        }

        List<int[]> combinations = combination(people.length, teamSize);

        List<int[]> oneOnOneCombinations = combination(teamSize, 2);
        int max = Integer.MIN_VALUE;
        int maxCombinationIndex = 0;
        int index = 0;
        for (int[] combination : combinations) {
            int sum = 0;
            for (int[] oneOnOneCombination : oneOnOneCombinations) {
                int index1 = combination[oneOnOneCombination[0]];
                int index2 = combination[oneOnOneCombination[1]];
                sum += likes[index1][index2];
            }

            if (sum > max) {
                max = sum;
                maxCombinationIndex = index;
            }

            index++;
        }

        String[] result = new String[teamSize];
        index = 0;
        for (int i : combinations.get(maxCombinationIndex)) {
            result[index] = names[i];
            index++;
        }
        Arrays.sort(result);
        return result;
    }


    @Test
    public void test0() {
        PickTeam pt = new PickTeam();
        String[] result = pt.pickPeople(3,
                new String[]{"ALICE 0 1 -1 3",
                        "BOB 1 0 2 -4",
                        "CAROL -1 2 0 2",
                        "DAVID 3 -4 2 0"});
        String[] expectedResult = new String[]{"ALICE", "CAROL", "DAVID"};
        Assert.assertArrayEquals(result, expectedResult);
    }

    @Test
    public void test2() {
        PickTeam pt = new PickTeam();
        String[] result = pt.pickPeople(2, new String[]{"A 0 -2 -2", "B -2 0 -1", "C -2 -1 0"});
        String[] expectedResult = new String[]{"B", "C"};
        Assert.assertArrayEquals(expectedResult, result);
    }
}
