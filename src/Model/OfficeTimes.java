package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.*;
import java.time.temporal.WeekFields;

/**
 * A class that contains a list of Office Times translated to the system local time to be used to limit time input choice
 * to those listed in a combo box.
 *
 * @author Scott VanBrunt
 */
public class OfficeTimes {
    private static ObservableList<LocalTime> times = FXCollections.observableArrayList();

    private static final LocalDate aDate = LocalDate.of(2022, 1, 1);
    private static final LocalTime officeOpen = LocalTime.of(8, 0);
    private static final ZoneId eastern = ZoneId.of("America/New_York");
    private static final ZonedDateTime easternOpen = ZonedDateTime.of(aDate, officeOpen, eastern);
    private static final ZoneId myZone = ZoneId.systemDefault();
    private static final ZonedDateTime myOfficeOpen = ZonedDateTime.ofInstant(easternOpen.toInstant(), myZone);
    private static final LocalTime localOfficeOpen = myOfficeOpen.toLocalTime();

    /**
     * Takes the local time equivalent of the EST office opening time and adds more times for each 15 minutes
     * increment until office closing time.
     */
    public static void addTimes() {
        times.clear();
        times.add(localOfficeOpen);
        LocalTime current = localOfficeOpen;
        for(int i = 0; i < 56; i++){
            current = current.plusMinutes(15);
            times.add(current);
        }
    }

    /**
     *
     * @return an ObservableList of all LocalTimes in times.
     */
    public static ObservableList<LocalTime> getAllTimes() {
        return times;
    }
}
