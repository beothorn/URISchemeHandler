package uriSchemeHandler;

import java.io.IOException;

@SuppressWarnings("serial")
public class CouldNotOpenUriSchemeHandler extends Exception {

	public CouldNotOpenUriSchemeHandler(final IOException e, final String uri) {
		super("Could not open Uri scheme handler for "+uri,e);
	}

}
