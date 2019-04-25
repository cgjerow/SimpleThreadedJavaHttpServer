package HttpServer;

import java.util.HashMap;

public abstract class HttpMessage {

	protected String bodyString;
	protected byte[] bodyBytes;
	protected String bodyType; // Can be "string" or "bytes"
	protected int bodyLength;
	protected HashMap<String,String> headers = new HashMap<String,String>();
	protected String httpVersion = "1.1";


	/*//////////////////////
	 * SETTER METHODS
	 *//////////////////////
	
	protected void setHttpVersion(String version) {
		// TODO Validate version input
		this.httpVersion = version;
	}
	
	public void setBody(String body) {
		this.bodyString = body;
		this.bodyType = "string";
	}

	public void setBody(byte[] body) {
		this.bodyBytes = body;
		this.bodyType = "bytes";
		this.bodyLength = (int) body.length;
	}

	public void setBodyType(String bodyType) {
		if (bodyType=="string" || bodyType=="bytes")
			this.bodyType = bodyType;
		
		// TODO Else exception - invalid bodytype 
	}

	public void addHeader(String header, String value) {
		this.headers.put(header,value);
	}

	public void addHeader(String header, int value) {
		this.addHeader(header,String.valueOf(value));
	}


	/*////////////////////
	 * GETTER METHODS
	 *//////////////////// 
	
	public String getBodyType() {
		return this.bodyType;
	}

	public String getBodyString() {
		return this.bodyString;
	}

	public byte[] getBodyBytes() {
		return this.bodyBytes;
	}

	public int getBodyLength() {
		return this.bodyLength;
	}

	public String getHeaderInfo(){
		String headers = "";

		for (int i=0;i<this.headers.keySet().size();i++) {
			headers += this.headers.keySet().toArray()[i] + ": " 
				+ this.headers.get(this.headers.keySet().toArray()[i])
				+ "\r\n";
		}

		return headers;
	}

	public String getMetaInfo() {
		return this.getStartLine() + "\r\n" + this.getHeaderInfo();
	}

	// The Start Line will vary between requests and responses
	// Each will have to implement their own method
	public abstract String getStartLine();
	
}
