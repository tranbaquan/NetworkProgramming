package tbq.rmi.file;

import java.rmi.RemoteException;
import java.rmi.registry.*;

public class FileServer {
	public static void main(String[] args) throws RemoteException {
		FileImpl fileImpl = new FileImpl();
		Registry registry = LocateRegistry.createRegistry(12345);
		registry.rebind("File", fileImpl);
	}
}
