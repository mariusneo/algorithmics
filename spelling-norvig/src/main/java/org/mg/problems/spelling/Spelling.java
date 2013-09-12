package org.mg.problems.spelling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Type in a search like [speling] and Google comes back in 0.1 seconds or so
 * with Did you mean: spelling. Algorithm described by P. Norvig here :
 * http://norvig.com/spell-correct.html
 * 
 * This is a java implementation of this algorithm.
 * 
 * @author mga
 * 
 */
public class Spelling {

	private final Map<String, Integer> words = new HashMap<String, Integer>();

	public Spelling(String filename) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));

		Pattern pattern = Pattern.compile("\\w+");
		String line;
		while ((line = in.readLine()) != null) {
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				String word = "";
				words.put(word = matcher.group(),
						words.containsKey(word) ? words.get(word) + 1 : 1);
			}
		}

		in.close();
	}

	private List<String> edits(String word) {
		List<String> edits = new ArrayList<String>();
		for (int i = 0; i < word.length(); i++) {
			// deletes
			edits.add(word.substring(0, i) + word.substring(i + 1));
		}
		for (int i = 0; i < word.length() - 1; i++) {
			// transposes
			edits.add(word.substring(0, i) + word.substring(i + 1, i + 2)
					+ word.substring(i, i + 1) + word.substring(i + 2));
		}
		for (int i=0;i<word.length();i++){
			for (char c= 'a';c<='z';c++){
				// replaces
				edits.add(word.substring(0,i) + c + word.substring(i+1));
			}
		}
		for (int i=0;i<=word.length();i++){
			for (char c= 'a';c<='z';c++){
				// additions
				edits.add(word.substring(0,i) + c + word.substring(i));
			}
		}
		

		return edits;
	}

	public final String correct(String word) {
		if (words.containsKey(word)) {
			return word;
		}

		List<String> edits = edits(word);
		Map<Integer, String> candidates = new HashMap<Integer, String>();
		for (String edit : edits) {
			if (words.containsKey(edit)) {
				candidates.put(words.get(edit), edit);
			}
		}
		if (candidates.size() > 0) {
			// return the candidate which is being used at most in the
			// dictionary
			return candidates.get(Collections.max(candidates.keySet()));
		}

		// obtain the candidates that are obtained with 2 edits
		for (String edit : edits) {
			for (String edit2 : edits(edit)) {
				if (words.containsKey(edit2)) {
					candidates.put(words.get(edit2), edit2);
				}
			}
		}

		if (candidates.size() > 0) {
			// return the candidate which is being used at most in the
			// dictionary
			return candidates.get(Collections.max(candidates.keySet()));
		}

		return word;
	}

	public static void main(String[] args) throws IOException {
		Spelling spelling = new Spelling("big.txt");

		System.out.println(spelling.correct("somthing"));
		System.out.println(spelling.correct("somteihng"));
		
		System.out.println(spelling.correct("thay"));
	}
}
