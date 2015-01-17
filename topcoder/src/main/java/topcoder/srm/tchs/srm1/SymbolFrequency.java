package topcoder.srm.tchs.srm1;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertEquals;

/**
 * Problem Statement
 *     
 * In written languages, some symbols may appear more often than others. Expected frequency tables have been defined for many languages.
 * For each symbol in a language, a frequency table will contain its expected percentage in a typical passage written in that language.
 * For example, if the symbol "a" has an expected percentage of 5, then 5% of the letters in a typical passage will be "a".
 * If a passage contains 350 letters, then 'a' has an expected count of 17.5 for that passage (17.5 = 350 * 5%). Please note that
 * the expected count can be a non-integer value.
 * The deviation of a text with respect to a language frequency table can be computed in the following manner.
 * For each letter ('a'-'z') determine the difference between the expected count and the actual count in the text.
 * The deviation is the sum of the squares of these differences. Blank spaces (' ') and line breaks (each element of text is a line)
 * are ignored when calculating percentages.
 * Each frequency table will be described as a concatenation of up to 16 strings of the form "ANN", where A is a
 * lowercase letter ('a'-'z') and NN its expected frequency as a two-digit percentage between "00" (meaning 0%)
 * and "99" (meaning 99%), inclusive. Any letter not appearing in a table is not expected to appear in a typical passage (0%).
 * You are given a String[] frequencies of frequency tables of different languages.
 * Return the lowest deviation the given text has with respect to the frequency tables.
 * Definition
 *     
 * Class:
 * SymbolFrequency
 * Method:
 * language
 * Parameters:
 * String[], String[]
 * Returns:
 * double
 * Method signature:
 * double language(String[] frequencies, String[] text)
 * (be sure your method is public)
 * Limits
 *     
 * Time limit (s):
 * 2.000
 * Memory limit (MB):
 * 64
 * Notes
 * -
 * The returned value must be accurate to within a relative or absolute value of 1E-9.
 * Constraints
 * -
 * frequencies will contain between 1 and 10 elements, inclusive.
 * -
 * Each element of frequencies will be formatted as described in the statement.
 * -
 * Each element of frequencies will contain between 6 and 48 characters, inclusive.
 * -
 * No letter will appear twice in the same element of frequencies.
 * -
 * The sum of the percentages in each element of frequencies will be equal to 100.
 * -
 * text will contain between 1 and 10 elements, inclusive.
 * -
 * Each element of text will contain between 1 and 50 characters, inclusive.
 * -
 * Each element of text will contain only lowercase letters ('a'-'z') and spaces (' ').
 * -
 * text will have at least one non-space character.
 * Examples
 * 0)
 * <p/>
 *     
 * {"a30b30c40","a20b40c40"}
 * {"aa bbbb cccc"}
 * Returns: 0.0
 * The first table indicates that 30% of the letters are expected to be 'a', 30% to be 'b', and 40% to be 'c'.
 * The second table indicates that 20% are expected to be 'a', 40% to be 'b', and 40% to be 'c'.
 * We consider the text to have length 10, as blank spaces are ignored. With respect to the first table,
 * there are 2 'a' where 3 were expected (a difference of 1), one more 'b' than expected (again a difference of 1)
 * and as many 'c' as expected. The sum of the squares of those numbers gives a deviation of 2.0.
 * As for the second table, the text matches expected counts exactly, so its deviation with respect to that language is 0.0.
 * 1)
 * <p/>
 *     
 * {"a30b30c40","a20b40c40"}
 * {"aaa bbbb ccc"}
 * Returns: 2.0
 * Here we use the same tables as in the previous example, but with a different text. The counts for 'b' and 'c' each differ by 1 from the expected counts in the first table, and the counts for 'a' and 'c' each differ by 1 from the expected counts in the second table. The text has a deviation of 2.0 with respect to both tables.
 * 2)
 * <p/>
 *     
 * {"a10b10c10d10e10f50"}
 * {"abcde g"}
 * Returns: 10.8
 * Here, each of the letters 'a' through 'e' is expected to make up 10% of the letters (0.6 letters).
 * Each of those letters actually appears once, so the difference is 0.4, which becomes 0.16 when squared.
 * 50% of the letters (3 letters) are expected to be 'f', but 'f' does not appear at all.
 * The square of this difference is 9.0. No 'g's are expected to appear, but there is one in the text. This adds 1.0 to the deviation.
 * The final deviation for this table is: 0.16+0.16+0.16+0.16+0.16+9.0+1.0=10.8.
 * 3)
 * <p/>
 *     
 * {"a09b01c03d05e20g01h01i08l06n08o06r07s09t08u07x01"
 * ,"a14b02c05d06e15g01h01i07l05n07o10r08s09t05u04x01"}
 * {"this text is in english"
 * ,"the letter counts should be close to"
 * ,"that in the table"}
 * Returns: 130.6578
 * These two frequency tables correspond (roughly) to the frequencies found in the English and Spanish languages, respectively. The English passage, as expected, has a lower deviation in the first table than in the second one.
 * 4)
 * <p/>
 *     
 * {"a09b01c03d05e20g01h01i08l06n08o06r07s09t08u07x01"
 * ,"a14b02c05d06e15g01h01i07l05n07o10r08s09t05u04x01"}
 * {"en esta es una oracion en castellano"
 * ,"las ocurrencias de cada letra"
 * ,"deberian ser cercanas a las dadas en la tabla"}
 * Returns: 114.9472
 * The same tables again, but with Spanish passage. This time the second table, which correspond to frequencies in Spanish, gives the lowest deviation.
 * 5)
 * <p/>
 *     
 * {"z99y01", "z99y01", "z99y01", "z99y01", "z99y01",
 * "z99y01", "z99y01", "z99y01", "z99y01", "z99y01"}
 * {"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
 * "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
 * "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
 * "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
 * "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
 * "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
 * "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
 * "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
 * "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
 * "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"}
 * Returns: 495050.0
 * <p/>
 * This problem statement is the exclusive and proprietary property of TopCoder, Inc. Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited. (c)2003, TopCoder, Inc. All rights reserved.
 */
