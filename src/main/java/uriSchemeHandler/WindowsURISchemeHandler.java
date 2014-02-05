package uriSchemeHandler;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

public class WindowsURISchemeHandler implements RealURISchemeHandler {

	private static final String REGSTRY_TYPE_SZ = "REG_SZ";
	private static final String REGSTRY_TYPE_SZ_EXPANDED = "REG_EXPAND_SZ";

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
		final String uriAsString = uri.toString();
		
		return getCommandFor(result, uriAsString);
	}

	public static String getCommandFor(final String regDotExeOutput, final String uriAsString) {
		int valueTypeIndex = regDotExeOutput.indexOf(REGSTRY_TYPE_SZ);
		String regSZ = REGSTRY_TYPE_SZ;
		if (valueTypeIndex == -1){
			valueTypeIndex = regDotExeOutput.indexOf(REGSTRY_TYPE_SZ_EXPANDED);
			regSZ = REGSTRY_TYPE_SZ_EXPANDED;
		}
		if (valueTypeIndex == -1){
			throw new RuntimeException(REGSTRY_TYPE_SZ+" or "+REGSTRY_TYPE_SZ_EXPANDED+" not found.");
		}

		final String resultExecutable = regDotExeOutput.substring(valueTypeIndex + regSZ.length()).trim();
		
		final String finalCommand =  resultExecutable.replaceAll("%[0-9lL]", uriAsString);
		return finalCommand;
	}

	public static String[] commandToStringArray(final String command){
		final ArrayList<String> arrayList = new ArrayList<String>();
		String lastToken = "";
		boolean ignoreChar = false;
		for (final char c : command.toCharArray()) {
			if(c == '"' && !ignoreChar){
				if(!lastToken.trim().isEmpty())
					arrayList.add(lastToken);
				lastToken = "";
				continue;
			}
			lastToken += c;
			if(ignoreChar){
				ignoreChar = false;
			}
			if(c == '\\'){
				ignoreChar = true;
			}
			
		}
		return arrayList.toArray(new String[0]);
	}
	
	public void open(final URI uri) throws IOException {
		final String commandForUri = getCommandForUrl(uri);
		Runtime.getRuntime().exec(commandToStringArray(commandForUri));
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
