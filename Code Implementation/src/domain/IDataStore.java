package domain;

import java.util.List;

public interface IDataStore {
    // Get Functions
    public List<IUser> getUserList(String userRole);
    public List<EvaluationActivity> getActivityList();
    public List<Feedback> getFeedbackList(EvaluationActivity activity);

    // User Functions
    public void addUser(IUser newUser);
    public void updateUser(IUser newUser);
    public void deleteUser(IUser user);
    public IUser validateLogin(String userID, String password);

    // Evaluation Activity Functions
    public void addActivity(EvaluationActivity newActivity);

    // Feedback Functions
    public void addFeedback(EvaluationActivity activity, Feedback newFeedback);
    public void updateFeedback(EvaluationActivity activity, Feedback newFeedback);
}
