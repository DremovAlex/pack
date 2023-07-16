package oriseus.pack.utils;

public class ServerException extends RuntimeException {

	private static final long serialVersionUID = -1884990027213676873L;
	
	private int statusCode;
	private String message;

	public ServerException(String exceptionMessage) {
		int statusCode = Integer.parseInt(exceptionMessage.substring(0, exceptionMessage.indexOf(":")).trim());
		String message = exceptionMessage.substring(exceptionMessage.indexOf(":") + 3, exceptionMessage.length() - 1).trim();
		
		this.statusCode = statusCode;
		this.message = message;
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	public String getMessage() {
		return message;
	}
}
