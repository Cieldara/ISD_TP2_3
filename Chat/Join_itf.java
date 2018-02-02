
import java.rmi.*;

public interface Join_itf extends Remote {

    public boolean join(Accounting_itf client) throws RemoteException;
}
