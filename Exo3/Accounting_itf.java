import java.rmi.*;

public interface Accounting_itf extends Remote {
public void numberOfCalls(int number) throws RemoteException;
public int getnbCall() throws RemoteException;
public String getName() throws RemoteException;
public void setnbCall(int nb) throws RemoteException;
}
