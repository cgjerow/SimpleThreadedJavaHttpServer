import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

public class ReturnImage extends Thread {

	static final File WEB_ROOT = new File(".");
	static final String DEFAULT_FILE = "index.html";
	static final String FILE_NOT_FOUND = "404.html";
	static final String METHOD_NOT_SUPPORTED = "not_supported.html";
	// port to listen
	static final int PORT = 8080;

	// verbose mode - log server events in terminal
	static final boolean verbose = true;

	// Connected Client
	private HttpServer.HttpConnectionManager client;
	
	public ReturnImage(Socket c) {
		this.client = HttpServer.HttpConnectionManager.of(c);
	}
	public static ReturnImage of(Socket c) {
		return new ReturnImage(c);
	}

	public static void main(String[] args) {
		try {
			// Create Http Socket Server
			// This will manage your client connection threads for you
			HttpServer.HttpSocketServer server = HttpServer.HttpSocketServer.of(PORT,"ReturnImage");
			new Thread(server).start();
		} catch (Exception e) {
			System.err.println("Server Connection error : " + e.getMessage());
		}
	}


	// Manage each client connection
	@Override
	public void run() {
		HttpServer.HttpResponse response = HttpServer.HttpResponse.of();
		try {	
			// Parse Request
			HttpServer.HttpRequest req = this.client.parseRequest();	
			System.out.println(req);			
			//
			// Do Stuff
			//
			File f = new File(WEB_ROOT,"/astraun.jpeg");
			int fileLength = (int) f.length();
			byte[] fileData = readFileData(f,fileLength);
			response.setStatus(200);
			response.addHeader("Server","Antaun : 1.0");
			response.addHeader("Date",new Date().toString());
			response.addHeader("Content-length",fileLength);
			//out.append("Content-type: " + content+"\r\n");
			response.setBody(fileData);
			//
			// Done Doing Stuff
			//
			
			
			// Send Response
			this.client.respond(response);
			if (verbose) System.out.println("Data returned");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}




	/*///////////////////
	 * HELPER METHODS
	 *///////////////////
	
	private byte[] readFileData(File file, int fileLength) throws IOException {
		FileInputStream fileIn = null;
		byte[] fileData = new byte[fileLength];

		try {
			fileIn = new FileInputStream(file);
			fileIn.read(fileData);
		} finally {
			if (fileIn != null)
				fileIn.close();
		}
		
		return fileData;
	}

}		
