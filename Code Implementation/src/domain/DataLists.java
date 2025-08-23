package domain;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataLists implements IDataStore {
    public List<Course> courseList;
    public List<IUser> administratorList = userRead("administrator");
    public List<IUser> studentList = userRead("student");
    public List<IUser> lecturerList = userRead("lecturer");

    // Read students from file
    public List<IUser> userRead(String userRole) {
        List<IUser> userList = new ArrayList<>();
        Path absPath = Paths.get("").toAbsolutePath();

        switch(userRole) {
            case "student" -> {
                Path studentPath = Paths.get(absPath.normalize().toString(), "\\Database", "\\student.txt");
                try (Scanner studentScanner = new Scanner(studentPath)) {
                    while(studentScanner.hasNext()) {
                        String[] studentDetails = studentScanner.nextLine().split(";");
                        userList.add(new Student(studentDetails[0], studentDetails[1], studentDetails[2], studentDetails[3], studentDetails[4]));
                    }
                }
                catch (IOException e) {

                }
            }
            case "lecturer" -> {
                Path lecturerPath = Paths.get(absPath.normalize().toString(), "\\Database", "\\lecturer.txt");
                try (Scanner lecturerScanner = new Scanner(lecturerPath)) {
                    while(lecturerScanner.hasNext()) {
                        String[] lecturerDetails = lecturerScanner.nextLine().split(";");
                        userList.add(new Lecturer(lecturerDetails[0], lecturerDetails[1], lecturerDetails[2], lecturerDetails[3], lecturerDetails[4]));
                    }
                }
                catch (IOException e) {

                }
            }
            case "administrator" -> {
                Path administratorPath = Paths.get(absPath.normalize().toString(), "\\Database", "\\administrator.txt");
                try (Scanner administratorScanner = new Scanner(administratorPath)) {
                    while(administratorScanner.hasNext()) {
                        String[] administratorDetails = administratorScanner.nextLine().split(";");
                        userList.add(new Administrator(administratorDetails[0], administratorDetails[1], administratorDetails[2], administratorDetails[3], administratorDetails[4]));
                    }
                } catch (IOException e) {

                }
            }
        }
        return userList;
    }

    // Validate User Login
    @Override
    public IUser validateLogin(String userID, String password) {
        List<IUser> userList = getUserList("all");
        for (IUser user : userList) {
            if (user.getUserID().equals(userID) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    // Get List of Users by Role
    @Override
    public List<IUser> getUserList(String userRole) {
        List<IUser> userList = new ArrayList<>();

        switch (userRole) {
            case "student" -> userList.addAll(studentList);
            case "lecturer" -> userList.addAll(lecturerList);
            case "administrator" -> userList.addAll(administratorList);
            case "all" -> {
                userList.addAll(studentList);
                userList.addAll(lecturerList);
                userList.addAll(administratorList);
            }
            default -> {}
        }
        return userList;
    }

    // Add new User
    @Override
    public void addUser(User newUser) {
        if (newUser instanceof Student) {
            studentList.add(newUser);
        }
        else if (newUser instanceof Lecturer) {
            lecturerList.add(newUser);
        }
        else if (newUser instanceof Administrator) {
            administratorList.add(newUser);
        }
    }

    @Override
    public void updateUser(String userID, User newUser) {
        if (newUser instanceof Student) {
            for (int i = 0; i < studentList.size(); i++) {
                if (studentList.get(i).getUserID().equals(userID)) {
                    studentList.set(i, newUser);
                    break;
                }
            }
        }
        else if (newUser instanceof Lecturer) {
            for (int i = 0; i < lecturerList.size(); i++) {
                if (lecturerList.get(i).getUserID().equals(userID)) {
                    lecturerList.set(i, newUser);
                    break;
                }
            }
        }
        else if (newUser instanceof Administrator) {
            for (int i = 0; i < administratorList.size(); i++) {
                if (administratorList.get(i).getUserID().equals(userID)) {
                    administratorList.set(i, newUser);
                    break;
                }
            }
        }
    }

    @Override
    public void deleteUser(User user) {
        if (user instanceof Student) {
            studentList.remove(user);
        }
        else if (user instanceof Lecturer) {
            lecturerList.remove(user);
        }
        else if (user instanceof Administrator) {
            administratorList.remove(user);
        }
    }

    @Override
    public List<Course> getCourseList() {
        return courseList;
    }

    @Override
    public List<EvaluationActivity> getActivityList(Course course) {
        return course.getActivityList();
    }

    @Override
    public void addActivity(EvaluationActivity newActivity, Course course) {
        course.addActivity(newActivity);
    }

    @Override
    public List<Feedback> getFeedbackList(EvaluationActivity activity) {
        return activity.getFeedbackList();
    }

    @Override
    public void addFeedback(EvaluationActivity activity, Feedback feedback) {
        activity.addFeedback(feedback);
    }

    @Override
    public void updateFeedback(Feedback oldFeedback, Feedback newFeedback) {
        oldFeedback = newFeedback;
    }
}
