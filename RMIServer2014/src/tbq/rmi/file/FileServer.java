

package tbq.rmi.file;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FileServer {
	public static void main(String[] args) throws RemoteException {
		Registry registry = LocateRegistry.createRegistry(12345);
		FileImpl fileImpl = new FileImpl();
		registry.rebind("File", fileImpl);
	}
}
