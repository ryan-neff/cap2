package sample.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author ryan neff
 */
public class DbConnectionManager {

    public static Connection connect() {
        try{
            String url = "jdbc:mysql://capstone.cno7c7zt0gap.us-west-2.rds.amazonaws.com:3306/";
            String userName = "capstone";
            String password = "pass12345";
            String dbName = "capstonedb";
            String driver = "com.mysql.jdbc.Driver";
            System.out.println("connecting...");

            Connection connection = DriverManager.getConnection(url + dbName, userName, password);
            System.out.println("Connected!");
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
