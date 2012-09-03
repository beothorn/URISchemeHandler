package uriSchemeHandler;

import java.io.IOException;
import java.net.URI;

public class URISchemeHandler {

	private final RealURISchemeHandler realURLProtocolHandler;

	public URISchemeHandler() {
		final String osName = System.getProperty("os.name");
		realURLProtocolHandler = RealURLProtocolHandlerFactory.produce(osName); 
	}
	
	public void open(final URI uri) throws CouldNotOpenUriSchemeHandler{
		try {
			realURLProtocolHandler.open(uri);
		} catch (final IOException e) {
			//TODO: Try opening the browser here
			throw new CouldNotOpenUriSchemeHandler(e,uri.toString());
		}
	}
	
	public void register(final String schemeName,final String applicationExecutablePath) throws CouldNotRegisterUriSchemeHandler {
		try {
			realURLProtocolHandler.register(schemeName,applicationExecutablePath);
		} catch (final IOException e) {
			throw new CouldNotRegisterUriSchemeHandler(schemeName,applicationExecutablePath,e);
		}
	}
}
