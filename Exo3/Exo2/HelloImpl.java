
import java.rmi.*;

public  class HelloImpl implements Hello {

	private String message;

	public HelloImpl(String s) {
		message = s ;
	}

	public String sayHello(Info_itf i) throws RemoteException {
		return message + " " + i.getName();
	}
}
