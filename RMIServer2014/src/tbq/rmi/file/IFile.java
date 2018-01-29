package tbq.rmi.file;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IFile extends Remote {
	boolean findName(String name) throws RemoteException;
	boolean checkPass(String pass) throws RemoteException;
	void createDest(String df) throws RemoteException;
	void writeData(byte[] buff, int length) throws RemoteException;
	void closeDest() throws RemoteException;
	void openSource(String sf) throws RemoteException;
	byte[] readData() throws RemoteException;
	void closeSource() throws RemoteException;
	
}
