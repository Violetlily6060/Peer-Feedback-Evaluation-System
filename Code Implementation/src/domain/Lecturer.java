package domain;

import java.util.List;

public class Lecturer extends User {
    private List<Course> currentCourses;

    public Lecturer(String userID, String password, String name, String email, String phoneNo) {
        super(userID, password, name, email, phoneNo);
    }

    public List<Course> getCurrentCourses() {
        return currentCourses;
    }
}
