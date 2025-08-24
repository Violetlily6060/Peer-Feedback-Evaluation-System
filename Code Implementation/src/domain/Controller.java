package domain;

import java.util.List;

public class Controller {
    IDataStore dataLists = new DataLists();

    public boolean validateLogin(String userID, String password) {
        return dataLists.validateLogin(userID, password) != null;
    }

    public List<IUser> getUserList(String userRole) {
        return dataLists.getUserList(userRole);
    }

    public void addUser(User newUser) {
        dataLists.addUser(newUser);
    }

    public void updateUser(String userID, User newUser) {
        dataLists.updateUser(userID, newUser);
    }

    public void deleteUser(User user) {
        dataLists.deleteUser(user);
    }

    public List<EvaluationActivity> getActivityList() {
        return dataLists.getActivityList();
    }

    public void addActivity(EvaluationActivity newActivity) {
        dataLists.addActivity(newActivity);
    }

    public List<Feedback> getFeedbackList(EvaluationActivity activity) {
        return dataLists.getFeedbackList(activity);
    }

    public void addFeedback(EvaluationActivity activity, Feedback feedback) {
        dataLists.addFeedback(activity, feedback);
    }

    public void updateFeedback(Feedback oldFeedback, Feedback newFeedback) {
        dataLists.updateFeedback(oldFeedback, newFeedback);
    }
}
