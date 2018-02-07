
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.ArrayList;

public class Server {

    public static void main(String[] args) {
        try {
            File historyFile = new File("history.txt");
            ArrayList<String> history = new ArrayList<>();
            if (!historyFile.exists()) {
                historyFile.createNewFile();
            } else {
                FileInputStream is = new FileInputStream(historyFile);
                InputStreamReader isw = new InputStreamReader(is);
                BufferedReader r = new BufferedReader(isw);
                String tmp = r.readLine();
                while(tmp != null){
                    history.add(tmp);
                    tmp = r.readLine();
                }
            }
            PrintWriter writer = new PrintWriter(new FileWriter(historyFile, true));
            Registry registry = LocateRegistry.getRegistry();
            Chat_itf chat_stub = (Chat_itf) UnicastRemoteObject.exportObject(new Chat(history,writer), 0);
            registry.bind("ChatService", chat_stub);

            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Error on server :" + e);
            e.printStackTrace();
        }
    }
}
