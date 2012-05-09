package urlProtocolHandler;

import java.io.IOException;

@SuppressWarnings("serial")
public class CouldNotOpenUrlHandler extends Exception {

	public CouldNotOpenUrlHandler(final IOException e, final String url) {
		super("Could not open Url Handler for url "+url,e);
	}

}
