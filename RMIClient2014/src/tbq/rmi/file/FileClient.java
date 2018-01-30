
package tbq.rmi.file;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;

public class FileClient {
	BufferedReader userIn;
	boolean isLogin;
	BufferedInputStream bis;
	BufferedOutputStream bos;

	public void start() throws NotBoundException, IOException {
		Registry registry = LocateRegistry.getRegistry("127.0.0.1", 12345);
		IFile file = (IFile) registry.lookup("File");
		StringTokenizer st;
		userIn = new BufferedReader(new InputStreamReader(System.in));
		String line;
		System.out.println("Welcome....!");
		while (true) {
			try {
				line = userIn.readLine();
				if ("QUIT".equalsIgnoreCase(line))
					break;
				if (!isLogin) {
					st = new StringTokenizer(line);
					String command = st.nextToken();
					switch (command) {
					case "TEN":
						try {
							String name = st.nextToken();
							if (file.findName(name)) {
								System.out.println("Tên đăng nhập đúng");
							} else {
								System.out.println("Tên đăng nhập sai");
							}
						} catch (NoSuchElementException e) {
							System.out.println("Cú pháp không đầy đủ!");
						}
						break;
					case "MATKHAU":
						try {
							String pass = st.nextToken();
							if (file.checkPass(pass)) {
								System.out.println("Đăng nhập thành công!");
								isLogin = true;
							} else {
								System.out.println("Mật khẩu sai!");
							}
						} catch (NoSuchElementException e) {
							System.out.println("Cú pháp không đầy đủ!");
						}
						break;

					default:
						break;
					}
				} else {
					st = new StringTokenizer(line);
					String command = st.nextToken();
					String sf, df;
					switch (command) {
					case "SEND":
						try {
							sf = st.nextToken();
							df = st.nextToken();
							bis = new BufferedInputStream(new FileInputStream(sf));
							file.createDest(df);
							byte[] buff = new byte[10 * 1024];
							int length;
							while ((length = bis.read(buff)) != -1) {
								file.writeData(buff, length);
							}
							bis.close();
							file.closeDest();
						} catch (NoSuchElementException e) {
							System.out.println("Cú pháp không đầy đủ!");
						} catch (FileNotFoundException e) {
							System.out.println("Không mở được file nguồn của client!");
						}
						break;
					case "GET":
						try {
							sf = st.nextToken();
							df = st.nextToken();
							file.openSource(sf);
							bos = new BufferedOutputStream(new FileOutputStream(df));
							byte[] data;
							while ((data = file.readData()) != null) {
								bos.write(data, 0, data.length);
							}
							file.closeSource();
							bos.close();
						} catch (NoSuchElementException e) {
							System.out.println("Cú pháp không đầy đủ!");
						}
						break;

					default:
						System.out.println("Cú pháp sai!");
						break;
					}
				}
			} catch (RemoteException e) {
				System.out.println(e.getMessage());
			}
		}
		userIn.close();
	}
	public static void main(String[] args) throws NotBoundException, IOException {
		FileClient fc = new FileClient();
		fc.start();
	}
}
