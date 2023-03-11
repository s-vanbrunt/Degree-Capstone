package Model;

/**
 * Class to create Division objects with id, name and Country fields and related methods.
 *
 * @author Scott VanBrunt
 */
public class Division {
    private int id;
    private String name;
    private Country country;
    private String countryName;

    /**
     * Default constructor to assign default values on creation.
     */
    public Division(){
        this.id = 0;
        this.name = null;
        this.country = null;
        this.countryName = null;
    }

    /**
     * Overloaded constructor to assign fields based on arguments.
     *
     * @param id int to set to Division id.
     * @param name String to set to Division name.
     * @param country String to set to Division country.
     */
    public Division(int id, String name, Country country){
        this.id = id;
        this.name = name;
        this.country = country;
        this.countryName = country.getName();
    }

    /**
     *
     * @return int for Division id.
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id int to set to Division id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return String for Division name.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name String to set to Division name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return Country for Division Country.
     */
    public Country getCountry() {
        return country;
    }

    /**
     *
     * @param country object to set as Division Country.
     */
    public void setCountry(Country country) {
        this.country = country;
        this.countryName = country.getName();
    }

    /**
     *
     * @return String of associated Country name.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Override the toString() method to format output in a combo box.
     *
     * @return String to show in a combo box.
     */
    @Override
    public String toString(){
        return (name);
    }
}
