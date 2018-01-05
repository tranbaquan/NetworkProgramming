package tbq.rmi.download;

import java.rmi.*;

public interface IDownload extends Remote{
	void openSource(String sf) throws RemoteException;
	byte[] readData() throws RemoteException;
	void closeFile() throws RemoteException;
}
