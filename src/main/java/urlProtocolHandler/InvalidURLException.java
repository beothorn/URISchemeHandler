package urlProtocolHandler;

@SuppressWarnings("serial")
public class InvalidURLException extends Exception {

	public InvalidURLException(final String url) {
		super("Url '"+url+"' is invalid, it must have this format 'protocol:arguments'.");
	}

}
