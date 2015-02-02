package org.mg.catcoder.puzzles;

import junit.framework.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

/**
 * The problem is about finding solutions to filling puzzles.
 * In the beginnings of the puzzle problem we concentrate on finding a solution on arranging blocks with certain
 * constraints on one row. This problem is an application of finding all the combinations of blank cells that
 * be found between the block cells on the row.
 * NOTE that if the array of block cell lengths is of size n, the array of black cell lengths will have the size n+1
 * e.g. :
 * for blocks cell lengths with size  1, there can exist blank cells before, as well as after the block cell.
 * @see #arrangeMultipleBlocksPerRow(int, int[])
 * @see #arrangeMultipleColoredBlocksPerRow(int, int[], int[])
 *
 * Then the blocks have to be arranged on a row that follows a certain pattern. Even though this new constraint does not
 * seem to make much sense when it is first formulated, it will make sense in the future levels. At this point, in the
 * selection of the subsets of blank cells, has to be checked, at each step, whether the string formed by concatenating
 * the blank cells and the block cells matches the requested pattern.
 *
 * @see #arrangeMultipleColoredBlocksPerRowWithPattern(int, int[], int[], String)
 *
 * The constraint for the pattern reveals itself useful when dealing with a puzzle composed out of multiple rows.
 * We start with the puzzle filled with '?' for all the rows x cols cells. Than all the rows, and for each row, all
 * the columns, are iterated, and rowPattern and colPatterns are found at each step. If the rowPattern/colPattern
 * contains characters that are different from '?', they are set on the puzzle, and this improvement is acting as a
 * refinement for the rows/columns patterns constraints. NOTE here that a similar treatment is applied to the columns,
 * as it was previously done for the rows, the only difference being that the characters from a column have to be
 * concatenated for obtaining the column pattern to be followed.
 *
 * @see #arrangeMultipleColoredBlocksOnMatrix(int, int, int[][], int[][], int[][], int[][])
 *
 * Created by marius on 29.01.15.
 */
public class Puzzles {
    public String arrangeBlock(int columns, int blockLength) {
        StringBuilder sb = new StringBuilder();
        if (blockLength < columns - blockLength) {
            for (int i = 0; i < columns; i++) {
                sb.append("?");
            }
        } else {
            for (int i = 0; i < columns; i++) {
                if (i < Math.abs(blockLength - columns)) {
                    sb.append("?");
                } else if (i < blockLength) {
                    sb.append("1");
                } else {
                    sb.append("?");
                }
            }
        }
        return sb.toString();
    }

