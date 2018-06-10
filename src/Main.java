import Model.MovableWindow;
import controller.TimerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.prefs.Preferences;

public class Main extends Application {

    private TimerController timerController;


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setOpacity(0);

        Stage secendaryStage = new Stage();
        secendaryStage.initOwner(primaryStage);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/timer.fxml"));
        Parent root = fxmlLoader.load();


        MovableWindow movableWindow = new MovableWindow(secendaryStage,root);
        movableWindow.allowMoving();

        Scene scene = new Scene(root,300,185);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("resources/main.css");

        timerController = fxmlLoader.getController();
        timerController.setStage(secendaryStage);


        setXY(secendaryStage);

        secendaryStage.setTitle("Change - Timer");
        secendaryStage.setResizable(false);
        secendaryStage.setScene(scene);
        secendaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        secendaryStage.show();

    }

    private void setXY(Stage secendaryStage) {
        Preferences preferences = Preferences.userNodeForPackage(TimerController.class);
        secendaryStage.setX(preferences.getDouble("stageX",500));
        secendaryStage.setY(preferences.getDouble("stageY",500));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
