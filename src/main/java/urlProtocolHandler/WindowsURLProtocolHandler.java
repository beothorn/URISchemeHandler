package urlProtocolHandler;

import java.io.IOException;

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

	public void register(final String protocol, final String handlingCommand) {
		throw new RuntimeException("Method not implemented");
	}

}
