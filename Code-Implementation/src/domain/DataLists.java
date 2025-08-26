package domain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class DataLists implements IDataStore {
    public List<IUser> studentList = userRead("student");
    public List<IUser> lecturerList = userRead("lecturer");
    public List<IUser> administratorList = userRead("administrator");
    public List<EvaluationActivity> activityList = activityRead();

    // Get Functions
    // Get Working Database Directory
    private Path getDirectory() {
        // Initialize File Paths
        Path absPath = Paths.get("").toAbsolutePath();
        Path directory;
        if (absPath.getFileName().toString().equals("Code-Implementation")) {
            directory = absPath.resolve(Paths.get("Database"));
        }
        else {
            directory = absPath.resolve(Paths.get("Code-Implementation", "Database"));
        }
        return directory;
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
        }
        return userList;
    }

    // Get Evaluation Activity List
    @Override
    public List<EvaluationActivity> getActivityList() {
        return activityList;
    }
    
    // Filter Evaluation Activity List by Creator ID
    @Override
    public List<EvaluationActivity> activityFilterByCreator(String creatorID) {
        List<EvaluationActivity> filteredList = new ArrayList<>();
        for (EvaluationActivity activity : activityList) {
            if (activity.getCreator().getUserID().equals(creatorID)) {
                filteredList.add(activity);
            }
        }
        return filteredList;
    }

    // Exclude Participants By User ID in Evaluation Activity
    @Override
    public List<IUser> participantExclude(String activityID, String participantID) {

         // Search Evaluation Activity
        for (EvaluationActivity activity : activityList) {
            if (activity.getActivityID().equals(activityID)) {
                return activity.participantExlude(participantID);
            }
        }
        return null;
    }

    // Filter Feedback By Creator ID in Evaluation Activity
    @Override
    public List<Feedback> feedbackFilterByCreator(String activityID, String creatorID) {

        // Search for Evaluation Activity
        for (EvaluationActivity activity : activityList) {
            if (activity.getActivityID().equals(activityID)) {
                return activity.feedbackFilterByCreator(creatorID);
            }
        }
        return null;
    }

    // Filter Feedback By Receiver ID in Evaluation Activity
    @Override
    public List<Feedback> feedbackFilterByReceiver(String activityID, String receiverID) {

        // Seach for Evaluation Activity
        for (EvaluationActivity activity : activityList) {
            if (activity.getActivityID().equals(activityID)) {
                return activity.feedbackFilterByReceiver(receiverID);
            }
        }
        return null;
    }

    // User Functions
    // Add new User to List
    @Override
    public void addUser(String userRole, String userID, String newPassword, String newName) {
        switch(userRole) {
            case "student" -> {
                studentList.add(new Student(userID, newPassword, newName));
            }
            case "lecturer" -> {
                lecturerList.add(new Lecturer(userID, newPassword, newName));
            }
            case "administrator" -> {
                administratorList.add(new Administrator(userID, newPassword, newName));
            }
        }
        userWrite(userRole);
    }

    // Update User Details in List
    @Override
    public void updateUser(String userRole, String userID, String newPassword, String newName) {
        switch(userRole) {
            case "student" -> {
                for (IUser student : studentList) {
                    if (student.getUserID().equals(userID)) {
                        student.setPassword(newPassword);
                        student.setName(newName);
                        break;
                    }
                }
            }
            case "lecturer" -> {
                for (IUser lecturer : lecturerList) {
                    if (lecturer.getUserID().equals(userID)) {
                        lecturer.setPassword(newPassword);
                        lecturer.setName(newName);
                        break;
                    }
                }
            }
            case "administrator" -> {
                for (IUser administrator : administratorList) {
                    if (administrator.getUserID().equals(userID)) {
                        administrator.setPassword(newPassword);
                        administrator.setName(newName);
                        break;
                    }
                }       
            }
        }
        userWrite(userRole);
    }

    // Delete User from List
    @Override
    public void deleteUser(String userRole, String userID) {
        switch(userRole) {
            case "student" -> {
                for (int i = 0; i < studentList.size(); i++) {
                    if (studentList.get(i).getUserID().equals(userID)) {
                        studentList.remove(i);
                        break;
                    }
                }
            }
            case "lecturer" -> {
                for (int i = 0; i < lecturerList.size(); i++) {
                    if (lecturerList.get(i).getUserID().equals(userID)) {
                        lecturerList.remove(i);
                        break;
                    }
                }
            }
            case "administrator" -> {
                for (int i = 0; i < administratorList.size(); i++) {
                    if (administratorList.get(i).getUserID().equals(userID)) {
                        administratorList.remove(i);
                        break;
                    }
                }
            }
        }
        userWrite(userRole);
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
    public void addActivity(String activityID, String name, String creatorID) {
        for (IUser creator : lecturerList) {
            if (creator.getUserID().equals(creatorID)) {
                activityList.add(new EvaluationActivity(activityID, name, creator, studentList));
                break;
            }
        }
        activityWrite();
    }

    // Participant Functions
    // Add new Participant to List
    @Override public void addParticipant(String activityID, String participantID) {

        // Search Evaluation Activity
        for (EvaluationActivity activity : activityList) {
            if (activity.getActivityID().equals(activityID)) {

                // Search Participant Detail
                for (IUser participant : studentList) {
                    if (participant.getUserID().equals(participantID)) {
                        activity.addParticipant(participant);
                        break;
                    }
                }
                break;
            }
        }
    }

    // Feedback Functions
    // Add new Feedback to List
    @Override
    public void addFeedback(String activityID, String feedbackID, String creatorID, String receiverID, String dateCreated, String dateUpdated, String content) {
        
        // Search Evaluation Activity
        for (EvaluationActivity activity : activityList) {
            if (activity.getActivityID().equals(activityID)) {

                // Search Creator & Receiver
                IUser fCreator = null;
                IUser fReceiver = null;
                for (IUser student : studentList) {
                    if (student.getUserID().equals(creatorID)) {
                        fCreator = student;
                    }
                    else if (student.getUserID().equals(receiverID)) {
                        fReceiver = student;
                    }
                    if (fCreator != null && fReceiver != null) {
                        activity.addFeedback(new Feedback(feedbackID, fCreator, fReceiver, dateCreated, dateUpdated, content));
                        break;
                    }
                }
                break;
            }
        } 
    }

    // Update Feedback in List
    @Override
    public void updateFeedback(String activityID, String feedbackID, String creatorID, String receiverID, String dateCreated, String dateUpdated, String content) {
        // Search Evaluation Activity
        for (EvaluationActivity activity : activityList) {
            if (activity.getActivityID().equals(activityID)) {

                // Search Creator & Receiver
                IUser fCreator = null;
                IUser fReceiver = null;
                for (IUser student : studentList) {
                    if (student.getUserID().equals(creatorID)) {
                        fCreator = student;
                    }
                    else if (student.getUserID().equals(receiverID)) {
                        fReceiver = student;
                    }
                    if (fCreator != null && fReceiver != null) {
                        activity.updateFeedback(feedbackID, dateUpdated, content);
                        break;
                    }
                }
                break;
            }
        }
    }

    // Read Functions
    // Read Users from File
    private List<IUser> userRead(String userRole) {
        List<IUser> userList = new ArrayList<>();
        // File Path
        Path directory = getDirectory();
        Path userPath = directory.resolve(Paths.get(userRole + ".txt"));

        // Read if File Exist
        if (Files.exists(userPath)) {
            try (BufferedReader userReader = Files.newBufferedReader(userPath)) {
                
                // Insert Details into List
                String userLine;
                while((userLine = userReader.readLine()) != null) {
                    String[] userDetails = userLine.split(";");

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
        Path directory = getDirectory();
        Path userPath = directory.resolve(Paths.get(userRole + ".txt"));

        // Create Directory if it does not Exists
        if (!Files.exists(directory)) {
            new File(directory.normalize().toString()).mkdirs();
        }

        // Write Data
        try (BufferedWriter userWriter = Files.newBufferedWriter(userPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
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

    // Read Evaluation Activity from File
    private List<EvaluationActivity> activityRead() {
        List<EvaluationActivity> activities = new ArrayList<>();
        // File Path
        Path directory = getDirectory();
        Path activityPath = directory.resolve(Paths.get("activity.txt"));

        // Read if File Exist
        if (Files.exists(activityPath)) {
            try (BufferedReader activityReader = Files.newBufferedReader(activityPath)) {

                // Insert Details into List
                String activityLine;
                while((activityLine = activityReader.readLine()) != null) {
                    String[] activityDetails = activityLine.split(";");

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
        Path directory = getDirectory();
        Path activityPath = directory.resolve(Paths.get("activity.txt"));

        // Create Directory if it does not Exists
        if(!Files.exists(directory)) {
            new File(directory.normalize().toString()).mkdirs();
        }

        // Write Data
        try (BufferedWriter activityWriter = Files.newBufferedWriter(activityPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
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
}