import java.rmi.*;


public interface Join_itf extends Remote {
public void join(Accounting_itf client) throws RemoteException;
}
