package sample.models.notecardModels;

import sample.models.DbConnectionManager;

import java.sql.Connection;

/**
 * @author rn046359
 */
public class UserModel {
    private Connection connection;

    public UserModel() {
    connection = DbConnectionManager.connect();
    }


}
