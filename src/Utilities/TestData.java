package Utilities;

import Model.*;

import java.time.LocalDateTime;

/**
 * Static class holding test data to add to lists when developing without a local database
 *
 * @author Scott VanBrunt
 */
public class TestData {

    private static User user1 = new User(1, "test", "test");
    private static User user2 = new User(2, "admin", "admin");

    private static Country c1 = new Country(1, "First Country");
    private static Country c2 = new Country(2, "Second Country");

    private static Division d1 = new Division(1, "First Division", c1);
    private static Division d2 = new Division(2, "Second Division", c2);

    private static Contact con1 = new Contact(1, "First Contact","first@email");
    private static Contact con2 = new Contact(2, "Second Contact","Second@email");

    private static Customer cust1 = new Customer(1, "First Customer", "1111 First Road", "11111", "111-111-1111", d1);
    private static Customer cust2 = new Customer(2, "Second Customer", "222 Second Road", "22222", "222-222-2222", d2);

    private static Appointment appt1 = new Appointment(1, "First Appt", "The First Appointment", "First Place", "This Type",
            LocalDateTime.of(2022, 12, 5, 12, 30),
            LocalDateTime.of(2022, 12, 5, 1, 30), cust1, user1, con1);
    private static Appointment appt2 = new Appointment(2, "Second Appt", "The Second Appointment", "Second Place", "That Type",
            LocalDateTime.of(2022, 12, 10, 14, 30),
            LocalDateTime.of(2022, 12, 10, 15, 30), cust2, user2, con2);

    /**
     * Static method to add test data to lists
     */
    public static void addTestData(){
        UserList.addUser(user1);
        UserList.addUser(user2);

        CountryList.addCountry(c1);
        CountryList.addCountry(c2);

        DivisionList.addDivision(d1);
        DivisionList.addDivision(d2);

        ContactList.addContact(con1);
        ContactList.addContact(con2);

        CustomerList.addCustomer(cust1);
        CustomerList.addCustomer(cust2);

        AppointmentList.addAppointment(appt1);
        AppointmentList.addAppointment(appt2);
    }

}
