package Utilities;

import Model.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Class for sending UPDATE SQL statements to a database.
 *
 * @author Scott VanBrunt
 */
public abstract class UpdateQuery {

    /**
     *
     * @param cust Customer object to get values for modification of database item.
     * @return int for number of rows affected.
     */
    public static int modifyCustomer(Customer cust){
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        try{
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setString(1, cust.getName());
            ps.setString(2, cust.getAddress());
            ps.setString(3, cust.getPostal());
            ps.setString(4, cust.getPhone());
            ps.setInt(5, cust.getDivision().getId());
            ps.setInt(6, cust.getId());
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
     * @param appt Appointment object to get values for modification of database item.
     * @return int for number of rows affected.
     */
    public static int modifyAppt(Appointment appt){
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
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
            ps.setInt(10, appt.getId());
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
     * @param user User object to get values for modification of database item.
     * @return int for number of rows affected
     */
    public static int modifyUser(User user){
        String sql = "UPDATE users SET User_Name = ?, Password = ? WHERE User_ID = ?";
        try{
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getId());
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
     * @param contact Contact object to get values for modification of database item.
     * @return int for number of rows affected
     */
    public static int modifyContact(Contact contact){
        String sql = "UPDATE contacts SET Contact_Name = ?, Email = ? WHERE Contact_ID = ?";
        try{
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setString(1, contact.getName());
            ps.setString(2, contact.getEmail());
            ps.setInt(3, contact.getId());
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
     * @param country Country object to get values for modification of database item.
     * @return int for number of rows affected
     */
    public static int modifyCountry(Country country){
        String sql = "UPDATE countries SET Country = ? WHERE Country_ID = ?";
        try{
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setString(1, country.getName());
            ps.setInt(2, country.getId());
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
     * @param division Division object to get values for modification of database item.
     * @return int for number of rows affected
     */
    public static int modifyDivision(Division division){
        String sql = "UPDATE first_level_divisions SET Division = ?, Country_ID = ? WHERE Division_ID = ?";
        try{
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setString(1, division.getName());
            ps.setInt(2, division.getCountry().getId());
            ps.setInt(3, division.getId());
            int rows = ps.executeUpdate();
            return rows;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
