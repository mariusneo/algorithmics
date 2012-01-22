package personal.infoarena.flip;

/**
 * Solutie gasita pe site-ul :
 * 
 * http://www.worldit.info/articole/backtracking-generare-permutari-si-combinari
 * /
 * 
 * @author mga
 * 
 */
public class PermutariRecursiv {
	private static final int n = 3;
	private static int[] array = new int[n];
	private static int[] indexes = new int[n];

	public static void back_version1(int k, int n) {
		if (k - 1 == n) {
			for (int value : array) {
				System.out.print(value + " ");
			}
			System.out.println();
		} else {
			for (int index = 0; index < n; index++) {
				if (indexes[index] == 0) {
					array[k - 1] = index + 1;
					indexes[index] = 1;
					back_version1(k + 1, n);
					indexes[index] = 0;
				}
			}
		}
	}

	public static void back_version2(int k) {
		for (int index = 0; index < n; index++) {
			if (indexes[index] == 0) {
				array[k] = index + 1;
				indexes[index] = 1;
				if (k + 1 == n) {
					for (int value : array) {
						System.out.print(value + " ");
					}
					System.out.println();
				}else{
					back_version2(k + 1);
				}
				
				indexes[index] = 0;
			}
		}
	}

	public static void main(String[] args) {
		// back_version1(1, n);
		back_version2(0);
	}

}
