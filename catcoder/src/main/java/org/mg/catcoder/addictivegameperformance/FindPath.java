package org.mg.catcoder.addictivegameperformance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

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

    private static Map<Integer, List<Integer>> color2CellMap = new HashMap<>();

    private static int testNumber;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(FindPath.class.getClassLoader()
                .getResourceAsStream("org/mg/catcoder/addictivegameperformance/level1-2.in")));
        String line = br.readLine();
        br.close();
        String[] components = line.trim().split(" ");
        testNumber = Integer.parseInt(components[0]);
        int rows = Integer.parseInt(components[1]);
        int cols = Integer.parseInt(components[2]);
        String[][] matrix = new String[rows][cols];
        List<Integer> colorCells = new ArrayList<>();
        int pointsCount = Integer.parseInt(components[3]);
        for (int i = 0; i < pointsCount; i++) {
            int index = 4 + i * 2;
            int cellIndex = Integer.parseInt(components[index]);
            int cellColor = Integer.parseInt(components[index + 1]);

            if (color2CellMap.containsKey(cellColor)) {
                color2CellMap.get(cellColor).add(cellIndex);
            } else {
                List<Integer> currentColorCells = new ArrayList<>();
                currentColorCells.add(cellIndex);
                color2CellMap.put(cellColor, currentColorCells);
            }
            colorCells.add(cellIndex);

            int row = cellIndex / cols - (cellIndex % cols == 0 ? 1 : 0);
            int col = (cellIndex - 1) % cols;
            matrix[row][col] = Integer.toString(cellColor);
        }

        fillMatrix(matrix, 0, 0);

        System.out.println("DONE");
    }

    /**
     * possible directions for the pipes are : | N, - E, :- NE, -: NW, :_ SE, _: SW
     * find a solution where all the table is filled and the pipes do
     * not go on top of each other
     */
    private static void fillMatrix(String[][] matrix, int i, int j) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        if (i == rows || i == -1) return;

        if (i == 0) {
            if (j == 0) {
                if (!isColorCell(matrix[i][j])) {
                    matrix[i][j] = "NE";
                }
                if (j == cols - 1) {
                    fillMatrix(matrix, i + 1, 0);
                } else {
                    fillMatrix(matrix, i, j + 1);
                }
            } else if (j == cols - 1) {
                if (!isColorCell(matrix[i][j])) {
                    matrix[i][j] = "NW";
                }
                if (i < rows - 1) {
                    fillMatrix(matrix, i + 1, 0);
                }
            } else {
                if (!isColorCell(matrix[i][j])) {
                    // possible directions are now : E, NE, NW
                    List<String> possibleDirections = new ArrayList<>();

                    if (matrix[i][j - 1].equals("NW")) {
                        possibleDirections.add("NE");
                    } else if (matrix[i][j - 1].equals("NE")) {
                        possibleDirections.add("E");
                    } else if (matrix[i][j - 1].equals("E")) {
                        if (!isColorCell(matrix[i][j + 1])) {
                            possibleDirections.add("NW");
                        }
                        possibleDirections.add("E");
                    } else if (isColorCell(matrix[i][j - 1])) {
                        possibleDirections.add("E");
                    }
                    for (String direction : possibleDirections) {
                        matrix[i][j] = direction;
                        fillMatrix(matrix, i, j + 1);
                    }
                }
            }
        } else if (i == rows - 1) {
            if (j == 0) {
                if (isColorCell(matrix[i][j])) {
                    if (j < cols - 1) fillMatrix(matrix, i, j + 1);
                } else {
                    if (matrix[i - 1][j].equals("N") || matrix[i - 1][j].equals("NE") || isColorCell(matrix[i - 1][j])) {
                        matrix[i][j] = "SE";
                        if (j < cols - 1) fillMatrix(matrix, i, j + 1);
                    }
                }
            } else if (j == cols - 1) {
                boolean valid = false;
                if (isColorCell(matrix[i][j])) {
                    valid = true;
                } else {
                    if (matrix[i][j - 1].equals("E") || matrix[i][j - 1].equals("SE") || isColorCell(matrix[i][j - 1])) {
                        if (matrix[i - 1][j].equals("N") || isColorCell(matrix[i - 1][j])) {
                            valid = true;
                            matrix[i][j] = "SW";
                        }
                    }
                }
                if (valid) {
                    validateSolution(matrix);
                }
            } else {
                if (isColorCell(matrix[i][j])) {
                    fillMatrix(matrix, i, j + 1);
                    fillMatrix(matrix, i, j + 1);
                } else {
                    // the possible directions on the last row can be : SE, E, SW
                    if (matrix[i][j - 1].equals("SE") || matrix[i][j - 1].equals("E") || isColorCell(matrix[i][j - 1])) {
                        if (matrix[i - 1][j].equals("N") || matrix[i - 1][j].equals("NE") || matrix[i - 1][j].equals("NW")) {
                            matrix[i][j] = "SW";
                            fillMatrix(matrix, i, j + 1);
                        } else {
                            matrix[i][j] = "E";
                            fillMatrix(matrix, i, j + 1);
                        }
                    } else if (matrix[i][j - 1].equals("SW")) {
                        if (matrix[i - 1][j].equals("N") || matrix[i - 1][j].equals("NE") || matrix[i - 1][j].equals("NW")) {
                            matrix[i][j] = "SE";
                            fillMatrix(matrix, i, j + 1);
                        }
                    }
                }
            }
        } else {
            if (j == 0) {
                if (isColorCell(matrix[i][j])) {
                    if (j < cols - 1) fillMatrix(matrix, i, j + 1);
                    else fillMatrix(matrix, i + 1, 0);
                } else {

                    if (j == cols - 1) {
                        matrix[i][j] = "N";
                        fillMatrix(matrix, i + 1, 0);
                    } else {
                        if (matrix[i - 1][j].equals("N") || matrix[i - 1][j].equals("NE")) {
                            for (String direction : new String[]{"N", "SE"}) {
                                matrix[i][j] = direction;
                                fillMatrix(matrix, i, j + 1);
                            }
                        } else if (isColorCell(matrix[i - 1][j])) {
                            for (String direction : new String[]{"N", "SE", "NE"}) {
                                matrix[i][j] = direction;
                                fillMatrix(matrix, i, j + 1);
                            }
                        } else if (matrix[i - 1][j].equals("SE")) {
                            matrix[i][j] = "NE";
                            fillMatrix(matrix, i, j + 1);
                        }
                    }
                }
            } else if (j == cols - 1) {
                if (isColorCell(matrix[i][j])) {
                    fillMatrix(matrix, i + 1, 0);
                } else {
                    // possible directions on the last column : N, NW, SW
                    if (matrix[i][j - 1].equals("E") || matrix[i][j - 1].equals("SE") || matrix[i][j - 1].equals("NE")) {
                        if (matrix[i - 1][j].equals("N") || matrix[i - 1][j].equals("NW")) {
                            matrix[i][j] = "SW";
                            fillMatrix(matrix, i + 1, 0);
                        } else if (matrix[i - 1][j].equals("SW")) {
                            matrix[i][j] = "NW";
                            fillMatrix(matrix, i + 1, 0);
                        }
                    } else if (isColorCell(matrix[i][j - 1])) {
                        if (matrix[i - 1][j].equals("N") || isColorCell(matrix[i - 1][j])) {
                            for (String direction : new String[]{"N", "SW"}) {
                                matrix[i][j] = direction;
                                fillMatrix(matrix, i + 1, 0);
                            }
                        }
                    } else if (matrix[i][j - 1].equals("N") || matrix[i][j - 1].equals("SW") || matrix[i][j - 1].equals("NW")) {
                        if (matrix[i - 1][j].equals("N") || isColorCell(matrix[i - 1][j])) {
                            matrix[i][j] = "N";
                            fillMatrix(matrix, i + 1, 0);
                        }
                    }
                }
            } else {
                if (isColorCell(matrix[i][j])) {
                    fillMatrix(matrix, i, j + 1);
                } else {
                    // possbile directions : | N, - E, :- NE, -: NW, :_ SE, _: SW
                    if (matrix[i][j - 1].equals("E") || matrix[i][j - 1].equals("SE") || matrix[i][j - 1].equals("NE")) {
                        if (matrix[i - 1][j].equals("N") || matrix[i - 1][j].equals("NW") || matrix[i - 1][j].equals("NE")) {
                            matrix[i][j] = "SW";
                            fillMatrix(matrix, i, j + 1);
                        } else if (matrix[i - 1][j].equals("SW") || matrix[i - 1][j].equals("SE") || matrix[i - 1][j].equals("E")) {
                            for (String direction : new String[]{"E", "NW"}) {
                                matrix[i][j] = direction;
                                fillMatrix(matrix, i, j + 1);
                            }
                        } else if (isColorCell(matrix[i - 1][j])) {
                            for (String direction : new String[]{"E", "NW", "SW"}) {
                                matrix[i][j] = direction;
                                fillMatrix(matrix, i, j + 1);
                            }
                        }
                    } else if (isColorCell(matrix[i][j - 1])) {
                        if (matrix[i - 1][j].equals("N") || matrix[i - 1][j].equals("NE") || matrix[i - 1][j].equals("NW")) {
                            for (String direction : new String[]{"N", "SW"}) {
                                matrix[i][j] = direction;
                                fillMatrix(matrix, i, j + 1);
                            }
                        } else if (isColorCell(matrix[i - 1][j])) {
                            for (String direction : new String[]{"N", "SW", "E"}) {
                                matrix[i][j] = direction;
                                fillMatrix(matrix, i, j + 1);
                            }
                        } else if (matrix[i - 1][j].equals("SW") || matrix[i - 1][j].equals("SE") || matrix[i - 1][j].equals("E")) {
                            for (String direction : new String[]{"E", "NW", "NE"}) {
                                matrix[i][j] = direction;
                                fillMatrix(matrix, i, j + 1);
                            }
                        }
                    } else if (matrix[i][j - 1].equals("N") || matrix[i][j - 1].equals("NW") || matrix[i][j - 1].equals("SW")) {
                        if (matrix[i - 1][j].equals("N") || matrix[i - 1][j].equals("NW") || matrix[i - 1][j].equals("NE")) {
                            for (String direction : new String[]{"N", "SE"}) {
                                matrix[i][j] = direction;
                                fillMatrix(matrix, i, j + 1);
                            }
                        } else if (isColorCell(matrix[i - 1][j])) {
                            for (String direction : new String[]{"N", "SE", "NE"}) {
                                matrix[i][j] = direction;
                                fillMatrix(matrix, i, j + 1);
                            }
                        } else if (matrix[i - 1][j].equals("SW") || matrix[i - 1][j].equals("SE") || matrix[i - 1][j].equals("E")) {
                            matrix[i][j] = "E";
                            fillMatrix(matrix, i, j + 1);
                        }
                    }
                }
            }
        }

    }

    public static void validateSolution(String[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        // matrix has been fully filled, verify whether the paths connect
        // the right dots to each other
        boolean allPathsFound = true;
        StringBuilder output = new StringBuilder();
        output.append(testNumber);
        output.append(" " + color2CellMap.size());

        for (Integer color : new TreeSet<>(color2CellMap.keySet())) {
            List<Integer> colorCellIndexes = color2CellMap.get(color);

            int cellIndex1 = Math.min(colorCellIndexes.get(0), colorCellIndexes.get(1));
            int row1 = cellIndex1 / cols - (cellIndex1 % cols == 0 ? 1 : 0);
            int col1 = (cellIndex1 - 1) % cols;

            int cellIndex2 = Math.max(colorCellIndexes.get(0), colorCellIndexes.get(1));
            int row2 = cellIndex2 / cols - (cellIndex2 % cols == 0 ? 1 : 0);
            int col2 = (cellIndex2 - 1) % cols;

            int currentRow = row1, currentCol = col1;
            // N and E have dual meaning
            int direction = 1;

            output.append(" " + color);
            output.append(" " + cellIndex1);

            int lengthIndex = output.length();

            // find the next cell from the path next to the cell1
            if (currentRow == 0) {
                if (currentCol == 0) {
                    if (matrix[currentRow][currentCol + 1].equals("E") || matrix[currentRow][currentCol + 1].equals("NW")) {
                        output.append(" E");
                        currentCol = currentCol + 1;
                        direction = 1;
                    } else if (matrix[currentRow + 1][currentCol].equals("N") || matrix[currentRow + 1][currentCol].equals("SE")) {
                        output.append(" S");
                        currentRow = currentRow + 1;
                        direction = 1;
                    }
                } else if (currentCol == cols - 1) {
                    if (matrix[currentRow][currentCol - 1].equals("E") || matrix[currentRow][currentCol - 1].equals("NE")) {
                        output.append(" W");
                        currentCol = currentCol - 1;
                        direction = -1;
                    } else if (matrix[currentRow + 1][currentCol].equals("N") || matrix[currentRow + 1][currentCol].equals("SW")) {
                        output.append(" S");
                        currentRow = currentRow + 1;
                        direction = 1;
                    }
                } else {
                    if (matrix[currentRow][currentCol + 1].equals("E") || matrix[currentRow][currentCol + 1].equals("NW")) {
                        output.append(" E");
                        currentCol = currentCol + 1;
                        direction = 1;
                    } else if (matrix[currentRow][currentCol - 1].equals("E") || matrix[currentRow][currentCol - 1].equals("NE")) {
                        output.append(" W");
                        currentCol = currentCol - 1;
                        direction = -1;
                    } else if (matrix[currentRow + 1][currentCol].equals("N") || matrix[currentRow + 1][currentCol].equals("SE") || matrix[currentRow + 1][currentCol].equals("SW")) {
                        output.append(" S");
                        currentRow = currentRow + 1;
                        direction = 1;
                    }
                }
            } else if (currentRow == rows - 1) {
                if (currentCol == 0) {
                    if (matrix[currentRow][currentCol + 1].equals("E") || matrix[currentRow][currentCol + 1].equals("SW")) {
                        output.append(" E");
                        currentCol = currentCol + 1;
                        direction = 1;
                    } else if (matrix[currentRow - 1][currentCol].equals("N") || matrix[currentRow - 1][currentCol].equals("NE")) {
                        output.append(" S");
                        currentRow = currentRow - 1;
                        direction = -1;
                    }
                } else if (currentCol == cols - 1) {
                    if (matrix[currentRow][currentCol - 1].equals("E") || matrix[currentRow][currentCol - 1].equals("SE")) {
                        output.append(" W");
                        currentCol = currentCol - 1;
                        direction = -1;
                    } else if (matrix[currentRow - 1][currentCol].equals("N") || matrix[currentRow - 1][currentCol].equals("NE")) {
                        output.append(" S");
                        currentRow = currentRow - 1;
                        direction = -1;
                    }
                } else {
                    if (matrix[currentRow][currentCol + 1].equals("E") || matrix[currentRow][currentCol + 1].equals("SW")) {
                        output.append(" E");
                        currentCol = currentCol + 1;
                        direction = 1;
                    } else if (matrix[currentRow][currentCol - 1].equals("E") || matrix[currentRow][currentCol - 1].equals("SE")) {
                        output.append(" W");
                        currentCol = currentCol - 1;
                        direction = -1;
                    } else if (matrix[currentRow - 1][currentCol].equals("N") || matrix[currentRow + 1][currentCol].equals("NE") || matrix[currentRow + 1][currentCol].equals("NW")) {
                        output.append(" S");
                        currentRow = currentRow - 1;
                        direction = -1;
                    }
                }
            } else {
                if (currentCol == 0) {
                    if (matrix[currentRow][currentCol + 1].equals("E") || matrix[currentRow][currentCol + 1].equals("NW") || matrix[currentRow][currentCol + 1].equals("SW")) {
                        output.append(" E");
                        currentCol = currentCol + 1;
                        direction = 1;
                    } else if (matrix[currentRow + 1][currentCol].equals("N") || matrix[currentRow + 1][currentCol].equals("SE")) {
                        output.append(" S");
                        currentRow = currentRow + 1;
                        direction = 1;
                    } else if (matrix[currentRow - 1][currentCol].equals("N") || matrix[currentRow - 1][currentCol].equals("NE")) {
                        output.append(" N");
                        currentRow = currentRow - 1;
                        direction = -1;
                    }
                } else if (currentCol == cols - 1) {
                    if (matrix[currentRow][currentCol - 1].equals("E") || matrix[currentRow][currentCol - 1].equals("NE") || matrix[currentRow][currentCol - 1].equals("SE")) {
                        output.append(" W");
                        currentCol = currentCol - 1;
                        direction = -1;
                    } else if (matrix[currentRow + 1][currentCol].equals("N") || matrix[currentRow + 1][currentCol].equals("SW")) {
                        output.append(" S");
                        currentRow = currentRow + 1;
                        direction = 1;
                    } else if (matrix[currentRow - 1][currentCol].equals("N") || matrix[currentRow - 1][currentCol].equals("NW")) {
                        output.append(" S");
                        currentRow = currentRow - 1;
                        direction = -1;
                    }
                } else {
                    if (matrix[currentRow][currentCol + 1].equals("E") || matrix[currentRow][currentCol + 1].equals("NW") || matrix[currentRow][currentCol + 1].equals("SW")) {
                        output.append(" E");
                        currentCol = currentCol + 1;
                        direction = 1;
                    } else if (matrix[currentRow][currentCol - 1].equals("E") || matrix[currentRow][currentCol - 1].equals("NE") || matrix[currentRow][currentCol - 1].equals("SE")) {
                        output.append(" W");
                        currentCol = currentCol - 1;
                        direction = -1;
                    } else if (matrix[currentRow + 1][currentCol].equals("N") || matrix[currentRow + 1][currentCol].equals("SE") || matrix[currentRow + 1][currentCol].equals("SW")) {
                        output.append(" S");
                        currentRow = currentRow + 1;
                        direction = 1;
                    } else if (matrix[currentRow - 1][currentCol].equals("N") || matrix[currentRow - 1][currentCol].equals("NE") || matrix[currentRow + 1][currentCol].equals("NW")) {
                        output.append(" N");
                        currentRow = currentRow - 1;
                        direction = 1;
                    }
                }
            }

            if (currentRow == row1 && currentCol == col1) {
                allPathsFound = false;
                break;
            }


            int previousRow = row1;
            int previousCol = col1;
            while (currentRow < rows && currentCol < cols && !isColorCell(matrix[currentRow][currentCol])) {
                int tempRow = currentRow, tempCol = currentCol;
                switch (matrix[currentRow][currentCol]) {
                    case "N":
                        output.append(direction == 1 ? " S" : " N");
                        currentRow += direction;
                        break;
                    case "E":
                        output.append(direction == 1 ? " E" : " W");
                        currentCol += direction;
                        break;
                    case "NE":
                        if (previousCol == currentCol) {
                            currentCol += 1;
                            output.append(" E");
                        } else {
                            currentRow += 1;
                            output.append(" S");
                        }
                        direction = 1;
                        break;
                    case "NW":
                        if (previousCol == currentCol) {
                            output.append(" N");
                            currentCol -= 1;
                            direction = -1;
                        } else {
                            output.append(" W");
                            currentRow += 1;
                            direction = 1;
                        }
                        break;
                    case "SE":
                        if (previousCol == currentCol) {
                            output.append(" E");
                            currentCol += 1;
                            direction = 1;
                        } else {
                            output.append(" W");
                            currentRow -= 1;
                            direction = -1;
                        }
                        break;
                    case "SW":
                        if (currentCol == previousCol) {
                            output.append(" W");
                            currentCol -= 1;
                        } else {
                            output.append(" N");
                            currentRow -= 1;
                        }
                        direction = -1;
                        break;
                    default:
                        break;

                }
                previousRow = tempRow;
                previousCol = tempCol;
            }

            if (currentRow != row2 || currentCol != col2) {
                allPathsFound = false;
                break;
            }
            output.insert(lengthIndex, " " + ((output.length() - lengthIndex) / 2));

        }

        if (allPathsFound) {
            System.out.println("SOLUTION FOUND");
            System.out.println(matrixRepresentation(matrix, rows - 1, cols - 1));
            System.out.println(output.toString());

        }
    }


    public static String matrixRepresentation(String[][] matrix, int row, int col) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i <= row; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (i == row && j > col) continue;
                sb.append(String.format("%-5s", matrix[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    public static boolean isColorCell(String content) {
        try {
            Integer.parseInt(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
