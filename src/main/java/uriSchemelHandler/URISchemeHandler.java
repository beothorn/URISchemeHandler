package uriSchemelHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class URISchemeHandler {

	private final RealURISchemeHandler realURLProtocolHandler;

	public URISchemeHandler() {
		final String osName = System.getProperty("os.name");
		realURLProtocolHandler = RealURLProtocolHandlerFactory.produce(osName); 
	}
	
	public static void main(String[] args) throws URISyntaxException {
		URI uri = new URI("magnet:?xt=urn:btih:4fdfcbb19b522a6ba389d08602abe97685673f51&dn=virtualbox-4.1_4.1.14-77440%7EUbuntu%7Elucid_i386.deb&tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A80&tr=udp%3A%2F%2Ftracker.publicbt.com%3A80&tr=udp%3A%2F%2Ftracker.ccc.de%3A80");
		System.out.println(uri.getScheme());
		String authority = uri.getAuthority();
		System.out.println(authority);
	}
	
	public void open(final URI uri) throws CouldNotOpenUriSchemeHandler{
		try {
			realURLProtocolHandler.open(uri);
		} catch (final IOException e) {
			//TODO: Try opening the browser here
			throw new CouldNotOpenUriSchemeHandler(e,uri.toString());
		}
	}
	
	public void register(final String protocol,final String applicationExecutablePath) throws CouldNotRegisterUriSchemeHandler {
		try {
			realURLProtocolHandler.register(protocol,applicationExecutablePath);
		} catch (final IOException e) {
			throw new CouldNotRegisterUriSchemeHandler(protocol,applicationExecutablePath,e);
		}
	}
}
