package HttpServer;
import java.util.HashMap;

public class HttpResponse extends HttpServer.HttpMessage  {
	private String status;
	private HashMap<String,String> statusCodes;

	protected HttpResponse(HashMap<String,String> statusCodes){
		super();
		this.statusCodes = statusCodes;
	}


	public static HttpResponse of(){
		HashMap<String,String> statusCodes = generateStatusCodes();
		if (statusCodes==null) // Error generating status codes
			return null;
		return new HttpResponse(statusCodes);
	}

	@Override
	public String toString() {
		String response = "";
		try{ 
			response += this.getMetaInfo();
			response += "\r\n"; // Signals end of meta-information
			// Add body
			if (this.bodyString!=null) response += this.bodyString;
			// TODO handle Byte body
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}		
		return response;
	}

	@Override
	public String getStartLine() {
		String startline = "";
		try {
			startline += "HTTP/"+this.httpVersion + " " +
				this.status+" " + this.statusCodes.get(this.status);
			return startline;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStatus(int status) {
		this.setStatus(String.valueOf(status));
	}

	
	/**
	 * Helper Methods
	**/


	// Map Status Code to Message
	private static HashMap<String,String> generateStatusCodes() {
		try {	
			HashMap<String,String> statusCodes = new HashMap<String,String>();
			statusCodes.put("100","Continue");
			statusCodes.put("101","Switching Protocol");
			statusCodes.put("102","Processing");
			statusCodes.put("103","Early Hints");
			statusCodes.put("200","OK");
			statusCodes.put("201","Created");
			statusCodes.put("202","Accepted");
			statusCodes.put("203","Non-Authoritative Information");
			statusCodes.put("204","No Content");
			statusCodes.put("205","Reset Content");
			statusCodes.put("206","Partial Content");
			statusCodes.put("207","Multi-Status");
			statusCodes.put("208","IM Used");
			statusCodes.put("300","Multiple Choice");
			statusCodes.put("301","Moved Permanently");
			statusCodes.put("302","Found");
			statusCodes.put("303","See Other");
			statusCodes.put("304","Not Modified");
			// 305 and 306 no longer used
			statusCodes.put("307","Temporary Redirect");
			statusCodes.put("308","Permanent Redirect");
			statusCodes.put("400","Bad Request");
			statusCodes.put("401","Unauthorized"); // Really means unauthenticated (identity unknown to server)
			statusCodes.put("402","Payment Required");
			statusCodes.put("403","Forbidden"); // Means unauthorized (identity known to server)
			statusCodes.put("404","Not Found");
			statusCodes.put("405","Method Not Allowed");
			statusCodes.put("406","Not Acceptable");
			statusCodes.put("407","Proxy Authentication Required");
			statusCodes.put("408","Request Timeout");
			statusCodes.put("409","Conflict");
			statusCodes.put("410","Gone");
			statusCodes.put("411","Length Required");
			statusCodes.put("412","Precondition Failed");
			statusCodes.put("413","Payload Too Large");
			statusCodes.put("414","URI Too Long");
			statusCodes.put("415","Unsupported Media Type");
			statusCodes.put("416","Requested Range Not Satisfiable");
			statusCodes.put("417","Expectation Failed");
			statusCodes.put("418","I'm a teapot"); // Server refuses the attempt to brew coffee with a teapot
			statusCodes.put("421","Misdirected Request");
			statusCodes.put("422","Unprocessable Entity");
			statusCodes.put("423","Locked");
			statusCodes.put("424","Failed Dependency");
			statusCodes.put("425","Too Early"); // Unwilling to process a request that might be replayed
			statusCodes.put("426","Upgrade Required");
			statusCodes.put("428","Precondition Required");
			statusCodes.put("429","Too Many Requests");
			statusCodes.put("431","Request Header Fields Too Large");
			statusCodes.put("451","Unavailable for Legal Reasons");
			statusCodes.put("500","Internal Server Error");
			statusCodes.put("501","Not Implemented"); // Method not supported (POST, PATCH, etc.)
			statusCodes.put("502","Bad Gateway");
			statusCodes.put("503","Service Unavailable");
			statusCodes.put("504","Gateway Timeout");
			statusCodes.put("505","HTTP Version Not Supported");
			statusCodes.put("506","Variant Also Negotiates");
			statusCodes.put("507","Insufficient Storage");
			statusCodes.put("508","Loop Detected");
			statusCodes.put("510","Not Extended");
			statusCodes.put("511","Network Authentication Required");

			return statusCodes;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
