package topcoder.srm.div2.srm225;

import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ParameterSubstitution {
	public String processParams(String code, String[] params) {
		StringBuffer solution = new StringBuffer();

		char[] codeChars = code.toCharArray();
		boolean dollarSignActive = false;

		Integer paramsCount = params.length;
		int index = 0;
		while (index < code.length()) {
			if (codeChars[index] == '$') {
				dollarSignActive = true;
			} else {
				solution.append(codeChars[index]);
			}
			index++;

			if (dollarSignActive && index < codeChars.length) {
				dollarSignActive = false;
				if ((Character.isDigit(codeChars[index]) || codeChars[index] == '-')
						&& codeChars[index] != '0') {
					int minusIndex = -1;
					if (codeChars[index] == '-') {
						minusIndex = index;
						if (index < codeChars.length - 1) {
							index++;
						}
					}
					StringBuffer sbParameterIndex = new StringBuffer();
					Integer parameterIndex = null;
					if (Character.isDigit(codeChars[index]) && codeChars[index] != '0') {
						do {
							sbParameterIndex.append(codeChars[index]);
							Integer tempParameterIndex = Math.abs(new Integer(
									sbParameterIndex.toString()));
							if (tempParameterIndex > paramsCount) {
								break;
							} else {
								index++;
								parameterIndex = tempParameterIndex;
							}
						} while (index < codeChars.length
								&& Character.isDigit(codeChars[index]));
					}
					if (parameterIndex != null) {
						solution.append(params[parameterIndex - 1]);
					} else {
						if (minusIndex != -1) {
							solution.append(codeChars[minusIndex]);
						}
					}
				} else {
					// append previous character. i.e.: $
					solution.append(codeChars[index - 1]);
				}
			}

		}

		if (dollarSignActive) {
			solution.append(codeChars[index - 1]);
		}
		return solution.toString();
	}

	@Test
	public void testIfInstruction() {
		String code = "if ($1 == $2) $3;";
		String[] params = { "12", "12", "doWhatIWant()" };
		String expected = "if (12 == 12) doWhatIWant();";

		assertThat(processParams(code, params), is(expected));
	}

	@Test
	public void testParameters() {
		String code = "12342123$13231232$2123211242$a$dlkj$";
		String[] params = { "$2", "$1" };
		String expected = "12342123$23231232$1123211242$a$dlkj$";
		assertThat(processParams(code, params), is(expected));
	}

	@Test
	public void testParameters2() {
		String code = "{[(+.*-,/\\:;<=>?@)]}_`~|$$1";
		String[] params = { "1{[(+.*-,/\\:;<=>?@)]}_`~|" };
		String expected = "{[(+.*-,/\\:;<=>?@)]}_`~|$1{[(+.*-,/\\:;<=>?@)]}_`~|";
		assertThat(processParams(code, params), is(expected));
	}

	@Test
	public void testParameters3() {

	String code = "$01";
	String[] params = {"abc"};
	
	String expected = "$01";
	assertThat(processParams(code, params), is(expected));
	}
}
