package Controller;

import Model.*;
import Utilities.ReadQuery;
import Utilities.WindowLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This creates a controller object to handle UI events on the Login window.
 *
 * LAMBDA expression is used to call the WindowLoader interface and prepare to load a new window.
 *
 * @author Scott VanBrunt
 */
public class LoginController implements Initializable {

    private String loginErrorMes = "Invalid Username or Password";

    // Create a lambda expression to load the MainMenu window
    WindowLoader wl = (ActionEvent event, String url) -> {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(scene));
        stage.show();
    };

    @FXML
    private Button cancelButton;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginErrorLabel;

    @FXML
    private Label loginLabel;

    @FXML
    private Label loginZoneLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label loginZoneTitle;

    @FXML
    private TextField loginPasswordField;

    @FXML
    private TextField loginUsernameField;

    /**
     * Closes the application when Cancel button is clicked.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void cancelButtonAction(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Checks for user input Username and Password, if found loads the MainMenu window,
     * if not found, stays at Login window and displays a message to user.
     * Each login attempt is logged in the login_activity.txt file.
     *
     * Call a lambda expression to load the MainMenu window
     *
     * @param event object passed from clicked button.
     * @throws IOException possible due to searching for new fxml resource.
     */
    @FXML
    void loginButtonAction(ActionEvent event) throws IOException {

        // Clear error text field
        loginErrorLabel.setText("");

        // Get user input for username and password
        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();
        String filename = "login_activity.txt";
        boolean success = false;
        LocalDateTime attemptTime = LocalDateTime.now();

        // Verify username and password against data
        User user  = UserList.searchUserList(username);
        if(user != null && user.getPassword().equals(password)){
            success = true;

            // Check username against appointments and display appropriate alert.
            Appointment appt = AppointmentList.searchUpcomingAppt(user.getId());
            if(appt != null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Upcoming appointment:\nAppointment Number: " + appt.getId() +
                        "\nDate: " + appt.getStart().toLocalDate() + "\nTime: " + appt.getStart().toLocalTime());
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent()){
                    // Call a lambda expression to load the MainMenu window
                    wl.loadNewWindow(event,"/View/MainMenu.fxml");
                }
            }
            else{
                // If user is admin set an object flag and go to Main Menu, otherwise display no appointments alert and go to Main Menu.
                if(user.getName().equals("admin")){
                    Permission.setVerified(true);
                    // Call a lambda expression to load the MainMenu window
                    wl.loadNewWindow(event, "/View/MainMenu.fxml");
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "No upcoming appointments.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent()) {
                        // Call a lambda expression to load the MainMenu window
                        wl.loadNewWindow(event, "/View/MainMenu.fxml");
                    }
                }
            }
        }
        else{
            loginErrorLabel.setText(loginErrorMes);
        }

        // Log login attempt in login_activity.txt
        FileWriter fw = new FileWriter(filename, true);     // Create FileWriter for the filename variable
        PrintWriter pw = new PrintWriter(fw);                      // Create PrintWriter on the FileWriter

        pw.println("Username = " + username + " | " + "Date/Time = " + attemptTime + " | " + "Login Succeeded = " + success);

        pw.close();     // Close the file
    }

    /**
     * Initializes the Login window with labels in the correct system language, either English or French.
     *
     * @param url is not used in this override.
     * @param resourceBundle is not used in this override.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //FIXME commented out for local development
        // Get all tables from database
        //ReadQuery.readAllTables();

        //  Get the local zoneId and display in loginZoneLabel
        loginZoneLabel.setText(String.valueOf(ZoneId.systemDefault()));

        // Use ResourceBundle to add a french language localization
        ResourceBundle rb = ResourceBundle.getBundle("Controller/loc", Locale.getDefault());

        if(Locale.getDefault().getLanguage().equals("fr")){
            loginLabel.setText(rb.getString("Login"));
            userNameLabel.setText(rb.getString("Username"));
            passwordLabel.setText(rb.getString("Password"));
            loginButton.setText(rb.getString("Login"));
            cancelButton.setText(rb.getString("Cancel"));
            loginZoneTitle.setText(rb.getString("Zone"));
            loginErrorMes = "Nom d'utilisateur ou mot de passe invalide";
        }
    }
}
