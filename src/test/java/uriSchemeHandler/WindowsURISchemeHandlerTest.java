package uriSchemeHandler;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class WindowsURISchemeHandlerTest{

	@Test
	public void testCommandToArray(){
		final String command = "\"foo bar\"  \"baz\"";
		final String[] expecteds = new String[]{"foo bar","baz"};
		final String[] commandToStringArray = WindowsURISchemeHandler.commandToStringArray(command);
		assertArrayEquals(expecteds, commandToStringArray);
	}
	
	@Test
	public void testCommandWithScapedCharToArray(){
		final String command = "\"foo \\\"bar\\\"\" \"baz\"";
		final String[] expecteds = new String[]{"foo \\\"bar\\\"","baz"};
		final String[] commandToStringArray = WindowsURISchemeHandler.commandToStringArray(command);
		assertArrayEquals(expecteds, commandToStringArray);
	}
}
