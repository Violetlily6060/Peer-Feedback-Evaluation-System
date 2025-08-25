package domain;

import java.util.List;

public class Controller {
    private final IDataStore dataLists = new DataLists();

    // Get Functions
    public List<IUser> getUserList(String userRole) {
        return dataLists.getUserList(userRole);
    }

    public List<EvaluationActivity> getActivityList() {
        return dataLists.getActivityList();
    }

    public List<Feedback> getFeedbackList(String activityID) {
        return dataLists.getFeedbackList(activityID);
    }

    // User Functions
    public void addUser(String userRole, String userID, String newPassword, String newName) {
        dataLists.addUser(userRole, userID, newPassword, newName);
    }

    public void updateUser(String userRole, String userID, String newPassword, String newName) {
        dataLists.updateUser(userRole, userID, newPassword, newName);
    }

    public void deleteUser(String userRole, String userID) {
        dataLists.deleteUser(userRole, userID);
    }
    
    public IUser validateLogin(String userID, String password) {
        return dataLists.validateLogin(userID, password);
    }

    // Evaluation Activity Functions
    public void addActivity(String activityID, String name, String creatorID) {
        dataLists.addActivity(activityID, name, creatorID);
    }

    // Feedback Functions
    public void addFeedback(String activityID, String feedbackID, String creatorID, String receiverID, String dateCreated, String dateUpdated, String content) {
        dataLists.addFeedback(activityID, feedbackID, creatorID, receiverID, dateCreated, dateUpdated, content);
    }

    public void updateFeedback(String activityID, String feedbackID, String creatorID, String receiverID, String dateCreated, String dateUpdated, String content) {
        dataLists.updateFeedback(activityID, feedbackID, creatorID, receiverID, dateCreated, dateUpdated, content);
    }
}
