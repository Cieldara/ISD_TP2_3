import java.rmi.*;

public interface Message_itf extends Remote {
	public void sendMessage(Accounting_itf client, String message)  throws RemoteException;
}
