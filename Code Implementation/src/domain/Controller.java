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

    public List<Feedback> getFeedbackList(EvaluationActivity activity) {
        return dataLists.getFeedbackList(activity);
    }

    // User Functions
    public void addUser(IUser newUser) {
        dataLists.addUser(newUser);
    }

    public void updateUser(IUser newUser) {
        dataLists.updateUser(newUser);
    }

    public void deleteUser(IUser user) {
        dataLists.deleteUser(user);
    }
    
    public IUser validateLogin(String userID, String password) {
        return dataLists.validateLogin(userID, password);
    }

    // Evaluation Activity Functions
    public void addActivity(EvaluationActivity newActivity) {
        dataLists.addActivity(newActivity);
    }

    // Feedback Functions
    public void addFeedback(EvaluationActivity activity, Feedback newFeedback) {
        dataLists.addFeedback(activity, newFeedback);
    }

    public void updateFeedback(EvaluationActivity activity, Feedback newFeedback) {
        dataLists.updateFeedback(activity, newFeedback);
    }
}
