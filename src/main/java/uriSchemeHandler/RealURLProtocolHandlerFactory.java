package uriSchemeHandler;

public class RealURLProtocolHandlerFactory {

    public static RealURISchemeHandler produce(final String osName) {
        final String osLowerCase = osName.toLowerCase();
        if (osLowerCase.contains("linux")) {
            return new LinuxURISchemeHandler();
        }
        if (osLowerCase.contains("windows")) {
            return new WindowsURISchemeHandler();
        }
        if (osLowerCase.contains("mac")) {
            return new MacURISchemeHandler();
        }
        throw new RuntimeException("OS not supported");
    }

}
