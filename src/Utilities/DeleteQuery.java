package Utilities;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class for sending DELETE SQL statements to a database.
 *
 * @author Scott VanBrunt
 */
public abstract class DeleteQuery {

    /**
     *
     * @param custId int to identify a record in the database to remove.
     * @return int of rows affected.
     */
    public static int deleteCustomer(int custId){
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        try{
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setInt(1, custId);
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
     * @param apptId int to identify a record in the database to remove.
     * @return int of rows affected.
     */
    public static int deleteAppt(int apptId){
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        try{
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setInt(1, apptId);
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
     * @param userId int to identify a record in the database to remove.
     * @return int of rows affected.
     */
    public static int deleteUser(int userId){
        String sql = "DELETE FROM users WHERE User_ID = ?";
        try{
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setInt(1, userId);
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
     * @param contactId int to identify a record in the database to remove.
     * @return int of rows affected.
     */
    public static int deleteContact(int contactId){
        String sql = "DELETE FROM contacts WHERE Contact_ID = ?";
        try{
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setInt(1, contactId);
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
     * @param countryId int to identify a record in the database to remove.
     * @return int of rows affected.
     */
    public static int deleteCountry(int countryId){
        String sql = "DELETE FROM countries WHERE Country_ID = ?";
        try{
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setInt(1, countryId);
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
     * @param divisionId int to identify a record in the database to remove.
     * @return int of rows affected.
     */
    public static int deleteDivision(int divisionId){
        String sql = "DELETE FROM first_level_divisions WHERE Division_ID = ?";
        try{
            PreparedStatement ps = JDBC.conn.prepareStatement(sql);
            ps.setInt(1, divisionId);
            int rows = ps.executeUpdate();
            return rows;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
