package personal.infoarena.flip;

/**
 * Solutie adaptata de aici 
 * 
 * http://www.scribd.com/doc/13396582/Limbajul-Pascal-Metoda-Backtracking-Permutari
 * 
 * @author mga
 *
 */
public class PermutariIterativ {

	private static final int n = 3;
	private static int[] stack = new int[n];

	private static boolean valid(int p) {
		for (int index = 0; index < p; index++) {
			if (stack[index] == stack[p]) {
				return false;
			}
		}

		return true;
	}

	private static void back() {
		int k = 0;
		do {
			if (stack[k] < n) {
				stack[k] = stack[k] + 1;

				if (valid(k)) {
					if (k +1 == n) {
						for (int value : stack) {
							System.out.print(value + " ");
						}
						System.out.println();
					} else {
						k++;
						stack[k] = 0;
					}
				}
			} else {
				k--;
			}

		} while (k >= 0);
	}

	public static void main(String[] args) {
		back();
	}
}
