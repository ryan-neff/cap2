package studyTool.models.notecardModels;

import studyTool.models.DbConnectionManager;
import studyTool.models.notecardModels.noteCards.User;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;


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
     *
     * Legacy Code
     * Functionality now in getLoginInfo function.
     */
    /*public User getUserInfo(String userId){
        User user = new User();
        try{
            final String query = "Select FName, LName FROM User WHERE user_id = ?";
            final PreparedStatement stmt = connection.prepareStatement(query);
                    stmt.setString(1, userId);
            final ResultSet rset = stmt.executeQuery();

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
    */
    /**
     * Inserts a new user into the database.
     *
     * @param user
     *      a user instance
     * @param rawPassword
     *      the User's password.

     * @return true if the insert succeeds. False otherwise.
     */
    public User createUser(User user, String rawPassword){
        int salt = generateSalt();
        String passwordHash = authUser(rawPassword, salt);
        try{
            final String query = "INSERT INTO User (FName, LName, user_id, PassHash, Salt) " +
                    "VALUES (?, ?, ?, ?, ?)";
            final PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getUserId());
            stmt.setString(4, passwordHash);
            stmt.setInt(5, salt);

            final int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0){
                System.out.println("A new User was created");
                user.setPwd(passwordHash);
                return user;
            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * Authenticates the user with the database.
     *
     * @param userId
     *      the userId
     * @param password
     *      the user's password entered into the client
     * @return true if the provided password matches the password in the database. False otherwise.
     */
    public User getLoginInfo(String userId, String password){
        try{
            final String query = "SELECT * FROM User WHERE user_id = '"+ userId + "'";
            final Statement stmt = connection.createStatement();
            final ResultSet rset = stmt.executeQuery(query);
            User user = new User();
            
            int rowsReturned = 0;
            
            while(rset.next())
            {
                user.setFirstName(rset.getString(1));
                user.setLastName(rset.getString(2));
                user.setUserId(userId);
                user.setPwd(rset.getString(5));
                user.setSalt(rset.getInt(4));
                rowsReturned++;
            }
            
            if(rowsReturned != 1)
            {
                
                return null;
            }
            
            if(user.getPwd().equals(authUser(password, user.getSalt()))){
                return user;
            }
            else{
                return null;
            }
        }catch(SQLException e){
            
            e.printStackTrace();
            return null;
        }
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
    public boolean updateUserInfo(String userId, User updatedUser){
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
    public boolean updatePassword(String oldPasswordHash, String newPasswordHash){
        try{
            final String query = "UPDATE User " +
                                "SET PassHash=? " +
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
    public boolean deleteUser(User user){
        try{
            final String query = "DELETE FROM User " +
                                "WHERE FName=? " +
                                "AND LName=? " +
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

    /**
     *
     * @param pwd
     *      The user password entered into the client.
     * @param salt
     *      The user salt value.
     * @return the hashed password and salt.
     */
    public String authUser(String pwd, int salt){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String newString = pwd + Integer.toString(salt);
            md.update(newString.getBytes());

            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++){
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return salt value as an int.
     */
    public int generateSalt(){
        Random rand = new Random();
        return rand.nextInt(9999998) + 1;
    }
}
