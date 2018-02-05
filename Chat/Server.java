
import java.rmi.server.*;
import java.rmi.registry.*;

public class Server {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry();
            Chat_itf chat_stub = (Chat_itf) UnicastRemoteObject.exportObject(new Chat(registry), 0);
            registry.bind("ChatService", chat_stub);

            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Error on server :" + e);
            e.printStackTrace();
        }
    }
}
