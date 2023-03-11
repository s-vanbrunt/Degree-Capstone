package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates a static list to hold Country objects along with required methods.
 *
 * @author Scott VanBrunt
 */
public class CountryList {

    private static ObservableList<Country> countryList = FXCollections.observableArrayList();

    /**
     * @param country Object to be added to countryList.
     */
    public static void addCountry(Country country){
        countryList.add(country);
    }

    /**
     * Empties the ObservableList of all Country objects.
     */
    public static void emptyCountryList(){
        countryList.clear();
    }

    /**
     * @return ObservableList containing all Countries in countryList.
     */
    public static ObservableList<Country> getAllCountries(){
        return countryList;
    }

    /**
     *
     * @param id int to search for in Customer IDs.
     * @return Country if search successful, null otherwise.
     */
    public static Country searchById(int id){
        for(Country country : countryList){
            if(country.getId() == id){
                return country;
            }
        }
        return null;
    }
}
