
package ex1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {
	private ServerSocket serverSocket;
	public static final int PORT = 12345;

	public FileServer() {
	}

	public void start() {
		try {
			serverSocket = new ServerSocket(PORT);
			while (true) {
				try {
					Socket socket = serverSocket.accept();
					FileServerProcess fsp = new FileServerProcess(socket);
					fsp.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		FileServer fileServer = new FileServer();
		fileServer.start();
	}
}
