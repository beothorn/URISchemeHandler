package uriSchemeHandler;

import java.io.IOException;

@SuppressWarnings("serial")
public class CouldNotRegisterUriSchemeHandler extends Exception {

	public CouldNotRegisterUriSchemeHandler(final String schemeName,final String applicationExecutablePath, final IOException e) {
		super("Could not register Uri scheme handler '"+applicationExecutablePath+"' for scheme name '"+schemeName+"'",e);
	}

}
