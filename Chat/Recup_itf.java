import java.rmi.*;

public interface Recup_itf extends Remote {
	public void recupMessage(String message)  throws RemoteException;
}