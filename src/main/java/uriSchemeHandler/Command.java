package uriSchemeHandler;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class Command {

	private String result;
	private String error;
	private final String[] args;

	public Command(final String... args){
		this.args = args;
	}
	
	public Command(final String command){
		this(new String[]{command});
	}
	
	public CommandResult run() throws IOException {
		final Process process = Runtime.getRuntime().exec(args);
		return collectResult(process);
	}

	private CommandResult collectResult(final Process process) throws IOException {
		final InputStream inputStream = process.getInputStream();
		final InputStream errorStream = process.getErrorStream();
		result = IOUtils.toString(inputStream);
		error = IOUtils.toString(errorStream);
		return new CommandResult(result,error);
	}
	
}
