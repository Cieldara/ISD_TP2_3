
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

    public boolean join(Accounting_itf client) throws RemoteException {

        if (!isNotUsed(client.getName())) {
            return false;
        }
        System.out.println(client.getName() + " joined !");
        client_list.add(client);
        try {
            for (int i = 0; i < client_list.size(); i++) {
                Recup_itf chat = (Recup_itf) reg.lookup("RecupService" + client_list.get(i).getName());
                chat.recupMessage(client.getName() + " joined !", "green");
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
                chat.recupMessage(client.getName() + " left !", "green");
            }
        } catch (Exception e) {

        }
    }

    public void sendMessage(Accounting_itf client, String message) throws RemoteException {
        String mess = "<" + client.getName() + "> " + message;
        try {
            for (int i = 0; i < client_list.size(); i++) {
                Recup_itf chat = (Recup_itf) reg.lookup("RecupService" + client_list.get(i).getName());
                chat.recupMessage(mess, "black");
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
                chat.recupMessage(history.get(i), "purple");
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void whisper(Accounting_itf sender, String receiver, String message) throws RemoteException, AccessException {
        try {
            Recup_itf senderChat = (Recup_itf) reg.lookup("RecupService" + sender.getName());
            if (!isNotUsed(receiver)) {
                Recup_itf receiverChat = (Recup_itf) reg.lookup("RecupService" + receiver);
                receiverChat.recupMessage(sender.getName() + " says to you : " + message, "blue");
                senderChat.recupMessage("You say to " + receiver + " : " + message, "blue");
            } else {
                senderChat.recupMessage(receiver + " is not known !", "red");
            }

        } catch (NotBoundException ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void wizz(Accounting_itf sender, String receiver) throws RemoteException, AccessException {
        try {
                 
            if (!isNotUsed(receiver)) {
                Wizz_itf wizzService = (Wizz_itf) reg.lookup("WizzService"+receiver);
                wizzService.wizz();
            } else {
                System.out.println("Error ! ");
                Recup_itf senderChat = (Recup_itf) reg.lookup("RecupService" + sender.getName());
                senderChat.recupMessage(receiver + " is not known !", "red");
            }
        } catch (NotBoundException ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
