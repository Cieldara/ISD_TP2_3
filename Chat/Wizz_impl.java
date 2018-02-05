
import java.rmi.RemoteException;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Utilisateur
 */

//Maybe a wizz service ? :)
    class Shaker {
        private TranslateTransition tt;

        public Shaker(Node node) {
            tt = new TranslateTransition(Duration.millis(50), node);
            tt.setFromX(0f);
            tt.setByX(10f);
            tt.setCycleCount(10);
            tt.setAutoReverse(true);
        }

        public void shake() {
            tt.playFromStart();
        }
    }

public class Wizz_impl implements Wizz_itf{
    private BorderPane pane;
    private Shaker shaker;

    public Wizz_impl(BorderPane pane) {
        this.pane = pane;
        shaker = new Shaker(pane);
    }
    
    @Override
    public void wizz() throws RemoteException {
        System.out.println("Wizz !");
        shaker.shake();
    }
}
