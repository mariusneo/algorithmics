package topcoder.srm.div2.srm270;

import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountriesRankList {

    public String[] findPlaces(String[] knownPoints) {
        Map<String, List<Integer>> countryScores = new HashMap<>();
        int minScore = Integer.MAX_VALUE;
        for (String line : knownPoints) {
            String[] tokens = line.split(" ");
            String country = tokens[0];
            int score = Integer.parseInt(tokens[2]);
            if (minScore > score)
                minScore = score;
            if (countryScores.containsKey(country)) {
                countryScores.get(country).add(score);
            } else {
                List<Integer> scores = new ArrayList<>();
                scores.add(score);
                countryScores.put(country, scores);
            }
        }

        Map<String, Integer> countryMinScores = new HashMap<>();
        Map<String, Integer> countryMaxScores = new HashMap<>();

        for (String country : countryScores.keySet()) {
            List<Integer> scores = countryScores.get(country);
            int sumScores = scores.stream().reduce(0, Integer::sum);
            countryMaxScores.put(country, sumScores + (4 - scores.size()) * (minScore - 1));
            countryMinScores.put(country, sumScores);
        }

        Map<String, Integer> countryMinPlacements = new HashMap<>();
        for (String country : countryScores.keySet()) {
            List<Integer> minPlacements = new ArrayList<>();
            for (Map.Entry<String, Integer> otherCountryScore : countryMaxScores.entrySet()) {
                if (!otherCountryScore.getKey().equals(country)) {
                    minPlacements.add(otherCountryScore.getValue());
                }
            }
            int countryMinScore = countryMinScores.get(country);
            minPlacements.add(countryMinScore);
            minPlacements.sort((a, b) -> b - a);
            countryMinPlacements.put(country, minPlacements.indexOf(countryMinScore) + 1);
        }

        Map<String, Integer> countryMaxPlacements = new HashMap<>();
        for (String country : countryScores.keySet()) {
            List<Integer> maxPlacements = new ArrayList<>();
            for (Map.Entry<String, Integer> otherCountryScore : countryMinScores.entrySet()) {
                if (!otherCountryScore.getKey().equals(country)) {
                    maxPlacements.add(otherCountryScore.getValue());
                }
            }
            int countryMaxScore = countryMaxScores.get(country);
            maxPlacements.add(countryMaxScore);
            maxPlacements.sort((a, b) -> b - a);
            countryMaxPlacements.put(country, maxPlacements.indexOf(countryMaxScore) + 1);
        }


        return countryScores.keySet()
                .stream()
                .sorted()
                .map(country ->
                        country + " " + countryMaxPlacements.get(country) + " " + countryMinPlacements.get(country))
                .toArray(String[]::new);
    }

    @Test
    public void test0() {
        CountriesRankList crl = new CountriesRankList();
        String[] places = crl.findPlaces(new String[]{"Poland Krzysztof 101", "Ukraine Evgeni 30", "Ukraine Ivan 24"});
        Assert.assertTrue(Arrays.equals(new String[]{"Poland 1 1", "Ukraine 2 2"}, places));
    }


    @Test
    public void test1() {
        CountriesRankList crl = new CountriesRankList();
        String[] places = crl.findPlaces(new String[]{"Poland Krzysztof 100", "CzechRep Martin 30", "CzechRep Jirka " +
                "25"});
        Assert.assertTrue(Arrays.equals(new String[]{"CzechRep 1 2", "Poland 1 2"}, places));
    }

    @Test
    public void test3() {
        CountriesRankList crl = new CountriesRankList();
        String[] places = crl.findPlaces(new String[]{"usa Jack 14", "USA Jim 10", "USA Jim 10", "USA Jim 10",
                "USA Jim 10", "usa Jack 14", "usa Jack 14", "Zimbabwe Jack 10"});
        Assert.assertTrue(Arrays.equals(new String[]{"USA 2 2", "Zimbabwe 3 3", "usa 1 1"}, places));
    }
}
