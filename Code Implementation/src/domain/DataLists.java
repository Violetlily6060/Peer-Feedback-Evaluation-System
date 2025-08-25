package domain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataLists implements IDataStore {
    public List<IUser> studentList = userRead("student");
    public List<IUser> lecturerList = userRead("lecturer");
    public List<IUser> administratorList = userRead("administrator");
    public List<EvaluationActivity> activityList = activityRead();

    // Get Functions
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
        }
        return userList;
    }

    // Get Evaluation Activity List
    @Override
    public List<EvaluationActivity> getActivityList() {
        return activityList;
    }
    
    // Get Feedback List
    @Override
    public List<Feedback> getFeedbackList(EvaluationActivity activity) {
        return activity.getFeedbackList();
    }

    // User Functions
    // Add new User to List
    @Override
    public void addUser(IUser newUser) {
        if (newUser instanceof Student) {
            studentList.add(newUser);
            userWrite("student");
        }
        else if (newUser instanceof Lecturer) {
            lecturerList.add(newUser);
            userWrite("lecturer");
        }
        else if (newUser instanceof Administrator) {
            administratorList.add(newUser);
            userWrite("administrator");
        }
    }

    // Update User Details in List
    @Override
    public void updateUser(IUser newUser) {
        if (newUser instanceof Student) {
            for (int i = 0; i < studentList.size(); i++) {
                if (studentList.get(i).getUserID().equals(newUser.getUserID())) {
                    studentList.set(i, newUser);
                    userWrite("student");
                    break;
                }
            }
        }
        else if (newUser instanceof Lecturer) {
            for (int i = 0; i < lecturerList.size(); i++) {
                if (lecturerList.get(i).getUserID().equals(newUser.getUserID())) {
                    lecturerList.set(i, newUser);
                    userWrite("lecturer");
                    break;
                }
            }
        }
        else if (newUser instanceof Administrator) {
            for (int i = 0; i < administratorList.size(); i++) {
                if (administratorList.get(i).getUserID().equals(newUser.getUserID())) {
                    administratorList.set(i, newUser);
                    userWrite("administrator");
                    break;
                }
            }
        }
    }

    // Delete User from List
    @Override
    public void deleteUser(IUser user) {
        if (user instanceof Student) {
            studentList.remove(user);
            userWrite("student");
        }
        else if (user instanceof Lecturer) {
            lecturerList.remove(user);
            userWrite("lecturer");
        }
        else if (user instanceof Administrator) {
            administratorList.remove(user);
            userWrite("administrator");
        }
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

    // Evaluation Activity Functions
    // Add new Evaluation Activity to List
    @Override
    public void addActivity(EvaluationActivity newActivity) {
        activityList.add(newActivity);
        activityWrite();
    }

    // Feedback Functions
    // Add new Feedback to List
    @Override
    public void addFeedback(EvaluationActivity activity, Feedback newFeedback) {
        activity.addFeedback(newFeedback);
    }

    // Update Feedback in List
    @Override
    public void updateFeedback(EvaluationActivity activity, Feedback newFeedback) {
        activity.updateFeedback(newFeedback);
    }

    // Read Functions
    // Read Users from File
    private List<IUser> userRead(String userRole) {
        List<IUser> userList = new ArrayList<>();
        // File Path
        Path absPath = Paths.get("").toAbsolutePath();
        Path directory = absPath.resolve(Paths.get("Database"));
        Path userPath = directory.resolve(Paths.get(userRole + ".txt"));

        // Read if File Exist
        if (Files.exists(userPath)) {
            try (BufferedReader userReader = new BufferedReader(new FileReader(userPath.normalize().toString()))) {

                
                // Insert Details into List
                String[] userDetails;
                while((userDetails = userReader.readLine().split(";")) != null) {
                    switch (userRole) {
                        case "student" -> {
                            userList.add(new Student(userDetails[0], userDetails[1], userDetails[2]));
                        }
                        case "lecturer" -> {
                            userList.add(new Lecturer(userDetails[0], userDetails[1], userDetails[2]));
                        }
                        case "administrator" -> {
                            userList.add(new Administrator(userDetails[0], userDetails[1], userDetails[2]));
                        }
                    }
                }
            }
            catch (IOException e) {
                System.out.println("Error Reading from File: " + e.getMessage());
            }
        }

        // Create Directory & File if File Does Not Exist
        else {
            try {
                new File(directory.normalize().toString()).mkdirs();
                new File(userPath.normalize().toString()).createNewFile();
            }
            catch (IOException e) {
                System.out.println("Error Creating New File: " + e.getMessage());
            }    
            
        }
        return userList;
    }

    // Write User to File
    private void userWrite(String userRole) {
        List<IUser> userList = new ArrayList<>();
        switch(userRole) {
            case "student" -> userList = studentList;
            case "lecturer" -> userList = lecturerList;
            case "administrator" -> userList = administratorList;
        }

        // File Path
        Path absPath = Paths.get("").toAbsolutePath();
        Path directory = absPath.resolve(Paths.get("Database"));
        Path userPath = directory.resolve(Paths.get(userRole + ".txt"));

        // Write if File Exists
        if (Files.exists(userPath)) {
            try (BufferedWriter userWriter = new BufferedWriter(new FileWriter(userPath.normalize().toString()))) {

                // Write User into File
                for (IUser user : userList) {
                    userWriter.write(user.toString());
                    userWriter.newLine();
                }
                userWriter.close();
            }
            catch (IOException e) {
                System.out.println("Error Writing to File: " + e.getMessage());
            }
        }
            
        // Create Directory & File if File Does Not Exist
        else {
            try {
                new File(directory.normalize().toString()).mkdirs();
                new File(userPath.normalize().toString()).createNewFile();
            }
            catch (IOException e) {
                System.out.println("Error Creating New File: " + e.getMessage());
            }
        }
    }

    // Read Evaluation Activity from File
    private List<EvaluationActivity> activityRead() {
        List<EvaluationActivity> activities = new ArrayList<>();
        // File Path
        Path absPath = Paths.get("").toAbsolutePath();
        Path directory = absPath.resolve(Paths.get("Database"));
        Path activityPath = directory.resolve(Paths.get("activity.txt"));

        // Read if File Exist
        if (Files.exists(activityPath)) {
            try (BufferedReader activityReader = new BufferedReader(new FileReader(activityPath.normalize().toString()))) {

                // Insert Details into List
                String[] activityDetails;
                while((activityDetails = activityReader.readLine().split(";")) != null) {

                    // Get Full Lecturer Details
                    for (IUser lecturer : lecturerList) {
                        if (lecturer.getUserID().equals(activityDetails[2])) {
                            activities.add(new EvaluationActivity(activityDetails[0], activityDetails[1], lecturer, studentList));
                            break;
                        }
                    }
                }
            }
            catch (IOException e) {
                System.out.println("Error Reading from File: " + e.getMessage());
            }
        }
        // Create Directory & File if File Does Not Exist
        else {
            try {
                new File(directory.normalize().toString()).mkdirs();
                new File(activityPath.normalize().toString()).createNewFile();
            }
            catch (IOException e) {
                System.out.println("Error Creating New File: " + e.getMessage());
            }
        }
        return activities;
    }

    // Write Evaluation Activity to File
    private void activityWrite() {
        // File Path
        Path absPath = Paths.get("").toAbsolutePath();
        Path directory = absPath.resolve(Paths.get("Database"));
        Path activityPath = directory.resolve(Paths.get("activity.txt"));

        // Write if File Exists
        if (Files.exists(activityPath)) {
            try (BufferedWriter activityWriter = new BufferedWriter(new FileWriter(activityPath.normalize().toString()))) {

                // Write User into File
                for (EvaluationActivity activity : activityList) {
                    activityWriter.write(activity.toString());
                    activityWriter.newLine();
                }
            }
            catch (IOException e) {
                System.out.println("Error Writing to File: " + e.getMessage());
            }
        }
            
        // Create Directory & File if File Does Not Exist
        else {
            try {
                new File(directory.normalize().toString()).mkdirs();
                new File(activityPath.normalize().toString()).createNewFile();
            }
            catch (IOException e) {
                System.out.println("Error Creating New File: " + e.getMessage());
            }
        }
    }
}