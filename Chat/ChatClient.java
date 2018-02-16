
import java.rmi.RemoteException;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ListView;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;

public class ChatClient extends Application {

    String username;

    public static void sendMessage(TextField messageField, Chat_itf chat, Accounting_itf client, Registry registry, Accounting_itf a_stub) {
        try {
            String message = messageField.getText();
            String decomposition[] = message.split(" ");
            if (message.equals("/exit")) {
                chat.leave(client);
                Platform.exit();
                System.exit(0);
            } else if (message.equals("/history")) {
                chat.requestHistory(a_stub);
            } else if (decomposition[0].equals("/whisper")) {
                if (decomposition.length >= 3) {
                    String realMessage = "";
                    for (int i = 2; i < decomposition.length; i++) {
                        realMessage += decomposition[i] + " ";
                    }
                    chat.whisper(client, decomposition[1], realMessage);
                }
            } else if (decomposition[0].equals("/wizz")) {
                if (decomposition.length == 2) {
                    chat.wizz(client, decomposition[1]);
                }
            } else {
                chat.sendMessage(a_stub, messageField.getText());
            }
        } catch (Exception ex) {

        }
        messageField.clear();
    }

    public void start(Stage stage) {
        try {
            if (getParameters().getRaw().size() < 1) {
                System.out.println("Usage: java ChatClient <rmiregistry host>");
                return;
            }

            String host = getParameters().getRaw().get(0);
            username = "Anonymous";
            ListView displayArea = new ListView();
            /* Interface du tchat */
            BorderPane root = new BorderPane();

            VBox online = new VBox();
            online.prefWidthProperty().bind(root.widthProperty().multiply(0.25));
            online.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0))));
            TextField messageField = new TextField();
            Client client = new Client(username, displayArea, root, online, messageField);
            Accounting_itf a_stub = (Accounting_itf) UnicastRemoteObject.exportObject(client, 0);

            Registry registry = LocateRegistry.getRegistry(host);
            Chat_itf chat = (Chat_itf) registry.lookup("ChatService");

            BorderPane inputPane = new BorderPane();

            Button send = new Button("Send");
            inputPane.setCenter(messageField);
            inputPane.setRight(send);
            BorderPane infoPane = new BorderPane();
            Text name = new Text();
            Text serverName = new Text("We-llBehaved Chat");
            infoPane.setRight(serverName);
            infoPane.setLeft(name);
            root.setTop(infoPane);
            root.setCenter(displayArea);
            root.setLeft(online);
            root.setBottom(inputPane);

            send.setOnAction((ActionEvent e) -> {
                sendMessage(messageField, chat, client, registry, a_stub);
            });

            messageField.setOnKeyPressed((KeyEvent ke) -> {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    sendMessage(messageField, chat, client, registry, a_stub);
                }
            });

            /* Interface de connection */
            BorderPane rootConnection = new BorderPane();
			rootConnection.setStyle("-fx-background-color: #FFFFFF;");
            BorderPane fieldPane = new BorderPane();
            TextField userNameField = new TextField();
            userNameField.setPromptText("Enter your pseudonyme !");
            Button connectionButton = new Button("Connection");
            rootConnection.setCenter(fieldPane);
            fieldPane.setCenter(new ImageView(new Image("baby.png")));
            fieldPane.setBottom(userNameField);
            rootConnection.setBottom(connectionButton);
            rootConnection.setTop(new Text("We-llBehaved Chat"));
            stage.setTitle("We-llBehaved Chat");
            Scene scene = new Scene(rootConnection);
            connectionButton.setOnAction((ActionEvent event) -> {
                username = userNameField.getText();
                try {
                    client.setName(username);
                    if (chat.join(client)) {
                        name.setText("Logged as " + username);
                        scene.setRoot(root);
                    } else {
                        Shaker shaker = new Shaker(userNameField);
                        shaker.shake();
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            stage.setScene(scene);
            stage.setWidth(500);
            stage.setHeight(500);
            stage.show();
            rootConnection.requestFocus();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent event) {
                    try {
                        chat.leave(client);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Platform.exit();
                    System.exit(0);

                }
            });
        } catch (Exception e) {
            System.err.println("Error on client: " + e);
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}
