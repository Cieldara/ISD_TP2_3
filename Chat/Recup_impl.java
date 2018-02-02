import java.rmi.*;
import javafx.scene.control.ListView;


public class Recup_impl implements Recup_itf{
    private ListView list;

    public Recup_impl(ListView l){
        this.list = l;
    }

    public void recupMessage(String message)  throws RemoteException{
        System.out.println(message);
	l.add(message);

    }

}
