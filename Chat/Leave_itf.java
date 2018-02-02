
import java.rmi.*;

public interface Leave_itf extends Remote {

    public void leave(Accounting_itf client) throws RemoteException;
}
