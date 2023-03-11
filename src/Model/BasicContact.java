package Model;

/**
 * Abstract object class to hold id and name fields and associated methods to inherit.
 *
 * @author Scott VanBrunt
 */
public abstract class BasicContact {
    private int id;
    private String name;

    /**
     * Default constructor to assign default values to fields.
     */
    public BasicContact(){
        this.id = 0;
        this.name = null;
    }

    /**
     * Overloaded constructor that takes in values for each object field.
     *
     * @param id int to store ID number.
     * @param name String to store name.
     */
    public BasicContact(int id, String name){
        this.id = id;
        this.name = name;
    }

    /**
     *
     * @return int for ID number.
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id int to set ID number.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return String for name.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name String to set name.
     */
    public void setName(String name) {
        this.name = name;
    }
}
