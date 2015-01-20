package org.mg.catcoder.addictivegameperformance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your task is to connect all pairs of points with the same color, with paths.
 * The paths can’t leave the board, can’t intersect, and can’t touch points of different color.
 * A path must start and end at a point with the same color.
 * The paths must fill the entire board.
 * In case of multiple solutions any valid solution is accepted.
 * <p/>
 * Input
 * Each input has multiple tests. One test will have the form:
 * rows cols numberOfPoints Point 1 Point 2 ... Point numberOfPoints 0
 * where Point i :
 * <p/>
 * position i color i
 * The input will consist of a list of tests:
 * numberOfTests test 1 test 2 test numberOfTests
 * <p/>
 * Output
 * Your output has to contain the solution to all the tests:
 * numberOfTests solution 1 solution 2 solution numberOfTests
 * Every solution should have the form:
 * numberOfPaths path 1 path 2 path 3 ... path numberOfPaths
 * where Path:
 * color startingPoint length step 1 step 2 step 3 ... step length
 * <p/>
 * <p/>
 * Example Input :
 * 1 5 5 8 1 1 5 1 7 2 8 3 9 2 10 4 17 3 24 4 0
 * Explanations :
 * 1 - test number
 * 5 5 - size of board is 5x5 rows x cols
 * 8 - number of points on the board
 * 1 1 5 1  - on the cell 1 there is a point having color 1, on the cell 5 there is a point having color 1
 * <p/>
 * <p/>
 * Example Output :
 * 1 4 1 1 4 E E E E 2 7 10 W S S S E E N E N N 3 8 3 S W S 4 10 4 S S S W
 * <p/>
 * 1 - test number
 * 4 - number of paths
 * 1 1 4 E E E E - path starting from cell 1 of color 1 to get to cell 5 of color 1
 */
public class FindPath {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(FindPath.class.getClassLoader()
                .getResourceAsStream("org/mg/catcoder/addictivegameperformance/level1-1.in")));
        String line;
        while ((line = br.readLine()) != null) {
            String[] components = line.trim().split(" ");
            int testNumber = Integer.parseInt(components[0]);
            int rows = Integer.parseInt(components[1]);
            int cols = Integer.parseInt(components[2]);
            int[][] matrix = new int[rows][cols];
            Map<Integer, List<Integer>> color2CellMap = new HashMap<>();
            int pointsCount = Integer.parseInt(components[3]);
            for (int i = 0; i < pointsCount; i++) {
                int index = 4 + i * 2;
                int cellIndex = Integer.parseInt(components[index]);
                int cellColor = Integer.parseInt(components[index + 1]);

                if (color2CellMap.containsKey(cellColor)) {
                    color2CellMap.get(cellColor).add(cellIndex);
                }

                int row = cellIndex / cols - (cellIndex % cols == 0 ? 1 : 0);
                int col = (cellIndex - 1) % cols;
                matrix[row][col] = cellColor;
            }

            //TODO a naive approach would be to use the maze solving algorithm,
            // but this algorithm does not take into account the fact that the
            // paths between the nodes have to be disjoint.
            // A possible solution could be by using a disjoint path algorithm
            // (but i haven't found so far something to match the needs of this problem).
        }
        br.close();

    }

}
