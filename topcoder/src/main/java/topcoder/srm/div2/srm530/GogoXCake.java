package topcoder.srm.div2.srm530;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GogoXCake {
	private static final String YES = "\"YES\"";
	private static final String NO = "\"NO\"";

	private static final String PIECE_PRESENT = "X";
	private static final String PIECE_NOT_PRESENT = ".";

	private static final String CUTTER_USED = ".";
	private static final String CUTTER_IGNORED = "X";

	public static String solve(String[] cake, String[] cutter) {
		for (int cakeLine = 0; cakeLine < cake.length; cakeLine++) {
			for (int cakeColumn = 0; cakeColumn < cake[cakeLine].length(); cakeColumn++) {
				String cakePiece = cake[cakeLine].substring(cakeColumn, cakeColumn + 1);

				if (cakePiece.equals(PIECE_NOT_PRESENT)) {
					// see if the cutter can be fit in
					if ((cakeColumn + cutter[0].length() <= cake[cakeLine].length())
							&& (cakeLine + cutter.length <= cake.length)) {
						for (int cutterLine = 0; cutterLine < cutter.length; cutterLine++) {
							for (int cutterColumn = 0; cutterColumn < cutter[cutterLine].length(); cutterColumn++) {
								String currentCakePiece = cake[cakeLine + cutterLine].substring(cakeColumn
										+ cutterColumn, cakeColumn + cutterColumn + 1);
								String currentCutterPiece = cutter[cutterLine]
										.substring(cutterColumn, cutterColumn + 1);
								if (currentCakePiece.equals(PIECE_NOT_PRESENT)) {
									if (currentCutterPiece.equals(CUTTER_USED)) {
										cake[cakeLine+ cutterLine] = cake[cakeLine + cutterLine].substring(0, cakeColumn
												+ cutterColumn)
												+ PIECE_PRESENT
												+ cake[cakeLine + cutterLine].substring(cakeColumn + cutterColumn + 1);
									}
								} else {
									if (currentCutterPiece.equals(CUTTER_USED)) {
										return NO;
									}
								}
							}
						}
					}
				}

			}
		}
		
		for (int cakeLine = 0; cakeLine < cake.length; cakeLine++) {
			for (int cakeColumn = 0; cakeColumn < cake[cakeLine].length(); cakeColumn++) {
				String cakePiece = cake[cakeLine].substring(cakeColumn, cakeColumn + 1);

				if (cakePiece.equals(PIECE_NOT_PRESENT)) {
					return NO;
				}
			}
		}

		return YES;
	}

	@Test
	public void test1() {
		String[] cake = { "X.X", "...", "...", "X.X" };
		String[] cutter = { ".X", "..", "X." };

		assertThat(solve(cake, cutter), is(YES));
	}
	
	@Test
	public void test2(){
		String[] cake = {"..XX","...X","X...","XX.."};
		String[] cutter ={"..",".."};
		
		assertThat(solve(cake, cutter), is(NO));
	}
	
	@Test
	public void test3(){
		String[] cake = {"...X..."};
		String[] cutter= {"..."};
	
		assertThat(solve(cake, cutter), is(YES));
	}
	
	@Test
	public void test4(){
		String[] cake = {"XXXXXXX"
			,"X.....X"
			,"X.....X"
			,"X.....X"
			,"XXXXXXX"};
		
		String[] cutter = {".X."
				,"XXX"
				,".X."};
		
		
		assertThat(solve(cake, cutter), is(NO));	
			
	}
}