    public String arrangeMultipleBlocksPerRow(int columns, int[] blockLengths) {
        int sumBlackBlocks = 0;
        for (int i = 0; i < blockLengths.length; i++) {
            sumBlackBlocks += blockLengths[i];
        }
        int minWhiteBlocks = blockLengths.length - 1;

        if (sumBlackBlocks + minWhiteBlocks > columns) throw new IllegalArgumentException("too many blocks");
        if (sumBlackBlocks + minWhiteBlocks == columns) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < blockLengths.length; i++) {
                if (sb.length() > 0) {
                    sb.append("0");
                }
                for (int j = 0; j < blockLengths[i]; j++) {
                    sb.append("1");
                }
            }
            return sb.toString();
        } else if (blockLengths.length == 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < columns; i++) {
                sb.append('0');
            }
            return sb.toString();
        } else {
            List<List<Integer>> whiteboxCombinations = new ArrayList<>();
            sumSubsets(columns - sumBlackBlocks, blockLengths.length + 1, new ArrayList<>(), whiteboxCombinations);

            List<String> solutions = new ArrayList<>();
            for (List<Integer> whiteBoxCombination : whiteboxCombinations) {
                StringBuilder arrangement = new StringBuilder();
                for (int i = 0; i < whiteBoxCombination.size(); i++) {
                    for (int j = 0; j < whiteBoxCombination.get(i); j++) {
                        arrangement.append("0");
                    }
                    if (i < whiteBoxCombination.size() - 1) {
                        for (int j = 0; j < blockLengths[i]; j++) {
                            arrangement.append("1");
                        }
                    }
                }
                solutions.add(arrangement.toString());
            }

            char[] combinedSolution = new char[columns];
            for (int i = 0; i < solutions.size(); i++) {
                String solution = solutions.get(i);
                if (i == 0) {
                    for (int j = 0; j < solution.length(); j++) {
                        combinedSolution[j] = solution.charAt(j);
                    }
                } else {
                    for (int j = 0; j < solution.length(); j++) {
                        if (combinedSolution[j] != '?' && combinedSolution[j] != solution.charAt(j)) {
                            combinedSolution[j] = '?';
                        }
                    }
                }
            }

            return new String(combinedSolution);
        }
    }


    public String arrangeMultipleColoredBlocksPerRow(int columns, int[] colors, int[] blockLengths) {
        int sumBlackBlocks = 0;
        for (int i = 0; i < blockLengths.length; i++) {
            sumBlackBlocks += blockLengths[i];
        }
        int minWhiteBlocks = blockLengths.length - 1;

        if (sumBlackBlocks + minWhiteBlocks > columns) throw new IllegalArgumentException("too many blocks");
        if (blockLengths.length == 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < columns; i++) {
                sb.append('0');
            }
            return sb.toString();
        } else {
            List<List<Integer>> whiteboxCombinations = new ArrayList<>();
            sumSubsetsMultipleColors(columns - sumBlackBlocks, blockLengths.length + 1, colors, new ArrayList<>(),
                    whiteboxCombinations);

            List<String> solutions = new ArrayList<>();
            for (List<Integer> whiteBoxCombination : whiteboxCombinations) {
                StringBuilder arrangement = new StringBuilder();
                for (int i = 0; i < whiteBoxCombination.size(); i++) {
                    for (int j = 0; j < whiteBoxCombination.get(i); j++) {
                        arrangement.append("0");
                    }
                    if (i < whiteBoxCombination.size() - 1) {
                        for (int j = 0; j < blockLengths[i]; j++) {
                            arrangement.append(colors[i]);
                        }
                    }
                }
                solutions.add(arrangement.toString());
            }

            if (solutions.size() == 0) {
                // no suitable arrangement found
                return null;
            }

            char[] combinedSolution = new char[columns];
            for (int i = 0; i < solutions.size(); i++) {
                String solution = solutions.get(i);
                if (i == 0) {
                    for (int j = 0; j < solution.length(); j++) {
                        combinedSolution[j] = solution.charAt(j);
                    }
                } else {
                    for (int j = 0; j < solution.length(); j++) {
                        if (combinedSolution[j] != '?' && combinedSolution[j] != solution.charAt(j)) {
                            combinedSolution[j] = '?';
                        }
                    }
                }
            }

            return new String(combinedSolution);
        }
    }


    public String arrangeMultipleColoredBlocksPerRowWithPattern(int columns, int[] colors, int[] blockLengths, String
            pattern) {
        int sumBlackBlocks = 0;
        for (int i = 0; i < blockLengths.length; i++) {
            sumBlackBlocks += blockLengths[i];
        }

        if (blockLengths.length == 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < columns; i++) {
                sb.append("0");
            }
            return sb.toString();
        } else {
            List<List<Integer>> whiteboxCombinations = new ArrayList<>();
            sumSubsetsMultipleColorsWithPattern(columns - sumBlackBlocks, blockLengths, colors, pattern,
                    new ArrayList<>(), whiteboxCombinations);

            List<String> solutions = new ArrayList<>();
            for (List<Integer> whiteBoxCombination : whiteboxCombinations) {
                StringBuilder arrangement = new StringBuilder();
                for (int i = 0; i < whiteBoxCombination.size(); i++) {
                    for (int j = 0; j < whiteBoxCombination.get(i); j++) {
                        arrangement.append("0");
                    }
                    if (i < whiteBoxCombination.size() - 1) {
                        for (int j = 0; j < blockLengths[i]; j++) {
                            arrangement.append(colors[i]);
                        }
                    }
                }
                solutions.add(arrangement.toString());
            }


            if (solutions.size() == 0) {
                // no suitable arrangement found
                return null;
            }

            char[] combinedSolution = new char[columns];


            boolean firstSolutionSet = false;
            for (int i = 0; i < solutions.size(); i++) {
                String solution = solutions.get(i);

                if (!firstSolutionSet) {
                    for (int j = 0; j < solution.length(); j++) {
                        if (combinedSolution[j] != '?') {
                            combinedSolution[j] = solution.charAt(j);
                        }
                    }
                    firstSolutionSet = true;
                } else {
                    for (int j = 0; j < solution.length(); j++) {
                        if (combinedSolution[j] != '?' && combinedSolution[j] != solution.charAt(j)) {
                            combinedSolution[j] = '?';
                        }
                    }
                }
            }

            return new String(combinedSolution);

        }
    }


    public char[][] arrangeMultipleColoredBlocksOnMatrix(int rows, int cols,
                                                         int[][] rowBlockLengths, int[][] rowColors,
                                                         int[][] colBlockLengths, int[][] colColors) {
        char[][] rowPatterns = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rowPatterns[i][j] = '?';
            }
        }

        int openSpots = rows * cols;

        while (openSpots > 0) {
            int previousOpenSpots = openSpots;
            for (int i = 0; i < rows; i++) {

                int rowOpenSpots = openSpots(rowPatterns[i]);
                if (rowOpenSpots > 0) {
                    String rowPatternString = arrangeMultipleColoredBlocksPerRowWithPattern(cols, rowColors[i],
                            rowBlockLengths[i], new String(rowPatterns[i]));
                    if (rowPatternString == null) return null;
                    char[] rowPattern = rowPatternString.toCharArray();
                    rowPatterns[i] = rowPattern;

                    int currentRowOpenSpots = openSpots(rowPatterns[i]);
                    openSpots -= (rowOpenSpots - currentRowOpenSpots);
                }

                for (int j = 0; j < cols; j++) {
                    StringBuilder sb = new StringBuilder();
                    for (int k = 0; k < rows; k++) {
                        sb.append(rowPatterns[k][j]);
                    }
                    String intermediateColPattern = sb.toString();

                    int colOpenSpots = openSpots(intermediateColPattern.toCharArray());
                    if (colOpenSpots > 0) {
                        String colPattern = arrangeMultipleColoredBlocksPerRowWithPattern(rows, colColors[j],
                                colBlockLengths[j], intermediateColPattern);

                        if (colPattern == null) {
                            // no suitable arrangement found
                            return null;
                        }

                        int currentColOpenSpots = openSpots(colPattern.toCharArray());
                        if (colOpenSpots > currentColOpenSpots) {
                            openSpots -= (colOpenSpots - currentColOpenSpots);
                            for (int k = 0; k < rows; k++) {
                                if (colPattern.charAt(k) != '?' && rowPatterns[k][j] == '?') {
                                    rowPatterns[k][j] = colPattern.charAt(k);
                                }
                            }
                        }
                    }
                }
            }

            if (previousOpenSpots == openSpots) {
                // no advance has been made since the last iteration
                return null;
            }

        }

        return rowPatterns;
    }

    private int openSpots(char[] arr) {
        int openSpots = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == '?') {
                openSpots++;
            }
        }
        return openSpots;
    }


    public void sumSubsetsMultipleColorsWithPattern(int target, int[] blockLengths, int[] colors, String pattern,
                                                    List<Integer> partial, List<List<Integer>> solutions) {
        int partialSum = 0;
        for (int i : partial) {
            partialSum += i;
        }

        if (partialSum <= target) {
            if (partial.size() == blockLengths.length) {
                if (target - partialSum > 0) {
                    // verify if the last empty cells to be added match the requested pattern
                    int indexInPattern = pattern.length() - (target - partialSum);
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < target - partialSum; j++) {
                        sb.append("0");
                    }
                    String arrangement = sb.toString();
                    boolean isValidArrangement = true;
                    for (int j = 0; j < arrangement.length(); j++) {
                        if (pattern.charAt(indexInPattern + j) != '?'
                                && pattern.charAt(indexInPattern + j) != arrangement.charAt(j)) {
                            isValidArrangement = false;
                            break;
                        }
                    }
                    if (!isValidArrangement) return;
                }

                partial.add(target - partialSum);
                solutions.add(partial);
            } else {
                for (int i = 0; i <= target - partialSum; i++) {
                    if (i == 0 && partial.size() > 0) {
                        int previousColor = colors[partial.size() - 1];
                        int currentColor = colors[partial.size()];
                        if (previousColor == currentColor) continue;
                    }

                    // Verify at the current step if the number of empty cells and the block cells
                    // that follow them match the requested pattern
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < i; j++) {
                        sb.append("0");
                    }
                    int currentIndex = partial.size();
                    for (int j = 0; j < blockLengths[currentIndex]; j++) {
                        sb.append(colors[currentIndex]);
                    }

                    int indexInPattern = 0;
                    for (int j = 0; j < partial.size(); j++) {
                        indexInPattern += partial.get(j) + blockLengths[j];
                    }

                    String arrangement = sb.toString();
                    boolean isValidArrangement = true;
                    for (int j = 0; j < arrangement.length(); j++) {
                        if (pattern.charAt(indexInPattern + j) != '?'
                                && pattern.charAt(indexInPattern + j) != arrangement.charAt(j)) {
                            isValidArrangement = false;
                            break;
                        }
                    }
                    if (!isValidArrangement) continue;


                    List<Integer> current = new ArrayList<>(partial);
                    current.add(i);

                    sumSubsetsMultipleColorsWithPattern(target, blockLengths, colors, pattern, current, solutions);
                }
            }
        }
    }


    public void sumSubsetsMultipleColors(int target, int elementsCount, int[] colors, List<Integer> partial,
                                         List<List<Integer>>
                                                 solutions) {
        int partialSum = partial.stream().mapToInt(Integer::intValue).sum();

        if (partialSum <= target) {
            if (partial.size() == elementsCount - 1) {
                partial.add(target - partialSum);
                solutions.add(partial);
            } else {
                for (int i = 0; i <= target - partialSum; i++) {
                    if (i == 0 && partial.size() > 0) {
                        int previousColor = colors[partial.size() - 1];
                        int currentColor = colors[partial.size()];
                        if (previousColor == currentColor) continue;
                    }
                    List<Integer> current = new ArrayList<>(partial);
                    current.add(i);

                    sumSubsetsMultipleColors(target, elementsCount, colors, current, solutions);
                }
            }
        }
    }


    public void sumSubsets(int target, int elementsCount, List<Integer> partial, List<List<Integer>> solutions) {
        int partialSum = partial.stream().mapToInt(Integer::intValue).sum();

        if (partialSum <= target) {
            if (partial.size() == elementsCount - 1) {
                partial.add(target - partialSum);
                solutions.add(partial);
            } else {
                for (int i = 0; i <= target - partialSum; i++) {
                    if (i == 0 && partial.size() > 0) continue;
                    List<Integer> current = new ArrayList<>(partial);
                    current.add(i);

                    sumSubsets(target, elementsCount, current, solutions);
                }
            }
        }
    }


    @Test
    public void testSumSubsets() {
        List<List<Integer>> solutions = new ArrayList<>();

        Puzzles puzzles = new Puzzles();
        puzzles.sumSubsets(2, 2, new ArrayList<Integer>(), solutions);

        for (List<Integer> solution : solutions) {
            for (int e : solution) {
                System.out.print(e + " ");
            }
            System.out.println();
        }
    }

    @Test
    public void testSumSubsetsMultipleColors() {
        List<List<Integer>> solutions = new ArrayList<>();

        Puzzles puzzles = new Puzzles();
        puzzles.sumSubsetsMultipleColors(2, 3, new int[]{1, 1}, new ArrayList<Integer>(), solutions);

        for (List<Integer> solution : solutions) {
            for (int e : solution) {
                System.out.print(e + " ");
            }
            System.out.println();
        }
    }

    @Test
    public void testOneRow() {
        Puzzles p = new Puzzles();

        Assert.assertEquals("??1??", p.arrangeBlock(5, 3));
        Assert.assertEquals("?111?", p.arrangeBlock(5, 4));
    }

    @Test
    public void quizOneRow() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(Puzzles.class.getClassLoader()
                    .getResourceAsStream("org/mg/catcoder/puzzles/input-level1.txt")));

            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, " ");
                if (st.hasMoreTokens()) {
                    int columns = parseInt(st.nextToken());
                    int blockLength = parseInt(st.nextToken());
                    Puzzles puzzles = new Puzzles();
                    System.out.println(puzzles.arrangeBlock(columns, blockLength));
                }
            }


        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    @Test
    public void testOneRowMultipleBlocks() {
        Puzzles puzzles = new Puzzles();
        //System.out.println(puzzles.arrangeMultipleBlocksPerRow(5, new int[]{1, 2}));
        //System.out.println(puzzles.arrangeMultipleBlocksPerRow(5, new int[]{2, 2}));

        System.out.println(puzzles.arrangeMultipleBlocksPerRow(10, new int[]{5, 4}));
        System.out.println(puzzles.arrangeMultipleBlocksPerRow(10, new int[]{5, 3}));
        System.out.println(puzzles.arrangeMultipleBlocksPerRow(30, new int[]{}));
        System.out.println(puzzles.arrangeMultipleBlocksPerRow(40, new int[]{11, 10, 5, 7}));
    }


    @Test
    public void testOneRowMultipleColors() {
        Puzzles puzzles = new Puzzles();
        System.out.println(puzzles.arrangeMultipleColoredBlocksPerRow(10, new int[]{1, 2}, new int[]{5, 4}));
        System.out.println(puzzles.arrangeMultipleColoredBlocksPerRow(10, new int[]{1, 2}, new int[]{5, 3}));
        System.out.println(puzzles.arrangeMultipleColoredBlocksPerRow(12, new int[]{1, 2, 3}, new int[]{4, 3, 1}));
        System.out.println(puzzles.arrangeMultipleColoredBlocksPerRow(8, new int[]{2, 2}, new int[]{4, 3}));
        System.out.println(puzzles.arrangeMultipleColoredBlocksPerRow(50, new int[]{1, 2, 3, 2, 2, 1},
                new int[]{5, 3, 10, 8, 9, 9}));
    }


    @Test
    public void testOneRowMultipleColorsWithPattern() {
        Puzzles puzzles = new Puzzles();
        System.out.println(puzzles.arrangeMultipleColoredBlocksPerRowWithPattern(10, new int[]{1, 2}, new int[]{5, 4},
                "?????????2"));
        System.out.println(puzzles.arrangeMultipleColoredBlocksPerRowWithPattern(10, new int[]{1, 2}, new int[]{5,
                3}, "?????????0"));
        System.out.println(puzzles.arrangeMultipleColoredBlocksPerRowWithPattern(10, new int[]{1, 2}, new int[]{5,
                3}, "???????22?"));
    }

    @Test
    public void testOneRowMultipleColorsWithPatternLevel6() {
        String[] lines = new String[]{"50 15 3 1 2 1 1 1 2 1 1 1 2 1 1 1 2 1 1 1 2 2 1 1 1 1 1 1 1 2 3 1 " +
                "???????????????3??????????????????3???????????????"};
        Puzzles puzzles = new Puzzles();
        for (String line : lines) {
            String[] components = line.split(" ");
            int columns = parseInt(components[0]);
            int blocks = parseInt(components[1]);
            int index = 2;
            int[] colors = new int[blocks];
            int[] blockLengths = new int[blocks];
            for (int i = 0; i < blocks; i++) {
                int color = parseInt(components[index++]);
                int blockLength = parseInt(components[index++]);
                colors[i] = color;
                blockLengths[i] = blockLength;
            }
            String pattern = components[components.length - 1];

            System.out.println(puzzles.arrangeMultipleColoredBlocksPerRowWithPattern(columns, colors, blockLengths,
                    pattern));
        }
    }

    @Test
    public void testArrangeMultipleColoredBlocksOnMatrix() {
        String[] lines = new String[]{"3 3 1 1 2 1 2 1 1 2 1 1 1 1 2 1 1 2 2 0",
                "20 20 2 2 3 2 1 2 2 3 2 2 2 2 5 2 4 2 2 5 2 4 2 2 5 2 4 5 2 3 2 1 2 1 2 2 2 1 " +
                        "5 2 3 2 1 2 2 2 2 2 1 4 2 3 2 1 2 4 2 2 5 2 4 2 4 3 1 2 1 3 1 4 2 2 2 2 2 1 1 3 5 1 1 3 1 2 " +
                        "2 2 1 3 1 3 3 2 2 3 3 1 3 3 1 2 1 1 1 4 3 2 3 1 1 1 3 1 8 1 2 3 1 1 1 3 1 1 1 1 1 3 1 1 3 7 " +
                        "3 2 1 1 3 1 1 1 3 1 1 1 1 3 9 1 1 3 2 1 1 1 1 3 1 1 1 1 1 1 1 1 2 8 3 1 1 1 3 1 1 1 1 4 1 2 " +
                        "3 1 1 1 6 3 2 3 3 1 1 1 7 3 1 1 2 6 3 4 3 2 1 1 1 7 3 1 1 2 3 3 1 1 1 3 2 3 3 1 1 1 3 2 3 2 " +
                        "6 3 2 3 1 6 2 9 1 1 3 1 1 1 3 1 3 1 7 2 10 1 1 3 1 1 1 3 1 1 1 3 1 4 2 5 2 2 3 5 3 4 5 2 7 1" +
                        " 1 3 1 1 2 3 2 1 1 2 2 2 4 1 2 3 2 6 3 1 1 2 5 2 4 2 2 1 2 3 1 1 4 2 2 5 1 3 1 1 4 4 2 5 3 1" +
                        " 1 1 1 2 6 2 8 1 1 3 2 1 1 3 2 1 5 5 2 5 2 1 3 1 1 1 1 3 4 2 5 1 2 3 2 1 1 2 1 3 3 1 2 1 2 1" +
                        " 2 2 1 1 1 3",
                "30 25 1 1 10 5 1 3 1 1 1 1 1 1 1 4 6 1 4 1 1 1 1 1 1 1 1 1 3 8 1 3 1 1 1 1 1 1 1 1 1 1 1 1 1 2 8 1 2" +
                        " 1 1 1 1" +
                        " 1 1 1 1 1 1 1 1 1 2 8 1 3 1 1 1 1 1 1 1 1 1 1 1 1 1 2 8 1 2 1 1 1 1 1 1 1 1 1 1 1 1 1 2 4 1" +
                        " 2 1 14 1 1 1 2 9 1 2 1 3 2 2 1 1 2 2 1 1 1 1 1 6 1 1 13 1 4 2 1 1 1 2 2 1 1 2 2 1 1 2 2 1 1" +
                        " 2 2 1 1 1 1 1 2 17 1 1 2 1 1 1 2 2 1 1 2 2 1 1 2 2 1 1 2 2 1 1 2 2 1 1 2 2 1 1 2 1 1 1 17 1" +
                        " 1 2 1 1 1 2 2 1 1 2 2 1 1 2 2 1 1 2 2 1 1 2 2 1 1 2 2 1 1 2 3 1 1 17 1 1 2 2 1 1 2 2 1 1 2 " +
                        "1 1 1 2 2 1 1 2 2 1 1 2 2 1 1 2 2 1 1 2 3 1 1 11 1 1 2 2 1 1 2 2 1 1 2 1 1 11 2 1 1 1 2 3 1 " +
                        "1 10 1 2 2 1 1 2 2 1 1 3 1 1 1 1 1 4 2 2 1 1 8 1 1 2 2 1 3 1 1 1 1 1 1 1 1 1 5 7 1 3 1 1 1 1" +
                        " 1 1 1 1 1 1 1 3 7 1 2 1 1 1 1 1 1 1 1 1 1 1 2 7 1 2 1 1 1 1 1 1 1 1 1 1 1 1 6 1 3 1 1 1 1 1" +
                        " 1 1 1 1 2 6 1 3 1 1 1 1 1 1 1 1 1 2 3 1 4 1 4 1 3 1 1 11 1 1 9 2 1 2 1 2 2 1 1 1 1 1 1 5 5 " +
                        "2 5 1 1 3 3 1 1 2 6 5 2 9 1 1 3 3 1 1 2 8 3 2 6 1 5 2 4 2 1 10 2 1 4 1 6 2 4 1 3 2 1 5 1 3 1" +
                        " 3 2 4 1 2 2 3 7 1 2 1 2 2 2 1 3 2 1 1 3 2 3 6 1 2 1 1 2 5 1 2 1 2 2 3 6 1 2 1 7 2 3 1 1 1 2" +
                        " 2 3 5 1 4 1 1 2 4 1 10 2 3 6 1 2 1 1 2 6 1 1 1 3 2 2 4 1 1 1 16 1 3 2 1 5 1 3 1 1 2 5 1 1 1" +
                        " 6 6 1 1 1 1 2 5 1 1 1 3 1 4 5 1 1 1 21 1 2 3 2 1 1 8 1 3 1 1 2 4 1 1 1 3 1 1 3 2 1 1 8 1 1 " +
                        "1 1 2 4 1 1 1 3 1 2 3 2 1 1 2 1 25 1 4 5 1 1 1 1 2 4 1 1 1 3 5 1 2 1 1 2 4 1 1 1 5 3 1 18 1 " +
                        "2 2 1 6 1 1 1 2 2 3 1 2 1 2 2 2 6 1 3 1 1 2 4 1 2 1 2 2 3 3 1 1 1 16 2 3 5 1 2 1 1 2 5 1 3 2" +
                        " 3 5 1 2 1 1 2 4 1 2 2 3 5 1 4 1 1 2 3 1 2 2 2 2 1 9 2 1",
                "25 40 2 1 1 2 13 3 1 1 2 2 2 8 4 1 2 2 3 1 2 2 1 7 1 2 1 1 1 1 2 1 1 1 1 1 2 1 6 1 2 1 2 1 2 2 1 1 1" +
                        " 2 2 6 1 1 1 2 1 2 2 1 1 1 2 2 4 1 14 2 1 1 1 2 1 6 1 2 2 13 1 3 2 1 1 4 2 2 8 1 2 2 5 2 5 1" +
                        " 2 2 7 1 1 2 3 1 2 9 1 3 2 5 2 5 1 1 2 10 1 1 2 1 1 1 2 3 8 2 2 1 1 2 6 2 4 1 6 2 7 1 2 2 5 " +
                        "10 2 4 1 1 2 4 2 4 1 2 2 5 1 3 2 4 1 1 2 6 16 2 1 1 1 2 2 1 1 2 3 2 4 1 2 2 2 1 1 2 5 1 2 2 " +
                        "3 1 1 2 4 1 1 2 1 16 2 1 1 1 2 2 1 1 2 2 2 4 1 1 2 2 1 1 1 1 2 5 1 1 2 3 1 1 2 3 1 3 16 2 1 " +
                        "1 1 2 2 1 1 2 2 2 4 1 1 2 2 1 1 1 1 2 3 1 1 2 4 1 1 2 3 1 1 16 2 2 1 1 2 3 1 1 2 1 2 3 1 1 2" +
                        " 3 1 1 1 1 2 2 1 2 2 4 1 1 2 3 1 1 17 2 2 1 2 2 4 1 1 2 1 2 3 1 1 2 3 1 1 1 1 2 3 1 3 2 3 1 " +
                        "1 2 3 1 1 1 1 13 2 8 1 1 2 1 2 2 1 1 2 5 1 2 2 3 1 1 1 2 2 6 1 2 1 1 12 2 5 1 1 2 2 1 2 2 2 " +
                        "1 1 2 11 1 1 1 1 2 4 1 3 1 1 13 1 4 2 2 1 1 2 3 1 4 2 2 1 1 2 9 1 2 1 1 2 2 1 5 1 2 6 1 1 1 " +
                        "19 2 2 1 2 1 4 1 5 8 1 3 1 1 1 1 1 1 2 1 1 2 1 2 1 3 4 1 6 1 9 1 11 1 2 2 1 22 1 6 2 1 13 1 " +
                        "4 1 1 4 3 2 4 1 1 1 3 3 2 6 1 1 1 3 5 2 1 1 1 2 2 1 1 1 2 4 2 1 1 1 2 3 1 2 6 2 2 1 1 1 1 2 " +
                        "4 1 1 1 3 5 2 3 1 2 2 3 1 4 1 2 4 1 2 2 9 1 1 1 3 6 1 1 2 1 1 2 2 7 1 1 1 3 6 1 2 2 3 1 6 2 " +
                        "1 1 1 1 3 4 1 2 2 9 1 3 1 3 4 1 2 2 7 1 2 1 3 5 1 3 2 5 2 1 1 2 1 3 6 1 2 1 1 2 4 2 5 1 2 1 " +
                        "3 8 1 2 1 1 2 3 2 6 1 1 2 1 1 1 1 3 8 1 2 1 1 2 2 2 6 1 1 2 2 1 1 1 3 10 1 1 2 1 1 2 1 1 2 1" +
                        " 2 5 1 2 2 2 1 3 1 2 8 2 2 1 3 2 1 2 5 1 1 2 5 1 1 1 3 7 2 1 1 2 2 5 1 2 2 6 1 1 1 2 7 2 1 1" +
                        " 1 2 4 1 2 2 7 1 1 1 2 9 2 1 1 1 2 3 1 2 2 2 1 3 2 3 1 1 1 2 11 2 1 1 2 1 1 2 3 1 1 2 2 1 1 " +
                        "1 1 2 2 1 1 1 2 10 2 1 1 3 2 2 1 2 2 1 1 1 1 1 2 2 1 1 1 2 11 2 1 1 2 2 1 1 1 2 1 1 1 2 2 1 " +
                        "1 1 1 2 3 1 3 10 2 2 1 1 2 1 1 1 2 1 1 1 2 3 1 2 2 6 1 2 6 2 2 1 1 2 2 1 1 2 10 1 4 6 2 2 1 " +
                        "1 2 3 1 1 2 5 1 8 8 2 3 1 1 2 3 1 1 2 3 1 2 1 2 1 3 5 2 11 1 2 2 1 1 4 1 2 7 2 3 1 1 1 1 2 4" +
                        " 1 2 2 2 1 8 5 2 1 1 1 1 1 2 12 1 3 5 2 1 1 1 1 1 2 12 1 1 4 2 1 1 6 2 10 1 2 5 2 2 2 1 1 8 " +
                        "2 2 1 1 5 2 2 2 2 1 1 2 7 1 3 4 2 4 1 1 2 8 1 4 4 2 1 1 1 2 8 1 5 5 1 1 2 4 1 1 1 2 1 2 3 2 " +
                        "3 1 2 1 2 2 2 3 1 7"};
        String[] solutions = new
                String[]{"110020020",
                "0002220000000002000000022200000000220000002222200000022220000022222000000222200000222220000002222000002220200020022020000022202002200220200000222020222200220000000222202222032300000000220022020111000000001300220200300000000033000222003000000000030000200010000033000300001000300000113103100010003001110033103010300010111000013310131010101100000313100111101130013300333101111111301133330331001111111311",
                "000000011111111110000000000000111010010101111000000001111001001010010111000001110101001001001010110001100010100100100100101101110010010010010010010011110001001001001001001001111000111111111111110100111101112212210010011111101111121221221221221001001112122122122122122122120011212212212212212212212221122122121221221221221222112212212111111111112122211121121110010010011112210012211101001001001011111001110010100100100100111000011001010010010010011000000110101001001001001000000001110010100101001100000000011101010010101100000000000111101111011100000000000001111111111100000000000000011111111100000000000000000110001100000000000000000000101000000000000000000000111110000000000002222200013331000222222022222222201333100222222220022222200111110000222200",
                "0000000000000000122222222222220000000000000000000000000122000000222222220000000000000000000000110000000000022211200000000000000000000110100001000000210012000000000000000000110011000110000020001220000000000000000010000110001100002000102200000000000000011111111111111000200010020000000000000011222222222222211121111002200000000000011222220022222112222222122211000000000111222220022222122222222221212220000002212222220022221111112222222112222200002222122220022221122222111222212222220002012212220022221122122222112221222212002000122122002222122101222221222122211100200012212202222122100012221222212220010220012221200222122210001221122221222001022112222120022212221001222111222122210102222222212002212222211222101122222211010222221221102212222222222210012222111001111122122211112212222222221101221111101110000111111111111111111122110111101111101110001000000000100000012110011000011100111111011111111101111111111101100000000001111111111111111111111011111100000000000000011111111111110000000111100000000000"};
        for (int index = 0; index < lines.length; index++) {
            String line = lines[index];
            String solution = solutions[index];
            StringTokenizer st = new StringTokenizer(line, " ");
            int rows = parseInt(st.nextToken());
            int cols = parseInt(st.nextToken());

            int[][] rowBlockLengths = new int[rows][];
            int[][] rowColors = new int[rows][];
            int[][] colBlockLengths = new int[cols][];
            int[][] colColors = new int[cols][];
            for (int i = 0; i < rows; i++) {
                int blocks = parseInt(st.nextToken());
                rowColors[i] = new int[blocks];
                rowBlockLengths[i] = new int[blocks];
                for (int j = 0; j < blocks; j++) {
                    rowColors[i][j] = parseInt(st.nextToken());
                    rowBlockLengths[i][j] = parseInt(st.nextToken());
                }
            }
            for (int i = 0; i < cols; i++) {
                int blocks = parseInt(st.nextToken());
                colColors[i] = new int[blocks];
                colBlockLengths[i] = new int[blocks];
                for (int j = 0; j < blocks; j++) {
                    colColors[i][j] = parseInt(st.nextToken());
                    colBlockLengths[i][j] = parseInt(st.nextToken());
                }
            }

            Puzzles p = new Puzzles();
            char[][] puzzle = p.arrangeMultipleColoredBlocksOnMatrix(rows, cols, rowBlockLengths, rowColors,
                    colBlockLengths, colColors);

            StringBuffer sb = new StringBuffer();
            if (puzzle == null) {
                Assert.fail("No solution found");
            } else {
                for (char[] puzzleLine : puzzle) {
                    sb.append(new String(puzzleLine));
                    System.out.println(new String(puzzleLine));
                }
                System.out.println();
            }

            Assert.assertEquals(solution, sb.toString());
        }
    }
}
