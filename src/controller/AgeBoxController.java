package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class AgeBoxController implements Initializable {

    TimerController timerController;

    static int answer;

    private static Parent root;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label label;

    @FXML
    private TextField textField;

    @FXML
    private Button confirmButton;


    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Preferences preferences = Preferences.userNodeForPackage(TimerController.class);


        EventHandler<ActionEvent> actionEvent = event -> {
            if (isValid(textField, textField.getText())) {
                answer = Integer.parseInt(textField.getText());
                preferences.putBoolean("savedAge",true);
                preferences.putInt("age",answer);
                timerController.start();
                System.out.println(textField.getText());
                stage.close();
            } else {
                System.out.println("not number");
                textField.clear();
                textField.setPromptText("Invalid number!");
            }
        };


        confirmButton.addEventHandler(ActionEvent.ACTION, actionEvent);

    }


    static boolean isValid(TextField input, String text) {
        try {
            int age = Integer.parseInt(input.getText());
            System.out.println("User is " + age);
            return age <= 99;
        } catch (NumberFormatException e) {
            System.out.println("User is " + text + " in not number");
            return false;
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setTimerController(TimerController timerController){
        this.timerController = timerController;
    }

}
