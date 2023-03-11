package Model;

/**
 * Class to create Country objects with id and name fields and related methods.
 *
 * @author Scott VanBrunt
 */
public class Country {
    private int id;
    private String name;

    /**
     * Default constructor to assign default values on object creation.
     */
    public Country(){
        this.id = 0;
        this.name = null;
    }

    /**
     * Overloaded constructor to assign arguments values to object fields.
     *
     * @param id int to assign to Country id.
     * @param name String to assign to Country name.
     */
    public Country(int id, String name){
        this.id = id;
        this.name = name;
    }

    /**
     *
     * @return int of Country id.
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id int to set Country id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return String of Country name.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name String to set Country name.
     */
    public void setName(String name) {
        this.name = name;
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
