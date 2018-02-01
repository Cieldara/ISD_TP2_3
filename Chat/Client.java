import java.rmi.*;

public  class Client implements Accounting_itf {

    private String name;

    public Client(String name){
        this.name = name;
    }

    public String getName() throws RemoteException{
        return this.name;
    }
}
