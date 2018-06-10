package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class OptionsController implements Initializable {

    private TimerController timerController;
    private Stage stage;


    @FXML
    private CheckBox startup;

    @FXML
    private CheckBox stayOnFront;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void saveSettings(ActionEvent event) {
        Preferences preferences = Preferences.userNodeForPackage(TimerController.class);
        preferences.putBoolean("startup", startup.isSelected());
        preferences.putBoolean("stayOnFront", stayOnFront.isSelected());
        stage.close();
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    void setTimerController(TimerController timerController) {
        this.timerController = timerController;
    }
}
