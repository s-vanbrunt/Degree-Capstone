package Controller;

import Model.*;
import Utilities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

/**
 * This creates a controller object to handle UI events on the Customers window.
 *
 * LAMBDA expression is used to call the WindowLoader interface and prepare to load a new window.
 *
 * @author Scott VanBrunt
 */
public class CustomersController implements Initializable {
    boolean modifyMode = false;
    private Customer selCust;
    private ObservableList<Division> divList = FXCollections.observableArrayList();

    // Create a lambda expression to prepare to load a new window
    WindowLoader wl = (ActionEvent event, String url) -> {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(scene));
        stage.show();
    };

    @FXML
    private TextField customerIdText;

    @FXML
    private Label customerAddLabel;

    @FXML
    private TableColumn<?, ?> customerAddressCol;

    @FXML
    private TextField customerAddressText;

    @FXML
    private TableColumn<?, ?> customerCountryCol;

    @FXML
    private ComboBox<Country> customerCountryCombo;

    @FXML
    private TableColumn<?, ?> customerDivCol;

    @FXML
    private ComboBox<Division> customerDivCombo;

    @FXML
    private TableColumn<?, ?> customerNameCol;

    @FXML
    private TextField customerNameText;

    @FXML
    private TableColumn<?, ?> customerPhoneCol;

    @FXML
    private TextField customerPhoneText;

    @FXML
    private TableColumn<?, ?> customerIdCol;


    @FXML
    private TableColumn<?, ?> customerPostalCol;

    @FXML
    private TextField customerPostalText;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private Button customerAddButton;

    @FXML
    private Label customerErrorLabel;

    @FXML
    private Button customerClearButton;

    /**
     * On button click retrieve values from text fields and combo boxes. If any are empty or null, display error message.
     * Use values to modify selected Customer object or add new Customer object.
     * Push new or modified object to JDBC.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void customerAddButtonAction(ActionEvent event) {
        String errorMes = "";
        customerErrorLabel.setText(errorMes);
        // Get values from text fields and combo boxes
        String name = customerNameText.getText();
        String address = customerAddressText.getText();
        String phone = customerPhoneText.getText();
        String postal = customerPostalText.getText();
        Country country = customerCountryCombo.getValue();
        Division division = customerDivCombo.getValue();
        // Check if any fields were left empty, add a message to errorMes
        if(name == "" || name == null){
            errorMes += "Name needs a value.\n";
        }
        if(address == "" || address == null){
            errorMes += "Address needs a value.\n";
        }
        if(phone == "" || phone == null){
            errorMes += "Phone needs a value.\n";
        }
        if(postal == "" || postal == null){
            errorMes += "Postal needs a value.\n";
        }
        if(country == null){
            errorMes += "Country needs a value.\n";
        }
        if(division == null){
            errorMes += "Division needs a value.\n";
        }
        // If errorMes is not an empty string, display it, otherwise check if this is to modify or add a new Customer
        if(errorMes != ""){
            customerErrorLabel.setText(errorMes);
        }
        else{
            if(modifyMode){
                selCust.setName(name);
                selCust.setAddress(address);
                selCust.setPhone(phone);
                selCust.setPostal(postal);
                selCust.setDivision(division);

                //FIXME commented out for local development
                //int rows = UpdateQuery.modifyCustomer(selCust);
                //System.out.println(rows);
                //CustomerList.emptyCustomerList();
                //ReadQuery.readCustomers();
                customerTable.refresh();
            }
            else{
                int id = 0;
                Customer cust = new Customer(id, name, address, phone, postal, division);

                //FIXME line added for local only
                CustomerList.addCustomer(cust);

                //FIXME commented out for local development
                //int rows = CreateQuery.insertCustomer(cust);
                //System.out.println(rows);
                //CustomerList.emptyCustomerList();
                //ReadQuery.readCustomers();
                customerTable.refresh();
            }
            // Clear fields and return to Add Customer
            clearModify();
        }
    }

    /**
     * Gets a selection from the table view and deletes the object after user confirmation.
     * A message is displayed if delete accepted or cancelled.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void customerDeleteButtonAction(ActionEvent event) {
        customerErrorLabel.setText("");
        Customer selCust = customerTable.getSelectionModel().getSelectedItem();
        // Check if no selection is made and display an error message
        if(selCust == null){
            customerErrorLabel.setText("You must make a selection to Delete.");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected Customer and all associated Appointments.\nAre you sure?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                customerErrorLabel.setText("Customer " + selCust.getName() + " and all related appointments deleted.");
                for(Appointment appt : AppointmentList.getAllAppointments()){
                    if(appt.getCustomer().getId() == selCust.getId()){

                        //FIXME commented out for local development
                        //int rows = DeleteQuery.deleteAppt(appt.getId());
                        //System.out.println(rows);
                    }
                }

                //FIXME commented out for local development
                //int rows = DeleteQuery.deleteCustomer(selCust.getId());
                //System.out.print(rows);
                //CustomerList.emptyCustomerList();
                //ReadQuery.readCustomers();
                clearModify();
                customerTable.refresh();
            } else {
                customerErrorLabel.setText("Customer not deleted.");
            }
        }
    }

    /**
     * Clears modify fields, and returns the Modify Customer section to Add Customer.
     * Hides the clear/cancel modify button.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void customerClearButtonAction(ActionEvent event) {
        customerErrorLabel.setText("");
        clearModify();
    }

    /**
     * When a selection is made in the Country combo box, a list is populated with Division objects that contain the
     * selected Country.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void customerCountryComboAction(ActionEvent event) {
        // Populate list shown in Division combo box with only Divisions that contain the selected Country object.
        Country selCountry = customerCountryCombo.getSelectionModel().getSelectedItem();
        divList.clear();
        for(Division division : DivisionList.getAllDivisions()){
            if(division.getCountry().equals(selCountry)){
                divList.add(division);
            }
        }
    }

    /**
     * Gets item selected in table view and populated text fields with object values.
     * Alters the Add Customer label to Modify Customer.
     * Alters the Add button to Modify button.
     * If no selection is made an error message is displayed.
     * Shows the clear/cancel modify button.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void customerDetailsButtonAction(ActionEvent event) {
        customerErrorLabel.setText("");
        selCust = customerTable.getSelectionModel().getSelectedItem();
        if(selCust == null){
            //Display message is no selection made
            customerErrorLabel.setText("No selection made in Customers table.");
        }
        else{
            // Alter Add Label and Button to Modify
            customerAddLabel.setText("Modify Customer");
            customerAddButton.setText("Modify");

            // Show and enable button
            customerClearButton.setOpacity(1);
            customerClearButton.setDisable(false);

            // Populate text fields and combo boxes with Customer values
            customerIdText.setText(String.valueOf(selCust.getId()));
            customerNameText.setText(selCust.getName());
            customerAddressText.setText(selCust.getAddress());
            customerPhoneText.setText(selCust.getPhone());
            customerPostalText.setText(selCust.getPostal());
            customerCountryCombo.setValue(selCust.getDivision().getCountry());
            customerDivCombo.setValue(selCust.getDivision());

            modifyMode = true;
        }
    }

    /**
     * Not used in this implementation.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void customerDivComboAction(ActionEvent event) {

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
    void customerMainMenuButtonAction(ActionEvent event) throws IOException {
        wl.loadNewWindow(event, "/View/MainMenu.fxml");
    }

    /**
     * Initialize the Customers window combo boxes and table view.
     *
     * @param url is not used in this override.
     * @param resourceBundle is not used in this override.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Set TableView to show Customer objects
        customerTable.setItems(CustomerList.getAllCustomers());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("postal"));
        customerDivCol.setCellValueFactory(new PropertyValueFactory<>("divName"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));

        // Set Country combo box to show Country objects
        customerCountryCombo.setItems(CountryList.getAllCountries());

        // Set Division combo box to a currently empty list, to be filled once a Country is selected
        customerDivCombo.setItems(divList);
    }

    /**
     * Used to revert Customer Modify form to the Customer Add form.
     */
    private void clearModify(){
        // Alter Modify Label and Button to Add
        customerAddLabel.setText("Add Customer");
        customerAddButton.setText("Add");

        // Hide and disable button
        customerClearButton.setOpacity(0);
        customerClearButton.setDisable(true);

        // Clears fields of values
        customerIdText.setText("");
        customerNameText.setText("");
        customerAddressText.setText("");
        customerPhoneText.setText("");
        customerPostalText.setText("");
        customerCountryCombo.setValue(null);

        modifyMode = false;
    }
}
