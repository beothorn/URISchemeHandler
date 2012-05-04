package urlProtocolHandler;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class Command {

	private String result;
	private String error;

	public Command(final String... args) throws IOException {
		final Process process = Runtime.getRuntime().exec(args);
		collectOutput(process);
	}
	
	public Command(final String command) throws IOException {
		final Process process = Runtime.getRuntime().exec(command);
		collectOutput(process);
	}

	private void collectOutput(final Process process) throws IOException {
		final InputStream inputStream = process.getInputStream();
		final InputStream errorStream = process.getErrorStream();
		result = IOUtils.toString(inputStream);
		error = IOUtils.toString(errorStream);
	}
	
	public boolean anyErrorOccured(){
		return error.length() > 0;
	}
	
	public String getResult(){
		return result;
	}

	public String getError(){
		return error;
	}
	
}
