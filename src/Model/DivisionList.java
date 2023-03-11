package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates a static list to hold Division objects along with required methods.
 *
 * @author Scott VanBrunt
 */
public class DivisionList {

    private static ObservableList<Division> divisionList = FXCollections.observableArrayList();

    /**
     * @param division Object to be added to contactList.
     */
    public static void addDivision(Division division){
        divisionList.add(division);
    }

    /**
     * Empties the ObservableList of all Division objects.
     */
    public static void emptyDivisionList(){
        divisionList.clear();
    }

    /**
     * @return ObservableList containing all Divisions in divisionList.
     */
    public static ObservableList<Division> getAllDivisions(){
        return divisionList;
    }

    /**
     *
     * @param id int to search for in Division IDs.
     * @return Division if search successful, null otherwise.
     */
    public static Division searchById(int id){
        for(Division div : divisionList){
            if(div.getId() == id){
                return div;
            }
        }
        return null;
    }
}
