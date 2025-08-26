package domain;

import java.util.List;

public class Controller {
    private final IDataStore dataLists = new DataLists();

    // Get Functions
    // Get List of Users by Role
    public List<IUser> getUserList(String userRole) {
        return dataLists.getUserList(userRole);
    }

    // Get Full List of Evaluation Activity
    public List<EvaluationActivity> getActivityList() {
        return dataLists.getActivityList();
    }

    // Get List of Evaluation Activity Filtered by Creator ID
    public List<EvaluationActivity> getActivityFilterByCreator(String creatorID) {
        return dataLists.getActivityFilterByCreator(creatorID);
    }

    // Get List of Evaluation Activity Filtered by Participant ID
    public List<EvaluationActivity> getActivityFilterByParticipant(String participantID) {
        return dataLists.getActivityFilterByParticipant(participantID);
    }

    // Get List of Participant Exclude Participant ID
    public List<IUser> getParticipantExlude(String activityID, String participantID) {
        return dataLists.getParticipantExclude(activityID, participantID);
    }

    // Get List of Feedback Filtered by Creator ID
    public List<Feedback> getFeedbackFilterByCreator(String activityID, String creatorID) {
        return dataLists.getFeedbackFilterByCreator(activityID, creatorID);
    }

    // Get List of Feedback Filtered by Receiver ID
    public List<Feedback> getFeedbackFilterByReceiver(String activityID, String receiverID) {
        return dataLists.getFeedbackFilterByReceiver(activityID, receiverID);
    }

    // User Functions
    // Add New User to List
    public void addUser(String userRole, String userID, String newPassword, String newName) {
        dataLists.addUser(userRole, userID, newPassword, newName);
    }

    // Update Existing User Details in List
    public void updateUser(String userRole, String userID, String newPassword, String newName) {
        dataLists.updateUser(userRole, userID, newPassword, newName);
    }

    // Delete User From List
    public void deleteUser(String userRole, String userID) {
        dataLists.deleteUser(userRole, userID);
    }

    // Validate User Login
    public IUser validateLogin(String userID, String password) {
        return dataLists.validateLogin(userID, password);
    }

    // Evaluation Activity Functions
    // Add New Evaluation Activity to List
    public void addActivity(String activityID, String name, String creatorID) {
        dataLists.addActivity(activityID, name, creatorID);
    }

    // Feedback Functions
    // Add New Feedback to List
    public void addFeedback(String activityID, String feedbackID, String creatorID, String receiverID,
            String dateCreated, String dateUpdated, String content) {
        dataLists.addFeedback(activityID, feedbackID, creatorID, receiverID, dateCreated, dateUpdated, content);
    }

    // Update Existing Feedback Details in List
    public void updateFeedback(String activityID, String feedbackID, String creatorID, String receiverID,
            String dateCreated, String dateUpdated, String content) {
        dataLists.updateFeedback(activityID, feedbackID, creatorID, receiverID, dateCreated, dateUpdated, content);
    }
}
