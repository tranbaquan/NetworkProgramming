package tbq.rmi.download;

import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

public class DownloadImpl extends UnicastRemoteObject implements IDownload {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedInputStream bis;
	protected DownloadImpl() throws RemoteException {
		super();
	}

	@Override
	public void openSource(String sf) throws RemoteException {
		try {
			bis = new BufferedInputStream(new FileInputStream(sf));
		} catch (FileNotFoundException e) {
			throw new RemoteException(e.getMessage());
		}
	}

	@Override
	public byte[] readData() throws RemoteException {
		byte[] data = new byte[100*2024];
		int count;
		try {
			count = bis.read(data);
			if(count == -1) return null;
			byte[] tmp = new byte[count];
			System.arraycopy(data, 0, tmp, 0, count);
			return tmp;
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}
	}

	@Override
	public void closeFile() throws RemoteException {
		try {
			bis.close();
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}
	}

}
