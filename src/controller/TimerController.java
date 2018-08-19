package controller;


import Model.MovableWindow;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class TimerController implements Initializable {

    private AgeBoxController ageBoxController;
    private OptionsController optionsController;

    private volatile Stage stage;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button optionsButton;

    @FXML
    private Label time;

    @FXML
    private Label timeBottom;

    private Preferences preferences;


    private static boolean savedAge;
    private static int age;
//    private static boolean startup;
//    private static boolean stayOnFront;


    //for timer

    private long yearsToSeventy;

    private long elapsedYears;
    private long elapsedDays;
    private long elapsedHours;
    private long elapsedMinutes;
    private long elapsedSeconds;


    private long secondsInMilli = 1000;
    private long minutesInMilli = secondsInMilli * 60;
    private long hoursInMilli = minutesInMilli * 60;
    private long daysInMilli = hoursInMilli * 24;
    private long yearsInMilli = daysInMilli * 365;

    private Calendar calendar;
    private Calendar calendarLater = Calendar.getInstance();

    //  y/d/h/min/sec
    private Date dateNow;
    private Date dateLater;

    private long different;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        start();

//        Image image = new Image(getClass().getResourceAsStream("../resources/cog.png"));
        Image image = new Image(getClass().getResourceAsStream("/resources/cog.png"));

        optionsButton.setGraphic(new ImageView(image));
        optionsButton.setScaleX(0.5);
        optionsButton.setScaleY(0.5);
    }

    void start() {
        getPrefs();

        if (savedAge) {
            runTimer();
            applySettings();
        } else {
            displayAgeWindow();
            addToStartup();
        }


    }

    private String getExecPath() {
        String path = null;
        try {
            path = new File(TimerController.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return path;
    }

    private void getPrefs() {
        borderPane.getStyleClass().add("paneLeft");
        preferences = Preferences.userNodeForPackage(TimerController.class);
        savedAge = preferences.getBoolean("savedAge", false);
        age = preferences.getInt("age", 70);
//        startup = preferences.getBoolean("startup", false);
//        stayOnFront = preferences.getBoolean("stayOnFront", true);

    }

    private void displayOptions() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/options.fxml"));
            Parent root = fxmlLoader.load();


            optionsController = fxmlLoader.getController();


            Stage stage = new Stage();


            MovableWindow movableWindow = new MovableWindow(stage, root);
            movableWindow.allowMoving();


            optionsController.setStage(stage);
            optionsController.setTimerController(this);


            Scene scene = new Scene(root);
            scene.getStylesheets().add("resources/options.css");
            scene.setFill(Color.TRANSPARENT);

            stage.setScene(scene);

            stage.setTitle("Options");
            stage.toFront();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);

            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load window");
        }
    }

    private void displayAgeWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/ageBox.fxml"));
            Parent root = fxmlLoader.load();


            ageBoxController = fxmlLoader.getController();


            Stage stage = new Stage();


            MovableWindow movableWindow = new MovableWindow(stage, root);
            movableWindow.allowMoving();


            ageBoxController.setStage(stage);
            ageBoxController.setTimerController(this);


            Scene scene = new Scene(root);
            scene.getStylesheets().add("resources/ageBoxStyle.css");
            scene.setFill(Color.TRANSPARENT);

            stage.setScene(scene);

            stage.setTitle("Age check");
            stage.toFront();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);

            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load window");
        }
    }

    private void runTimer() {
        yearsToSeventy = 70 - age;

        calendar = Calendar.getInstance();
        calendarLater.set((int) (calendar.get(Calendar.YEAR) + yearsToSeventy), Calendar.JANUARY, 1, 1, 0, 0);

        dateLater = calendarLater.getTime();

        Timer timer = new Timer(1000, e -> Platform.runLater(() -> {
            calendar = Calendar.getInstance();
            dateNow = calendar.getTime();

            different = dateLater.getTime() - dateNow.getTime();
//            System.out.println("diff" + different);

            elapsedYears = different / yearsInMilli;
            different = different % yearsInMilli;

            elapsedDays = different / daysInMilli;
            different = different % daysInMilli;


            elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;


            elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            elapsedSeconds = different / secondsInMilli;


            //adding zero when lower than 10
            String elMinStr = String.valueOf(elapsedMinutes);
            String elSecStr = String.valueOf(elapsedSeconds);

            if (Long.parseLong(elMinStr) < 10) {
                elMinStr = "0" + elMinStr;
            }
            if (Long.parseLong(elSecStr) < 10) {
                elSecStr = "0" + elSecStr;
            }


//                    time.setText(elapsedHours + ":" + elapsedMinutes + ":" + elapsedSeconds);
            time.setText(elapsedHours + ":" + elMinStr + ":" + elSecStr);
            timeBottom.setText(elapsedYears + " Y " + elapsedDays + " Days");
        }));
        timer.setDelay(1000);
        timer.start();
    }

    @FXML
    public void openSettings() {
        displayOptions();
    }

    @FXML
    void mouseEntered(MouseEvent event) {
        borderPane.getStyleClass().remove("paneLeft");
        System.out.println("mouseenteres");
    }

    @FXML
    void mouseExited(MouseEvent event) {
        borderPane.getStyleClass().add("paneLeft");
        System.out.println("mouseexits");
    }

    @FXML
    void mouseDrag(MouseEvent event) {
        double stageX = stage.getX();
        double stageY = stage.getY();

        preferences.putDouble("stageX", stageX);
        preferences.putDouble("stageY", stageY);

        System.out.println("drag: " + stageX + " " + stageY + " event " + event.toString());
    }

    public void applySettings() {
        Platform.runLater(() -> {

            setStageTimer();

            if (preferences.getBoolean("startup", true)) {
                System.out.println("addd from settings");
                try {
                    Runtime.getRuntime().exec("REG ADD \"HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run\" /V \"ChangeTimer\" /t REG_SZ /F /D \"" + getExecPath() + "\"\n");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error adding to startup");
                }
            } else {
                System.out.println("delete");
                try {
                    Runtime.getRuntime().exec("REG DELETE \"HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run\" /V \"ChangeTimer\" /F");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error adding to startup");
                }
            }
        });

    }

    private void setStageTimer() {

        while(stage.equals(null))
        {
//            System.out.println("timer");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        stage.setAlwaysOnTop(preferences.getBoolean("stayOnFront", true));


//        Timer timer = new Timer(2000, e -> Platform.runLater(() -> {
//            System.out.println("timer");
//            if (stage.equals(null)) {
//                setStageTimer();
//                System.out.println("is null");
//            } else {
//                System.out.println("is on top");
//            }
//        }));
//
//        timer.start();


    }

    private void addToStartup() {
        System.out.println("adddonapp startup");
        try {
            Runtime.getRuntime().exec("REG ADD \"HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run\" /V \"ChangeTimer\" /t REG_SZ /F /D \"" + getExecPath() + "\"\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error adding to startup");
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
