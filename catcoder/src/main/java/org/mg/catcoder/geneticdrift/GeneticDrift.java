package org.mg.catcoder.geneticdrift;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.*;

public class GeneticDrift {

    @SuppressWarnings("unchecked")
    public Pair<Integer>[] orientedPairs(int[] permutation) {
        List<Pair<Integer>> result = new ArrayList<>();
        for (int i = 0; i < permutation.length - 1; i++) {
            for (int j = i + 1; j < permutation.length; j++) {
                if ((permutation[i] >= 0 && permutation[j] < 0) || (permutation[i] < 0 && permutation[j] >= 0)) {
                    if (abs(abs(permutation[i]) - abs(permutation[j])) == 1) {
                        result.add(new Pair(i, j));
                    }
                }
            }
        }

        return result.stream().sorted((p1, p2) -> Integer.compare(permutation[p1.first], permutation[p2.first]))
                .toArray(Pair[]::new);
    }

    /**
     * Performs inversion of the elements for a given permutation.
     * In general: for an oriented pair op(x i , x j )
     * If x(i) + x(j) == 1 invert P from i to j-1
     * If x(i) + x(j) == -1 invert P from i+1 to j
     *
     * @param permutation
     * @param i
     * @param j
     */
    public void invert(int[] permutation, int i, int j) {
        if (i == j) return;
        if (i < 0 || i > permutation.length)
            throw new IllegalArgumentException("Parameter i has an invalid value : " + i);
        if (j < 0 || j > permutation.length)
            throw new IllegalArgumentException("Parameter j has an invalid value : " + j);
        if (abs(abs(permutation[i]) - abs(permutation[j])) != 1)
            throw new IllegalArgumentException("Parameters i and j (" + i + ", " + j + ") do not respect the relation |permutation[i]| - |permutation[j]|=1");

        int min, max;
        if (permutation[i] + permutation[j] == 1) {
            min = min(i, j - 1);
            max = max(i, j - 1);
        } else {
            min = min(i + 1, j);
            max = max(i + 1, j);
        }
        for (int index = 0; index <= (max - min) / 2; index++) {
            int temp = permutation[min + index];
            permutation[min + index] = -permutation[max - index];
            permutation[max - index] = -temp;
        }
    }


    public int minNumberOfInversions(int[] permutation) {
        Pair<Integer>[] orientedPairs = orientedPairs(permutation);
        int minInversions = 0;
        int[] p = Arrays.copyOf(permutation, permutation.length);
        while (orientedPairs.length > 0) {
            int[] newPermutation = null;
            Pair<Integer>[] maxPairs = new Pair[]{};
            for (Pair<Integer> pair : orientedPairs) {
                int[] temp = Arrays.copyOf(p, p.length);
                invert(temp, pair.first, pair.second);
                Pair<Integer>[] newPairs = orientedPairs(temp);
                if (newPairs.length > maxPairs.length) {
                    maxPairs = newPairs;
                    newPermutation = temp;
                }
            }

            orientedPairs = maxPairs;
            p = newPermutation;
            minInversions++;
        }

        return minInversions;
    }

    @Test
    public void testMinNumberOfInversions() {
        int[] permutation = new int[]{0, 3, 1, 6, 5, -2, 4, 7};
        GeneticDrift gd = new GeneticDrift();
        int minNumberOfInversions = gd.minNumberOfInversions(permutation);
        Assert.assertEquals(5, minNumberOfInversions);
    }

