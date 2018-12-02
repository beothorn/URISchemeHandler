package uriSchemeHandler;

import java.awt.Desktop;
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
			try {
				Desktop.getDesktop().browse(uri);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
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
