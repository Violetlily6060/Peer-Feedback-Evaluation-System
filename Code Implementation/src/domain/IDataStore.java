package domain;

import java.util.List;

public interface IDataStore {
    // Login
    public IUser validateLogin(String userID, String password);

    // Manage User
    public List<IUser> getUserList(String userRole);
    public void addUser(User newUser);
    public void updateUser(String userID, User newUser);
    public void deleteUser(User user);

    // Add Activity
    public List<EvaluationActivity> getActivityList();
    public void addActivity(EvaluationActivity newActivity);

    // Add Feedback
    public List<Feedback> getFeedbackList(EvaluationActivity activity);
    public void addFeedback(EvaluationActivity activity, Feedback feedback);
    public void updateFeedback(Feedback oldFeedback, Feedback newFeedback);
}
