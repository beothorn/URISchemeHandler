package uriSchemeHandler;

import java.io.IOException;
import java.net.URI;

public interface RealURISchemeHandler {

	void open(URI uri) throws IOException;
	void register(String schemeName, String applicationPath) throws IOException;

}
