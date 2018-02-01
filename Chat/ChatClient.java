import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.Scanner;

public class ChatClient {
    public static void main(String [] args) {

        try {
          if (args.length < 2) {
           System.out.println("Usage: java HelloClient <rmiregistry host>");
           return;}

           String host = args[0];

           Client client = new Client(args[1]);
           Accounting_itf a_stub = (Accounting_itf) UnicastRemoteObject.exportObject(client, 0);

           Registry registry = LocateRegistry.getRegistry(host);
           Chat_itf chat = (Chat_itf)registry.lookup("ChatService");

           Recup_impl r = new Recup_impl();
           Recup_itf chat_stub = (Recup_itf) UnicastRemoteObject.exportObject(r, 0);
           registry.bind("RecupService"+args[1], chat_stub);

           chat.join(client);

           Scanner scanner = new Scanner(System.in);
           String res;
           boolean fin = false;

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
           registry.unbind("RecupService"+args[1]);
           System.exit(0);

        }catch (Exception e)  {
            System.err.println("Error on client: " + e);
        }
    }
}
