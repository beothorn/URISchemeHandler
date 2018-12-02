package uriSchemeHandler;

import java.io.IOException;
import java.net.URI;

public class MacURISchemeHandler implements RealURISchemeHandler {

    public void open(URI uri) throws IOException {
        Runtime.getRuntime().exec(new String[] {"open", uri.toString()});
    }

    public void register(String schemeName, String applicationPath) throws IOException {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

}