    @Test
    public void quizMinNumberOfInversions() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(GeneticDrift.class.getClassLoader()
                    .getResourceAsStream("org/mg/catcoder/geneticdrift/input-level4.txt")));

            String line;
            while ((line = br.readLine()) != null) {
                String[] components = line.split(" ");
                int length = Integer.parseInt(components[0]);
                int[] permutation = new int[length];
                for (int i = 0; i < permutation.length; i++) {
                    permutation[i] = Integer.parseInt(components[i + 1]);
                }


                GeneticDrift gd = new GeneticDrift();
                int minNumberOfInversions = gd.minNumberOfInversions(permutation);
                System.out.println(minNumberOfInversions);
            }

        } finally {
            if (br != null) {
                br.close();
            }
        }
    }


    @Test
    public void testInvert() {
        int[] permutation1 = new int[]{3, 1, 6, 5, -2, 4};
        GeneticDrift gd = new GeneticDrift();
        gd.invert(permutation1, 1, 4);
        Arrays.stream(permutation1).forEach(e -> System.out.print(e + " "));
        System.out.println();
        Assert.assertArrayEquals(permutation1, new int[]{3, 1, 2, -5, -6, 4});

        int[] permutation2 = new int[]{3, 1, 6, 5, -2, 4};
        gd.invert(permutation2, 0, 4);
        Arrays.stream(permutation2).forEach(e -> System.out.print(e + " "));
        System.out.println();
        Assert.assertArrayEquals(permutation2, new int[]{-5, -6, -1, -3, -2, 4});

    }

    @Test
    public void quizInvert() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(GeneticDrift.class.getClassLoader()
                    .getResourceAsStream("org/mg/catcoder/geneticdrift/input-level2.txt")));

            String line;
            while ((line = br.readLine()) != null) {
                String[] components = line.split(" ");
                int length = Integer.parseInt(components[0]);
                int[] permutation = new int[length];
                for (int i = 0; i < permutation.length; i++) {
                    permutation[i] = Integer.parseInt(components[i + 1]);
                }

                int i = Integer.parseInt(components[length + 2]);
                int j = Integer.parseInt(components[length + 4]);

                GeneticDrift gd = new GeneticDrift();
                gd.invert(permutation, i, j);
                Arrays.stream(permutation).forEach(e -> System.out.print(e + " "));
                System.out.println();
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    @Test
    public void testOrientedPairScore() {
        int[] permutation1 = new int[]{0, 3, 1, 6, 5, -2, 4, 7};
        GeneticDrift gd = new GeneticDrift();
        gd.invert(permutation1, 2, 5);
        Pair<Integer>[] pairs1 = gd.orientedPairs(permutation1);
        System.out.println(pairs1.length);
        Assert.assertEquals(2, pairs1.length);

        int[] permutation2 = new int[]{0, 3, 1, 6, 5, -2, 4, 7};
        gd.invert(permutation2, 1, 5);
        Pair<Integer>[] pairs2 = gd.orientedPairs(permutation2);
        System.out.println(pairs2.length);
        Assert.assertEquals(4, pairs2.length);
    }

    @Test
    public void quizOrientedPairScore() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(GeneticDrift.class.getClassLoader()
                    .getResourceAsStream("org/mg/catcoder/geneticdrift/input-level3.txt")));

            String line;
            while ((line = br.readLine()) != null) {
                String[] components = line.split(" ");
                int length = Integer.parseInt(components[0]);
                int[] permutation = new int[length];
                for (int i = 0; i < permutation.length; i++) {
                    permutation[i] = Integer.parseInt(components[i + 1]);
                }

                int i = Integer.parseInt(components[length + 2]);
                int j = Integer.parseInt(components[length + 4]);

                GeneticDrift gd = new GeneticDrift();
                gd.invert(permutation, i, j);
                Pair<Integer>[] pairs = gd.orientedPairs(permutation);
                System.out.println(pairs.length);
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }


    @Test
    public void testOrientedPairs() throws IOException {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(GeneticDrift.class.getClassLoader()
                    .getResourceAsStream("org/mg/catcoder/geneticdrift/input-level1.txt")));

            String line;
            while ((line = br.readLine()) != null) {
                String[] components = line.split(" ");
                int length = Integer.parseInt(components[0]);
                int[] permutation = new int[length];
                for (int i = 0; i < permutation.length; i++) {
                    permutation[i] = Integer.parseInt(components[i + 1]);
                }

                GeneticDrift gd = new GeneticDrift();
                Pair<Integer>[] orientedPairs = gd.orientedPairs(permutation);
                System.out.print(orientedPairs.length);
                Arrays.stream(orientedPairs).forEach(pair -> System.out.print(" " + permutation[pair.first] + " " + permutation[pair.second]));
                System.out.println();
            }

            GeneticDrift op = new GeneticDrift();
            int[] perm = new int[]{3, 1, 6, 5, -2, 4};
            Pair<Integer>[] orientedPairs = op.orientedPairs(perm);
            System.out.print(orientedPairs.length);
            Arrays.stream(orientedPairs).forEach(pair -> System.out.print(" " + perm[pair.first] + " " + perm[pair.second]));
            System.out.println();

        } finally {
            if (br != null) {
                br.close();
            }
        }
    }


    private class Pair<T> {
        T first;
        T second;

        public Pair(T first, T second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "first=" + first +
                    ", second=" + second +
                    '}';
        }
    }
}
