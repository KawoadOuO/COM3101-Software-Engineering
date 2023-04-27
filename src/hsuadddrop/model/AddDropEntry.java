package hsuadddrop.model;
public class AddDropEntry {
    private Student student;
    private Course course;
    private Course.Session sessionToAdd;
    private Course.Session sessionToDrop;
    private Status status;
    private String reason;

    public enum Status {
        PENDING,
        SUCCESS,
        FAIL
    }

    public AddDropEntry(Student student, Course course, Course.Session sessionToAdd, Course.Session sessionToDrop) {
        this.student = student;
        this.course = course;
        this.sessionToAdd = sessionToAdd;
        this.sessionToDrop = sessionToDrop;
        this.status = Status.PENDING;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Course.Session getSessionToAdd() {
        return sessionToAdd;
    }

    public void setSessionToAdd(Course.Session sessionToAdd) {
        this.sessionToAdd = sessionToAdd;
    }

    public Course.Session getSessionToDrop() {
        return sessionToDrop;
    }

    public void setSessionToDrop(Course.Session sessionToDrop) {
        this.sessionToDrop = sessionToDrop;
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
