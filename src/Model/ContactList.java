package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates a static list to hold Contact objects along with required methods.
 *
 * @author Scott VanBrunt
 */
public class ContactList {

    private static ObservableList<Contact> contactList = FXCollections.observableArrayList();

    /**
     * @param contact Object to be added to contactList.
     */
    public static void addContact(Contact contact){
        contactList.add(contact);
    }

    /**
     * Empties the ObservableList of all Contact objects.
     */
    public static void emptyContactList(){
        contactList.clear();
    }

    /**
     * @return ObservableList containing all Contacts in contactList.
     */
    public static ObservableList<Contact> getAllContacts(){
        return contactList;
    }

    /**
     *
     * @param id int to search for in Customer IDs.
     * @return Contact if search successful, null otherwise.
     */
    public static Contact searchById(int id){
        for(Contact contact : contactList){
            if(contact.getId() == id){
                return contact;
            }
        }
        return null;
    }
}
