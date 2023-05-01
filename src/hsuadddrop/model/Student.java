package hsuadddrop.model;

import hsuadddrop.model.database.CourseDAO;
import hsuadddrop.model.database.DatabaseConnection;
import hsuadddrop.model.database.StudentDAO;


import java.sql.SQLException;
import java.util.List;

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

    @Override
    public String toString() {
        return "Student{" + "studentID=" + studentID + ", gender=" + gender + ", studentName=" + studentName + '}';
    }

    public List<Session> getRegisteredSessions() {
        return new StudentDAO(DatabaseConnection.getInstance().getConnection()).getRegisteredSessions(this);
    }

    public void addSession(Session sessionToAdd) throws SQLException {
        new StudentDAO(DatabaseConnection.getInstance().getConnection()).addSession(this, sessionToAdd);
    }

    public void dropSession(Session sessionToDrop) throws SQLException {
        new StudentDAO(DatabaseConnection.getInstance().getConnection()).dropSession(this, sessionToDrop);
    }
}
