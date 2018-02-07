
import java.rmi.*;

public interface Accounting_itf extends Remote {

    public String getName() throws RemoteException;

    public void recupMessage(String message, String color) throws RemoteException;

    public void wizz() throws RemoteException;

}
