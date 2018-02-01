import java.rmi.*;

public  class Client implements Accounting_itf {

    private String name;
    private int nbCall;


    public Client(String name){
        this.name = name;
        this.nbCall = 0;
    }

    public void setnbCall(int nb){
        this.nbCall = nb;
    }

    public int getnbCall(){
        return this.nbCall;
    }

    public void numberOfCalls(int number) throws RemoteException{
        System.out.println("I called " + number + " times !");
    }

    public String getName() throws RemoteException{
        return this.name;
    }
}
