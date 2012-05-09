package urlProtocolHandler;

import java.io.IOException;

public class URLProtocolHandler {

	private final RealURLProtocolHandler realURLProtocolHandler;

	public URLProtocolHandler() {
		final String osName = System.getProperty("os.name");
		realURLProtocolHandler = RealURLProtocolHandlerFactory.produce(osName); 
	}
	
	public void open(final String url) throws CouldNotOpenUrlHandler, InvalidURLException{
		if(!url.contains(":")){
			throw new InvalidURLException(url);
		}
		try {
			realURLProtocolHandler.open(url);
		} catch (final IOException e) {
			//TODO: Try opening the browser here
			throw new CouldNotOpenUrlHandler(e,url);
		}
	}
	
	public void register(final String protocol,final String applicationExecutablePath) throws CouldNotRegisterUrlHandler {
		try {
			realURLProtocolHandler.register(protocol,applicationExecutablePath);
		} catch (final IOException e) {
			throw new CouldNotRegisterUrlHandler(protocol,applicationExecutablePath,e);
		}
	}
}
