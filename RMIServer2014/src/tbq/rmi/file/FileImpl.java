package tbq.rmi.file;

import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;

import tbq.rmi.model.Data;
import tbq.rmi.model.User;

public class FileImpl extends UnicastRemoteObject implements IFile {
	/**
	 * 
	 */
	ArrayList<User> data;
	User user;
	private static final long serialVersionUID = 1L;
	BufferedOutputStream bos;
	BufferedInputStream bis;

	protected FileImpl() throws RemoteException {
		super();
		data = Data.getData();
		user = null;
	}

	@Override
	public boolean findName(String name) throws RemoteException {
		for (User u : data) {
			if (u.getName().equals(name)) {
				this.user = new User(name, null);
				return true;
			}
		}
		this.user = null;
		return false;
	}

	@Override
	public boolean checkPass(String pass) throws RemoteException {
		if (user == null) {
			throw new RemoteException("Bạn chưa nhập tên, vui lòng nhập tên theo cú pháp: TEN ten_nguoi_dung.");
		}
		for (User u : data) {
			if (u.getName().equals(this.user.getName()) && u.getPass().equals(pass)) {
				this.user.setPass(pass);
				return true;
			}
		}
		return false;
	}

	@Override
	public void createDest(String df) throws RemoteException {
		try {
			bos = new BufferedOutputStream(new FileOutputStream(df));
		} catch (FileNotFoundException e) {
			throw new RemoteException("Không tạo được file đích!");
		}
	}

	@Override
	public void writeData(byte[] buff, int length) throws RemoteException {
		try {
			bos.write(buff, 0, length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void closeDest() throws RemoteException {
		try {
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void openSource(String sf) throws RemoteException {
		try {
			bis = new BufferedInputStream(new FileInputStream(sf));
		} catch (FileNotFoundException e) {
			throw new RemoteException("Không tìm thấy file nguồn!");
		}
	}

	@Override
	public byte[] readData() throws RemoteException {
		try {
			byte[] buff = new byte[10 * 1024];
			int length = bis.read(buff);
			if (length == -1)
				return null;
			byte[] result = new byte[length];
			System.arraycopy(buff, 0, result, 0, length);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void closeSource() throws RemoteException {
		try {
			bis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
