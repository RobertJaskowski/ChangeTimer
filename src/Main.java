import Model.MovableWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setOpacity(0);

        Stage secendaryStage = new Stage();
        secendaryStage.initOwner(primaryStage);

        Parent root = FXMLLoader.load(getClass().getResource("resources/timer.fxml"));



        MovableWindow movableWindow = new MovableWindow(secendaryStage,root);
        movableWindow.allowMoving();



        Scene scene = new Scene(root,300,185);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("resources/main.css");

        secendaryStage.setTitle("Change - Timer");
        secendaryStage.setResizable(false);
        secendaryStage.setScene(scene);
        secendaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        secendaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
