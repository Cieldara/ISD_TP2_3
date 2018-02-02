
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.*;

public class ChatClient extends Application {

    String username;

    public void start(Stage stage) {
        try {
            if (getParameters().getRaw().size() < 1) {
                System.out.println("Usage: java HelloClient <rmiregistry host>");
                return;
            }

            String host = getParameters().getRaw().get(0);
            username = "Anonymous";

            Client client = new Client(username);
            Accounting_itf a_stub = (Accounting_itf) UnicastRemoteObject.exportObject(client, 0);

            Registry registry = LocateRegistry.getRegistry(host);
            Chat_itf chat = (Chat_itf) registry.lookup("ChatService");

            BorderPane root = new BorderPane();
            ListView displayArea = new ListView();
            ScrollPane displayContainer = new ScrollPane(displayArea);
            BorderPane inputPane = new BorderPane();

            TextField messageField = new TextField();
            Button send = new Button("Send");
            inputPane.setCenter(messageField);
            inputPane.setRight(send);
            root.setCenter(displayContainer);
            root.setBottom(inputPane);

            send.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try {
                        String message = messageField.getText();
                        if (message.equals("/exit")) {
                            chat.leave(client);
                            registry.unbind("RecupService" + client.getName());
                            System.exit(0);
                        } else if (message.equals("/history")) {
                            chat.requestHistory(a_stub);
                        } else {
                            chat.sendMessage(a_stub, messageField.getText());
                        }
                    } catch (Exception ex) {

                    }
                    messageField.clear();
                }
            });

            BorderPane rootConnection = new BorderPane();
            TextField userNameField = new TextField();
            Button connectionButton = new Button("Connection");
            rootConnection.setTop(userNameField);
            rootConnection.setBottom(connectionButton);
            stage.setTitle("We-llBehaved Chat");
            Scene scene = new Scene(rootConnection);
            connectionButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    username = userNameField.getText();
                    try {
                        client.setName(username);
                        if (chat.join(client)) {
                            Recup_impl r = new Recup_impl(displayArea);
                            Recup_itf chat_stub = (Recup_itf) UnicastRemoteObject.exportObject(r, 0);
                            registry.bind("RecupService" + client.getName(), chat_stub);
                            scene.setRoot(root);
                        }
                    } catch (RemoteException | AlreadyBoundException ex) {
                        ex.printStackTrace();
                        Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            stage.setScene(scene);
            stage.setWidth(500);
            stage.setHeight(500);
            stage.show();

            /*
           while(!fin){

               String message = scanner.nextLine();
               if(message.equals("/exit")){
                   fin = true;
               }
               else if(message.equals("/history")){
                   chat.requestHistory(a_stub);
               }
               else{
                   chat.sendMessage(a_stub,message);
               }

           }

           chat.leave(client);
           registry.unbind("RecupService"+getParameters().getRaw().get(1));
           System.exit(0);*/
        } catch (Exception e) {
            System.err.println("Error on client: " + e);
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}
