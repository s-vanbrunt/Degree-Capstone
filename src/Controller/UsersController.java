package Controller;

import Model.*;
import Utilities.WindowLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UsersController implements Initializable {
    boolean modifyMode = false;
    private User selUser;

    // Create a lambda expression to prepare to load a new window
    WindowLoader wl = (ActionEvent event, String url) -> {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(scene));
        stage.show();
    };

    @FXML
    private Button userAddButton;

    @FXML
    private Label userAddLabel;

    @FXML
    private Button userClearButton;

    @FXML
    private Label userErrorLabel;

    @FXML
    private TableColumn<?, ?> userIdCol;

    @FXML
    private TextField userIdText;

    @FXML
    private TableColumn<?, ?> userNameCol;

    @FXML
    private TextField userNameText;

    @FXML
    private TableColumn<?, ?> userPasswordCol;

    @FXML
    private TextField userPasswordText;

    @FXML
    private TableView<User> userTable;

    /**
     * On button click retrieve values from text fields and combo boxes. If any are empty or null, display error message.
     * Use values to modify selected User object or add new User object.
     * Push new or modified object to JDBC.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void userAddButtonAction(ActionEvent event) {
        String errorMes = "";
        userErrorLabel.setText(errorMes);
        // Get values from text fields and combo boxes
        String name = userNameText.getText();
        String password = userPasswordText.getText();
        // Check if any fields were left empty, add a message to errorMes
        if(name == "" || name == null){
            errorMes += "Name needs a value.\n";
        }
        if(password == "" || password == null){
            errorMes += "Password needs a value.\n";
        }
        // If errorMes is not an empty string, display it, otherwise check if this is to modify or add a new Customer
        if(errorMes != ""){
            userErrorLabel.setText(errorMes);
        }
        else{
            if(modifyMode){
                selUser.setName(name);
                selUser.setPassword(password);

                //FIXME commented out for local development
                //int rows = UpdateQuery.modifyUser(selUser);
                //System.out.println(rows);
                //UserList.emptyUserList();
                //ReadQuery.readUsers();
                userTable.refresh();
            }
            else{
                int id = 0;
                User user = new User(id, name, password);

                //FIXME line added for local only
                UserList.addUser(user);

                //FIXME commented out for local development
                //int rows = CreateQuery.insertUser(user);
                //System.out.println(rows);
                //UserList.emptyUserList();
                //ReadQuery.readUsers();
                userTable.refresh();
            }
            // Clear fields and return to Add User
            clearModify();
        }
    }

    /**
     * Clears modify fields, and returns the Modify User section to Add User.
     * Hides the clear/cancel modify button.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void userClearButtonAction(ActionEvent event) {
        userErrorLabel.setText("");
        clearModify();
    }

    /**
     * Gets a selection from the table view and deletes the object after user confirmation.
     * A message is displayed if delete accepted or cancelled.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void userDeleteButtonAction(ActionEvent event) {
        userErrorLabel.setText("");
        selUser = userTable.getSelectionModel().getSelectedItem();
        // Check if no selection is made and display an error message
        if(selUser == null){
            userErrorLabel.setText("You must make a selection to Delete.");
        }
        else{
            // Check is the selected user has any related appointments
            int appts = 0;
            for(Appointment appt : AppointmentList.getAllAppointments()) {
                if (appt.getUser().getId() == selUser.getId()) {
                    appts++;
                }
            }
            if(appts == 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected User.\nAre you sure?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    userErrorLabel.setText("User " + selUser.getName() + " deleted.");

                    //FIXME commented out for local development
                    //int rows = DeleteQuery.deleteUser(selUser.getId());
                    //System.out.print(rows);
                    //UserList.emptyUserList();
                    //ReadQuery.readUsers();
                    clearModify();
                    userTable.refresh();
                } else {
                    userErrorLabel.setText("User not deleted.");
                }
            }
            else{
                userErrorLabel.setText("Can not delete.\nUser has " + appts + " associated Appointment(s).");
            }
        }
    }

    /**
     * Gets item selected in table view and populated text fields with object values.
     * Alters the Add User label to Modify User.
     * Alters the Add button to Modify button.
     * If no selection is made an error message is displayed.
     * Shows the clear/cancel modify button.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void userDetailsButtonAction(ActionEvent event) {
        userErrorLabel.setText("");
        selUser = userTable.getSelectionModel().getSelectedItem();
        if(selUser == null){
            //Display message is no selection made
            userErrorLabel.setText("No selection made in Users table.");
        }
        else{
            // Alter Add Label and Button to Modify
            userAddLabel.setText("Modify User");
            userAddButton.setText("Modify");

            // Show and enable button
            userClearButton.setOpacity(1);
            userClearButton.setDisable(false);

            // Populate text fields with User values
            userIdText.setText(String.valueOf(selUser.getId()));
            userNameText.setText(selUser.getName());
            userPasswordText.setText(selUser.getPassword());

            modifyMode = true;
        }
    }

    /**
     * Returns user to the Main Menu when Main Menu button clicked.
     *
     * Calls a lambda expression declared at the start of the class to load a new window.
     *
     * @param event object passed from clicked button.
     * @throws IOException possible due to searching for new fxml resource.
     */
    @FXML
    void userMainMenuButtonAction(ActionEvent event) throws IOException {
        wl.loadNewWindow(event, "/View/MainMenu.fxml");
    }

    /**
     * Initialize the Users window table view.
     *
     * @param url is not used in this override.
     * @param resourceBundle is not used in this override.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Set TableView to show User objects
        userTable.setItems(UserList.getAllUsers());
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        userPasswordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
    }

    /**
     * Used to revert User Modify form to the User Add form.
     */
    private void clearModify(){
        // Alter Modify Label and Button to Add
        userAddLabel.setText("Add User");
        userAddButton.setText("Add");

        // Hide and disable button
        userClearButton.setOpacity(0);
        userClearButton.setDisable(true);

        // Clears fields of values
        userIdText.setText("");
        userNameText.setText("");
        userPasswordText.setText("");

        modifyMode = false;
    }
}
