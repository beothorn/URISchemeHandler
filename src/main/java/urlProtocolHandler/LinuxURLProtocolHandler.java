package urlProtocolHandler;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class LinuxURLProtocolHandler implements RealURLProtocolHandler {
	
	public void open(final String url) {
		try {
			new Command("xdg-open",url);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void register(final String protocol, final String handlerApplication) {
		try {
			final boolean isGnome3 = isGnome3();
			if(isGnome3){
				gnome3Registry(protocol, handlerApplication);
			}else{				
				oldGnomeRegistry(protocol, handlerApplication);
			}
		} catch (final IOException e1) {
			throw new RuntimeException(e1);
		}
	}

	private boolean isGnome3() throws IOException {
		final Command getGnomeVersion = new Command("gnome-shell --version");
		if(getGnomeVersion.anyErrorOccured()){
			throw new RuntimeException("Only gnome is currently supported.");
		}
		final String result = getGnomeVersion.getResult().trim();
		final boolean isGnome3 = result.matches(".*?3\\.[0-9]*\\.[0-9]*.*");
		return isGnome3;
	}

	private void gnome3Registry(final String protocol, final String handlerApplication) {
		final String destopFile = 
				"[Desktop Entry]\n" + 
				"Exec="+handlerApplication+" %U\n" + 
				"MimeType=x-scheme-handler/"+protocol+";\n" + 
				"Name="+protocol+"\n" + 
				"Terminal=false\n" + 
				"Type=Application";
		final File tempDesktopFile;
		try {
			tempDesktopFile = File.createTempFile("URLAPPLICATIONHANDLER", ".desktop");
			FileUtils.writeStringToFile(tempDesktopFile, destopFile);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		
		final String shellScript = 
				"#!/bin/bash\n" +
				"sudo cp "+tempDesktopFile.getAbsolutePath()+" /usr/share/applications/"+protocol+".desktop\n" +
				"sudo update-desktop-database";
		
		final File tempShellScriptFile;
		try {
			tempShellScriptFile = File.createTempFile("URLAPPLICATIONHANDLER", "reg.sh");
			FileUtils.writeStringToFile(tempShellScriptFile, shellScript);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		
		final String command = "gksudo sh "+tempShellScriptFile.getAbsolutePath();
		try {
			new Command(command);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void oldGnomeRegistry(final String protocol,
			final String handlerApplication) {
		final String registerScript = 
				"gconftool-2 -t string -s /desktop/gnome/url-handlers/"+protocol+"/command '"+handlerApplication+" \"%s\"'\n" +
				"gconftool-2 -s /desktop/gnome/url-handlers/"+protocol+"/needs_terminal false -t bool\n" +
				"gconftool-2 -t bool -s /desktop/gnome/url-handlers/"+protocol+"/enabled true";
		final File script;
		try {
			script = File.createTempFile("PROTOCOL-", "-REGISTER.sh");
			FileUtils.writeStringToFile(script, registerScript);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		try {
			new Command("sh \""+script.getAbsolutePath()+"\"");
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}
