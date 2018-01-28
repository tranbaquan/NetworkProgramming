package ex1;

import java.io.*;
import java.net.*;
import java.util.*;

public class FileClient {
	private Socket socket;
	private DataInputStream netIn;
	private DataOutputStream netOut;
	private BufferedReader userIn;
	private String clientDir;
	public static final String HOST = "127.0.0.1";
	public static final int PORT = 12345;
	public FileClient() {
		clientDir = "C:\\source";
	}

	public void request() {
		try {
			this.socket = new Socket(HOST, PORT);
			netIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			netOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			userIn = new BufferedReader(new InputStreamReader(System.in));
			String line;
			// vòng lặp vô tận để nhận request của client
			while (true) {
				try {
					// đọc lệnh từ người dùng
					line = userIn.readLine();
					// hàm này để phân tích lệnh
					analysis(line);
					// nếu là lệnh QUIT thì thoát khỏi vòng lặp
					if("QUIT".equalsIgnoreCase(line)) break;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			netIn.close();
			netOut.flush();
			netOut.close();
			userIn.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// phân tích lệnh
	private void analysis(String line) throws IOException {
		// tách các thành phần của lệnh ra
		StringTokenizer st = new StringTokenizer(line);
		String command = st.nextToken();
		String sf, df;
		switch (command) {
		case "SET_SERVER_DIR":
			// nếu command là SET_SERVER_DIR thì phía server phải xử lí. mình chỉ cần gửi luôn dòng lệnh lên server là được
			netOut.writeUTF(line);
			break;
		case "SET_CLIENT_DIR":
			// nếu command là SET_CLIENT_DIR thì token tiếp theo sẽ là thư mục gốc của client
			// mình chỉ cần thay thế thư mục gốc ở client thôi không cần phải đưa lên server
			clientDir = st.nextToken();
			break;
		case "SEND":
			sf = st.nextToken();
			df = st.nextToken();
			// gửi lên server lệnh: SEND df
			netOut.writeUTF(command + " " + df);
			sendFile(sf);
			break;
		case "GET":
			sf = st.nextToken();
			df = st.nextToken();
			// gửi lên server lệnh: GET sf
			netOut.writeUTF(command + " " + sf);
			receiveFile(df);
			break;
		case "QUIT":
			// tương tự với lệnh QUIT.. lệnh này để báo cho server kết thúc vòng lặp
			netOut.writeUTF(line);
			break;

		default:
			break;
		}
		// nhớ flush
		netOut.flush();
	}

	private void receiveFile(String df) throws IOException {
		File file = new File(clientDir + File.separator + df);
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

	private void sendFile(String sf) throws IOException {
		File file = new File(clientDir + File.separator + sf);
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
	public static void main(String[] args) {
		FileClient fileClient = new FileClient();
		fileClient.request();
	}
}
