package hsuadddrop.model;

public class AddDropEntry {
    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }

    private Student student;
    private Course courseToAdd;
    private Course courseToDrop;
    private Course.Session sessionToAdd;
    private Course.Session sessionToDrop;
    private Status status;
    private String reason;

    public AddDropEntry(Student student, Course courseToAdd, Course courseToDrop, Course.Session sessionToAdd, Course.Session sessionToDrop) {
        this.student = student;
        this.courseToAdd = courseToAdd;
        this.courseToDrop = courseToDrop;
        this.sessionToAdd = sessionToAdd;
        this.sessionToDrop = sessionToDrop;
        this.status = Status.PENDING;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourseToAdd() {
        return courseToAdd;
    }

    public Course getCourseToDrop() {
        return courseToDrop;
    }

    public Course.Session getSessionToAdd() {
        return sessionToAdd;
    }

    public Course.Session getSessionToDrop() {
        return sessionToDrop;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}