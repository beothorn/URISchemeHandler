package urlProtocolHandler;

import java.io.IOException;

public interface RealURLProtocolHandler {

	void open(String url) throws IOException;
	void register(String protocol, String applicationPath) throws IOException;

}