public class SymbolFrequency {
    private SymbolFrequency sf;


    public double language(String[] frequencies, String[] texts) {
        List<Map<Character, Integer>> languageFrequencyLists = new LinkedList();

        for (int i = 0; i < frequencies.length; i++) {
            String frequency = frequencies[i];
            Map<Character, Integer> languageFrequencyMap = new HashMap();
            int index = 0;
            while (index < frequency.length()) {
                char c = frequency.charAt(index);
                int start = ++index;
                do {
                    index++;
                } while (index <frequency.length() && Character.isDigit(frequency.charAt(index)));

                int percent = Integer.parseInt(frequency.substring(start, index));
                languageFrequencyMap.put(c, percent);
            }
            languageFrequencyLists.add(languageFrequencyMap);
        }

        double minDeviation = Double.MAX_VALUE;
        int ignoredChars = 0;

        for (int i = 0;i<texts.length;i++){
            String text = texts[i];
            Map<Character, Integer> frequencyMap = new HashMap();
            for (int index = 0 ; index < text.length();index++){
                char c = text.charAt(index);
                if (Character.isLetter(c)) {
                    if (frequencyMap.containsKey(c)) {
                        frequencyMap.put(c, frequencyMap.get(c) + 1);
                    } else {
                        frequencyMap.put(c, 1);
                    }
                }else{
                    ignoredChars++;
                }
            }


            int textLettersCount = text.length() - ignoredChars;
            Set<Character> textKeys = frequencyMap.keySet();
            for (Map<Character,Integer> languageFrequency : languageFrequencyLists){
                double deviation = 0.0d;
                Set<Character> languageKeys = languageFrequency.keySet();
                Set<Character> commonKeys = new HashSet<Character>(textKeys);
                commonKeys.retainAll(languageKeys);
                Set<Character> onlyTextKeys = new HashSet<Character>(textKeys);
                onlyTextKeys.removeAll(commonKeys);
                Set<Character> onlyLanguageKeys = new HashSet<Character>(languageKeys);
                onlyLanguageKeys.removeAll(commonKeys);

                for (Character c : commonKeys){
                    double expectedCharacters = languageFrequency.get(c) * 1.0 * textLettersCount /100;
                    deviation += Math.pow( frequencyMap.get(c) - expectedCharacters, 2);
                }
                for (Character c : onlyTextKeys){
                    deviation += Math.pow(frequencyMap.get(c), 2);
                }
                for (Character c : onlyLanguageKeys){
                    deviation += Math.pow((languageFrequency.get(c) / 100.0) * textLettersCount, 2);
                }

                if (deviation < minDeviation){
                    minDeviation = deviation;
                }
            }

        }

        return minDeviation;
    }

    @Before
    public void setup(){
        sf = new SymbolFrequency();
    }

    @Test
    public void test1() {
        String[] frequencies = new String[]{"a30b30c40","a20b40c40"};
        String[] texts = new String[]{"aa bbbb cccc"};
        double minDeviation = sf.language(frequencies, texts);
        assertEquals(0.0, minDeviation);
    }

    @Test
    public void test2(){
        String[] frequencies = {"a10b10c10d10e10f50"};
        String[] texts = {"abcde g"};
        double minDeviation = sf.language(frequencies, texts);
        assertEquals(10.8, minDeviation);

    }
}
