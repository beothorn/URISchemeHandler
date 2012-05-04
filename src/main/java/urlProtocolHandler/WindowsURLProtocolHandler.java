package urlProtocolHandler;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class WindowsURLProtocolHandler implements RealURLProtocolHandler {

	private static final String REGSTRY_TYPE_SZ = "REG_SZ";

	public static String getCommandForUrl(final String url) {
		try {
			if(!url.contains(":")){
				throw new RuntimeException("Url protocol is invalid, it must have this format 'protocol:arguments'.");
			}
			
			String protocol = StringUtils.substringBefore(url, ":");
			
			String commandString = "reg query HKEY_CLASSES_ROOT\\" + protocol + "\\shell\\open\\command /ve";
			Command command = new Command(commandString);
			
			if(command.anyErrorOccured()){
				throw new RuntimeException(command.getError());
			}
			
			String result = command.getResult();
			
			int valueTypeIndex = result.indexOf(REGSTRY_TYPE_SZ);

			if (valueTypeIndex == -1){
				throw new RuntimeException(REGSTRY_TYPE_SZ+" not found.");
			}

			String commandResult = result.substring(valueTypeIndex + REGSTRY_TYPE_SZ.length()).trim();
			return commandResult.replace("%1", url);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void open(final String url) {
		String commandForUrl = getCommandForUrl(url);
		try {
			new Command(commandForUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new WindowsURLProtocolHandler().register("Magfakefake", "C:\\Documents and Settings\\lucas\\Local Settings\\Application Data\\Google\\Chrome\\Application\\chrome.exe");
		new WindowsURLProtocolHandler().open("Magfakefake:?xt=urn:btih:a6e2cd5c6a736767e101fa378e4e9fb45451ac95&dn=Community+S03E18+HDTV+XviD-AFG%5Bettv%5D&tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A80&tr=udp%3A%2F%2Ftracker.publicbt.com%3A80&tr=udp%3A%2F%2Ftracker.ccc.de%3A80");
	}
	
	public void register(final String protocol, final String applicationPath) {
		String escapedApplicationPath = applicationPath.replaceAll("\\\\", "\\\\\\\\");
		String regFile = 
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
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		
		String command = "regedit /s "+tempFile.getAbsolutePath();
		try {
			new Command(command);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
