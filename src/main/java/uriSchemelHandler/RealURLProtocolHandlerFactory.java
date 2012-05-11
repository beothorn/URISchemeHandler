package uriSchemelHandler;

public class RealURLProtocolHandlerFactory {

	public static RealURISchemeHandler produce(final String osName) {
		final String osLowerCase = osName.toLowerCase();
		if(osLowerCase.contains("linux")){
			return new LinuxURISchemeHandler();
		}
		if(osLowerCase.contains("windows")){
			return new WindowsURISchemeHandler();
		}
		throw new RuntimeException("OS not supported");
	}

}
