package personal.infoarena.flip;

public class CombinariSemneRecursiv {
	private static final int[] array = new int[] { 1, 2, 3 };

	public static void back(int k) {
		for (int index = 0; index < 2; index++) {
			array[k] = index == 0 ? array[k] : array[k] * (-1);
			if (k + 1 == array.length) {
				for (int value : array) {
					System.out.print(value + " ");
				}
				System.out.println();
			} else {
				back(k + 1);
			}

		}
	}

	public static void main(String[] args) {
		back(0);
	}
}