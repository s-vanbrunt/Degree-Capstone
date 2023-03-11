package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates a static list to hold Customer objects along with required methods.
 *
 * @author Scott VanBrunt
 */
public class CustomerList {

    private static ObservableList<Customer> customerList = FXCollections.observableArrayList();

    /**
     * @param customer Object to be added to customerList.
     */
    public static void addCustomer(Customer customer){
        customerList.add(customer);
    }

    /**
     * Empties the ObservableList of all Customer objects.
     */
    public static void emptyCustomerList(){
        customerList.clear();
    }

    /**
     * @return ObservableList containing all Customer in customerList.
     */
    public static ObservableList<Customer> getAllCustomers(){
        return customerList;
    }

    /**
     *
     * @param id int to search for in Customer IDs.
     * @return Customer if search successful, null otherwise.
     */
    public static Customer searchById(int id){
        for(Customer cust : customerList){
            if(cust.getId() == id){
                return cust;
            }
        }
        return null;
    }
}
