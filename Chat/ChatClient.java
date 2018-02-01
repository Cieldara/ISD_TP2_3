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


        }catch (Exception e)  {
            System.err.println("Error on client: " + e);
        }
    }
}
