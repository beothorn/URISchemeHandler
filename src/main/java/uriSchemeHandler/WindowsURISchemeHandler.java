package uriSchemeHandler;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.io.FileUtils;

public class WindowsURISchemeHandler implements RealURISchemeHandler {

	private static final String REGSTRY_TYPE_SZ = "REG_SZ";

	public static String getCommandForUrl(final URI uri) throws IOException {		
		final String schemeName = uri.getScheme();
		final String sysdir = System.getenv("WINDIR") + "\\system32\\reg";
		final String[] commandString = new String[]{sysdir,"query","HKEY_CLASSES_ROOT\\" + schemeName + "\\shell\\open\\command","/ve"};
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
		return resultExecutable.replace("%1", uri.toString());
	}

	public void open(final URI uri) throws IOException {
		final String commandForUri = getCommandForUrl(uri);
		Runtime.getRuntime().exec(commandForUri);
	}

	public void register(final String schemeName, final String applicationPath) throws IOException {
		final String escapedApplicationPath = applicationPath.replaceAll("\\\\", "\\\\\\\\");
		final String regFile = 
				"Windows Registry Editor Version 5.00\r\n" + 
				"\r\n" + 
				"[HKEY_CLASSES_ROOT\\"+schemeName+"]\r\n" + 
				"@=\""+schemeName+" URI\"\r\n" + 
				"\"URL Protocol\"=\"\"\r\n" + 
				"\"Content Type\"=\"application/x-"+schemeName+"\"\r\n" + 
				"\r\n" + 
				"[HKEY_CLASSES_ROOT\\"+schemeName+"\\shell]\r\n" + 
				"@=\"open\"\r\n" + 
				"\r\n" + 
				"[HKEY_CLASSES_ROOT\\"+schemeName+"\\shell\\open]\r\n" + 
				"\r\n" + 
				"[HKEY_CLASSES_ROOT\\"+schemeName+"\\shell\\open\\command]\r\n" + 
				"@=\"\\\""+escapedApplicationPath+"\\\" \\\"%1\\\"\"";
		
		File tempFile;
		try {
			tempFile = File.createTempFile("URLPROTOCOLHANDLERCREATION", ".reg");
			FileUtils.writeStringToFile(tempFile, regFile);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		
		
		final String[] commandStrings = new String[]{"regedit","/s",tempFile.getAbsolutePath()};
		final Command command = new Command(commandStrings);
		command.run();
	}

}
