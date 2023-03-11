package Model;

/**
 * Class to create Customer objects with id, name, address, postalCode, phone and Division fields and related methods.
 * Extends and inherits from BasicContact for id and name fields and methods.
 *
 * @author Scott VanBrunt
 */
public class Customer extends BasicContact{
    private String address;
    private String postal;
    private String phone;
    private Division division;
    private Country country;
    private String divName;
    private String countryName;

    /**
     * Default method to load default values into new objects.
     */
    public Customer(){
        super();
        this.address = null;
        this.postal = null;
        this.phone = null;
        this.division = null;
        this.country = null;
        this.divName = null;
        this.countryName = null;
    }

    /**
     * Overloaded constructor to pass values into new Customer object fields.
     *
     * @param id int to set to Customer id.
     * @param name String to set to Customer name.
     * @param address String to set to Customer address.
     * @param postal String to set to postal.
     * @param phone String to set to phone.
     * @param division Division object to set to division.
     */
    public Customer(int id, String name, String address, String postal, String phone, Division division){
        super(id, name);
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.division = division;
        this.country = division.getCountry();
        this.divName = division.getName();
        this.countryName = country.getName();
    }

    /**
     *
     * @return String of Customer address.
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address String to set Customer address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return String of Customer postal code.
     */
    public String getPostal() {
        return postal;
    }

    /**
     *
     * @param postal String to set Customer postal code.
     */
    public void setPostal(String postal) {
        this.postal = postal;
    }

    /**
     *
     * @return String of Customer phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone String to set Customer phone number.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return String of Customer Division.
     */
    public Division getDivision() {
        return division;
    }

    /**
     *
     * @param division String to set Customer Division.
     */
    public void setDivision(Division division) {
        this.division = division;
        this.country = division.getCountry();
        this.divName = division.getName();
        this.countryName = country.getName();
    }

    /**
     *
     * @return String of Division name.
     */
    public String getDivName() {
        return divName;
    }

    /**
     *
     * @return String of Country name.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Override the toString() method to format output in a combo box.
     *
     * @return String formatted to show in a combo box.
     */
    @Override
    public String toString(){
        return ("ID: " + super.getId() + " | Name: " + super.getName());
    }
}
