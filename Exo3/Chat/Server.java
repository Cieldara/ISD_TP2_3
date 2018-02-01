import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class HelloServer {

  public static void  main(String [] args) {
	  try {
          Registry registry= LocateRegistry.getRegistry();

          System.out.println ("Server ready");
      } catch (Exception e) {
          System.err.println("Error on server :" + e) ;
          e.printStackTrace();
      }
  }
}
