package domain;

import java.util.List;

public class Student extends User {
    private List<Course> enrolledCourses;

    public Student(String userID, String password, String name, String email, String phoneNo) {
        super(userID, password, name, email, phoneNo);
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }
}
