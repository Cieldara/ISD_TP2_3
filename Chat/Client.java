
import java.rmi.*;
import java.util.Objects;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Client implements Accounting_itf {

    private String name;
    private final ListView list;
    private final Pane pane;
    private final VBox onlinePeople;
    private final TextField messageField;

    public Client(String name, ListView l, Pane p, VBox online, TextField m) {
        this.name = name;
        this.list = l;
        this.pane = p;
        this.onlinePeople = online;
        this.messageField = m;
    }

    public void setName(String name) throws RemoteException {
        this.name = name;
    }

    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    public void recupMessage(String message, String color) throws RemoteException {
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
                list.scrollTo(list.getItems().size() - 1);
            }
        });

    }

    @Override
    public void wizz() throws RemoteException {
        Shaker shaker = new Shaker(pane);
        shaker.shake();
    }

    @Override
    public void addSomeone(String name) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("Add ok");
                Text text = new Text(name);
                text.setCursor(Cursor.HAND);
                text.setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        messageField.setText("/whisper " + text.getText());
                        messageField.requestFocus();
                    }
                });
                onlinePeople.getChildren().add(text);
            }
        });
    }

    @Override
    public void removeSomeone(String name) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < onlinePeople.getChildren().size(); i++) {
                    Text t = (Text) onlinePeople.getChildren().get(i);
                    if (t.getText().equals(name)) {
                        onlinePeople.getChildren().remove(i);
                    }
                }
            }
        });

    }
}
