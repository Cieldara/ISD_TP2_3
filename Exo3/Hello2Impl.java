
import java.rmi.*;

public class Hello2Impl implements Hello2 {

	private String message;

	public Hello2Impl(String s) {
		message = s ;
	}

	public String sayHello(Accounting_itf client) throws RemoteException {
		int new_nb = client.getnbCall()+1;
		client.setnbCall(new_nb);
		String s =  client.getName() + " " + message;
		if(new_nb%10 == 0){
			s += "\n Wow ! you reached " + new_nb + " calls !"; 
		}
		return s;
	}
}
