package domain;

import java.util.List;

public interface IDataStore {
    // Get Functions
    public List<IUser> getUserList(String userRole);
    public List<EvaluationActivity> getActivityList();
    public List<Feedback> getFeedbackList(String activityID);

    // User Functions
    public void addUser(String userRole, String userID, String newPassword, String newName);
    public void updateUser(String userRole, String userID, String newPassword, String newName);
    public void deleteUser(String userRole, String userID);
    public IUser validateLogin(String userID, String password);

    // Evaluation Activity Functions
    public void addActivity(String activityID, String name, String creatorID);

    // Participant Functions
    public void addParticipant(String activityID, String participantID);

    // Feedback Functions
    public void addFeedback(String activityID, String feedbackID, String creatorID, String receiverID, String dateCreated, String dateUpdated, String content);
    public void updateFeedback(String activityID, String feedbackID, String creatorID, String receiverID, String dateCreated, String dateUpdated, String content);
}
