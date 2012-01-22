package personal.infoarena.flip;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 * <p>
 * Problema : Flip
 * </p>
 * <p>
 * Site : http://infoarena.ro/problema/flip
 * </p>
 * <p>
 * Enunt : Gigel a descoperit un nou joc pe care l-a numit "Flip". Acesta se
 * joaca pe o tabla dreptunghiulara de dimensiuni N*M care contine numere
 * intregi. Fiecare linie si fiecare coloana are un comutator care schimba
 * starea tuturor elementelor de pe acea linie sau coloana, inmultindu-le cu -1.
 * Scopul jocului este ca pentru o configuratie data a tablei de joc sa se
 * actioneze asupra liniilor si coloanelor astfel incat sa se obtina o tabla cu
 * suma elementelor cat mai mare.
 * </p>
 * 
 * <p>
 * Cerinta Dandu-se o configuratie pentru tabla "Flip", realizati un program
 * care sa determine suma maxima pe care Gigel o poate obtine.
 * </p>
 * 
 * <p>
 * Solutie: Backtracking Combinari((n+m)*2, n+m) Fiecare linie/coloana poate
 * avea semnul +/-
 * </p>
 * 
 * 
 * @author mga
 * 
 */
public class FlipImproved {
	private static int calculateMaxFlipSumRecursive(int[][] matrix,
			int[] columnSignArray, int currentRecursionIndex) {
		int maximumSum = Integer.MIN_VALUE;
		if (currentRecursionIndex == columnSignArray.length) {
			int currentSum = 0;
			for (int lineIndex = 0; lineIndex < matrix.length; lineIndex++) {
				int lineSum = 0;
				for (int columnIndex = 0; columnIndex < matrix[lineIndex].length; columnIndex++) {
					int value = matrix[lineIndex][columnIndex];
					value = value * columnSignArray[columnIndex];
					lineSum += value;
				}
				
				// flip the sum of the line if it is negative
				lineSum = lineSum < 0 ? -lineSum : lineSum;
				
				currentSum += lineSum;
			}
			// System.out.println("Current sum : " + currentSum);
			return currentSum;
		} else {
			columnSignArray[currentRecursionIndex] = 1;
			int sum = calculateMaxFlipSumRecursive(matrix, columnSignArray,
					currentRecursionIndex + 1);
			maximumSum = Math.max(maximumSum, sum);

			columnSignArray[currentRecursionIndex] = -1;
			sum = calculateMaxFlipSumRecursive(matrix, columnSignArray,
					currentRecursionIndex + 1);
			maximumSum = Math.max(maximumSum, sum);

			return maximumSum;
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream("flip.in")));

		String firstLine = reader.readLine();
		String[] dimensions = firstLine.split(" ");

		int lineCount = Integer.parseInt(dimensions[0]);
		int columnCount = Integer.parseInt(dimensions[1]);

		// System.out.println("lines : " + lineCount);
		// System.out.println("columns : " + columnCount);

		int lineIndex = 0;

		int[][] matrix = new int[lineCount][columnCount];

		while (lineIndex < lineCount) {
			String line = reader.readLine();
			String[] numbers = line.split(" ");
			for (int columnIndex = 0; columnIndex < numbers.length; columnIndex++) {
				matrix[lineIndex][columnIndex] = Integer
						.parseInt(numbers[columnIndex]);
			}

			lineIndex++;
		}

		reader.close();

		int[] columnSignArray = new int[columnCount];
		int maximumSum = calculateMaxFlipSumRecursive(matrix, columnSignArray, 0);

		System.out.println("MAXIMUM SUM : " + maximumSum);

		BufferedWriter writer = new BufferedWriter(new FileWriter("flip.out"));
		writer.write(Integer.toString(maximumSum));
		writer.flush();
		// Close the output stream
		writer.close();
	}
}

