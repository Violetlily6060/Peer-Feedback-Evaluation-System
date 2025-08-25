package domain;

public final class Feedback {
    private final String feedbackID;
    private final IUser creator;
    private final IUser receiver;
    private final String dateCreated;
    private String dateUpdated;
    private String content;

    public Feedback(String feedbackID, IUser creator, IUser receiver, String dateCreated, String dateUpdated, String content) {
        this.feedbackID = feedbackID;
        this.creator = creator;
        this.receiver = receiver;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.content = content;
    }

    // Get Functions
    public String getFeedbackID() {
        return feedbackID;
    }

    public IUser getCreator() {
        return creator;
    }

    public IUser getReceiver() {
        return receiver;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public String getContent() {
        return content;
    }

    // Set Functions
    public void setContent(String newContent) {
        this.content = newContent;
    }

    public void setDateUpdated(String newDate) {
        this.dateUpdated = newDate;
    }

    // Base Functions
    @Override
    public String toString() {
        return feedbackID + ";" + creator.getUserID() + ";" + receiver.getUserID() + ";" + dateCreated + ";" + dateUpdated + ";" + content;
    }
}