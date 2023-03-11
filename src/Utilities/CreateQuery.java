package Utilities;

import Model.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Class for sending INSERT SQL statements to a database.
 *
 * @author Scott VanBrunt
 */
public abstract class CreateQuery {

    /**
     *
     * @param cust Customer object to get values for insertion into database.
     * @return int for number of rows affected.
     */
    public static int insertCustomer(Customer cust){
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        try{
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setString(1, cust.getName());
            ps.setString(2, cust.getAddress());
            ps.setString(3, cust.getPostal());
            ps.setString(4, cust.getPhone());
            ps.setInt(5, cust.getDivision().getId());
            int rows = ps.executeUpdate();
            return rows;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     *
     * @param appt Appointment object to get values for insertion into database.
     * @return int for number of rows affected.
     */
    public static int insertAppt(Appointment appt){
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setString(1, appt.getTitle());
            ps.setString(2, appt.getDesc());
            ps.setString(3, appt.getLocation());
            ps.setString(4, appt.getType());
            ps.setTimestamp(5, Timestamp.valueOf(appt.getStart()));
            ps.setTimestamp(6, Timestamp.valueOf(appt.getEnd()));
            ps.setInt(7, appt.getCustomer().getId());
            ps.setInt(8, appt.getUser().getId());
            ps.setInt(9, appt.getContact().getId());
            int rows = ps.executeUpdate();
            return rows;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     *
     * @param user User object to get values for insertion into database
     * @return int for number of rows affected
     */
    public static int insertUser(User user){
        String sql = "INSERT INTO users (User_Name, Password) VALUES (?, ?)";
        try{
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            int rows = ps.executeUpdate();
            return rows;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     *
     * @param contact Contact object to get values for insertion into database
     * @return int for number of rows affected
     */
    public static int insertContact(Contact contact){
        String sql = "INSERT INTO contacts (Contact_Name, Email) VALUES (?, ?)";
        try{
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setString(1, contact.getName());
            ps.setString(2, contact.getEmail());
            int rows = ps.executeUpdate();
            return rows;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     *
     * @param country Country object to get values for insertion into database
     * @return int for number of rows affected
     */
    public static int insertCountry(Country country){
        String sql = "INSERT INTO countries (Country) VALUES (?)";
        try{
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setString(1, country.getName());
            int rows = ps.executeUpdate();
            return rows;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     *
     * @param division Division object to get values for insertion into database
     * @return int for number of rows affected
     */
    public static int insertDivision(Division division){
        String sql = "INSERT INTO first_level_divisions (Division, Country_ID) VALUES (?, ?)";
        try{
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setString(1, division.getName());
            ps.setInt(2, division.getCountry().getId());
            int rows = ps.executeUpdate();
            return rows;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
