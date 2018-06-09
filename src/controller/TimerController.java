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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class TimerController implements Initializable {

    private AgeBoxController ageBoxController;

    @FXML
    private Button optionsButton;

    @FXML
    private Label time;

    @FXML
    private Label timeBottom;

    private static boolean savedAge;
    private static int age;


    //for timer

    private long yearsToSeventy;

    private long elapsedDays;
    private long elapsedHours;
    private long elapsedMinutes;
    private long elapsedSeconds;


    private long secondsInMilli = 1000;
    private long minutesInMilli = secondsInMilli * 60;
    private long hoursInMilli = minutesInMilli * 60;
    private long daysInMilli = hoursInMilli * 24;

    private Calendar calendar;
    private Calendar calendarLater = Calendar.getInstance();

    //  y/d/h/min/sec
    private Date dateNow;
    private Date dateLater;

    private long different;


    @FXML
    public void openSettings() {
        System.out.println("opening settings");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        start();

        Image image = new Image(getClass().getResourceAsStream("../resources/cog.png"));

        optionsButton.setGraphic(new ImageView(image));
        optionsButton.setScaleX(0.5);
        optionsButton.setScaleY(0.5);
    }

    void start(){
        getPrefs();

        if (savedAge) {
            runTimer();
        } else {
            displayAgeWindow();
        }
    }

    private void getPrefs() {
        Preferences preferences = Preferences.userNodeForPackage(TimerController.class);
        savedAge = preferences.getBoolean("savedAge", false);
        age = preferences.getInt("age", 70);

    }

    private void displayAgeWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/ageBox.fxml"));
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
            stage.setX(0);
            stage.setY(0);
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

//todo load age from file not from answer

//        age = AgeBoxController.answer;
        yearsToSeventy = 70 - age;

//        long leftInSeconds = yearsToSeventy * 365 * 24 * 60 * 60;
//        seconds = leftInSeconds / 60 * 60 * 24 * 365;


//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/M/dd hh:mm:ss");

        calendar = Calendar.getInstance();
        calendarLater.set((int) (calendar.get(Calendar.YEAR) + yearsToSeventy), Calendar.JANUARY, 1, 1, 0, 0);

//        System.out.println("-" + calendar.getTime());
//        System.out.println("-" + calendarLater.getTime());


//        String currentYear = String.valueOf(calendar.get(Calendar.YEAR));


        dateLater = calendarLater.getTime();

        System.out.println(elapsedDays + ":" + elapsedHours + ":" + elapsedMinutes + ":" + elapsedSeconds);

//        try {
////            Date now = simpleDateFormat.parse(new Date().toString());
////            Date later = simpleDateFormat.parse(currentYear);
//
//
//
//
//
//
//
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }


        Timer timer = new Timer(1000, e -> Platform.runLater(() -> {
            calendar = Calendar.getInstance();

            dateNow = calendar.getTime();

            different = dateLater.getTime() - dateNow.getTime();
            System.out.println("diff" + different);


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
            timeBottom.setText(yearsToSeventy + " Y " + elapsedDays + " Days");
        }));
        timer.setDelay(1000);
        timer.start();
    }
}
