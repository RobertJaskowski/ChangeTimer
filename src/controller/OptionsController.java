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
    private Preferences preferences;

    private static boolean stayOnFront;
    private static boolean startup;


    @FXML
    private CheckBox startupCheckbox;

    @FXML
    private CheckBox stayOnFrontCheckbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preferences = Preferences.userNodeForPackage(TimerController.class);
        startup = preferences.getBoolean("startup",true);
        stayOnFront = preferences.getBoolean("stayOnFront", true);

        startupCheckbox.setSelected(startup);
        stayOnFrontCheckbox.setSelected(stayOnFront);
    }

    @FXML
    void saveSettings(ActionEvent event) {
        preferences.putBoolean("startup", startupCheckbox.isSelected());
        preferences.putBoolean("stayOnFront", stayOnFrontCheckbox.isSelected());
        stage.close();
        timerController.applySettings();
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    void setTimerController(TimerController timerController) {
        this.timerController = timerController;
    }
}
