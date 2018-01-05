package net_finding.server;

import java.io.*;
import java.net.*;

public class FServer {
	private ServerSocket server;
	public static final int PORT = 1234;

	public FServer() {
	}

	public void start() {
		try {
			this.server = new ServerSocket(PORT);
			while (true) {
				Socket socket = server.accept();
				ServerProcess serverProcess = new ServerProcess(socket);
				serverProcess.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		FServer server = new FServer();
		server.start();
	}
}
