package domain;

import java.util.List;
import java.util.ArrayList;

public class DataLists {
    public List<Course> courseList;
    public List<Feedback> feedbackList;
    public List<Administrator> adminList;
    public List<Student> studentList;
    public List<Lecturer> lecturerList;

    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        userList.addAll(studentList);
        userList.addAll(lecturerList);
        userList.addAll(adminList);
        return userList;
    }

    public boolean validateUser(User account) {
        List<User> userList = getUserList();
        for (User user : userList) {
            if (user.equals(account)) {
                return true;
            }
        }
        return false;
    }

    public List<Administrator> getAdminList() {
        return adminList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public List<Lecturer> getLecturerList() {
        return lecturerList;
    }
}
