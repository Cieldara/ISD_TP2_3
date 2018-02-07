
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

//Maybe a wizz service ? :)
class Shaker {

    private final TranslateTransition tt;

    public Shaker(Node node) {
        tt = new TranslateTransition(Duration.millis(50), node);
        tt.setFromX(0f);
        tt.setByX(10f);
        tt.setCycleCount(20);
        tt.setAutoReverse(true);
    }

    public void shake() {
        tt.playFromStart();
    }
}
