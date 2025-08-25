package domain;

public class Student extends User {
    public Student(String userID, String password, String name) {
        super(userID, password, name);
    }

    @Override
    public String getRole() {
        return "student";
    }
}