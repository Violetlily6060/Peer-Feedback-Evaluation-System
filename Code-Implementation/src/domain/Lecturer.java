package domain;

public class Lecturer extends User {
    public Lecturer(String userID, String password, String name) {
        super(userID, password, name);
    }

    @Override
    public String getRole() {
        return "lecturer";
    }
}