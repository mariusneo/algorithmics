package topcoder.srm.div2.srm298;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * Own comments
 * </p>
 * <p>
 * L: \-/| R: \|/- S F : flip
 * </p>
 * 
 * <p>
 * Problem Statement
 * </p>
 * <p>
 * In this problem, you will simulate a program that controls the actions of a
 * progress indicator. The indicator is a single bar character in the middle of
 * the screen with one of 4 states: '|', '-', '\', and '/'. The program is given
 * as a sequence of instructions in the form: <instr> <secs> where <instr>
 * represents one of 4 possible actions, and <secs> is the action's duration in
 * seconds. The action is performed once each second. The 4 possible actions
 * are:: 'L': Spin left. If the bar is in state '|', it goes to '\'. State '\'
 * goes to '-', '-' goes to '/', and '/' goes to '|'. 'R': Spin right. This is
 * the exact opposite of 'L': '\' goes to '|', '|' goes to '/', '/' goes to '-',
 * and '-' goes to '\'. 'S': Stay. The bar remains in its current state. 'F':
 * Flip. The bar is rotated 90 degrees: '\' becomes '/', '/' becomes '\', '-'
 * becomes '|', and '|' becomes '-'. So, the sequence "F03L02" and the starting
 * state of '-' leads to the following sequence: "-|-|\-". You are given a
 * program and a startState. Return a String containing the sequence of states
 * produced by the program. The ith character of the String is the state of the
 * progress indicator after i seconds. At time 0, the indicator is in its
 * initial state, so the first character of the return value is always
 * startState. Definition      Class: IndicatorMotion Method: getMotion
 * Parameters: String, char Returns: String Method signature: String
 * getMotion(String program, char startState) (be sure your method is public)
 *     
 * </p>
 * 
 * Notes - In the examples the character '\' appears as '\\' because of the
 * C++/Java syntax for escaping characters. Constraints - startState will be
 * '|', '-', '\' or '/'. - program will contain exactly 3*k characters, where k
 * is an integer between 0 and 10, inclusive. - For each k, the (3*k)-th
 * character in program will be one of 'L', 'R', 'F' or 'S'. - For each k, the
 * (3*k+1)-th and (3*k+2)-th characters of program will be digits ('0'-'9').
 * Examples 0)
 * 
 *      "F03L02R03S02F04" '-' Returns: "-|-|\\-\\|///\\/\\/" This leads to the
 * following sequence of states (below each state is the action performed during
 * that second): -|-|\-\|///\/\/ .FFFLLRRRSSFFFF 1)
 * 
 *      "F10R01F10" '/' Returns: "/\\/\\/\\/\\/\\/-|-|-|-|-|-"
 * 
 * 2)
 * 
 *      "" '/' Returns: "/" Watch out for empty programs! 3)
 * 
 *      "F00R00L00S00" '\\' Returns: "\\" And "empty" programs. This problem
 * statement is the exclusive and proprietary property of TopCoder, Inc. Any
 * unauthorized use or reproduction of this information without the prior
 * written consent of TopCoder, Inc. is strictly prohibited. (c)2003, TopCoder,
 * Inc. All rights reserved.
 * 
 * @author mga
 * 
 */
public class IndicatorMotion {

	private static final char[] STATES = new char[] { '\\', '|', '/', '-'};

	public String getMotion(String program, char startState) {
		if (program.length() % 3 != 0) {
			throw new IllegalArgumentException("program argument '" + program
					+ "' is not valid");
		}

		int currentProgramIndex = 0;
		char[] programChars = program.toCharArray();

		int currentStateIndex = -1;
		for (int index = 0; index < STATES.length; index++) {
			char state = STATES[index];
			if (state == startState) {
				currentStateIndex = index;
				break;
			}
		}

		if (currentStateIndex == -1) {
			throw new IllegalArgumentException("The charState '" + startState
					+ "' is not a legal state.");
		}

		StringBuffer solution = new StringBuffer();
		solution.append(STATES[currentStateIndex]);
		while (currentProgramIndex < programChars.length) {
			char action = programChars[currentProgramIndex];
			int digit = Integer.parseInt(new String(new char[] {
					programChars[currentProgramIndex + 1],
					programChars[currentProgramIndex + 2] }));
			currentProgramIndex += 3;

			for (int i = 0; i < digit; i++) {
				if (action == 'L') {
					currentStateIndex = (STATES.length + currentStateIndex - 1)
							% STATES.length;
					solution.append(STATES[currentStateIndex]);
				} else if (action == 'R') {
					currentStateIndex = (currentStateIndex + 1)
							% STATES.length;
					solution.append(STATES[currentStateIndex]);
				} else if (action == 'F') {
					currentStateIndex = (currentStateIndex + 2)% STATES.length;
					solution.append(STATES[currentStateIndex]);
				} else if (action == 'S'){
					solution.append(STATES[currentStateIndex]);
				}
			}

		}

		return solution.toString();
	}

	public static void main(String[] args) {
		IndicatorMotion im = new IndicatorMotion();
		String result = im.getMotion("F03L02R03S02F04", '-');
		System.out.println(result);
		String expected = "-|-|\\-\\|///\\/\\/";
		System.out.println(expected);
		System.out.println();
		result= im.getMotion("F10R01F10",'/');
		expected = "/\\/\\/\\/\\/\\/-|-|-|-|-|-";
		System.out.println(result);
		System.out.println(expected);
	
		result = im.getMotion("F00R00L00S00", '\\');
		expected="\\";
		System.out.println(result);
		System.out.println(expected);
	}
}
