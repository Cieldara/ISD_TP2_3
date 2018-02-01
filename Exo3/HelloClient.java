import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.Scanner;

public class HelloClient {
  public static void main(String [] args) {

	try {
	  if (args.length < 2) {
	   System.out.println("Usage: java HelloClient <rmiregistry host>");
	   return;}

	String host = args[0];

    Client client = new Client(args[1]);
    Accounting_itf a_stub = (Accounting_itf) UnicastRemoteObject.exportObject(client, 0);


	// Get remote object reference
	Registry registry = LocateRegistry.getRegistry(host);
    Registry_itf reg = (Registry_itf)registry.lookup("RegisterService");
    Hello h = (Hello) registry.lookup("HelloService");
    Hello2 h2 = (Hello2) registry.lookup("Hello2Service");

    reg.register(client);

    Scanner scanner = new Scanner(System.in);
    String res;

    while(true){

        System.out.println("Choose your Hello :\n- 1 pour Hello\n- 2 pour Hello2\n- Autre chose pour quitter");
        String choice = scanner.next();

        switch (choice){
            case "1":
                res = h.sayHello();
                System.out.println(res);
            break;
            case "2":
                res = h2.sayHello(a_stub);
                System.out.println(res);
            break;
            default:
                System.exit(0);
            break;
        }

    }
	//Hello h = (Hello) registry.lookup("HelloService");

	//

	} catch (Exception e)  {
		System.err.println("Error on client: " + e);
	}
  }
}
