package domain;

public final class Feedback {
    private final IUser creator;
    private final IUser receiver;
    private final String dateCreated;
    private String dateUpdated;
    private String content;

    public Feedback(IUser creator, IUser receiver, String dateCreated, String dateUpdated, String content) {
        this.creator = creator;
        this.receiver = receiver;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.content = content;
    }

    // Get Functions
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
}
