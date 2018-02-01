import java.rmi.*;

public interface Request_history_itf extends Remote {
	public void requestHistory(Accounting_itf client) throws RemoteException;
}
