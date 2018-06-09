package Model;

import javafx.scene.Parent;
import javafx.stage.Stage;

public class MovableWindow {
    private double xOffset = 0;
    private double yOffset = 0;

    private Stage stage;
    private Parent root;

    public MovableWindow(Stage stage, Parent rootView) {
        this.stage = stage;
        this.root = rootView;
        allowMoving();
    }

    public void allowMoving() {
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX()-xOffset);
            stage.setY(event.getScreenY()-yOffset);
        });
    }
}
