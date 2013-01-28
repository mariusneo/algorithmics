package com.facebook.hackercup2013;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * When John was a little kid he didn't have much to do. There was no internet,
 * no Facebook, and no programs to hack on. So he did the only thing he could...
 * he evaluated the beauty of strings in a quest to discover the most beautiful
 * string in the world.
 * 
 * Given a string s, little Johnny defined the beauty of the string as the sum
 * of the beauty of the letters in it.
 * 
 * The beauty of each letter is an integer between 1 and 26, inclusive, and no
 * two letters have the same beauty. Johnny doesn't care about whether letters
 * are uppercase or lowercase, so that doesn't affect the beauty of a letter.
 * (Uppercase 'F' is exactly as beautiful as lowercase 'f', for example.)
 * 
 * You're a student writing a report on the youth of this famous hacker. You
 * found the string that Johnny considered most beautiful. What is the maximum
 * possible beauty of this string?
 * 
 * Input The input file consists of a single integer m followed by m lines.
 * Output Your output should consist of, for each test case, a line containing
 * the string "Case #x: y" where x is the case number (with 1 being the first
 * case in the input file, 2 being the second, etc.) and y is the maximum beauty
 * for that test case. Constraints 5 ≤ m ≤ 50 2 ≤ length of s ≤ 500
 * 
 * Example input:
 * 
 * <pre>
 * 5
 * ABbCcc
 * Good luck in the Facebook Hacker Cup this year!
 * Ignore punctuation, please :)
 * Sometimes test cases are hard to make up.
 * So I just go consult Professor Dalves
 * </pre>
 * 
 * Example output:
 * 
 * <pre>
 * Case #1: 152
 * Case #2: 754
 * Case #3: 491
 * Case #4: 729
 * Case #5: 646
 * </pre>
 * 
 * @author marius
 * 
 */
public class BeautifulStrings {
	public static class Pair {
		char letter;
		int count;
	}

	public static void main(String[] args) {
		int m = 5;
		String[] lineArray = { "ABbCcc", "Good luck in the Facebook Hacker Cup this year!",
				"Ignore punctuation, please :)", "Sometimes test cases are hard to make up.",
				"So I just go consult Professor Dalves" };

		Map<Character, Pair> letterOccurrences = new LinkedHashMap<Character, Pair>();
		for (String line : lineArray) {
			for (char ch : line.toCharArray()) {
				if (Character.isLetter(ch)) {
					char lowercaseChar = Character.toLowerCase(ch);
					// int letterIndex = Character.toLowerCase(ch) - 'a';
					if (letterOccurrences.containsKey(lowercaseChar)) {
						Pair pair = letterOccurrences.get(lowercaseChar);
						pair.count++;
					} else {
						Pair pair = new Pair();
						pair.letter = lowercaseChar;
						pair.count = 1;
						letterOccurrences.put(lowercaseChar, pair);
					}
				}
			}
		}

		List<Pair> pairs = new ArrayList<Pair>(letterOccurrences.values());
		Collections.sort(pairs, new Comparator<Pair>() {
			public int compare(Pair o1, Pair o2) {
				return -Integer.compare(o1.count, o2.count);
			}
		});

		Map<Character, Integer> letterBeautyMap = new HashMap<Character, Integer>();
		int beauty = 26;
		for (Pair pair : pairs) {
			letterBeautyMap.put(pair.letter, beauty);
			beauty--;
		}

		int lineIndex = 0;
		for (String line : lineArray) {
			lineIndex++;
			int lineBeauty = 0;
			for (char ch : line.toCharArray()) {
				if (Character.isLetter(ch)) {
					char lowercaseChar = Character.toLowerCase(ch);
					lineBeauty += letterBeautyMap.get(lowercaseChar);
				}
			}

			System.out.println("Case #" + lineIndex + ": " + lineBeauty);
		}
	}
}
