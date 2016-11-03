package sample.models.notecardModels.utils;

import sample.models.notecardModels.noteCards.User;

/**
 * @author rn046359
 */
public class UserSingleton {
    private static UserSingleton ourInstance = new UserSingleton();
    private User user;

    public static UserSingleton getInstance() {
        return ourInstance;
    }

    public UserSingleton() {
        
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }
}
