package algorithmsandme;

import org.junit.Test;

/**
 * Rotate a matrix by 90 degree
 * <p>
 * Rotation by 90 means first column of matrix becomes first row of matrix, second column becomes second row and so on.
 */
public class RotateMatrix {

    public int[][] rotate(int[][] a, int row, int col) {
        int[][] b = new int[col][row];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                b[j][i] = a[i][j];
            }
        }
        return b;
    }

    public void print(int[][] a, int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.printf("%4d", a[i][j]);
            }
            System.out.println();
        }
    }


    @Test
    public void testAccuracy() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };

        int row = matrix.length;
        int col = matrix[0].length;

        print(matrix, row, col);

        System.out.println();
        print(rotate(matrix, row, col), col, row);
    }
}
