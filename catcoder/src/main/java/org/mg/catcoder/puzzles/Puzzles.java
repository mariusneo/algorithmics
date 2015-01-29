package org.mg.catcoder.puzzles;

import junit.framework.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
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

            char[] combinedSolution = new char[columns];


            boolean firstSolutionSet = false;
            for (int i = 0; i < solutions.size(); i++) {
                String solution = solutions.get(i);

                boolean isValid = true;
                for (int j = 0; j < pattern.length(); j++) {
                    if (pattern.charAt(j) != '?' && pattern.charAt(j) != solution.charAt(j)) {
                        isValid = false;
                        continue;
                    }
                }

                if (isValid) {
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
            }

            return new String(combinedSolution);
        }
    }


    public char[][] arrangeMultipleColoredBlocksOnMatrix(int rows, int cols,
                                                         int[][] rowBlockLengths, int[][] rowColors,
                                                         int[][] colBlockLengths, int[][] colColors) {
        char[][] rowPatterns = new char[rows][];
        for (int i = 0; i < rows; i++) {
            char[] rowPattern = arrangeMultipleColoredBlocksPerRow(cols, rowColors[0], rowBlockLengths[0])
                    .toCharArray();
            rowPatterns[i] = rowPattern;

            for (int j = 0; j < cols; j++) {
                StringBuilder sb = new StringBuilder();
                for (int k = 0; k < rows; k++) {
                    if (k <= i) {
                        sb.append(rowPatterns[k][j]);
                    } else {
                        sb.append('?');
                    }
                }
                String intermediateColPattern = sb.toString();
                String colPattern = arrangeMultipleColoredBlocksPerRowWithPattern(rows, colColors[j],
                        colBlockLengths[j], intermediateColPattern);

                for (int k = 0; k <= i; k++) {
                    if (colPattern.charAt(j) != '?' && rowPattern[j] == '?') {
                        rowPattern[j] = colPattern.charAt(j);
                    }
                }
            }
        }

        return rowPatterns;
    }


    public void sumSubsetsMultipleColorsWithPattern(int target, int[] blockLengths, int[] colors, String pattern,
                                                    List<Integer> partial, List<List<Integer>> solutions) {
        int partialSum = 0;
        for (int i : partial) {
            partialSum += i;
        }

        if (partialSum <= target) {
            if (partial.size() == blockLengths.length) {
                partial.add(target - partialSum);
                solutions.add(partial);
            } else {
                for (int i = 0; i <= target - partialSum; i++) {
                    if (i == 0 && partial.size() > 0) {
                        int previousColor = colors[partial.size() - 1];
                        int currentColor = colors[partial.size()];
                        if (previousColor == currentColor) continue;
                    }

                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < i; j++) {
                        sb.append("0");
                    }
                    if (partial.size() > 0) {
                        int currentIndex = partial.size();
                        for (int j = 0; j < blockLengths[currentIndex]; j++) {
                            sb.append(colors[currentIndex]);
                        }
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
        int partialSum = 0;
        for (int i : partial) {
            partialSum += i;
        }

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
        int partialSum = 0;
        for (int i : partial) {
            partialSum += i;
        }

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
        puzzles.sumSubsets(2, 3, new ArrayList<Integer>(), solutions);

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
                    int columns = Integer.parseInt(st.nextToken());
                    int blockLength = Integer.parseInt(st.nextToken());
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
            int columns = Integer.parseInt(components[0]);
            int blocks = Integer.parseInt(components[1]);
            int index = 2;
            int[] colors = new int[blocks];
            int[] blockLengths = new int[blocks];
            for (int i = 0; i < blocks; i++) {
                int color = Integer.parseInt(components[index++]);
                int blockLength = Integer.parseInt(components[index++]);
                colors[i] = color;
                blockLengths[i] = blockLength;
            }
            String pattern = components[components.length - 1];

            System.out.println(puzzles.arrangeMultipleColoredBlocksPerRowWithPattern(columns, colors, blockLengths,
                    pattern));
        }
    }
}
