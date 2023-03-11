package Controller;

import Model.*;
import Utilities.ReadQuery;
import Utilities.ReportView;
import Utilities.WindowLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ResourceBundle;

/**
 * This creates a controller object to handle UI events on the Reports window.
 *
 * LAMBDA expression is used to call the WindowLoader interface and prepare to load a new window.
 *
 * Second LAMBDA expression is used twice to call ReportView interface and either show or clear a report label.
 *
 * @author Scott VanBrunt
 */
public class ReportsController implements Initializable {
    private ObservableList<Month> months = FXCollections.observableArrayList();
    private ObservableList<String> types = FXCollections.observableArrayList();
    private ObservableList<Contact> contacts = ContactList.getAllContacts();
    private String outputText;

    // Create a lambda expression to prepare to load a new window
    WindowLoader wl = (ActionEvent event, String url) -> {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource(url));
        stage.setScene(new Scene(scene));
        stage.show();
    };

    @FXML
    private ComboBox<Month> report1MonthCombo;

    @FXML
    private ComboBox<String> report1TypeCombo;

    @FXML
    private ComboBox<Contact> report2Combo;

    @FXML
    private Label reportsResultLabel;

    // Create a lambda expression to show a String in the report label
    ReportView showReport = (String message) -> {
        reportsResultLabel.setText(message);
    };

    // Create a lambda expression to show only an empty String in the report label
    ReportView clearReport = (String message) -> {
        reportsResultLabel.setText("");
    };

    /**
     * On click all Appointments sharing a month and type with combo box selections are tallied and displayed.
     *
     * Lambda expression is called as both clearReport and showReport, to clear and show the report label.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void report1ButtonAction(ActionEvent event) {
        int count = 0;
        LocalDateTime now = LocalDateTime.now();
        outputText = "Report: Count by Type and Month\nReport Date: " + now.toLocalDate() + "\nReport Time: " + now.toLocalTime() + "\n------------------------------------\n\n";
        clearReport.reportView(outputText);
        Month selMonth = report1MonthCombo.getSelectionModel().getSelectedItem();
        String type = report1TypeCombo.getValue();
        // Count occurrences of appointments that have the same month and type as selected in combo boxes
        for(Appointment appt : AppointmentList.getAllAppointments()){
            if(appt.getStart().getMonth().equals(selMonth) && appt.getType().equals(type)){
                count++;
            }
        }
        // Check that the combo boxes have a selection and display a message if not
        if(selMonth == null || type == null){
            outputText = "Both combo boxes must have a selection for this report.";
        }
        else {
            outputText += selMonth + " | " + type + ": " + count;
        }
        showReport.reportView(outputText);
    }

    /**
     * Not used in this implementation.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void report1MonthComboAction(ActionEvent event) {

    }

    /**
     * Not used in this implementation.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void report1TypeComboAction(ActionEvent event) {

    }

    /**
     * On click a Contact is retrieved from a combo box and each Appointment related to the Contact is listed.
     *
     * Lambda expression is called as both clearReport and showReport, to clear and show the report label.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void report2ButtonAction(ActionEvent event) {
        LocalDateTime now = LocalDateTime.now();
        outputText = "Report: Contact Schedule\nReport Date: " + now.toLocalDate() + "\nReport Time: " + now.toLocalTime() + "\n------------------------------------\n\n";;
        clearReport.reportView(outputText);
        Contact selCon = report2Combo.getSelectionModel().getSelectedItem();
        for(Appointment appt : AppointmentList.getAllAppointments()){
            if(appt.getContact().equals(selCon)){
                outputText += ("Appointment ID: " + appt.getId() + " | Title: " + appt.getTitle() + " | Description: " +
                        appt.getDesc() + "\nStart Date/Time: " + appt.getStart().toLocalDate() + " - " + appt.getStart().toLocalTime() +
                        " | End Date/Time: " + appt.getEnd().toLocalDate() + " - " + appt.getEnd().toLocalTime() +
                        " | Customer ID: " + appt.getCustomer().getId()) + "\n\n";
            }
        }
        // Check that the combo box has a selection and display a message if not
        if(selCon == null){
            outputText = "A selection must be made for this report.";
        }
        showReport.reportView(outputText);
    }

    /**
     * Not used in this implementation.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void report2ComboAction(ActionEvent event) {

    }

    /**
     * On button click the AppointmentList is checked and each appointment on today is tallied then displayed.
     *
     * Lambda expression is called as both clearReport and showReport, to clear and show the report label.
     *
     * @param event object passed from clicked button.
     */
    @FXML
    void report3ButtonAction(ActionEvent event) {
        LocalDateTime now = LocalDateTime.now();
        outputText = "Report: Appointments Today\nReport Date: " + now.toLocalDate() + "\nReport Time: " + now.toLocalTime() + "\n------------------------------------\n\n";;
        LocalDate today = LocalDate.now();
        int total = 0;
        for(Appointment appt : AppointmentList.getAllAppointments()){
            if(appt.getStart().toLocalDate().isEqual(today)){
                total++;
            }
        }
        outputText += "Total Appointments Today: " + total;
        showReport.reportView(outputText);
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
    void reportMainMenuButtonAction(ActionEvent event) throws IOException {
        wl.loadNewWindow(event, "/View/MainMenu.fxml");
    }

    /**
     * Initialize the Reports window combo boxes.
     *
     * @param url is not used in this override.
     * @param resourceBundle is not used in this override.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Filter an ObservableList to hold only the months there are appointments for and display in combo box
        for(Appointment appt : AppointmentList.getAllAppointments()){
            Month current = appt.getStart().getMonth();
            if(!months.contains(current)) {
                months.add(current);
            }
        }
        report1MonthCombo.setItems(months);

        // Filter an ObservableList to hold only the types of appointments available and display in combo box
        for(Appointment appt : AppointmentList.getAllAppointments()){
            String current = appt.getType();
            if(!types.contains(current)){
                types.add(current);
            }
        }
        report1TypeCombo.setItems(types);

        // Display contacts in combo box
        report2Combo.setItems(contacts);
    }
}
