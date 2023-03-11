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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This creates a controller object to handle UI events on the Appointments window.
 *
 * LAMBDA expression is used to call the WindowLoader interface and prepare to load a new window.
 *
 * @author Scott VanBrunt
 */
public class SchedulingController implements Initializable {

    boolean modifyMode = false;
    private Appointment selAppt;
    private ObservableList<Appointment> filteredList = FXCollections.observableArrayList();

    // Create a lambda expression to prepare to load a new window
    WindowLoader wl = (ActionEvent event, String url) -> {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(scene));
        stage.show();
    };

    @FXML
    private Label apptAddApptLabel;

    @FXML
    private Button apptAddButton;

    @FXML
    private Button apptClearButton;

    @FXML
    private TableColumn<?, ?> apptContactCol;

    @FXML
    private ComboBox<Contact> apptContactCombo;

    @FXML
    private TableColumn<?, ?> apptCustId;

    @FXML
    private ComboBox<Customer> apptCustIdCombo;

    @FXML
    private TableColumn<?, ?> apptDescCol;

    @FXML
    private TextField apptDescText;

    @FXML
    private TableColumn<?, ?> apptEndDate;

    @FXML
    private TableColumn<?, ?> apptEndTime;

    @FXML
    private ComboBox<LocalTime> apptEndTimeCombo;

    @FXML
    private Label apptErrorLabel;

    @FXML
    private TableColumn<?, ?> apptIdCol;

    @FXML
    private TextField apptIdText;

    @FXML
    private TableColumn<?, ?> apptLocCol;

    @FXML
    private TextField apptLocText;

    @FXML
    private TableColumn<?, ?> apptStartDate;

    @FXML
    private DatePicker apptDatePicker;

    @FXML
    private TableColumn<?, ?> apptStartTime;

    @FXML
    private ComboBox<LocalTime> apptStartTimeCombo;

    @FXML
    private TableView<Appointment> apptTable;

    @FXML
    private TableColumn<?, ?> apptTitleCol;

    @FXML
    private TextField apptTitleText;

    @FXML
    private TableColumn<?, ?> apptTypeCol;

    @FXML
    private TextField apptTypeText;

    @FXML
    private TableColumn<?, ?> apptUserId;

    @FXML
    private ComboBox<User> apptUserIdCombo;

    @FXML
    private TextField apptSearchField;

    @FXML
    private ToggleGroup apptToggles;

    /**
     * Searches all Appointments for the content of the Search text field.
     * Selected an object if found by an ID int, or a list of objects matching a String.
     *
     * @param event object passed from text field.
     */
    @FXML
    void apptSearchAction(ActionEvent event) {
        apptErrorLabel.setText("");
        if(apptSearchField.getText() != "") {
            try {
                apptTable.setItems(AppointmentList.getAllAppointments());
                apptTable.refresh();
                int searchId = Integer.parseInt(apptSearchField.getText());
                Appointment appt = AppointmentList.searchAppt(searchId);
                if (appt != null) {
                    apptTable.getSelectionModel().select(appt);
                } else {
                    apptErrorLabel.setText("Item not found.");
                }
            } catch (NumberFormatException e) {
                AppointmentList.searchAppt(apptSearchField.getText());
                if (AppointmentList.getSearchListSize() == 0) {
                    apptTable.setItems(AppointmentList.getAllAppointments());
                    apptErrorLabel.setText("Item not found.");
                } else {
                    apptTable.setItems(AppointmentList.searchAppt(apptSearchField.getText()));
                }
                apptTable.refresh();
            }
        }
        else{
            apptTable.setItems(AppointmentList.getAllAppointments());
        }
    }

    /**
     * Set the list used by the table view to all when radio button selected.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void apptRadioAll(ActionEvent event) {
        filteredList.clear();
        apptTable.setItems(AppointmentList.getAllAppointments());
    }

    /**
     * Set the list used by the table view to one containing only Appointments in the current month of the system clock.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void apptRadioMonth(ActionEvent event) {
        filteredList.clear();
        Month month = LocalDateTime.now().getMonth();
        for(Appointment appt : AppointmentList.getAllAppointments()){
            if(appt.getStart().getMonth().equals(month)){
                filteredList.add(appt);
            }
        }
        apptTable.setItems(filteredList);
    }

    /**
     * Set the list used by the table view to one containing only Appointments in the current week of the system clock.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void apptRadioWeek(ActionEvent event) {
        filteredList.clear();
        LocalDateTime current = LocalDateTime.now();
        int week = current.get(WeekFields.ISO.weekOfYear());

        for(Appointment appt : AppointmentList.getAllAppointments()){
            if(appt.getStart().get(WeekFields.ISO.weekOfYear()) == week){
                filteredList.add(appt);
            }
        }
        apptTable.setItems(filteredList);
    }

    /**
     * On button click retrieve values from text fields and combo boxes. If any are empty or null, display error message.
     * Use values to modify selected Appointment object or add new Appointment object.
     * Push new or modified object to JDBC.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void apptAddButtonAction(ActionEvent event) {
        String errorMes = "";
        apptErrorLabel.setText(errorMes);
        LocalDateTime start = null;
        LocalDateTime end = null;
        // Get values from text fields and combo boxes
        String title = apptTitleText.getText();
        String desc = apptDescText.getText();
        String location = apptLocText.getText();
        Contact contact = apptContactCombo.getValue();
        String type = apptTypeText.getText();
        LocalDate apptDate = apptDatePicker.getValue();
        LocalTime startTime = apptStartTimeCombo.getValue();
        LocalTime endTime = apptEndTimeCombo.getValue();
        Customer cust = apptCustIdCombo.getValue();
        User user = apptUserIdCombo.getValue();
        // Check if any fields were left empty, add a message to errorMes
        if(title == "" || title == null){
            errorMes += "Title needs a value.\n";
        }
        if(desc == "" || desc == null){
            errorMes += "Description needs a value.\n";
        }
        if(location == "" || location == null){
            errorMes += "Location needs a value.\n";
        }
        if(contact == null){
            errorMes += "Contact needs a value.\n";
        }
        if(type == "" || type == null){
            errorMes += "Type needs a value.\n";
        }
        if(apptDate == null || startTime == null || endTime == null){
            errorMes += "All Dates and Times needs values.\n";
        }
        else{
            // Check end time are after start date
            if(startTime.isAfter(endTime)){
                errorMes += "Start Time must be before End Time.\n";
            }
            else{
                start = LocalDateTime.of(apptDate, startTime);
                end = LocalDateTime.of(apptDate, endTime);
                // Check for time overlap with other Appointments of the same Customer
                for(Appointment apptSearch : AppointmentList.getAllAppointments()){
                    if(cust.equals(apptSearch.getCustomer())) {
                        if (modifyMode) {
                            if (selAppt.getId() != apptSearch.getId()) {
                                // If modifying and Appointment IDs don't match, check for time overlap
                                if ((start.isAfter(apptSearch.getStart()) || start.isEqual(apptSearch.getStart())) && (start.isBefore(apptSearch.getEnd()))) {
                                    errorMes += "Appointment time overlaps another appointment.";
                                    break;
                                }
                                if((end.isAfter(apptSearch.getStart())) && (end.isBefore(apptSearch.getEnd()) || end.isEqual(apptSearch.getEnd()))){
                                    errorMes += "Appointment time overlaps another appointment.";
                                    break;
                                }
                                if((start.isBefore(apptSearch.getStart()) || start.isEqual(apptSearch.getStart())) && (end.isAfter(apptSearch.getEnd()) || end.isEqual(apptSearch.getEnd()))){
                                    errorMes += "Appointment time overlaps another appointment.";
                                    break;
                                }
                            }
                        } else {
                            // If not modifying, check for time overlap
                            if ((start.isAfter(apptSearch.getStart()) || start.isEqual(apptSearch.getStart())) && (start.isBefore(apptSearch.getEnd()))) {
                                errorMes += "Appointment time overlaps another appointment.";
                                break;
                            }
                            if((end.isAfter(apptSearch.getStart())) && (end.isBefore(apptSearch.getEnd()) || end.isEqual(apptSearch.getEnd()))){
                                errorMes += "Appointment time overlaps another appointment.";
                                break;
                            }
                            if((start.isBefore(apptSearch.getStart()) || start.isEqual(apptSearch.getStart())) && (end.isAfter(apptSearch.getEnd()) || end.isEqual(apptSearch.getEnd()))) {
                                errorMes += "Appointment time overlaps another appointment.";
                                break;
                            }
                        }
                    }
                }
            }
        }
        if(cust == null){
            errorMes += "Customer ID needs a value.\n";
        }
        if(user == null){
            errorMes += "User ID needs a value.\n";
        }

        // If errorMes is not an empty string, display it, otherwise check if this is to modify or add a new Appointment
        if(errorMes != ""){
            apptErrorLabel.setText(errorMes);
        }
        else{
            if(modifyMode){
                selAppt.setTitle(title);
                selAppt.setDesc(desc);
                selAppt.setLocation(location);
                selAppt.setContact(contact);
                selAppt.setType(type);
                selAppt.setStart(start);
                selAppt.setEnd(end);
                selAppt.setCustomer(cust);
                selAppt.setUser(user);

                //FIXME commented out for local development
                //int rows = UpdateQuery.modifyAppt(selAppt);
                //System.out.println(rows);
                //AppointmentList.emptyAppointmentList();
                //ReadQuery.readAppts();
                apptTable.refresh();
            }
            else{
                int id = 0;
                Appointment appt = new Appointment(id, title, desc, location, type, start, end, cust, user, contact);

                //FIXME line added for local only
                AppointmentList.addAppointment(appt);

                //FIXME commented out for local development
                //int rows = CreateQuery.insertAppt(appt);
                //System.out.println(rows);
                //AppointmentList.emptyAppointmentList();
                //ReadQuery.readAppts();
                apptTable.refresh();
            }
            // Clear fields and return to Add Customer
            clearModify();
        }
    }

    /**
     * Clears modify fields, and returns the Modify Customer section to Add Customer.
     * Hides the clear/cancel modify button.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void apptClearButtonAction(ActionEvent event) {
        apptErrorLabel.setText("");
        clearModify();
    }

    /**
     * Gets a selection from the table view and deletes the object after user confirmation.
     * A message is displayed if delete accepted or cancelled.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void apptDeleteButtonAction(ActionEvent event) {
        apptErrorLabel.setText("");
        Appointment selAppt = apptTable.getSelectionModel().getSelectedItem();
        // Check if no selection is made and display an error message
        if(selAppt == null){
            apptErrorLabel.setText("You must make a selection to Delete.");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the selected Appointment.\nAre you sure?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                apptErrorLabel.setText("Appointment ID: " + selAppt.getId() + " | Type: " + selAppt.getType() + " was successfully deleted.");

                //FIXME commented out for local development
                //int rows = DeleteQuery.deleteAppt(selAppt.getId());
                //System.out.println(rows);
                //AppointmentList.emptyAppointmentList();
                //ReadQuery.readAppts();
                clearModify();
                apptTable.refresh();
            } else {
                apptErrorLabel.setText("Appointment not deleted.");
            }
        }
    }

    /**
     * Gets item selected in table view and populated text fields with object values.
     * Alters the Add Appointment label to Modify Appointment.
     * Alters the Add button to Modify button.
     * If no selection is made an error message is displayed.
     * Shows the clear/cancel modify button.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void apptDetailsButtonAction(ActionEvent event) {
        apptErrorLabel.setText("");
        selAppt = apptTable.getSelectionModel().getSelectedItem();
        if(selAppt == null){
            //Display message is no selection made
            apptErrorLabel.setText("No selection made in Appointments table.");
        }
        else{
            // Alter Add Label and Button to Modify
            apptAddApptLabel.setText("Modify Appointment");
            apptAddButton.setText("Modify");

            // Show and enable button
            apptClearButton.setOpacity(1);
            apptClearButton.setDisable(false);

            // Populate text fields and combo boxes with Customer values
            apptIdText.setText(String.valueOf(selAppt.getId()));
            apptTitleText.setText(selAppt.getTitle());
            apptDescText.setText(selAppt.getDesc());
            apptLocText.setText(selAppt.getLocation());
            apptContactCombo.setValue(selAppt.getContact());
            apptTypeText.setText(selAppt.getType());
            apptDatePicker.setValue(selAppt.getApptDate());
            apptStartTimeCombo.setValue(selAppt.getStartTime());
            apptEndTimeCombo.setValue(selAppt.getEndTime());
            apptCustIdCombo.setValue(selAppt.getCustomer());
            apptUserIdCombo.setValue(selAppt.getUser());

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
    void apptMainMenuButtonAction(ActionEvent event) throws IOException {
        wl.loadNewWindow(event, "/View/MainMenu.fxml");
    }

    /**
     * Initialize the Appointments window combo boxes and table view.
     *
     * @param url is not used in this override.
     * @param resourceBundle is not used in this override.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Set TableView to show Appointment objects
        apptTable.setItems(AppointmentList.getAllAppointments());
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
        apptLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartDate.setCellValueFactory(new PropertyValueFactory<>("apptDate"));
        apptStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        apptEndDate.setCellValueFactory(new PropertyValueFactory<>("apptDate"));
        apptEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        apptCustId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        apptUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));

        // Set combo Boxes to correct lists
        apptContactCombo.setItems(ContactList.getAllContacts());
        apptCustIdCombo.setItems(CustomerList.getAllCustomers());
        apptUserIdCombo.setItems(UserList.getAllUsers());

        // Setup time list and set to time combos
        OfficeTimes.addTimes();
        apptStartTimeCombo.setItems(OfficeTimes.getAllTimes());
        apptEndTimeCombo.setItems(OfficeTimes.getAllTimes());
    }

    /**
     * Used to revert Appointment Modify form to the Appointment Add form.
     */
    private void clearModify(){
        // Alter Modify Label and Button to Add
        apptAddApptLabel.setText("Add Appointment");
        apptAddButton.setText("Add");

        // Hide and disable button
        apptClearButton.setOpacity(0);
        apptClearButton.setDisable(true);

        // Clears fields of values
        apptIdText.setText("");
        apptTitleText.setText("");
        apptDescText.setText("");
        apptLocText.setText("");
        apptContactCombo.setValue(null);
        apptTypeText.setText("");
        apptDatePicker.setValue(null);
        apptStartTimeCombo.setValue(null);
        apptEndTimeCombo.setValue(null);
        apptCustIdCombo.setValue(null);
        apptUserIdCombo.setValue(null);

        modifyMode = false;
    }
}
