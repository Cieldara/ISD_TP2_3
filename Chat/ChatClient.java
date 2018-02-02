import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.Scanner;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.*;

public class ChatClient extends Application{

    public void start(Stage stage){
        try {
          if (getParameters().getRaw().size() < 2) {
           System.out.println("Usage: java HelloClient <rmiregistry host>");
           return;}

           String host = getParameters().getRaw().get(0);

           Client client = new Client(getParameters().getRaw().get(1));
           Accounting_itf a_stub = (Accounting_itf) UnicastRemoteObject.exportObject(client, 0);

           Registry registry = LocateRegistry.getRegistry(host);
           Chat_itf chat = (Chat_itf)registry.lookup("ChatService");



           BorderPane root = new BorderPane();
           ListView displayArea = new ListView();
           ScrollPane displayContainer = new ScrollPane(displayArea);
           BorderPane inputPane = new BorderPane();

           TextField messageField = new TextField();
           Button send = new Button();
           inputPane.setCenter(messageField);
           inputPane.setRight(send);
           root.setCenter(displayContainer);
           root.setBottom(inputPane);

           stage.setTitle("We-llBehaved Chat");
           stage.setScene(new Scene(root));
           stage.show();
           send.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    try{
                        chat.sendMessage(a_stub,messageField.getText());
                    }
                    catch(Exception ex){

                    }
                    messageField.clear();
                }
            });

            Recup_impl r = new Recup_impl(displayArea);
            Recup_itf chat_stub = (Recup_itf) UnicastRemoteObject.exportObject(r, 0);
            registry.bind("RecupService"+getParameters().getRaw().get(1), chat_stub);

            chat.join(client);

           Scanner scanner = new Scanner(System.in);
           String res;
           boolean fin = false;
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

        }catch (Exception e)  {
            System.err.println("Error on client: " + e);
        }
    }
    public static void main(String [] args) {
        launch(args);

    }
}
