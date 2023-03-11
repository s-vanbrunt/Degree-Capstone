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

public class ContactsController implements Initializable {
    boolean modifyMode = false;
    private Contact selContact;

    // Create a lambda expression to prepare to load a new window
    WindowLoader wl = (ActionEvent event, String url) -> {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(scene));
        stage.show();
    };

    @FXML
    private Button contactAddButton;

    @FXML
    private Label contactAddLabel;

    @FXML
    private Button contactClearButton;

    @FXML
    private TableColumn<?, ?> contactEmailCol;

    @FXML
    private TextField contactEmailText;

    @FXML
    private Label contactErrorLabel;

    @FXML
    private TableColumn<?, ?> contactIdCol;

    @FXML
    private TextField contactIdText;

    @FXML
    private TableColumn<?, ?> contactNameCol;

    @FXML
    private TextField contactNameText;

    @FXML
    private TableView<Contact> contactTable;

    /**
     * On button click retrieve values from text fields and combo boxes. If any are empty or null, display error message.
     * Use values to modify selected Contact object or add new Contact object.
     * Push new or modified object to JDBC.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void contactAddButtonAction(ActionEvent event) {
        String errorMes = "";
        contactErrorLabel.setText(errorMes);
        // Get values from text fields and combo boxes
        String name = contactNameText.getText();
        String email = contactEmailText.getText();
        // Check if any fields were left empty, add a message to errorMes
        if(name == "" || name == null){
            errorMes += "Name needs a value.\n";
        }
        if(email == "" || email == null){
            errorMes += "Email needs a value.\n";
        }
        // If errorMes is not an empty string, display it, otherwise check if this is to modify or add a new Customer
        if(errorMes != ""){
            contactErrorLabel.setText(errorMes);
        }
        else{
            if(modifyMode){
                selContact.setName(name);
                selContact.setEmail(email);

                //FIXME commented out for local development
                //int rows = UpdateQuery.modifyContact(selContact);
                //System.out.println(rows);
                //ContactList.emptyContactList();
                //ReadQuery.readContacts();
                contactTable.refresh();
            }
            else{
                int id = 0;
                Contact contact = new Contact(id, name, email);

                //FIXME line added for local only
                ContactList.addContact(contact);

                //FIXME commented out for local development
                //int rows = CreateQuery.insertContact(contact);
                //System.out.println(rows);
                //ContactList.emptyContactList();
                //ReadQuery.readContacts();
                contactTable.refresh();
            }
            // Clear fields and return to Add User
            clearModify();
        }
    }

    /**
     * Clears modify fields, and returns the Modify Contact section to Add Contact.
     * Hides the clear/cancel modify button.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void contactClearButtonAction(ActionEvent event) {
        contactErrorLabel.setText("");
        clearModify();
    }

    /**
     * Gets a selection from the table view and deletes the object after user confirmation.
     * A message is displayed if delete accepted or cancelled.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void contactDeleteButtonAction(ActionEvent event) {
        contactErrorLabel.setText("");
        selContact = contactTable.getSelectionModel().getSelectedItem();
        // Check if no selection is made and display an error message
        if(selContact == null){
            contactErrorLabel.setText("You must make a selection to Delete.");
        }
        else{
            // Check is the selected user has any related appointments
            int appts = 0;
            for(Appointment appt : AppointmentList.getAllAppointments()) {
                if (appt.getContact().getId() == selContact.getId()) {
                    appts++;
                }
            }
            if(appts == 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected Contact.\nAre you sure?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    contactErrorLabel.setText("Contact " + selContact.getName() + " deleted.");

                    //FIXME commented out for local development
                    //int rows = DeleteQuery.deleteContact(selContact.getId());
                    //System.out.print(rows);
                    //ContactList.emptyContactList();
                    //ReadQuery.readContacts();
                    clearModify();
                    contactTable.refresh();
                } else {
                    contactErrorLabel.setText("Contact not deleted.");
                }
            }
            else{
                contactErrorLabel.setText("Can not delete.\nContact has " + appts + " associated Appointment(s).");
            }
        }
    }

    /**
     * Gets item selected in table view and populated text fields with object values.
     * Alters the Add Contact label to Modify Contact.
     * Alters the Add button to Modify button.
     * If no selection is made an error message is displayed.
     * Shows the clear/cancel modify button.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void contactDetailsButtonAction(ActionEvent event) {
        contactErrorLabel.setText("");
        selContact = contactTable.getSelectionModel().getSelectedItem();
        if(selContact == null){
            //Display message is no selection made
            contactErrorLabel.setText("No selection made in Contacts table.");
        }
        else{
            // Alter Add Label and Button to Modify
            contactAddLabel.setText("Modify Contact");
            contactAddButton.setText("Modify");

            // Show and enable button
            contactClearButton.setOpacity(1);
            contactClearButton.setDisable(false);

            // Populate text fields with Contact values
            contactIdText.setText(String.valueOf(selContact.getId()));
            contactNameText.setText(selContact.getName());
            contactEmailText.setText(selContact.getEmail());

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
    void contactMainMenuButtonAction(ActionEvent event) throws IOException {
        wl.loadNewWindow(event, "/View/MainMenu.fxml");
    }

    /**
     * Initialize the Contacts window table view.
     *
     * @param url is not used in this override.
     * @param resourceBundle is not used in this override.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Set TableView to show Contact objects
        contactTable.setItems(ContactList.getAllContacts());
        contactIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    /**
     * Used to revert Contact Modify form to the Contact Add form.
     */
    private void clearModify(){
        // Alter Modify Label and Button to Add
        contactAddLabel.setText("Add Contact");
        contactAddButton.setText("Add");

        // Hide and disable button
        contactClearButton.setOpacity(0);
        contactClearButton.setDisable(true);

        // Clears fields of values
        contactIdText.setText("");
        contactNameText.setText("");
        contactEmailText.setText("");

        modifyMode = false;
    }
}
