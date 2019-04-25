package HttpServer;
import java.util.HashMap;

public class HttpRequest extends HttpServer.HttpMessage {
	private final String method;
	private final String resource;

	protected HttpRequest(String method, String resource, HashMap<String,String> headers, String httpVersion) {
		super();
		this.method = method;
		this.resource = resource;
		super.headers = headers;
		super.httpVersion = httpVersion;
	}

	public static HttpRequest of(String request) {
		String method = null;
		String resource = null;
		String httpVersion = null;
		HashMap<String,String> headers = new HashMap<String,String>();

		String[] splitReq = request.split("\r\n\r\n");
		String[] metaInfo = splitReq[0].split("\r\n");
		for (int i=0;i<metaInfo.length;i++) {
			if (i==0) {
				System.out.println(metaInfo[i]);
				String[] startLine = metaInfo[i].split(" ");

				method = startLine[0];
				resource = startLine[1];
				httpVersion = startLine[2];
			}
			else {
				String[] header = metaInfo[i].split(":");
				headers.put(header[0],header[1]);
			}
		}

		
		// TODO Handle Body;
		//

		return new HttpRequest(method,resource,headers,httpVersion);
	}


	@Override
	public String getStartLine() {
		return this.method+ " " + this.resource + " " + this.httpVersion;
	}	

	@Override
	public String toString() {
		String request = "";
		try {
			request += this.getMetaInfo();
			request += "\r\n";
			if (this.bodyString!=null)
				request += this.getBodyString();

			// TODO handle Byte body
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return request;
	}



}
