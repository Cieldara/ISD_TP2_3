
import java.rmi.*;

public interface Chat_itf extends Remote {

    public boolean join(Accounting_itf client) throws RemoteException;

    public void leave(Accounting_itf client) throws RemoteException;

    public void requestHistory(Accounting_itf client) throws RemoteException;

    public void whisper(Accounting_itf sender, String receiver, String message) throws RemoteException;

    public void wizz(Accounting_itf sender, String receiver) throws RemoteException;

    public void sendMessage(Accounting_itf client, String message) throws RemoteException;

}
