package tbq.rmi.file;

import java.rmi.*;

public interface IFile extends Remote {
	boolean findName(String name) throws RemoteException;
	boolean checkPass(String pass) throws RemoteException;
	void createDest(String df) throws RemoteException;
	void openSource(String sf) throws RemoteException;
	byte[] readData() throws RemoteException;
	void writeData(byte[] data, int len) throws RemoteException;
	void closeSource() throws RemoteException;
	void closeDest() throws RemoteException;
}
