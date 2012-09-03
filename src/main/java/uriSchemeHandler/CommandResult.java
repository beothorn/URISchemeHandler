package uriSchemeHandler;

public class CommandResult {

	private final String result;
	private final String error;

	public CommandResult(final String result, final String error) {
		this.result = result;
		this.error = error;
	}

	public String getResult() {
		return result;
	}

	public String getError() {
		return error;
	}
	
	public boolean anyErrorOccured(){
		return error.length() > 0;
	}
}
