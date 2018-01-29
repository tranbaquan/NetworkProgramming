
package tbq.rmi.file;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.*;
import java.util.ArrayList;

import tbq.rmi.data.Data;
import tbq.rmi.model.User;

public class FileImpl extends UnicastRemoteObject implements IFile {
	ArrayList<User> data;
	User user;
	BufferedInputStream bis;
	BufferedOutputStream bos;

	protected FileImpl() throws RemoteException {
		super();
		data = Data.createData();
		user = new User();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean findName(String name) throws RemoteException {
		if (name == null || "".equals(name)) {
			throw new RemoteException("Syntax error! Please re-enter username via syntax: \"TEN username\".");
		}
		for (User user : data) {
			if (user.getName().equals(name)) {
				this.user.setName(name);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean checkPass(String pass) throws RemoteException {
		if (this.user.getName() == null || "".equals(this.user.getName())) {
			throw new RemoteException("Username is not exist, please enter username via syntax: \"TEN username\".");
		}
		if (pass == null || "".equals(pass)) {
			throw new RemoteException("Syntax error! Please re-enter pass via syntax: \"MATKHAU pass\".");
		}
		for (User user : data) {
			if (user.getName().equals(this.user.getName()) && user.getPass().equals(pass)) {
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
			throw new RemoteException("Cannot create dest file");
		}
	}

	@Override
	public void openSource(String sf) throws RemoteException {
		try {
			bis = new BufferedInputStream(new FileInputStream(sf));
		} catch (FileNotFoundException e) {
			throw new RemoteException("source not found!");
		}
	}

	@Override
	public byte[] readData() throws RemoteException {
		byte[] data = new byte[100 * 2024];
		int count;
		try {
			count = bis.read(data);
			if (count == -1)
				return null;
			byte[] tmp = new byte[count];
			System.arraycopy(data, 0, tmp, 0, count);
			return tmp;
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}
	}

	@Override
	public void writeData(byte[] data, int len) throws RemoteException {
		try {
			bos.write(data, 0, len);
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}
	}

	@Override
	public void closeSource() throws RemoteException {
		try {
			bis.close();
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}
	}

	@Override
	public void closeDest() throws RemoteException {
		try {
			bos.close();
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}
	}

}
