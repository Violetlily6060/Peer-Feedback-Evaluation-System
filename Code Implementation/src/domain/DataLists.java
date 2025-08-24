package domain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataLists implements IDataStore {
    public List<IUser> administratorList = userRead("administrator");
    public List<IUser> studentList = userRead("student");
    public List<IUser> lecturerList = userRead("lecturer");
    public List<EvaluationActivity> activityList;

    // Read Students from File
    public List<IUser> userRead(String userRole) {
        List<IUser> userList = new ArrayList<>();
        // Database Path
        String absPath = Paths.get("").toAbsolutePath().normalize().toString();
        String directory = Paths.get(absPath, "\\Database").normalize().toString();

        switch(userRole) {
            case "student" -> {
                // Get From Database
                Path studentPath = Paths.get(directory, "\\student.txt");

                try {
                    // Read if File Exist
                    if (Files.exists(studentPath)) {
                        Scanner studentScanner = new Scanner(studentPath);
                        while(studentScanner.hasNext()) {
                            String[] studentDetails = studentScanner.nextLine().split(";");
                            userList.add(new Student(studentDetails[0], studentDetails[1], studentDetails[2]));
                        }
                        studentScanner.close();
                    }

                    // Create New File if File Does Not Exist
                    else {
                        new File(directory).mkdirs();
                        new File(studentPath.normalize().toString()).createNewFile();
                    }
                }
                catch (IOException e) {
                    System.out.println("Error Creating File: " + e.getMessage());
                }
            }
            case "lecturer" -> {
                // Get From Database
                Path lecturerPath = Paths.get(directory, "\\lecturer.txt");

                try {
                    //Read if File Exist
                    if (Files.exists(lecturerPath)) {
                        Scanner lecturerScanner = new Scanner(lecturerPath);
                        while(lecturerScanner.hasNext()) {
                            String[] lecturerDetails = lecturerScanner.nextLine().split(";");
                            userList.add(new Lecturer(lecturerDetails[0], lecturerDetails[1], lecturerDetails[2]));
                        }
                        lecturerScanner.close();
                    }

                    // Create New File if File Does Not Exist
                    else {
                        new File(directory).mkdirs();
                        new File(lecturerPath.normalize().toString()).createNewFile();
                    }
                }
                catch (IOException e) {
                    System.out.println("Error Creating File: " + e.getMessage());
                }
            }
            case "administrator" -> {
                Path administratorPath = Paths.get(directory, "\\administrator.txt");

                try {
                    // Get From Database
                    if (Files.exists(administratorPath)) {
                        Scanner administratorScanner = new Scanner(administratorPath);
                        while(administratorScanner.hasNext()) {
                            String[] adminDetails = administratorScanner.nextLine().split(";");
                            userList.add(new Administrator(adminDetails[0], adminDetails[1], adminDetails[2]));
                        }
                        administratorScanner.close();
                    }

                    // Create New File if File Does Not Exist
                    else {
                        new File(directory).mkdirs();
                        new File(administratorPath.normalize().toString()).createNewFile();
                    }
                } catch (IOException e) {
                    System.out.println("Error Creating File: " + e.getMessage());
                }
            }
        }
        return userList;
    }

    // Write Students to File
    public void userWrite(List<IUser> userList, String userRole) {
        // Database Path
        String absPath = Paths.get("").toAbsolutePath().normalize().toString();
        String directory = Paths.get(absPath, "\\Database").normalize().toString();

        switch (userRole) {
            case "student" -> {
                // Get From Database
                Path studentPath = Paths.get(directory, "\\student.txt");

                try {
                    // Read if File Exists
                    if (Files.exists(studentPath)) {
                        FileWriter studentWriter = new FileWriter(studentPath.normalize().toString());
                        for (IUser user : userList) {
                            studentWriter.write(user.toString());
                        }
                        studentWriter.close();
                    }

                    // Create New File if File Does Not Exist
                    else {
                        new File(directory).mkdirs();
                        new File(studentPath.normalize().toString()).createNewFile();
                    }
                } 
                catch (IOException e) {
                    System.out.println("Error Creating File: " + e.getMessage());
                }
            }
            case "lecturer" -> {
                // Get From Database
                Path lecturerPath = Paths.get(directory, "\\lecturer.txt");

                try {
                    // Read if File Exists
                    if (Files.exists(lecturerPath)) {
                        FileWriter lecturerWriter = new FileWriter(lecturerPath.normalize().toString());
                        for (IUser user : userList) {
                            lecturerWriter.write(user.toString());
                        }
                        lecturerWriter.close();
                    }

                    // Create New File if File Does Not Exist
                    else {
                        new File(directory).mkdirs();
                        new File(lecturerPath.normalize().toString()).createNewFile();
                    }
                } 
                catch (IOException e) {
                    System.out.println("Error Creating File: " + e.getMessage());
                }
            }
            case "administrator" -> {
                // Get From Database
                Path administratorPath = Paths.get(directory, "\\administrator.txt");

                try {
                    // Read if File Exists
                    if (Files.exists(administratorPath)) {
                        FileWriter administratorWriter = new FileWriter(administratorPath.normalize().toString());
                        for (IUser user : userList) {
                            administratorWriter.write(user.toString());
                        }
                        administratorWriter.close();
                    }

                    // Create New File if File Does Not Exist
                    else {
                        new File(directory).mkdirs();
                        new File(administratorPath.normalize().toString()).createNewFile();
                    }
                } 
                catch (IOException e) {
                    System.out.println("Error Creating File: " + e.getMessage());
                }
            }
        }
    }

    public List<EvaluationActivity> activityRead() {
        List<EvaluationActivity> activities = new ArrayList<>();
        

        return activities;
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
    public List<EvaluationActivity> getActivityList() {
        return activityList;
    }

    @Override
    public void addActivity(EvaluationActivity newActivity) {
        activityList.add(newActivity);
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
