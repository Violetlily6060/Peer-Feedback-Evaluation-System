package domain;

import java.util.List;

public class Course {
    private final String name;
    private final String courseID;
    private List<EvaluationActivity> evaluationActivityList;

    public Course(String name, String courseID) {
        this.name = name;
        this.courseID = courseID;
    }

    // Get Functions
    public String getName() {
        return name;
    }

    public String getCourseID() {
        return courseID;
    }

    public List<EvaluationActivity> getActivityList() {
        return evaluationActivityList;
    }

    // Other Functions
    public void addActivity(EvaluationActivity newActivity) {
        evaluationActivityList.add(newActivity);
    }
}