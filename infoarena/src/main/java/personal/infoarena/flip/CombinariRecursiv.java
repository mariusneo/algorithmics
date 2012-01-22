package personal.infoarena.flip;

public class CombinariRecursiv {
	private static final int n = 6;
	private static final int k = 3;
	private static final int[] array = new int[k];
	private static final int[] indexes = new int[n];

	private static void back(int currentIndex) {
		for (int i = 0; i < n; i++) {
			// ne asiguram ca generam solutiile crescatoare si unice
			if (indexes[i] == 0) {
				if ((currentIndex == 0)
						|| (currentIndex > 0 && array[currentIndex - 1] < i + 1)) {
					array[currentIndex] = i + 1;
					indexes[i] = 1;// used

					if (currentIndex + 1 == k) {
						for (int value : array) {
							System.out.print(value + " ");
						}
						System.out.println();
					} else {
						back(currentIndex + 1);
					}

					indexes[i] = 0;// released
				}
			}
		}
	}

	public static void main(String[] args) {
		back(0);
	}
}
