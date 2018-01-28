package ex1;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class FileServerProcess extends Thread {
	private Socket socket;
	private DataInputStream netIn;
	private DataOutputStream netOut;
	private String serverDir;

	public FileServerProcess(Socket socket) {
		try {
			this.socket = socket;
			netIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			netOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			String line;
			// vòng lặp vô tận để nhận request của client
			while (true) {
				try {
					// đọc lệnh từ client
					line = netIn.readUTF();
					// nếu là lệnh QUIT thì thoát khỏi vòng lặp
					if ("QUIT".equalsIgnoreCase(line))
						break;
					// hàm này để phân tích lệnh
					analysis(line);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			netIn.close();
			netOut.flush();
			netOut.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void analysis(String line) throws IOException {
		// tách các thành phần của lệnh ra
		StringTokenizer st = new StringTokenizer(line);
		String command = st.nextToken();
		String sf, df;
		switch (command) {
		case "SET_SERVER_DIR":
			// nếu command là SET_SERVER_DIR thì set lại serverDir
			serverDir = st.nextToken();
			break;
		// sẽ không có trường hợp này vì bên client không gửi lệnh này lên server
		// case "SET_CLIENT_DIR":
		// break;
		case "SEND":
			df = st.nextToken();
			receiveFile(df);
			break;
		case "GET":
			sf = st.nextToken();
			sendFile(sf);
			break;
		default:
			break;
		}
	}

	private void sendFile(String sf) throws IOException {
		File file = new File(serverDir + File.separator + sf);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		netOut.writeLong(file.length());
		// dùng mảng byte để tăng tốc độ đọc và ghi file
		byte[] buff = new byte[10*1024];
		int data;
		while ((data = bis.read(buff)) != -1) {
			netOut.write(buff, 0, data);
		}
		netOut.flush();
		bis.close();
	}

	private void receiveFile(String df) throws IOException {
		File file = new File(serverDir + File.separator + df);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
		long size = netIn.readLong();
		int byteRead, byteMustRead;
		long remain = size;
		byte[] buff = new byte[10*1024];
		while (remain > 0) {
			byteMustRead = buff.length > remain ? (int) remain : buff.length;
			byteRead = netIn.read(buff, 0, byteMustRead);
			bos.write(buff, 0, byteRead);
			remain-=byteRead;
		}
		bos.close();
	}
}
