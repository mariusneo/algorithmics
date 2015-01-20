package topcoder.srm.div2.srm151;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Problem Statement
 *     
 * A prefix code is a set of words in which no word is a prefix of another word in the set. A word v is said to be a prefix of a word w if w starts with v.  An important property of prefix codes is that they are uniquely decodable. Prefix codes are commonly used - telephone numbers are an everyday example (as you probably don't want a stranger to pick up the phone call you make just because his number is a prefix of the number you intend to dial). Prefix codes are also very popular in computer science, the Huffman code used for data compression being a famous example.  Given a String[] words, return the String "Yes" if that set of words is a prefix code or return the String "No, i" if it is not, where i is replaced by the lowest 0-based index of a String in words that is a prefix of another String in words. (That index should have no extra leading zeros.)
 * Definition
 *     
 * Class:
 * PrefixCode
 * Method:
 * isOne
 * Parameters:
 * String[]
 * Returns:
 * String
 * Method signature:
 * String isOne(String[] words)
 * (be sure your method is public)
 * Limits
 *     
 * Time limit (s):
 * 2.000
 * Memory limit (MB):
 * 64
 * Notes
 * -
 * Letters are case sensitive (e.g. "No" is not a prefix of "not").
 * -
 * Do not forget the single space between the comma and i in "No, i"
 * Constraints
 * -
 * words contains between 1 and 50 elements, inclusive.
 * -
 * Each element of words contains between 1 and 50 characters, inclusive.
 * -
 * Each element of words consists only of characters '0'-'9', 'A'-'Z' and 'a'-'z', inclusive.
 * -
 * No two elements of words are equal (as the input represents a set).
 * Examples
 * 0)
 * <p/>
 *     
 * {"trivial"}
 * Returns: "Yes"
 * As there is only one word, no word can be the prefix of another, so this is a trivial example of a prefix code.
 * 1)
 * <p/>
 *     
 * {"10001", "011", "100", "001", "10"}
 * Returns: "No, 2"
 * "100" (at index 2) and "10" (at index 4) are both a prefix of "10001" and "10" is also a prefix of "100", therefore it is no prefix code. "100" is the prefix with the lowest index.
 * 2)
 * <p/>
 *     
 * {"no", "nosy", "neighbors", "needed"}
 * Returns: "No, 0"
 * <p/>
 * 3)
 * <p/>
 *     
 * {"1010", "11", "100", "0", "1011"}
 * Returns: "Yes"
 * <p/>
 * 4)
 * <p/>
 *     
 * {"No", "not"}
 * Returns: "Yes"
 * <p/>
 * This problem statement is the exclusive and proprietary property of TopCoder, Inc. Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited. (c)2003, TopCoder, Inc. All rights reserved.
 */
public class PrefixCode {
    private PrefixCode pc;

    public String isOne(String[] words) {
        for (int i = 1; i < words.length; i++) {
            for (int j = 0; j < i; j++) {
                if (words[j].startsWith(words[i])) {
                    return "No, " + i;
                }
                if (words[i].startsWith(words[j])) {
                    return "No, " + j;
                }
            }
        }
        return "Yes";
    }

    @Before
    public void setup() {
        pc = new PrefixCode();
    }

    @Test
    public void test0() {
        String answer = pc.isOne(new String[]{"trivial"});
        Assert.assertEquals("Yes", answer);
    }

    @Test
    public void test1() {
        String answer = pc.isOne(new String[]{"10001", "011", "100", "001", "10"});
        Assert.assertEquals("No, 2", answer);
    }

}