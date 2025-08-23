package domain;

public interface IUser {
    // Get Functions
    public String getUserID();
    public String getPassword();
    public String getName();
    public String getEmail();
    public String getPhoneNo();

    // Set Functions
    public void setPassword(String newPassword);
    public void setName(String newName);
    public void setEmail(String newEmail);
    public void setPhoneNo(String newPhoneNo);
}
