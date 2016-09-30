package sample.models.notecardModels;

import sample.models.DbConnectionManager;
import sample.models.notecardModels.noteCards.User;

import java.sql.Connection;

/**
 * @author rn046359
 */
public class UserModel {
    private final Connection connection;

    public UserModel() {
    connection = DbConnectionManager.connect();
    }

}
