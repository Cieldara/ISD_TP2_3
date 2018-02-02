
import java.rmi.*;
import java.util.*;
import java.rmi.registry.*;

public class Chat implements Chat_itf {

    private ArrayList<Accounting_itf> client_list;
    private ArrayList<String> history;
    private Registry reg;

    public Chat(Registry reg) {
        this.client_list = new ArrayList<>();
        this.history = new ArrayList<>();
        this.reg = reg;
    }

    public boolean isNotUsed(Accounting_itf client) throws RemoteException {
        boolean ok = true;
        for (int i = 0; i < client_list.size() && ok; i++) {
            if (client.getName().equals(client_list.get(i).getName())) {
                ok = false;
            }
        }
        return ok;
    }

    public boolean join(Accounting_itf client) throws RemoteException {

        if (!isNotUsed(client)) {
            return false;
        }
        System.out.println(client.getName() + " joined !");
        client_list.add(client);
        try {
            for (int i = 0; i < client_list.size(); i++) {
                Recup_itf chat = (Recup_itf) reg.lookup("RecupService" + client_list.get(i).getName());
                chat.recupMessage(client.getName() + " joined !");
            }
        } catch (Exception e) {

        }
        return true;
    }

    public void leave(Accounting_itf client) throws RemoteException {
        System.out.println(client.getName() + " left !");
        client_list.remove(client);
        try {
            for (int i = 0; i < client_list.size(); i++) {
                Recup_itf chat = (Recup_itf) reg.lookup("RecupService" + client_list.get(i).getName());
                chat.recupMessage(client.getName() + " left !");
            }
        } catch (Exception e) {

        }
    }

    public void sendMessage(Accounting_itf client, String message) throws RemoteException {
        String mess = "<" + client.getName() + "> " + message;
        try {
            for (int i = 0; i < client_list.size(); i++) {
                Recup_itf chat = (Recup_itf) reg.lookup("RecupService" + client_list.get(i).getName());
                chat.recupMessage(mess);
            }
        } catch (Exception e) {

        }
        history.add(mess);
        System.out.println(mess);
    }

    public void requestHistory(Accounting_itf client) throws RemoteException {
        try {
            Recup_itf chat = (Recup_itf) reg.lookup("RecupService" + client.getName());
            for (int i = 0; i < history.size(); i++) {
                chat.recupMessage(history.get(i));
            }
        } catch (Exception e) {

        }
    }
}
