package hsuadddrop.model;

public class AddDropEntry {

    public enum Status {
        PENDING("Pending"),
        APPROVED("Approved"),
        REJECTED("Rejected");

        private final String text;

        Status(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }


    }

    private Student student;
    private Session sessionToAdd;
    private Session sessionToDrop;
    private Status status;
    private String reason;

    public AddDropEntry(Student student, Session sessionToAdd, Session sessionToDrop) {
        this.student = student;
        this.sessionToAdd = sessionToAdd;
        this.sessionToDrop = sessionToDrop;
        this.status = Status.PENDING;
    }
    public AddDropEntry(Student student, Session sessionToAdd, Session sessionToDrop, Status status, String reason) {
        this.student = student;
        this.sessionToAdd = sessionToAdd;
        this.sessionToDrop = sessionToDrop;
        this.status = status;
        this.reason = reason;
    }

    public Student getStudent() {
        return student;
    }



    public Session getSessionToAdd() {
        return sessionToAdd;
    }

    public Session getSessionToDrop() {
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