package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates a static list to hold User objects along with required methods.
 *
 * @author Scott VanBrunt
 */
public class UserList {

    private static ObservableList<User> userList = FXCollections.observableArrayList();

    /**
     * @param user object is taken and added to userList.
     */
    public static void addUser(User user){
        userList.add(user);
    }

    /**
     * Empties the userList ObservableList of all elements.
     */
    public static void emptyUserList(){
        userList.clear();
    }

    /**
     * @return ObservableList of all User objects in userList.
     */
    public static ObservableList<User> getAllUsers(){
        return userList;
    }

    /**
     *
     * @param userName String containing a userName to search userList for.
     * @return User object if search succeeded, null otherwise.
     */
    public static User searchUserList(String userName){
        for(User user : UserList.getAllUsers()){
            if(user.getName().equals(userName)){
                return user;
            }
        }
        return null;
    }

    /**
     *
     * @param id int to search for in Customer IDs.
     * @return User if search successful, null otherwise.
     */
    public static User searchById(int id){
        for(User user : userList){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }
}
