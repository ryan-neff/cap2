package studyTool.models.notecardModels.utils;

import studyTool.models.notecardModels.noteCards.StackModel;
import studyTool.models.notecardModels.noteCards.User;

/**
 * @author rn046359
 */
public class UserSingleton {
    private static UserSingleton ourInstance = new UserSingleton();
    private User user;
    private StackModel stack;

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

    public StackModel getStack() {
        return stack;
    }

    public void setStack(final StackModel stack) {
        this.stack = stack;
    }

    @Override
    public String toString() {
        return "UserSingleton{" +
                "user=" + user +
                ", stack=" + stack +
                '}';
    }
}
