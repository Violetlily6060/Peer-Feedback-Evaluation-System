package domain;

public class User implements IUser {
    private final String userID;
    private String password;
    private String name;

    // Constructor
    public User(String userID, String password, String name) {
        this.userID = userID;
        this.password = password;
        this.name = name;
    }

    // Get Functions
    @Override
    public String getUserID() {
        return userID;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getRole() {
        return null;
    }

    // Set Functions
    @Override
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    @Override
    public void setName(String newName) {
        this.name = newName;
    }

    // Base Functions
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        User user = (User) obj;
        return userID.equals(user.userID);
    }

    @Override
    public int hashCode() {
        return userID != null ? userID.hashCode() : 0;
    }

    @Override
    public String toString() {
        return userID + ";" + password + ";" + name;
    }
}
