package tbq.rmi.upload;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class UploadServer {
	public static void main(String[] args) throws RemoteException {
		Registry reg = LocateRegistry.createRegistry(12345);
		UploadImpl uploadImpl = new UploadImpl();
		reg.rebind("upload", uploadImpl);
	}
}
