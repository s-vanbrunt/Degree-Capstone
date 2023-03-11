package Utilities;

import Model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Class for sending SELECT SQL statements to a database and filling local model with results.
 *
 * @author Scott VanBrunt
 */
public abstract class ReadQuery {

    /**
     * Returns all Users from database User table and fills UserList.
     */
    public static void readUsers(){
        String sql = "SELECT User_ID, User_Name, Password FROM users";
        try {
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("User_ID");
                String name = rs.getString("User_Name");
                String password = rs.getString("Password");
                User user = new User(id, name, password);
                UserList.addUser(user);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Returns all Contacts from database Contacts table and fills ContactsList.
     */
    public static void readContacts(){
        String sql = "SELECT Contact_ID, Contact_Name, Email FROM contacts";
        try {
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact contact = new Contact(id, name, email);
                ContactList.addContact(contact);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Returns all Countries from database Country table and fills CountriesList.
     */
    public static void readCountries(){
        String sql = "SELECT Country_ID, Country FROM countries";
        try {
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Country");
                Country country = new Country(id, name);
                CountryList.addCountry(country);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Returns all Divisions from database Divisions table and fills DivisionsList.
     */
    public static void readDivisions(){
        String sql = "SELECT Division_ID, Division, Country_ID FROM first_level_divisions";
        try {
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("Division_ID");
                String name = rs.getString("Division");
                int countryId = rs.getInt("Country_Id");
                Country country = CountryList.searchById(countryId);
                Division division = new Division(id, name, country);
                DivisionList.addDivision(division);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Returns all Customers from database Customers table and fills CustomersList.
     */
    public static void readCustomers(){
        String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM customers";
        try {
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postal = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divId = rs.getInt("Division_ID");
                Division div = DivisionList.searchById(divId);
                Customer cust = new Customer(id, name, address, postal, phone, div);
                CustomerList.addCustomer(cust);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Returns all Appointments from database Appointment table and fills AppointmentList.
     */
    public static void readAppts(){
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM appointments";
        try{
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String loc = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startTs = rs.getTimestamp("Start");
                LocalDateTime start = startTs.toLocalDateTime();
                Timestamp endTs = rs.getTimestamp("End");
                LocalDateTime end = endTs.toLocalDateTime();
                int custId = rs.getInt("Customer_ID");
                Customer cust = CustomerList.searchById(custId);
                int userId = rs.getInt("User_ID");
                User user = UserList.searchById(userId);
                int contactId = rs.getInt("Contact_ID");
                Contact contact = ContactList.searchById(contactId);
                Appointment appt = new Appointment(id, title, desc, loc, type, start, end, cust, user, contact);
                AppointmentList.addAppointment(appt);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Empties all lists then reads data from all database tables and populates associated class objects.
     */
    public static void readAllTables(){
        AppointmentList.emptyAppointmentList();
        CustomerList.emptyCustomerList();
        DivisionList.emptyDivisionList();
        CountryList.emptyCountryList();
        UserList.emptyUserList();
        ContactList.emptyContactList();
        readUsers();
        readContacts();
        readCountries();
        readDivisions();
        readCustomers();
        readAppts();
    }
}
