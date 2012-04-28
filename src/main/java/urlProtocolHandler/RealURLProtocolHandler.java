package urlProtocolHandler;

public interface RealURLProtocolHandler {

	void open(String url);
	void register(String protocol, String handlingCommand);

}
