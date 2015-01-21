package topcoder.srm.div2.srm154;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Arrays.sort;
import static java.util.Arrays.stream;


public class ContestScore {
    public String[] sortResults(String[] data) {
        if (data.length == 0) {
            return new String[]{};
        }

        String[] names = new String[data.length];
        double[][] scoresPerPlayer = new double[data.length][];

        for (int i = 0; i < data.length; i++) {
            String[] components = data[i].split(" ");
            names[i] = components[0];
            scoresPerPlayer[i] = new double[components.length - 1];
            for (int j = 1; j < components.length; j++) {
                scoresPerPlayer[i][j - 1] = Double.parseDouble(components[j]);
            }

        }

        Double[][] scoresPerCompetition = new Double[scoresPerPlayer[0].length][scoresPerPlayer.length];

        for (int j = 0; j < scoresPerPlayer[0].length; j++) {
            for (int i = 0; i < scoresPerPlayer.length; i++) {
                scoresPerCompetition[j][i] = scoresPerPlayer[i][j];
            }
        }

        for (int j = 0; j < scoresPerCompetition.length; j++) {
            sort(scoresPerCompetition[j], Collections.reverseOrder());
        }

        int[][] playerRankings = new int[scoresPerPlayer.length][scoresPerPlayer[0].length];
        int[] sumRankingsPerCompetition = new int[scoresPerPlayer.length];
        for (int i = 0; i < playerRankings.length; i++) {
            for (int j = 0; j < scoresPerPlayer[0].length; j++) {
                playerRankings[i][j] = getIndexOf(scoresPerCompetition[j], scoresPerPlayer[i][j]) + 1;
                sumRankingsPerCompetition[i] += playerRankings[i][j];
            }
        }


        Map<Integer, Integer> index2ranking = new HashMap<>();
        Map<Integer, Double> index2SumScores = new HashMap<>();
        for (int i = 0; i < sumRankingsPerCompetition.length; i++) {
            index2ranking.put(i, sumRankingsPerCompetition[i]);
            index2SumScores.put(i, stream(scoresPerPlayer[i]).sum());
        }

        List<Integer> sortedIndexes = new ArrayList<>();
        Stream<Map.Entry<Integer, Integer>> st = index2ranking.entrySet().stream();

        Comparator<Map.Entry<Integer, Integer>> byValueAndSumOfScores = new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                if (o1.getValue().equals(o2.getValue())) {
                    // for players having the same ranking, the sum of scores makes the difference
                    int sumScoresComparison = Double.compare(index2SumScores.get(o2.getKey()), index2SumScores.get(o1.getKey()));
                    if (sumScoresComparison == 0) {
                        return names[o1.getKey()].compareTo(names[o2.getKey()]);
                    }
                    return sumScoresComparison;
                }
                return Double.compare(o1.getValue(), o2.getValue());
            }
        };

        st.sorted(byValueAndSumOfScores)
                .forEach(e -> sortedIndexes.add(e.getKey()));


        String[] result = new String[data.length];

        int i = 0;
        for (int sortedIndex : sortedIndexes) {
            result[i++] = names[sortedIndex] + " " + sumRankingsPerCompetition[sortedIndex] + " " + String.format("%.1f", index2SumScores.get(sortedIndex));
        }

        return result;
    }


    public int getIndexOf(Double[] tab, double toSearch) {
        for (int i = 0; i < tab.length; i++)
            if (tab[i] == toSearch)
                return i;

        return -1;
    }

    @Test
    public void test0() {
        String[] data = new String[]{"A 90.7 92.9 87.4",
                "B 90.5 96.6 88.0",
                "C 92.2 91.0 95.3"};
        ContestScore cs = new ContestScore();
        String[] result = cs.sortResults(data);
        String[] expectedResult = new String[]{"C 5 278.5", "B 6 275.1", "A 7 271.0"};
        Assert.assertArrayEquals(result, expectedResult);
    }

    @Test
    public void test1() {
        String[] data = new String[]{"STANFORD 85.3 90.1 82.6 84.6 96.6 94.5 87.3 90.3",
                "MIT 95.5 83.9 80.4 85.5 98.7 98.3 96.7 82.7",
                "PRINCETON 99.2 79.1 87.6 85.1 93.6 96.4 86.0 90.6",
                "HARVARD 83.6 92.0 85.5 94.3 97.5 91.5 92.5 83.0",
                "YALE 99.5 92.6 86.2 82.0 96.4 92.6 84.5 78.6",
                "COLUMBIA 97.2 87.6 81.7 93.7 88.0 86.3 95.9 89.6",
                "BROWN 92.2 95.8 92.1 81.5 89.5 87.0 95.5 86.4",
                "PENN 96.3 80.7 81.2 91.6 85.8 92.2 83.9 87.8",
                "CORNELL 88.0 83.7 85.0 83.8 99.8 92.1 91.0 88.9"};
        ContestScore cs = new ContestScore();
        String[] result = cs.sortResults(data);
        String[] expectedResult = new String[]{"PRINCETON 34 717.6",
                "MIT 36 721.7",
                "HARVARD 38 719.9",
                "COLUMBIA 39 720.0",
                "STANFORD 39 711.3",
                "YALE 40 712.4",
                "BROWN 41 720.0",
                "CORNELL 42 712.3",
                "PENN 51 699.5"};
        Assert.assertArrayEquals(result, expectedResult);
    }


    @Test
    public void test3() {
        String[] data = new String[]{"AA 90.0 80.0 70.0 60.0 50.0 40.0",
                "BBB 80.0 70.0 60.0 50.0 40.0 90.0",
                "EEE 70.0 60.0 50.0 40.0 90.0 80.0",
                "AAA 60.0 50.0 40.0 90.0 80.0 70.0",
                "DDD 50.0 40.0 90.0 80.0 70.0 60.0",
                "CCC 40.0 90.0 80.0 70.0 60.0 50.0"};
        ContestScore cs = new ContestScore();
        String[] result = cs.sortResults(data);
        String[] expectedResult = new String[]{"AA 21 390.0",
                "AAA 21 390.0",
                "BBB 21 390.0",
                "CCC 21 390.0",
                "DDD 21 390.0",
                "EEE 21 390.0"};
        Assert.assertArrayEquals(result, expectedResult);
    }
}
