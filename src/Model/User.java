package Model;

/**
 * Class to create User objects that hold an ID, name, and password fields with appropriate methods.
 * Extends and inherits from BasicContact for id and name fields and methods.
 *
 * @author Scott VanBrunt
 */
public class User extends BasicContact{
    private String password;

    /**
     * Default constructor method that assigns values for each object field.
     */
    public User(){
        super();
        this.password = null;
    }

    /**
     * Overloaded constructor method that takes arguments for each object field.
     *
     * @param id int to store userId.
     * @param name String to store userName.
     * @param password String to store password.
     */
    public User(int id, String name, String password){
        super(id, name);
        this.password = password;
    }

    /**
     * @return String for User password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password String used to set User password.
     */
    public void setPassword(String password) {
        this.password = password;
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
