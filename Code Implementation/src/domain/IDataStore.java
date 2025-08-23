package domain;

import java.util.List;

public interface IDataStore {
    public IUser validateLogin(String userID, String password);
    public List<IUser> getUserList(String userRole);
    public void addUser(User newUser);
    public void updateUser(String userID, User newUser);
    public void deleteUser(User user);
    public List<Course> getCourseList();
    public List<EvaluationActivity> getActivityList(Course course);
    public void addActivity(EvaluationActivity newActivity, Course course);
    public List<Feedback> getFeedbackList(EvaluationActivity activity);
    public void addFeedback(EvaluationActivity activity, Feedback feedback);
    public void updateFeedback(Feedback oldFeedback, Feedback newFeedback);
}
