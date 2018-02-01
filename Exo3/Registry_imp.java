import java.util.*;
import java.rmi.*;

public  class Registry_imp implements Registry_itf {

	ArrayList<Accounting_itf> client_list;

	public Registry_imp() {
        client_list = new ArrayList<>();
	}

	public void register(Accounting_itf client) throws RemoteException{
		System.out.println(client.getName() + " registered !");
        client_list.add(client);
    }
}
