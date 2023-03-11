package Capstone;

import Utilities.TestData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This program facilitates access to a SQL database for data on customers and appointments.
 * The database data can be updated through this program.
 *
 * Contains the main method and program entry point.
 *
 * @author Scott VanBrunt
 */
public class Main extends Application {

    /**
     * Overrides the JavaFX built-in start() method to display the first screen.
     *
     * @param primaryStage a Stage object
     * @throws Exception for the getResource() method
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        primaryStage.setTitle("Appointment Tracker");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    /**
     * The main program method. Only used to call launch() and begin the JavaFX lifecycle
     * as well as open and close the connection to the database.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        //FIXME remove call to add test data before using database
        TestData.addTestData();

        //FIXME database connection commented out for local development
        //JDBC.openConnection();

        launch(args);

        //FIXME database connection closure commented out for local development
        //JDBC.closeConnection();
    }
}
