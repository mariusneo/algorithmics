package org.mg.catcoder.bowling;


import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Bowling {
    public int[] score(int rounds, int[] throwsArray) {
        int[] score = new int[rounds];

        int i = 0;
        int round = 0;
        while (round < rounds) {
            int currentScore = 0;
            int firstThrow = throwsArray[i++];
            currentScore += firstThrow;
            if (firstThrow == 10) {
                currentScore += throwsArray[i] + throwsArray[i + 1];
            } else {
                int secondThrow = throwsArray[i++];
                currentScore += secondThrow;
                if (firstThrow + secondThrow == 10) {
                    currentScore += throwsArray[i];
                }
            }

            int previousScore = ((round > 0) ? score[round - 1] : 0);
            score[round] = previousScore + currentScore;
            round++;
        }


        return score;
    }


    @Test
    public void testScoreLevel1() {
        int rounds = 3;
        int[] throwsArray = new int[]{1, 4, 6, 4, 7, 0};
        Bowling bowling = new Bowling();
        int[] scores = bowling.score(rounds, throwsArray);
        Assert.assertArrayEquals(new int[]{5, 22, 29}, scores);

        scores = bowling.score(3, new int[]{0, 0, 9, 1, 0, 0});
        Assert.assertArrayEquals(new int[]{0, 10, 10}, scores);
    }

    @Test
    public void quizScore() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(Bowling.class.getClassLoader()
                    .getResourceAsStream("org/mg/catcoder/bowling/input-level6.txt")));

            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ":,");
                if (st.hasMoreTokens()) {
                    int[] throwsArray = new int[st.countTokens()];
                    int rounds = Integer.parseInt(st.nextToken());
                    int i = 0;
                    while (st.hasMoreTokens()) {
                        int throwPoints = Integer.parseInt(st.nextToken());
                        throwsArray[i++] = throwPoints;
                    }

                    Bowling bowling = new Bowling();
                    int[] scores = bowling.score(rounds, throwsArray);
                    StringBuilder sb = new StringBuilder();
                    for (int score : scores) {
                        if (sb.length() > 0) {
                            sb.append(",");
                        }
                        sb.append(score);
                    }
                    System.out.println(sb.toString());
                }
            }


        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    @Test
    public void testScoreLevel2() {
        Bowling bowling = new Bowling();
        int[] scores = bowling.score(4, new int[]{1, 4, 6, 4, 7, 3, 2, 5});
        Assert.assertArrayEquals(new int[]{5, 22, 34, 41}, scores);

    }

}
