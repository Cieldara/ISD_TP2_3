import java.rmi.*;
import javafx.application.Platform;
import javafx.scene.control.ListView;


public class Recup_impl implements Recup_itf{
    private ListView list;

    public Recup_impl(ListView l){
        this.list = l;
    }

    public void recupMessage(String message)  throws RemoteException{
        System.out.println(message);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                list.getItems().add(message);            }
        });
    }

}
