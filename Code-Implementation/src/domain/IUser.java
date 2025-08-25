package domain;

public interface IUser {
    // Get Functions
    public String getUserID();
    public String getPassword();
    public String getName();
    public String getRole();

    // Set Functions
    public void setPassword(String newPassword);
    public void setName(String newName);
}
