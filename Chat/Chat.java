import java.rmi.*;
import java.util.*;
import java.rmi.registry.*;

public class Chat implements Chat_itf{

    ArrayList<Accounting_itf> client_list;
    Registry reg;

    public Chat(Registry reg){
        this.client_list = new ArrayList<>();
        this.reg = reg;
    }

    public void join(Accounting_itf client) throws RemoteException{
		System.out.println(client.getName() + " joined !");
        client_list.add(client);
        try{
            for(int i = 0; i < client_list.size() ; i++ ){
                Recup_itf chat = (Recup_itf)reg.lookup("RecupService"+client_list.get(i).getName());
                chat.recupMessage(client.getName() + " joined !");
            }
        }
        catch(Exception e){

        }
    }

    public void leave(Accounting_itf client) throws RemoteException{
		System.out.println(client.getName() + " left !");
        client_list.remove(client);
        try{
            for(int i = 0; i < client_list.size() ; i++ ){
                Recup_itf chat = (Recup_itf)reg.lookup("RecupService"+client_list.get(i).getName());
                chat.recupMessage(client.getName() + " left !");
            }
        }
        catch(Exception e){

        }
    }

    public void sendMessage(Accounting_itf client, String message)  throws RemoteException{
        try{
            for(int i = 0; i < client_list.size() ; i++ ){
                Recup_itf chat = (Recup_itf)reg.lookup("RecupService"+client_list.get(i).getName());
                chat.recupMessage("<"+ client.getName() + "> " + message);
            }
        }
        catch(Exception e){

        }
        System.out.println("<"+ client.getName() + "> " + message);
    }

}
