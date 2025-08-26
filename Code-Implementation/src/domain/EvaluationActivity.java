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

    // Get Working Database Directory
    private Path getDirectory() {
        // Initialize File Paths
        Path absPath = Paths.get("").toAbsolutePath();
        Path directory;
        if (absPath.getFileName().toString().equals("Code-Implementation")) {
            directory = absPath.resolve(Paths.get("Database", "Evaluation Activity", activityID));
        }
        else {
            directory = absPath.resolve(Paths.get("Code-Implementation", "Database", "Evaluation Activity", activityID));
        }
        return directory;
    }

    // Participant Functions
    // Add Participants to List
    public void addParticipant(IUser newParticipant) {
        participantList.add(newParticipant);
        participantWrite();
    }

    // Exclude Participants from List
    public List<IUser> participantExlude(String participantID) {
        List<IUser> excludedList = new ArrayList<>();

        // Search for participant
        for (IUser participant : participantList) {
            if (!participant.getUserID().equals(participantID)) {
                excludedList.add(participant);
            }
        }
        return excludedList;
    }

    // Feedback Functions
    // Add New Feedback to List
    public void addFeedback(Feedback newFeedback) {
        feedbackList.add(newFeedback);
        feedbackWrite();
    }

    // Update Existing Feedback
    public void updateFeedback(String feedbackID, String dateUpdated, String content) {
        for (Feedback feedback : feedbackList) {
            if (feedback.getFeedbackID().equals(feedbackID)) {
                feedback.setDateUpdated(dateUpdated);
                feedback.setContent(content);
                break;
            }
        }
        feedbackWrite();
    }

    // Filter Feedback by Creator ID
    public List<Feedback> feedbackFilterByCreator(String creatorID) {
        List<Feedback> filteredList = new ArrayList<>();

        // Search for Creator
        for (Feedback feedback : feedbackList) {
            if (feedback.getCreator().getUserID().equals(creatorID)) {
                filteredList.add(feedback);
            }
        }
        return filteredList;
    }

    // Filter Feedback by Receiver ID
    public List<Feedback> feedbackFilterByReceiver(String receiverID) {
        List<Feedback> filteredList = new ArrayList<>();

        // Search for Receiver
        for (Feedback feedback : feedbackList) {
            if (feedback.getReceiver().getUserID().equals(receiverID)) {
                filteredList.add(feedback);
            }
        }
        return filteredList;
    }


    // File Functions
    // Read Participant From File
    public List<IUser> participantRead(List<IUser> studentList) {
        List<IUser> participants = new ArrayList<>();

        // File Path
        Path directory = getDirectory();
        Path participantPath = directory.resolve(Paths.get("participant.txt"));

        // Read if File Exist
        if (Files.exists(participantPath)) {
            try (BufferedReader participantReader = Files.newBufferedReader(participantPath)) {

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
        Path directory = getDirectory();
        Path participantPath = directory.resolve(Paths.get("participant.txt"));

        // Create Directory if it does not Exists
        if (!Files.exists(directory)) {
            new File(directory.normalize().toString()).mkdirs();
        }

        // Write Data        
        try (BufferedWriter participantWriter = Files.newBufferedWriter(participantPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
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

    // Read Feedback From File
    public List<Feedback> feedbackRead() {
        List<Feedback> feedbacks = new ArrayList<>();
        // File Path
        Path directory = getDirectory();
        Path feedbackPath = directory.resolve(Paths.get("feedback.txt"));

        // Read if File Exist
        if (Files.exists(feedbackPath)) {
            try (BufferedReader feedbackReader = Files.newBufferedReader(feedbackPath)) {

                String feedbackLine;
                while((feedbackLine = feedbackReader.readLine()) != null) {
                    String[] feedbackDetails = feedbackLine.split(";");

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
        Path directory = getDirectory();
        Path feedbackPath = directory.resolve(Paths.get("feedback.txt"));

        // Create Directory if it does not Exists
        if (!Files.exists(directory)) {
            new File(directory.normalize().toString()).mkdirs();
        }

        try (BufferedWriter feedbackWriter = Files.newBufferedWriter(feedbackPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
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

    // To String Methods
    @Override
    public String toString() {
        return activityID + ";" + name +  ";" + creator.getUserID();
    }
}
