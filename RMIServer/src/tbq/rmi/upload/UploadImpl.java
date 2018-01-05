package tbq.rmi.upload;

import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

public class UploadImpl extends UnicastRemoteObject implements IUpload {
	private static final long serialVersionUID = 1L;

	BufferedOutputStream bos;

	protected UploadImpl() throws RemoteException {
		super();
	}

	@Override
	public void openDest(String df) throws RemoteException {
		try {
			bos = new BufferedOutputStream(new FileOutputStream(df));
		} catch (FileNotFoundException e) {
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
	public void close() throws RemoteException {
		try {
			bos.close();
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}
	}

}
