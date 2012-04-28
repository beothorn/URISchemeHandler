package urlProtocolHandler;

public class URLProtocolHandler {

	public static void main(final String[] args) {
		final URLProtocolHandler urlProtocolHandler = new URLProtocolHandler();
//		urlProtocolHandler.open("magnet:?xt=urn:btih:6eee9806379ef47804658bdc35b8bb84a7f4f01e&dn=Community+S03E02+HDTV+XviD-LOL+%5Beztv%5D&tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A80&tr=udp%3A%2F%2Ftracker.publicbt.com%3A80&tr=udp%3A%2F%2Ftracker.ccc.de%3A80");
		urlProtocolHandler.register("deleteme", "send-notify");
	}
	
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
