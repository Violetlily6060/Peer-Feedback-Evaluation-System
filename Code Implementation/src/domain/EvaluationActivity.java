package domain;

import java.util.ArrayList;
import java.util.List;

public final class EvaluationActivity {
    private final String activityID;
    private final String name;
    private final String trimester;
    private final IUser creator;
    private List<IUser> participantList;
    private List<Feedback> feedbackList;

    public EvaluationActivity(String activityID, String name, String trimester, IUser creator, String dueDate) {
        this.activityID = activityID;
        this.name = name;
        this.trimester = trimester;
        this.creator = creator;
        this.participantList = getParticipant();
        this.feedbackList = getFeedBack();
    }

    // Read From Files
    public List<IUser> getParticipant() {
        List<IUser> activityParticipants = new ArrayList<>();
        return activityParticipants;
    }

    public List<Feedback> getFeedBack() {
        List<Feedback> userFeedback = new ArrayList<>();
        return userFeedback;
    }

    // Get Functions
    public String getActivityID() {
        return activityID;
    }

    public String getName() {
        return name;
    }

    public String getTrimester() {
        return trimester;
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
    public void addParticipant(User newParticipant) {
        participantList.add(newParticipant);
    }

    public void addFeedback(Feedback newFeedback) {
        feedbackList.add(newFeedback);
    }
}
