package HttpServer;
import java.lang.Runnable;
import java.util.Date;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

public class HttpSocketServer implements Runnable{
	private final boolean verbose = true;
	private final int port;
	private final String runnable;

	protected HttpSocketServer(int port, String runnable) {
		this.port = port;
		this.runnable = runnable;
	}

	public static HttpSocketServer of(int port,String runnable) {
		return new HttpSocketServer(port,runnable);
	}

	public void run(){
		try {
			ServerSocket serverConnect = new ServerSocket(this.port);
			System.out.println("Server started.\nListening for connections on port : " + this.port + " ...\n");
			
			// listen until server process ends
			while(true) {
				Socket connect = serverConnect.accept();
				// Block until request received
				System.out.println(Class.forName(this.runnable));
				System.out.println(Class.forName(this.runnable).getConstructor(Socket.class));
				Object serverProcessor = Class.forName(this.runnable).getConstructor(Socket.class).newInstance(connect);//serverConnect.accept());
				if (verbose) System.out.println("Connection opened. (" + new Date() + ")");
				// Create Thread for new Connection
				Thread t = (Thread)serverProcessor;
				t.start();
			}
			
		} catch (Exception e) {
			System.err.println("Server Connection error : " + e.getMessage());
		}

	}
}
