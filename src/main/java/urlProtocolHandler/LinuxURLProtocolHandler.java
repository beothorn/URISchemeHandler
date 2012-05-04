package urlProtocolHandler;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class LinuxURLProtocolHandler implements RealURLProtocolHandler {

	public void open(final String url) {
		try {
			new Command("xdg-open \""+url+"\"");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void register(final String protocol, final String handlerApplication) {
		final String registerScript = 
				"gconftool-2 -t string -s /desktop/gnome/url-handlers/"+protocol+"/command '"+handlerApplication+" \"%s\"'\n" +
				"gconftool-2 -s /desktop/gnome/url-handlers/"+protocol+"/needs_terminal false -t bool\n" +
				"gconftool-2 -t bool -s /desktop/gnome/url-handlers/"+protocol+"/enabled true";
		final File script;
		try {
			script = File.createTempFile("PROTOCOL-", "-REGISTER.sh");
			FileUtils.writeStringToFile(script, registerScript);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		try {
			new Command("sh \""+script.getAbsolutePath()+"\"");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
