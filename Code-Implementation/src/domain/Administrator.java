package domain;

public class Administrator extends User {
    public Administrator(String userID, String password, String name) {
        super(userID, password, name);
    }

    @Override
    public String getRole() {
        return "administrator";
    }
}