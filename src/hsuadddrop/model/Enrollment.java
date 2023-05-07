package hsuadddrop.model;

public class Enrollment {

    private String student_id;
    private String course_code;
    private String session_id;

    //Create an empty Enrollment object to transfer student_id, course_code and session_id;
    
    public Enrollment(String StudentID, String Code, String SessionID) {
        this.student_id = StudentID;
        this.course_code = Code;
        this.session_id = SessionID;
    }

    public String getStudent_id() {
        return student_id;
    }

    public String getCourse_code() {
        return course_code;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String toString() {
        return "Enrollment {" + "StudentID=" + student_id + ", CourseCode=" + course_code + ", Session_id=" + session_id + '}';
    }
}
