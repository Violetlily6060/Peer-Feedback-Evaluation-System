package domain;

public class Feedback {
    private String toUserID;
    private String activityID;
    private String content;
    private String Date;

    public Feedback(String toUserID, String activityID, String content, String date) {
        this.toUserID = toUserID;
        this.activityID = activityID;
        this.content = content;
        this.Date = date;
    }

    public String getToUserID() {
        return toUserID;
    }

    public String getActivityID() {
        return activityID;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return Date;
    }
}
