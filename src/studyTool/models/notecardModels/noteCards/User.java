package studyTool.models.notecardModels.noteCards;

/**
 * @author
 *
 */
public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private int salt;
    private String pwd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public int getSalt() {return salt;}

    public void setSalt(final int salt) {this.salt = salt;}

    public String getPwd() {return pwd;}

    public void setPwd(final String pass) {this.pwd = pass;}

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        final User user = (User) o;

        if (!getUserId().equals(user.getUserId())) return false;
        if (!getFirstName().equals(user.getFirstName())) return false;
        return getLastName() != null ? getLastName().equals(user.getLastName()) : user.getLastName() == null;

    }

    @Override
    public int hashCode() {
        int result = getUserId().hashCode();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}

