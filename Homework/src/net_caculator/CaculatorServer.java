package net_caculator;

import java.io.*;
import java.net.*;

public class CaculatorServer {
	private ServerSocket server;
	public CaculatorServer(int port) {
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// listening
	public void start() {
		while (true) {
			try {
				Socket socket = server.accept();
				ServerProcess sp = new ServerProcess(socket);
				sp.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		CaculatorServer server = new CaculatorServer(1234);
		server.start();
	}
}
