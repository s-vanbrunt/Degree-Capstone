package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Creates a static list to hold Appointment objects along with required methods.
 *
 * @author Scott VanBrunt
 */
public class AppointmentList {

    private static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private static ObservableList<Appointment> searchList = FXCollections.observableArrayList();
    private static int searchListSize;

    /**
     * @param appointment Object to be added to appointmentList.
     */
    public static void addAppointment(Appointment appointment){
        appointmentList.add(appointment);
    }

    /**
     * Empties the ObservableList of all Appointment objects.
     */
    public static void emptyAppointmentList(){
        appointmentList.clear();
    }

    /**
     * @return ObservableList containing all Appointments in appointmentList.
     */
    public static ObservableList<Appointment> getAllAppointments(){
        return appointmentList;
    }

    /**
     * Search for appointments with the username used for login, on same day and within 15 minutes of login.
     *
     * @param userId int containing a userId to search appointmentList for.
     * @return Appointment object if search succeeded, null otherwise.
     */
    public static Appointment searchUpcomingAppt(int userId){
        // Time checking for next 15 minutes
        for(Appointment appt : AppointmentList.getAllAppointments()){

            if(appt.getUser().getId() == (userId)){

                LocalDate date = appt.getStart().toLocalDate();
                LocalTime time = appt.getStart().toLocalTime();
                long diff = ChronoUnit.MINUTES.between(LocalTime.now(), time);

                if(date.isEqual(LocalDate.now()) && (diff >= 0 && diff <= 15)){    // If date is today and time is within 15 minutes
                    return appt;
                }
            }
        }
        return null;
    }

    /**
     *
     * @param id int to search.
     * @return Appointment if ID found, null otherwise.
     */
    public static Appointment searchAppt(int id){
        for(Appointment appt : AppointmentList.getAllAppointments()){
            if(appt.getId() == id){
                return appt;
            }
        }
        return null;
    }

    /**
     *
     * @param name accepts a name String and searches appointmentsList for matches.
     * @return an ObservableList of matching Appointment objects.
     */
    public static ObservableList<Appointment> searchAppt(String name){
        if(!searchList.isEmpty()){
            searchList.clear();
            searchListSize = 0;
        }
        for(Appointment appt : AppointmentList.getAllAppointments()){
            if(appt.getTitle().contains(name)){
                searchList.add(appt);
                searchListSize++;
            }
        }
        if(searchList.isEmpty()){
            return appointmentList;
        }
        return searchList;
    }

    /**
     *
     * @return int for number of items in searchList.
     */
    public static int getSearchListSize() {
        return searchListSize;
    }
}
