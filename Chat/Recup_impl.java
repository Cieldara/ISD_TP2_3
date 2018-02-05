
import java.rmi.*;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Recup_impl implements Recup_itf {

    private ListView list;

    public Recup_impl(ListView l) {
        this.list = l;
    }

    public void recupMessage(String message, String color) throws RemoteException {
        System.out.println(message);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Text text = new Text(message);
                Color col;
                switch (color) {
                    case "red":
                        col = Color.RED;
                        break;
                    case "blue":
                        col = Color.BLUE;
                        break;
                    case "green":
                        col = Color.GREEN;
                        break;
                    case "purple":
                        col = Color.PURPLE;
                        break;
                    default:
                        col = Color.BLACK;
                        break;
                }
                text.setFill(col);
                list.getItems().add(text);
            }
        });

    }
}
