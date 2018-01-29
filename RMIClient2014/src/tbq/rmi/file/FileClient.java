package tbq.rmi.file;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.StringTokenizer;

public class FileClient {
	BufferedReader userIn;

	public void start() throws NotBoundException, RemoteException {
		Registry reg = LocateRegistry.getRegistry("127.0.0.1", 12345);
		IFile file = (IFile) reg.lookup("File");
		userIn = new BufferedReader(new InputStreamReader(System.in));
		String line;
		while (true) {
			try {
				line = userIn.readLine();
				if ("QUIT".equalsIgnoreCase(line)) {
					break;
				}
				analysis(line, file);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private void analysis(String line, IFile file) throws Exception {
		StringTokenizer st = new StringTokenizer(line);
		String command = st.nextToken();
		String sf, df;
		switch (command) {
		case "TEN":
			String name = st.nextToken();
			try {
				if (file.findName(name)) {
					System.out.println("Username correct!");
				} else {
					System.out.println("Username incorrect!");
				}
			} catch (RemoteException e) {
				throw new Exception(e.getMessage());
			}
			break;
		case "MATKHAU":
			String pass = st.nextToken();
			try {
				
				if (file.checkPass(pass)) {
					System.out.println("Pass correct!");
				} else {
					System.out.println("Pass incorrect!");
				}
			} catch (RemoteException e) {
				throw new Exception(e.getMessage());
			}
			break;
		case "SEND":
			sf = st.nextToken();
			df = st.nextToken();
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sf));
			try {
				file.createDest(df);
				int data;
				byte[] buff = new byte[100 * 1024];
				while ((data = bis.read(buff)) != -1) {
					file.writeData(buff, data);
				}
				file.closeDest();
				bis.close();
			} catch (RemoteException e) {
				throw new Exception(e.getMessage());
			}

			break;
		case "GET":
			sf = st.nextToken();
			df = st.nextToken();
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(df));
			try {
				file.openSource(sf);
				byte[] data;
				while ((data = file.readData()) != null) {
					dos.write(data);
				}
				file.closeSource();
				dos.close();
			} catch (RemoteException e) {
				throw new Exception(e.getMessage());
			}
			break;

		default:
			break;
		}
	}

	public static void main(String[] args) throws RemoteException, NotBoundException {
		FileClient fileClient = new FileClient();
		fileClient.start();
	}
}
