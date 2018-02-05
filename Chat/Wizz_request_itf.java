
import java.rmi.RemoteException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Utilisateur
 */
public interface Wizz_request_itf {
        public void wizz(Accounting_itf sender, String receiver)throws RemoteException ;

}
