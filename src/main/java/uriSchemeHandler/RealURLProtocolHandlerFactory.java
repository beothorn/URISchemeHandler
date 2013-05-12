package uriSchemeHandler;

public class RealURLProtocolHandlerFactory {

	public static RealURISchemeHandler produce(final String osName) {
		final String osLowerCase = osName.toLowerCase();
		if(osLowerCase.contains("linux")){
			return new LinuxURISchemeHandler();
		}
		if(osLowerCase.contains("windows")){
			return new WindowsURISchemeHandler();
		}
		//For use wit osx
		//http://ss64.com/osx/lsregister.html
		//http://krypted.com/mac-os-x/lsregister-associating-file-types-in-mac-os-x/
		throw new RuntimeException("OS not supported");
	}

}
