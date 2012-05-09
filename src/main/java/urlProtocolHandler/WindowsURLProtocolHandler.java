package urlProtocolHandler;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class WindowsURLProtocolHandler implements RealURLProtocolHandler {

	private static final String REGSTRY_TYPE_SZ = "REG_SZ";

	public static String getCommandForUrl(final String url) throws IOException {		
		final String protocol = StringUtils.substringBefore(url, ":");
		final String commandString = "reg query HKEY_CLASSES_ROOT\\" + protocol + "\\shell\\open\\command /ve";
		final Command command = new Command(commandString);
		final CommandResult commandResult = command.run();
		
		if(commandResult.anyErrorOccured()){
			throw new RuntimeException(commandResult.getError());
		}
		
		final String result = commandResult.getResult();
		
		final int valueTypeIndex = result.indexOf(REGSTRY_TYPE_SZ);

		if (valueTypeIndex == -1){
			throw new RuntimeException(REGSTRY_TYPE_SZ+" not found.");
		}

		final String resultExecutable = result.substring(valueTypeIndex + REGSTRY_TYPE_SZ.length()).trim();
		return resultExecutable.replace("%1", url);
	}

	public void open(final String url) throws IOException {
		final String commandForUrl = getCommandForUrl(url);
		Runtime.getRuntime().exec(commandForUrl);
	}

	public void register(final String protocol, final String applicationPath) throws IOException {
		final String escapedApplicationPath = applicationPath.replaceAll("\\\\", "\\\\\\\\");
		final String regFile = 
				"Windows Registry Editor Version 5.00\r\n" + 
				"\r\n" + 
				"[HKEY_CLASSES_ROOT\\"+protocol+"]\r\n" + 
				"@=\""+protocol+" URI\"\r\n" + 
				"\"URL Protocol\"=\"\"\r\n" + 
				"\"Content Type\"=\"application/x-"+protocol+"\"\r\n" + 
				"\r\n" + 
				"[HKEY_CLASSES_ROOT\\"+protocol+"\\shell]\r\n" + 
				"@=\"open\"\r\n" + 
				"\r\n" + 
				"[HKEY_CLASSES_ROOT\\"+protocol+"\\shell\\open]\r\n" + 
				"\r\n" + 
				"[HKEY_CLASSES_ROOT\\"+protocol+"\\shell\\open\\command]\r\n" + 
				"@=\"\\\""+escapedApplicationPath+"\\\" \\\"%1\\\"\"";
		
		File tempFile;
		try {
			tempFile = File.createTempFile("URLPROTOCOLHANDLERCREATION", ".reg");
			FileUtils.writeStringToFile(tempFile, regFile);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		
		
		final String commandString = "regedit /s "+tempFile.getAbsolutePath();
		final Command command = new Command(commandString);
		command.run();
	}

}
