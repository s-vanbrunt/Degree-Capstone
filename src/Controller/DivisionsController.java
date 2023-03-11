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

public class DivisionsController implements Initializable {
    boolean modifyMode = false;
    private Division selDivision;

    WindowLoader wl = (ActionEvent event, String url) -> {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(scene));
        stage.show();
    };

    @FXML
    private Button divisionAddButton;

    @FXML
    private Label divisionAddLabel;

    @FXML
    private Button divisionClearButton;

    @FXML
    private TableColumn<?, ?> divisionCountryCol;

    @FXML
    private ComboBox<Country> divisionCountryCombo;

    @FXML
    private Label divisionErrorLabel;

    @FXML
    private TableColumn<?, ?> divisionIdCol;

    @FXML
    private TextField divisionIdText;

    @FXML
    private TableColumn<?, ?> divisionNameCol;

    @FXML
    private TextField divisionNameText;

    @FXML
    private TableView<Division> divisionTable;

    /**
     * On button click retrieve values from text fields and combo boxes. If any are empty or null, display error message.
     * Use values to modify selected Division object or add new Division object.
     * Push new or modified object to JDBC.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void divisionAddButtonAction(ActionEvent event) {
        String errorMes = "";
        divisionErrorLabel.setText(errorMes);
        // Get values from text fields and combo boxes
        String name = divisionNameText.getText();
        Country country = divisionCountryCombo.getValue();
        // Check if any fields were left empty, add a message to errorMes
        if(name == "" || name == null){
            errorMes += "Name needs a value.\n";
        }
        if(country == null){
            errorMes += "Country needs a value.\n";
        }
        // If errorMes is not an empty string, display it, otherwise check if this is to modify or add a new Customer
        if(errorMes != ""){
            divisionErrorLabel.setText(errorMes);
        }
        else{
            if(modifyMode){
                selDivision.setName(name);
                selDivision.setCountry(country);

                //FIXME commented out for local development
                //int rows = UpdateQuery.modifyDivision(selDivision);
                //System.out.println(rows);
                //DivisionList.emptyDivisionList();
                //ReadQuery.readDivisions();
                divisionTable.refresh();
            }
            else{
                int id = 0;
                Division division = new Division(id, name, country);

                //FIXME line added for local only
                DivisionList.addDivision(division);

                //FIXME commented out for local development
                //int rows = CreateQuery.insertDivision(division);
                //System.out.println(rows);
                //DivisionList.emptyDivisionList();
                //ReadQuery.readDivisions();
                divisionTable.refresh();
            }
            // Clear fields and return to Add User
            clearModify();
        }
    }

    /**
     * Clears modify fields, and returns the Modify Division section to Add Division.
     * Hides the clear/cancel modify button.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void divisionClearButtonAction(ActionEvent event) {
        divisionErrorLabel.setText("");
        clearModify();
    }

    /**
     * Not used in this implementation.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void divisionCountryComboAction(ActionEvent event) {

    }

    /**
     * Gets a selection from the table view and deletes the object after user confirmation.
     * A message is displayed if delete accepted or cancelled.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void divisionDeleteButtonAction(ActionEvent event) {
        divisionErrorLabel.setText("");
        selDivision = divisionTable.getSelectionModel().getSelectedItem();
        // Check if no selection is made and display an error message
        if(selDivision == null){
            divisionErrorLabel.setText("You must make a selection to Delete.");
        }
        else{
            // Check is the selected user has any related appointments
            int custs = 0;
            for(Customer cust : CustomerList.getAllCustomers()) {
                if (cust.getDivision().getId() == selDivision.getId()) {
                    custs++;
                }
            }
            if(custs == 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected Division.\nAre you sure?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    divisionErrorLabel.setText("Division " + selDivision.getName() + " deleted.");

                    //FIXME commented out for local development
                    //int rows = DeleteQuery.deleteDivision(selDivision.getId());
                    //System.out.print(rows);
                    //DivisionList.emptyDivisionList();
                    //ReadQuery.readDivisions();
                    clearModify();
                    divisionTable.refresh();
                } else {
                    divisionErrorLabel.setText("Division not deleted.");
                }
            }
            else{
                divisionErrorLabel.setText("Can not delete.\nDivision has " + custs + " associated Appointment(s).");
            }
        }
    }

    /**
     * Gets item selected in table view and populated text fields with object values.
     * Alters the Add Division label to Modify Division.
     * Alters the Add button to Modify button.
     * If no selection is made an error message is displayed.
     * Shows the clear/cancel modify button.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void divisionDetailsButtonAction(ActionEvent event) {
        divisionErrorLabel.setText("");
        selDivision = divisionTable.getSelectionModel().getSelectedItem();
        if(selDivision == null){
            //Display message is no selection made
            divisionErrorLabel.setText("No selection made in Divisions table.");
        }
        else{
            // Alter Add Label and Button to Modify
            divisionAddLabel.setText("Modify Division");
            divisionAddButton.setText("Modify");

            // Show and enable button
            divisionClearButton.setOpacity(1);
            divisionClearButton.setDisable(false);

            // Populate text fields with Contact values
            divisionIdText.setText(String.valueOf(selDivision.getId()));
            divisionNameText.setText(selDivision.getName());
            divisionCountryCombo.setValue(selDivision.getCountry());

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
    void divisionMainMenuButtonAction(ActionEvent event) throws IOException {
        wl.loadNewWindow(event, "/View/MainMenu.fxml");
    }

    /**
     * Initialize the Divisions window table view.
     *
     * @param url is not used in this override.
     * @param resourceBundle is not used in this override.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set TableView to show Division objects
        divisionTable.setItems(DivisionList.getAllDivisions());
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        divisionNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        divisionCountryCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));

        // Set Country combo box to show Country objects
        divisionCountryCombo.setItems(CountryList.getAllCountries());
    }

    /**
     * Used to revert Division Modify form to the Division Add form.
     */
    private void clearModify(){
        // Alter Modify Label and Button to Add
        divisionAddLabel.setText("Add Division");
        divisionAddButton.setText("Add");

        // Hide and disable button
        divisionClearButton.setOpacity(0);
        divisionClearButton.setDisable(true);

        // Clears fields of values
        divisionIdText.setText("");
        divisionNameText.setText("");
        divisionCountryCombo.setValue(null);

        modifyMode = false;
    }
}
