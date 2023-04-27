package hsuadddrop.model;
public class Student {
    private String studentID;
    private String gender;
    private String studentName;

    public Student(String studentID, String gender, String studentName) {
        this.studentID = studentID;
        this.gender = gender;
        this.studentName = studentName;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
