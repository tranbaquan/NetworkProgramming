package tbq.rmi.upload;

import java.rmi.*;

public interface IUpload extends Remote{
	void openDest(String df) throws RemoteException;
	void writeData(byte[] data, int len) throws RemoteException;
	void close() throws RemoteException;
}
