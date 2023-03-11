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

public class CountriesController implements Initializable {
    boolean modifyMode = false;
    private Country selCountry;

    // Create a lambda expression to prepare to load a new window
    WindowLoader wl = (ActionEvent event, String url) -> {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(scene));
        stage.show();
    };

    @FXML
    private Button countryAddButton;

    @FXML
    private Label countryAddLabel;

    @FXML
    private Button countryClearButton;

    @FXML
    private Label countryErrorLabel;

    @FXML
    private TableColumn<?, ?> countryIdCol;

    @FXML
    private TextField countryIdText;

    @FXML
    private TableColumn<?, ?> countryNameCol;

    @FXML
    private TextField countryNameText;

    @FXML
    private TableView<Country> countryTable;

    /**
     * On button click retrieve values from text fields and combo boxes. If any are empty or null, display error message.
     * Use values to modify selected Country object or add new Country object.
     * Push new or modified object to JDBC.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void countryAddButtonAction(ActionEvent event) {
        String errorMes = "";
        countryErrorLabel.setText(errorMes);
        // Get values from text fields and combo boxes
        String name = countryNameText.getText();
        // Check if any fields were left empty, add a message to errorMes
        if(name == "" || name == null){
            errorMes += "Name needs a value.\n";
        }
        // If errorMes is not an empty string, display it, otherwise check if this is to modify or add a new Customer
        if(errorMes != ""){
            countryErrorLabel.setText(errorMes);
        }
        else{
            if(modifyMode){
                selCountry.setName(name);

                //FIXME commented out for local development
                //int rows = UpdateQuery.modifyCountry(selCountry);
                //System.out.println(rows);
                //CountryList.emptyCountryList();
                //ReadQuery.readCountries();
                countryTable.refresh();
            }
            else{
                int id = 0;
                Country country = new Country(id, name);

                //FIXME line added for local only
                CountryList.addCountry(country);

                //FIXME commented out for local development
                //int rows = CreateQuery.insertCountry(country);
                //System.out.println(rows);
                //CountryList.emptyCountryList();
                //ReadQuery.readCountries();
                countryTable.refresh();
            }
            // Clear fields and return to Add User
            clearModify();
        }
    }

    /**
     * Clears modify fields, and returns the Modify Country section to Add Country.
     * Hides the clear/cancel modify button.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void countryClearButtonAction(ActionEvent event) {
        countryErrorLabel.setText("");
        clearModify();
    }

    /**
     * Gets a selection from the table view and deletes the object after user confirmation.
     * A message is displayed if delete accepted or cancelled.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void countryDeleteButtonAction(ActionEvent event) {
        countryErrorLabel.setText("");
        selCountry = countryTable.getSelectionModel().getSelectedItem();
        // Check if no selection is made and display an error message
        if(selCountry == null){
            countryErrorLabel.setText("You must make a selection to Delete.");
        }
        else{
            // Check is the selected user has any related appointments
            int divs = 0;
            for(Division div : DivisionList.getAllDivisions()) {
                if (div.getCountry().getId() == selCountry.getId()) {
                    divs++;
                }
            }
            if(divs == 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected Country.\nAre you sure?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    countryErrorLabel.setText("Country " + selCountry.getName() + " deleted.");

                    //FIXME commented out for local development
                    //int rows = DeleteQuery.deleteCountry(selCountry.getId());
                    //System.out.print(rows);
                    //CountryList.emptyCountryList();
                    //ReadQuery.readCountries();
                    clearModify();
                    countryTable.refresh();
                } else {
                    countryErrorLabel.setText("Country not deleted.");
                }
            }
            else{
                countryErrorLabel.setText("Can not delete.\nCountry has " + divs + " associated Division(s).");
            }
        }
    }

    /**
     * Gets item selected in table view and populated text fields with object values.
     * Alters the Add Country label to Modify Country.
     * Alters the Add button to Modify button.
     * If no selection is made an error message is displayed.
     * Shows the clear/cancel modify button.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void countryDetailsButtonAction(ActionEvent event) {
        countryErrorLabel.setText("");
        selCountry = countryTable.getSelectionModel().getSelectedItem();
        if(selCountry == null){
            //Display message is no selection made
            countryErrorLabel.setText("No selection made in Countries table.");
        }
        else{
            // Alter Add Label and Button to Modify
            countryAddLabel.setText("Modify Country");
            countryAddButton.setText("Modify");

            // Show and enable button
            countryClearButton.setOpacity(1);
            countryClearButton.setDisable(false);

            // Populate text fields with Country values
            countryIdText.setText(String.valueOf(selCountry.getId()));
            countryNameText.setText(selCountry.getName());

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
    void countryMainMenuButtonAction(ActionEvent event) throws IOException {
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
        countryTable.setItems(CountryList.getAllCountries());
        countryIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        countryNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    /**
     * Used to revert Country Modify form to the Country Add form.
     */
    private void clearModify(){
        // Alter Modify Label and Button to Add
        countryAddLabel.setText("Add Contact");
        countryAddButton.setText("Add");

        // Hide and disable button
        countryClearButton.setOpacity(0);
        countryClearButton.setDisable(true);

        // Clears fields of values
        countryIdText.setText("");
        countryNameText.setText("");

        modifyMode = false;
    }
}
