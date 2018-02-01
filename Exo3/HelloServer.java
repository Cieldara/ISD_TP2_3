
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class HelloServer {

  public static void  main(String [] args) {
	  try {

		  // Create a Hello remote object
	    HelloImpl h = new HelloImpl ("Hello world !");
	    Hello h_stub = (Hello) UnicastRemoteObject.exportObject(h, 0);

        Hello2Impl h2 = new Hello2Impl ("Hello world2 !");
        Hello2 h2_stub = (Hello2) UnicastRemoteObject.exportObject(h2, 0);

        Registry_imp reg = new Registry_imp();
        Registry_itf reg_stub = (Registry_itf) UnicastRemoteObject.exportObject(reg, 0);


	    // Register the remote object in RMI registry with a given identifier
	    Registry registry= LocateRegistry.getRegistry();
	    registry.bind("HelloService", h_stub);
	    registry.bind("Hello2Service", h2_stub);
        registry.bind("RegisterService", reg_stub);

	    System.out.println ("Server ready");

	  } catch (Exception e) {
		  System.err.println("Error on server :" + e) ;
		  e.printStackTrace();
	  }
  }
}
