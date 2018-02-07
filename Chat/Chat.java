
import java.rmi.*;
import java.util.*;
import java.rmi.registry.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Chat implements Chat_itf {

    private ArrayList<Accounting_itf> client_list;
    private ArrayList<String> history;
    private Registry reg;

    public Chat(Registry reg) {
        this.client_list = new ArrayList<>();
        this.history = new ArrayList<>();
        this.reg = reg;
    }

    public boolean isNotUsed(String client) throws RemoteException {
        boolean ok = true;
        for (int i = 0; i < client_list.size() && ok; i++) {
            if (client.equals(client_list.get(i).getName())) {
                ok = false;
            }
        }
        return ok;
    }

    public Accounting_itf findClient(String client) throws RemoteException {
        Accounting_itf person = null;
        for (int i = 0; i < client_list.size() && person == null; i++) {
            if (client_list.get(i).getName().equals(client)) {
                person = client_list.get(i);
            }
        }
        return person;
    }

    public boolean join(Accounting_itf client) throws RemoteException {

        if (!isNotUsed(client.getName())) {
            return false;
        }
        System.out.println(client.getName() + " joined !");
        client_list.add(client);
        try {
            for (int i = 0; i < client_list.size(); i++) {
                //Recup_itf chat = (Recup_itf) reg.lookup("RecupService" + client_list.get(i).getName());
                client.recupMessage(client.getName() + " joined !", "green");
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
                //Recup_itf chat = (Recup_itf) reg.lookup("RecupService" + client_list.get(i).getName());
                client.recupMessage(client.getName() + " left !", "green");
            }
        } catch (Exception e) {

        }
    }

    public void sendMessage(Accounting_itf client, String message) throws RemoteException {
        String mess = "<" + client.getName() + "> " + message;
        try {
            for (int i = 0; i < client_list.size(); i++) {
                client_list.get(i).recupMessage(mess, "black");
            }
        } catch (Exception e) {

        }
        history.add(mess);
        System.out.println(mess);
    }

    public void requestHistory(Accounting_itf client) throws RemoteException {
        try {
            //Recup_itf chat = (Recup_itf) reg.lookup("RecupService" + client.getName());
            for (int i = 0; i < history.size(); i++) {
                client.recupMessage(history.get(i), "purple");
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void whisper(Accounting_itf sender, String receiver, String message) throws RemoteException, AccessException {
        if (!isNotUsed(receiver)) {
            Accounting_itf receiverClient = findClient(receiver);
            receiverClient.recupMessage(sender.getName() + " says to you : " + message, "blue");
            sender.recupMessage("You say to " + receiver + " : " + message, "blue");

        } else {
            sender.recupMessage(receiver + " is not known !", "red");
        }
    }

    @Override
    public void wizz(Accounting_itf sender, String receiver) throws RemoteException, AccessException {
        if (!isNotUsed(receiver)) {
            Accounting_itf receiverW = findClient(receiver);
            receiverW.wizz();
        } else {
            System.out.println("Error ! ");
            sender.recupMessage(receiver + " is not known !", "red");
        }
    }
}
