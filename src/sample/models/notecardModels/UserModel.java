package sample.models.notecardModels;

import sample.models.DbConnectionManager;
import sample.models.notecardModels.noteCards.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 * @author rn046359
 */
public class UserModel {
    private final Connection connection;

    public UserModel() {
    connection = DbConnectionManager.connect();
    }

    /**
     * Get user information from the database
     *
     * @param userId
     *      the userID
     * @param passwordHash
     *      the user's password
     *
     * @return an instance of the User created from the database info.
     */
    public User getUserInfo(String userId, String passwordHash){
        User user = new User();
        try{
            final String query = "Select FName, LName FROM User WHERE user_id = "+ userId;
            final Statement stmt = connection.createStatement();
            final ResultSet rset = stmt.executeQuery(query);

            if(rset.next()){
                user.setFirstName(rset.getString("FName"));
                user.setLastName(rset.getString("LName"));
                user.setUserId(userId);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Inserts a new user into the database.
     *
     * @param user
     *      a user instance
     * @param passwordHash
     *      the User's password.
     * @param passwordSalt
     *      the salt applied to the password
     * @return true if the insert succeeds. False otherwise.
     */
    public Boolean createUser(User user, String passwordHash, String passwordSalt){
        try{
            final String query = "INSERT INTO User {FName, LName, user_id, PassHash, Salt)" +
                    "VALUES (?, ?, ?, ?, ?)";
            final PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getUserId());
            stmt.setString(4, passwordHash);
            stmt.setString(5, passwordSalt);

            final int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0){
                System.out.println("A new User was created");
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Authenticates the user with the database.
     *
     * @param userId
     *      the userId
     * @param passwordHash
     *      the user's password
     * @return true if the provided password matches the password in the database. False otherwise.
     */
    public Boolean getLoginInfo(String userId, String passwordHash){
        try{
            final String query = "SELECT PassHash FROM User WHERE user_id = "+ userId;
            final Statement stmt = connection.createStatement();
            final ResultSet rset = stmt.executeQuery(query);

            final int rowsReturned = rset.getFetchSize();

            if (rowsReturned == 1){
                if(rset.getString("PassHash").equals(passwordHash)){
                    return true;
                }
            }else{
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates the user information in the database.
     *
     * @param userId
     *      the userId
     * @param updatedUser
     *      an updated instance of the user
     * @return true if UPDATE was successful. False otherwise.
     */
    public Boolean updateUserInfo(String userId, User updatedUser){
        try{
            final String query = "UPDATE User " +
                                "SET FName=?, LName=?, user_id=? " +
                                "WHERE user_id=?";
            final PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, updatedUser.getFirstName());
            stmt.setString(2, updatedUser.getLastName());
            stmt.setString(3, updatedUser.getUserId());
            stmt.setString(4, userId);

            final int updated = stmt.executeUpdate();
            if (updated > 0){
                System.out.println("An existing user account was updated.");
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates the user's password in the database.
     *
     * @param oldPasswordHash
     *      the user's old password
     * @param newPasswordHash
     *      the user's new password
     * @return true if UPDATE was successful. False otherwise.
     */
    public Boolean updatePassword(String oldPasswordHash, String newPasswordHash){
        try{
            final String query = "UPDATE User " +
                                "SET PassHash=?" +
                                "WHERE PassHash=?";
            final PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, newPasswordHash);
            stmt.setString(2, oldPasswordHash);

            final int update = stmt.executeUpdate();
            if (update > 0){
                System.out.println("Password was updated successfully.");
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes the user from the database.
     *
     * @param user
     *      instance of the user to be deleted.
     * @return true if DELETE was successful. False otherwise.
     */
    public Boolean deleteUser(User user){
        try{
            final String query = "DELETE FROM User " +
                                "WHERE FName=? " +
                                "AND LName=?" +
                                "AND user_id=?";
            final PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getUserId());

            final int deleted = stmt.executeUpdate();
            if(deleted > 0){
                System.out.println("User record deleted from database");
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
