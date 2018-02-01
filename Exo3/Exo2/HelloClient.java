import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class HelloClient {
  public static void main(String [] args) {

	try {
	  if (args.length < 2) {
	   System.out.println("Usage: java HelloClient <rmiregistry host> <Name>");
	   return;}

	String host = args[0];

    Client client = new Client(args[1]);
    Info_itf i_stub = (Info_itf) UnicastRemoteObject.exportObject(client, 0);


	// Get remote object reference
	Registry registry = LocateRegistry.getRegistry(host);
	Hello h = (Hello) registry.lookup("HelloService");

	// Remote method invocation
	String res = h.sayHello(i_stub);
	System.out.println(res);

    System.exit(0);

	} catch (Exception e)  {
		System.err.println("Error on client: " + e);
	}
  }
}
