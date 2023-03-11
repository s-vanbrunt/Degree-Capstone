package Controller;

import Model.Permission;
import Utilities.ReadQuery;
import Utilities.WindowLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This creates a controller object to handle UI events on the MainMenu window.
 *
 * LAMBDA expression is used to call the WindowLoader interface and prepare to load new windows.
 *
 * @author Scott VanBrunt
 */
public class MainController implements Initializable {

    // Create a lambda expression to prepare to load a new window
    WindowLoader wl = (ActionEvent event, String url) -> {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(scene));
        stage.show();
    };

    @FXML
    private Button mainContacts;

    @FXML
    private Button mainCountries;

    @FXML
    private Button mainDivisions;

    @FXML
    private Button mainUsers;

    /**
     * Moves the GUI to the Appointments window on a button click.
     *
     * Call a lambda expression to load the Appointment window
     *
     * @param event object pointing to a button click.
     * @throws IOException possible due to searching for a new fxml resource.
     */
    @FXML
    void mainApptButtonAction(ActionEvent event) throws IOException {
        wl.loadNewWindow(event,"/View/Scheduling.fxml");
    }

    /**
     * Moves the GUI to the Customers window on a button click.
     *
     * Call a lambda expression to load the Customers window
     *
     * @param event object pointing to a button click.
     * @throws IOException possible due to searching for a new fxml resource.
     */
    @FXML
    void mainCustomerButtonAction(ActionEvent event) throws IOException {
        wl.loadNewWindow(event,"/View/Customers.fxml");
    }

    /**
     * Moves the GUI to the Reports window on a button click.
     *
     * Call a lambda expression to load the Reports window
     *
     * @param event object pointing to a button click.
     * @throws IOException possible due to searching for a new fxml resource.
     */
    @FXML
    void mainReportButtonAction(ActionEvent event) throws IOException {
        wl.loadNewWindow(event,"/View/Reports.fxml");
    }

    /**
     * Closes the application when Exit button is clicked.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void mainExitButton(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Moves the GUI to the Contacts window on a button click.
     *
     * Call a lambda expression to load the Reports window
     *
     * @param event object pointing to a button click.
     * @throws IOException possible due to searching for a new fxml resource.
     */
    @FXML
    void mainContactsButton(ActionEvent event) throws IOException {
        wl.loadNewWindow(event,"/View/Contacts.fxml");
    }
    /**
     * Moves the GUI to the Countries window on a button click.
     *
     * Call a lambda expression to load the Reports window
     *
     * @param event object pointing to a button click.
     * @throws IOException possible due to searching for a new fxml resource.
     */
    @FXML
    void mainCountriesButton(ActionEvent event) throws IOException {
        wl.loadNewWindow(event,"/View/Countries.fxml");
    }
    /**
     * Moves the GUI to the Divisions window on a button click.
     *
     * Call a lambda expression to load the Reports window
     *
     * @param event object pointing to a button click.
     * @throws IOException possible due to searching for a new fxml resource.
     */
    @FXML
    void mainDivisionsButton(ActionEvent event) throws IOException {
        wl.loadNewWindow(event,"/View/Divisions.fxml");
    }
    /**
     * Moves the GUI to the Users window on a button click.
     *
     * Call a lambda expression to load the Reports window
     *
     * @param event object pointing to a button click.
     * @throws IOException possible due to searching for a new fxml resource.
     */
    @FXML
    void mainUsersButton(ActionEvent event) throws IOException {
        wl.loadNewWindow(event,"/View/Users.fxml");
    }

    /**
     *
     * @param url is not used in this override.
     * @param resourceBundle is not used in this override.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //FIXME commented out for local development
        //ReadQuery.readAllTables();

        // If logged in as admin enable admin only buttons, otherwise disable admin only buttons
        if(Permission.isVerified()){
            mainUsers.setDisable(false);
            mainContacts.setDisable(false);
            mainCountries.setDisable(false);
            mainDivisions.setDisable(false);
        }
        else{
            mainUsers.setDisable(true);
            mainContacts.setDisable(true);
            mainCountries.setDisable(true);
            mainDivisions.setDisable(true);
        }
    }
}
