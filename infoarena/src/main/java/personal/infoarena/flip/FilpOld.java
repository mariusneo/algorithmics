package personal.infoarena.flip;

import java.util.List;

public class FilpOld {
	private static void calculateCombinations(List<int[]> solutionsList,
			int[] solutionArray, int[] indexes, int n, int k, int currentIndex) {
		for (int i = 0; i < n; i++) {
			if (indexes[i] == 0) {
				if ((currentIndex == 0)
						|| (currentIndex > 0 && solutionArray[currentIndex - 1] < i + 1)) {
					solutionArray[currentIndex] = i + 1;

					indexes[i] = 1;// used

					if (currentIndex + 1 == k) {
						solutionsList.add(solutionArray.clone());
					} else {
						calculateCombinations(solutionsList, solutionArray,
								indexes, n, k, currentIndex + 1);
					}

					indexes[i] = 0;// released
				}
			}
		}
	}
}
