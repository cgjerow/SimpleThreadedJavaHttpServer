package HttpServer;

import javax.net.ssl.SSLSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;


public class HttpConnectionManager {
	private Socket client;
	// Read text from char stream
	private BufferedReader bufIn;
	// Writes Bytes to Stream - For raw byte data push to client
	private BufferedOutputStream bufOut;
	// Decodes bytes to chars
	private InputStreamReader isr;
	// Encodes chars to bytes
	private OutputStreamWriter osw;	
	private HttpServer.HttpRequest request;

	private HttpConnectionManager(Socket client,
		       		InputStreamReader isr, BufferedReader bufIn, 
				OutputStreamWriter osw, BufferedOutputStream bufOut) {
		this.client = client;
		this.isr = isr;
		this.bufIn = bufIn;
		this.osw = osw;
		this.bufOut = bufOut;
	}

	public static HttpConnectionManager of(Socket client) {
		try {
			InputStreamReader isr = new InputStreamReader(client.getInputStream());
			BufferedReader bufIn = new BufferedReader(isr);
			OutputStreamWriter osw = new OutputStreamWriter(client.getOutputStream());
			BufferedOutputStream bufOut = new BufferedOutputStream(client.getOutputStream()); 
			return new HttpConnectionManager(client,isr,bufIn,osw,bufOut);
		}
		catch(IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public void respond(HttpServer.HttpResponse response) {
		System.out.println(response.getMetaInfo());
	
		try {	
			// write response to buffer
			this.osw.append(response.getMetaInfo()+"\r\n");
			if (response.getBodyType()=="text") 
				this.osw.append(response.getBodyString());
			this.osw.flush();
			
			if (response.getBodyType()=="bytes")
				this.bufOut.write(response.getBodyBytes(),0,response.getBodyLength());
		
			this.client.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public HttpServer.HttpRequest parseRequest() {
		// TODO Add HttpRequest Class and parse this into a new Object -> returning correct errors as necessary for malformed requests etc.

		String req = "";
		try{
			String line = this.bufIn.readLine();
			while(!line.isEmpty()) {
				req+=line+"\r\n";
				line = this.bufIn.readLine();
			}

			// Create HttpRequest here
			this.request = HttpServer.HttpRequest.of(req);
			return this.request;
		}
		catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public String toString() {
		return "Socket: " + this.client;
	}

	
}
