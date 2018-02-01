import java.rmi.*;


public class Recup_impl implements Recup_itf{
    public void recupMessage(String message)  throws RemoteException{
        System.out.println(message);
    }

}
