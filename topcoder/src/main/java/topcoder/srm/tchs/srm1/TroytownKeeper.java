package topcoder.srm.tchs.srm1;

import javafx.util.Pair;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * People enjoy mazes, but they also get them dirty. Arrows, graffiti, and chewing gum are just a few of the souvenirs people leave on the walls. You, the maze keeper, are assigned to whiten the maze walls. Each face of the wall requires one liter of paint, but you are only required to paint visible faces. You are given a map of the maze, and you must determine the amount of paint needed for the job.
 * The maze is described by a String[] maze, where each character can be either '#' (a wall) or '.' (an empty space). All '.' characters on the perimeter of the map are considered entrances to the maze. Upon entering the maze, one can only move horizontally and vertically through empty spaces, and areas that are not reachable by these movements are not considered visible. Each '#' represents a square block with four wall faces (each side of the square is a face). A face is visible if it is not directly adjacent to another wall (and is in a reachable area of the maze). For example, two adjacent blocks can have at most six visible faces since two of their faces are directly adjacent to each other. All exterior faces on the perimeter are considered visible.
 * For example, the following picture represents a trivial maze with just one (wide) entrance and only four empty reachable spaces:
 * <p>
 * To whiten this maze you must paint the faces highlighted in yellow above: 16 for its perimeter, plus 8 interior faces. Note that there are faces that are not visible and thus need not be painted.
 * Definition
 *     
 * Class:
 * TroytownKeeper
 * Method:
 * limeLiters
 * Parameters:
 * String[]
 * Returns:
 * int
 * Method signature:
 * int limeLiters(String[] maze)
 * (be sure your method is public)
 * Limits
 *     
 * Time limit (s):
 * 2.000
 * Memory limit (MB):
 * 64
 * Constraints
 * -
 * maze will contain between 1 and 50 elements, inclusive.
 * -
 * Each element of maze will contain between 1 and 50 characters, inclusive.
 * -
 * All elements of maze will have the same number of characters.
 * -
 * All characters in maze will be either '.' or '#'.
 * Examples
 * 0)
 * <p>
 *     
 * {"##..#"
 * ,"#.#.#"
 * ,"#.#.#"
 * ,"#####"}
 * Returns: 24
 * Example from the problem statement.
 * 1)
 * <p>
 *     
 * {"##",
 * "##"}
 * Returns: 8
 * Only the perimeter of the maze (which has no interior!) has to be painted.
 * 2)
 * <p>
 *     
 * {"######"
 * ,"#....."
 * ,"#.####"
 * ,"#.#..#"
 * ,"#.##.#"
 * ,"#....#"
 * ,"######"}
 * Returns: 56
 * <p>
 * 3)
 * <p>
 *     
 * {"######"
 * ,"#....."
 * ,"#..#.."
 * ,"#....."
 * ,"######"}
 * Returns: 36
 * <p>
 * 4)
 * <p>
 *     
 * {"#.#.#.#"
 * ,".#.#.#."
 * ,"#.#.#.#"
 * ,".#.#.#."}
 * Returns: 36
 * <p>
 * This problem statement is the exclusive and proprietary property of TopCoder, Inc. Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited. (c)2003, TopCoder, Inc. All rights reserved.
 */
public class TroytownKeeper {
    private TroytownKeeper troytownKeeper;

    public int limeLiters(String[] maze) {
        int liters = 0;
        ArrayList<Pair<Integer, Integer>> emptyCells = new ArrayList<Pair<Integer, Integer>>();
        // examine upper and bottom walls
        for (int i = 0; i < maze[0].length(); i++) {
            if (maze[0].charAt(i) == '#') {
                liters++;
            } else {
                emptyCells.add(new Pair<Integer, Integer>(0, i));
            }

            if (maze[maze.length - 1].charAt(i) == '#') {
                liters++;
            } else {
                emptyCells.add(new Pair<Integer, Integer>(maze.length - 1, i));
            }
        }

        // examine side walls
        for (int i = 0; i < maze.length; i++) {
            if (maze[i].charAt(0) == '#') {
                liters++;
            } else {
                emptyCells.add(new Pair<Integer, Integer>(i, 0));
            }
            if (maze[i].charAt(maze[i].length() - 1) == '#') {
                liters++;
            } else {
                emptyCells.add(new Pair<Integer, Integer>(i, maze[i].length() - 1));
            }
        }

        int index = 0;
        // check all the neighbours of empty cells from the maze  : up, down, left ,right
        while (index < emptyCells.size()){
            Pair<Integer, Integer> emptyCell = emptyCells.get(index++);
            int i = emptyCell.getKey();
            int j = emptyCell.getValue();

            if (i > 0){
                if (maze[i-1].charAt(j) == '#'){
                    liters++;
                }else{
                    Pair<Integer, Integer> newEmptyCell = new Pair<Integer, Integer>(i-1, j);
                    if (!emptyCells.contains(newEmptyCell)){
                        emptyCells.add(newEmptyCell);
                    }
                }
            }

            if (i < maze.length){
                if (maze[i+1].charAt(j) == '#'){
                    liters++;
                }else{
                    Pair<Integer, Integer> newEmptyCell = new Pair<Integer, Integer>(i+1, j);
                    if (!emptyCells.contains(newEmptyCell)){
                        emptyCells.add(newEmptyCell);
                    }
                }
            }

            if (j > 0){
                if (maze[i].charAt(j - 1) == '#'){
                    liters++;
                }else{
                    Pair<Integer, Integer> newEmptyCell = new Pair<Integer, Integer>(i, j - 1);
                    if (!emptyCells.contains(newEmptyCell)){
                        emptyCells.add(newEmptyCell);
                    }
                }
            }

            if (j < maze[i].length() - 1){
                if (maze[i].charAt(j+1) == '#'){
                    liters++;
                }else{
                    Pair<Integer, Integer> newEmptyCell = new Pair<Integer, Integer>(i, j+1);
                    if (!emptyCells.contains(newEmptyCell)){
                        emptyCells.add(newEmptyCell);
                    }
                }
            }
        }

        return liters;
    }

    @Before
    public void setup() {
        troytownKeeper = new TroytownKeeper();
    }

    @Test
    public void test1() {
        String[] maze = {
                  "##..#"
                , "#.#.#"
                , "#.#.#"
                , "#####"};
        int liters = troytownKeeper.limeLiters(maze);
        Assert.assertEquals(24, liters);
    }

    @Test
    public void test3(){
        String[] maze = {
                  "######"
                 ,"#....."
                 ,"#..#.."
                 ,"#....."
                 ,"######"};
        int liters = troytownKeeper.limeLiters(maze);
        Assert.assertEquals(36, liters);

    }
}
