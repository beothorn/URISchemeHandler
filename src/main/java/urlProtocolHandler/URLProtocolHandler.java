package urlProtocolHandler;

public class URLProtocolHandler {

	private final RealURLProtocolHandler realURLProtocolHandler;

	public URLProtocolHandler() {
		final String osName = System.getProperty("os.name");
		realURLProtocolHandler = RealURLProtocolHandlerFactory.produce(osName); 
	}
	
	public void open(final String url){
		realURLProtocolHandler.open(url);
	}
	
	public void register(final String protocol,final String applicationName){
		realURLProtocolHandler.register(protocol,applicationName);
	}
}
