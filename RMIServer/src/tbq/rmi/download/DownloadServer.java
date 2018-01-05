package tbq.rmi.download;

import java.rmi.*;
import java.rmi.registry.*;

public class DownloadServer {
	public static void main(String[] args) throws RemoteException {
		Registry reg = LocateRegistry.createRegistry(1234);
		DownloadImpl downloadImpl = new DownloadImpl();
		reg.rebind("download", downloadImpl);
	}
}
