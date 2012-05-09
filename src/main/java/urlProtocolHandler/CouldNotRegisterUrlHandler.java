package urlProtocolHandler;

import java.io.IOException;

@SuppressWarnings("serial")
public class CouldNotRegisterUrlHandler extends Exception {

	public CouldNotRegisterUrlHandler(final String protocol,final String applicationExecutablePath, final IOException e) {
		super("Could not register Url Handler '"+applicationExecutablePath+"' for protocol '"+protocol+"'",e);
	}

}
