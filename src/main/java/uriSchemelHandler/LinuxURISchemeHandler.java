package uriSchemelHandler;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.io.FileUtils;

public class LinuxURISchemeHandler implements RealURISchemeHandler {
	
	public void open(final URI uri) throws IOException {
		Runtime.getRuntime().exec(new String[]{"xdg-open",uri.toString()});
	}
	
	public void register(final String protocol, final String handlerApplication) throws IOException {
		final boolean isGnome3 = isGnome3();
		if(isGnome3){
			gnome3Registry(protocol, handlerApplication);
		}else{				
			oldGnomeRegistry(protocol, handlerApplication);
		}
	}

	private boolean isGnome3() throws IOException {
		final Command getGnomeVersion = new Command("gnome-shell --version");
		final CommandResult commandResult = getGnomeVersion.run();
		if(commandResult.anyErrorOccured()){
			throw new RuntimeException("Only gnome is currently supported.");
		}
		final String result = commandResult.getResult().trim();
		final boolean isGnome3 = result.matches(".*?3\\.[0-9]*\\.[0-9]*.*");
		return isGnome3;
	}

	private void gnome3Registry(final String protocol, final String handlerApplication) throws IOException {
		final String destopFile = 
				"[Desktop Entry]\n" + 
				"Exec="+handlerApplication+" %U\n" + 
				"MimeType=x-scheme-handler/"+protocol+";\n" + 
				"Name="+protocol+"\n" + 
				"Terminal=false\n" + 
				"Type=Application";
		final File tempDesktopFile;
		tempDesktopFile = File.createTempFile("URLAPPLICATIONHANDLER", ".desktop");
		FileUtils.writeStringToFile(tempDesktopFile, destopFile);
		
		final String shellScript = 
				"#!/bin/bash\n" +
				"sudo cp "+tempDesktopFile.getAbsolutePath()+" /usr/share/applications/"+protocol+".desktop\n" +
				"sudo update-desktop-database";
		
		final File tempShellScriptFile;
		tempShellScriptFile = File.createTempFile("URLAPPLICATIONHANDLER", "reg.sh");
		FileUtils.writeStringToFile(tempShellScriptFile, shellScript);
		
		final String commandString = "gksudo sh "+tempShellScriptFile.getAbsolutePath();
		final Command command = new Command(commandString);
		command.run();
	}

	private void oldGnomeRegistry(final String protocol,
			final String handlerApplication) throws IOException {
		final String registerScript = 
				"gconftool-2 -t string -s /desktop/gnome/url-handlers/"+protocol+"/command '"+handlerApplication+" \"%s\"'\n" +
				"gconftool-2 -s /desktop/gnome/url-handlers/"+protocol+"/needs_terminal false -t bool\n" +
				"gconftool-2 -t bool -s /desktop/gnome/url-handlers/"+protocol+"/enabled true";
		final File script;
		script = File.createTempFile("PROTOCOL-", "-REGISTER.sh");
		FileUtils.writeStringToFile(script, registerScript);
		final Command command = new Command("sh \""+script.getAbsolutePath()+"\"");
		command.run();
	}

}
