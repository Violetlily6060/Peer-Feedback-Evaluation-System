package domain;

public class User implements UserInterface {
    private String userID;
    private String password;
    private String name;
    private String email;
    private String phoneNo;

    // Constructor
    public User(String userID, String password, String name, String email, String phoneNo) {
        this.userID = userID;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
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
    public String getEmail() {
        return email;
    }

    @Override
    public String getPhoneNo() {
        return phoneNo;
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

    @Override
    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    @Override
    public void setPhoneNo(String newPhoneNo) {
        this.phoneNo = newPhoneNo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        return (userID.equals(user.getUserID()) && password.equals(user.getPassword()));
    }
}
