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

public final class EvaluationActivity {
    private final String activityID;
    private final String name;
    private final IUser creator;
    private final List<IUser> participantList;
    private final List<Feedback> feedbackList;

    public EvaluationActivity(String activityID, String name, IUser creator, List<IUser> studentList) {
        this.activityID = activityID;
        this.name = name;
        this.creator = creator;
        this.participantList = participantRead(studentList);
        this.feedbackList = feedbackRead();
    }

    // Get Functions
    public String getActivityID() {
        return activityID;
    }

    public String getName() {
        return name;
    }

    public IUser getCreator() {
        return creator;
    }

    public List<IUser> getParticipantList() {
        return participantList;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    // Other Functions
    public void addParticipant(IUser newParticipant) {
        participantList.add(newParticipant);
        participantWrite();
    }

    public void addFeedback(Feedback newFeedback) {
        feedbackList.add(newFeedback);
        feedbackWrite();
    }

    public void updateFeedback(Feedback newFeedback) {
        for (int i = 0; i < feedbackList.size(); i++) {
            if (feedbackList.get(i).getFeedbackID().equals(newFeedback.getFeedbackID())) {
                feedbackList.set(i, newFeedback);
                break;
            }
        }
    }

    // Read Participant From File
    public List<IUser> participantRead(List<IUser> studentList) {
        List<IUser> participants = new ArrayList<>();
        // File Path
        Path absPath = Paths.get("").toAbsolutePath();
        Path directory = absPath.resolve(Paths.get("Database", "Evaluation Activity", activityID));
        Path participantPath = directory.resolve(Paths.get("participant.txt"));

        // Read if File Exist
        if (Files.exists(participantPath)) {
            try (BufferedReader participantReader = new BufferedReader(new FileReader(participantPath.normalize().toString()))) {

                // Get Full Participant Details
                String participantID;
                while((participantID = participantReader.readLine()) != null) {
                    for (IUser student : studentList) {
                        if (student.getUserID().equals(participantID)) {
                            participants.add(student);
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
                new File(participantPath.normalize().toString()).createNewFile();
            }
            catch (IOException e) {
                System.out.println("Error Creating New File: " + e.getMessage());
            }
        }
        return participants;
    }

    // Write Participant to File
    private void participantWrite() {
        // File Path
        Path absPath = Paths.get("").toAbsolutePath();
        Path directory = absPath.resolve(Paths.get("Database", "Evaluation Activity", activityID));
        Path participantPath = directory.resolve(Paths.get("participant.txt"));

        // Write if File Exists
        if (Files.exists(participantPath)) {
            try (BufferedWriter participantWriter = new BufferedWriter(new FileWriter(participantPath.normalize().toString()))) {

                // Write User into File
                for (IUser participant : participantList) {
                    participantWriter.write(participant.getUserID());
                    participantWriter.newLine();
                }
            }
            catch (IOException e) {
                System.out.println("Error Reading from File: " + e.getMessage());
            }
        }
            
        // Create New File if File Does Not Exist
        else {
            try {
                new File(directory.normalize().toString()).mkdirs();
                new File(participantPath.normalize().toString()).createNewFile();
            }
            catch (IOException e) {
                System.out.println("Error Creating New File: " + e.getMessage());
            }
        }
    }

    // Read Feedback From File
    public List<Feedback> feedbackRead() {
        List<Feedback> feedbacks = new ArrayList<>();
        // File Path
        Path absPath = Paths.get("").toAbsolutePath();
        Path directory = absPath.resolve(Paths.get("Database", "Evaluation Activity", activityID));
        Path feedbackPath = directory.resolve(Paths.get("feedback.txt"));

        // Read if File Exist
        if (Files.exists(feedbackPath)) {
            try (BufferedReader feedbackReader = new BufferedReader(new FileReader(feedbackPath.normalize().toString()))) {

                String[] feedbackDetails;
                while((feedbackDetails = feedbackReader.readLine().split(";")) != null) {

                    // Get Full Creator and Receiver Details
                    IUser fCreator = null;
                    IUser fReceiver = null;
                    for (IUser participant : participantList) {
                        if (participant.getUserID().equals(feedbackDetails[1])) {
                            fCreator = participant;
                        }
                        else if (participant.getUserID().equals(feedbackDetails[2])) {
                            fReceiver = participant;
                        }
                        if (fCreator != null && fReceiver != null) {
                            feedbacks.add(new Feedback(feedbackDetails[0], fCreator, fReceiver, feedbackDetails[3], feedbackDetails[4], feedbackDetails[5]));
                            break;
                        }
                    }  
                }
            }
            catch (IOException e) {
                System.out.println("Error Reading from File: " + e.getMessage());
            }
        }

        // Create New File if File Does Not Exist
        else {
            try {
                new File(directory.normalize().toString()).mkdirs();
                new File(feedbackPath.normalize().toString()).createNewFile();
            }
            catch (IOException e) {
                System.out.println("Error Creating New File: " + e.getMessage());
            }
        }
        return feedbacks;
    }

    // Write Feedback to File
    private void feedbackWrite() {
        // File Path
        Path absPath = Paths.get("").toAbsolutePath();
        Path directory = absPath.resolve(Paths.get("Database", "Evaluation Activity", activityID));
        Path feedbackPath = directory.resolve(Paths.get("feedback.txt"));

        // Read if File Exists
        if (Files.exists(feedbackPath)) {
            try (BufferedWriter feedbackWriter = new BufferedWriter(new FileWriter(feedbackPath.normalize().toString()))) {

                // Write User into File
                for (Feedback feedback : feedbackList) {
                    feedbackWriter.write(feedback.toString());
                    feedbackWriter.newLine();
                }
            }
            catch (IOException e) {
                System.out.println("Error Writing to File: " + e.getMessage());
            }
        }
            
        // Create New File if File Does Not Exist
        else {
            try {
                new File(directory.normalize().toString()).mkdirs();
                new File(feedbackPath.normalize().toString()).createNewFile();
            }
            catch (IOException e) {
                System.out.println("Error Creating New File: " + e.getMessage());
            }
        }
    }

    // To String Methods
    @Override
    public String toString() {
        return activityID + ";" + name +  ";" + creator.getUserID();
    }
}
