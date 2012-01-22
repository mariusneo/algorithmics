package topcoder.srm.div2.srm225;

import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class SignatureDecorator {
	
	
	public String applyDecoration(String name, String[] commands, String[] decorations){
		StringBuilder sb= new StringBuilder();
		sb.append(name);
		
		for (int index=0;index<commands.length;index++){
			if ("append".equals(commands[index])){
				sb.append(decorations[index]);
			}else if ("prepend".equals(commands[index])){
				sb.insert(0, decorations[index]);
			}else if ("surround".equals(commands[index])){
				sb.insert(0, decorations[index]);
				sb.append(new StringBuffer(decorations[index]).reverse());
			}
		}
		
		return sb.toString();
	}
	
	@Test
	public void testBob(){
		String name = "Bob";
		String[] commands = {"surround", "append", "prepend"};
		String[] decorations = {"-=", "(", ")"};
		
		String expectedName = ")-=Bob=-(";
		assertThat(applyDecoration(name, commands, decorations), is(expectedName));
	}
	
	@Test
	public void testSuperMan(){
		String name = "Super_Man_01";
		String[] commands = {};
		String[] decorations = {};
		
		String expectedName = "Super_Man_01";
		assertThat(applyDecoration(name, commands, decorations), is(expectedName));
	}
	
	@Test
	public void testComplex(){
		String name = "4BcD3FgHIjklMN0pqrS7uVWxYZ_";
		String[] commands = {"append", "prepend", "surround"};
		String[] decorations = {"`{[(", ")]}'", ",.;<>?:|-=_+!@#$%^&*~"};
		
		String expectedName = ",.;<>?:|-=_+!@#$%^&*~)]}'4BcD3FgHIjklMN0pqrS7uVWxYZ_`{[(~*&^%$#@!+_=-|:?><;.,";
		assertThat(applyDecoration(name, commands, decorations), is(expectedName));
	}
	
	
	@Test
	public void testSurround(){
		String name = "RacEcaR";
		String[] commands = {"surround"};
		String[] decorations = {"([{/"};
		
		String expectedName = "([{/RacEcaR/{[(";
		assertThat(applyDecoration(name, commands, decorations), is(expectedName));
	}
	
}
