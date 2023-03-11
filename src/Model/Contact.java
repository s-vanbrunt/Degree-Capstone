package Model;

/**
 * Class to create Contact objects with id, name and email fields and related methods.
 * Extends and inherits from BasicContact for id and name fields and methods.
 *
 * @author Scott VanBrunt
 */
public class Contact extends BasicContact{
    private String email;

    /**
     * Default constructor to create a Contact object with default values.
     */
    public Contact(){
        super();
        this.email = null;
    }

    /**
     * Overloaded constructor to create a Contact object with argument values.
     *
     * @param id int to set to Contact id.
     * @param name String to set to Contact name.
     * @param email String to set to Contact email.
     */
    public Contact(int id, String name, String email){
        super(id, name);
        this.email = email;
    }

    /**
     * @return String for Contact email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email String used to set Contact email.
     */
    public void setEmail(String email) {
        this.email = email;
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
