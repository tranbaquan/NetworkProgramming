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
			System.out.println("Xin chào!");
			System.out.println("Vui lòng nhập yêu cầu của bạn!");
			String line;
			// vòng lặp vô tận để nhận request của client
			while (true) {
				try {
					// đọc lệnh từ người dùng
					line = userIn.readLine();
					// hàm này để phân tích lệnh
					analysis(line);
					// nếu là lệnh QUIT thì thoát khỏi vòng lặp
					if ("QUIT".equalsIgnoreCase(line)) {
						System.out.println("Hẹn găp lại!");
						break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (NoSuchElementException e) {
					// bắt ngoại lệ ở đây
					System.out.println(e.getMessage());
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
	private void analysis(String line) throws IOException, NoSuchElementException { // chuyển giao ngoại lệ
		// tách các thành phần của lệnh ra
		StringTokenizer st = new StringTokenizer(line);
		String command = st.nextToken();
		String sf, df;
		switch (command) {
		case "SET_SERVER_DIR":
			// nếu command là SET_SERVER_DIR thì phía server phải xử lí. mình chỉ cần gửi
			// luôn dòng lệnh lên server là được
			netOut.writeUTF(line);
			break;
		case "SET_CLIENT_DIR":
			// nếu command là SET_CLIENT_DIR thì token tiếp theo sẽ là thư mục gốc của
			// client
			// mình chỉ cần thay thế thư mục gốc ở client thôi không cần phải đưa lên server
			try {
				clientDir = st.nextToken();
			} catch (NoSuchElementException e) {
				// ném ngoại lệ
				throw new NoSuchElementException("Bạn đã nhập thiếu đường dẫn!");
			}
			break;
		case "SEND":
			try {
				sf = st.nextToken();
				df = st.nextToken();
			} catch (NoSuchElementException e) {
				throw new NoSuchElementException("Nhập thiếu kìa bạn gì gì ơi!");
			}
			// gửi lên server lệnh: SEND df
			netOut.writeUTF(command + " " + df);
			sendFile(sf);
			break;
		case "GET":
			try {
				sf = st.nextToken();
				df = st.nextToken();
			} catch (NoSuchElementException e) {
				throw new NoSuchElementException("Nhập thiếu kia kìa...  sao nói mãi không nghe nhỉ?");
			}
			// gửi lên server lệnh: GET sf
			netOut.writeUTF(command + " " + sf);
			netOut.flush();
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

		// Đây là file dest cần được ghi ra (data lấy từ Stream)
		// tạo file
		File file = new File(clientDir + File.separator + df);
		// Mở outputstream
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
		// đọc chiều dài của file nguồn
		long size = netIn.readLong();
		int byteRead, byteMustRead;

		// kích thước file còn lại cần đọc
		// ban đầu khi chưa đọc thì kích thước cần đọc bằng với kích thước file nguồn
		long remain = size;
		// Mảng buffered để tăng số byte trong 1 lần đọc thay vì bình thường chỉ đọc mỗi
		// lần 1 byte
		// ở đây kích thước mỗi lần đọc là 10MB gấp bao nhiêu lần chắc bạn tính được
		byte[] buff = new byte[10 * 1024];

		// đọc đến khi nào remain bằng 0
		while (remain > 0) {

			// lần 1 = buff
			byteMustRead = buff.length > remain ? (int) remain : buff.length;

			byteRead = netIn.read(buff, 0, byteMustRead);
			bos.write(buff, 0, byteRead);
			remain -= byteRead;
		}
		bos.close();
	}

	private void sendFile(String sf) throws IOException {
		File file = new File(clientDir + File.separator + sf);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		netOut.writeLong(file.length());
		// dùng mảng byte để tăng tốc độ đọc và ghi file
		byte[] buff = new byte[10 * 1024];
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
