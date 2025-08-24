package domain;

public interface IUser {
    // Get Functions
    public String getUserID();
    public String getPassword();
    public String getName();

    // Set Functions
    public void setPassword(String newPassword);
    public void setName(String newName);
}
