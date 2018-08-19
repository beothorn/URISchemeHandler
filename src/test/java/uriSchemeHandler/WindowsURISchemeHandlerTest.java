package uriSchemeHandler;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

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
	
	
	
	@Test
	public void testRegDotExeProcessingWithRegSzExpanded(){
		final String regQueryOutput = "! REG.EXE VERSION 3.0\n\n" + 
				"HKEY_CURRENT_USER\\mailto\\shell\\open\\command\n" +
				"    <NO NAME>   REG_EXPAND_SZ   \"%ProgramFiles%\\Outlook Express\\msimn.exe\" /mailurl:%1";
		final String command = WindowsURISchemeHandler.getCommandFor(regQueryOutput, "foo@example.com");
		assertEquals("\"%ProgramFiles%\\Outlook Express\\msimn.exe\" /mailurl:foo@example.com", command);
	}
	
	@Test
	public void testRegDotExeProcessingWithDifferentParameterToken(){
		
		final String regQueryOutput = "HKEY_CURRENT_USER\\mailto\\shell\\open\\command\n" +
		"(Default) REG_SZ \"C:\\Program Files (86)\\Vuze\\Azureus.exe\" \"%l\"";
		final String command = WindowsURISchemeHandler.getCommandFor(regQueryOutput, "foo@example.com");
		assertEquals("\"C:\\Program Files (86)\\Vuze\\Azureus.exe\" \"foo@example.com\"", command);
	}
	
	@Test
	public void testRegDotExeProcessing(){
		
		final String regQueryOutput = "\n" + 
				"! REG.EXE VERSION 3.0\n" + 
				"\n" + 
				"HKEY_CURRENT_USER\\magnet\\shell\\open\\command\n" +
				"    <NO NAME>   REG_SZ  \"C:\\Program Files\\Vuze\\Azureus.exe\" \"%1\"\n" + 
				"";
		final String command = WindowsURISchemeHandler.getCommandFor(regQueryOutput, "magnet:foo");
		assertEquals("\"C:\\Program Files\\Vuze\\Azureus.exe\" \"magnet:foo\"", command);
	}
	

}
