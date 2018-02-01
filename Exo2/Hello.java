import java.rmi.*;

public interface Hello extends Remote {
	public String sayHello(Info_itf info)  throws RemoteException;
}
