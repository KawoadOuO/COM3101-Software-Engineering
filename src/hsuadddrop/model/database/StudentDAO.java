package hsuadddrop.model.database;

import hsuadddrop.model.Session;
import hsuadddrop.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection conn;

    public StudentDAO(Connection conn) {
        this.conn = conn;
    }

    public void addStudent(Student student) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO student(student_id, gender, name) VALUES (?, ?, ?)");
        stmt.setString(1, student.getStudentID());
        stmt.setString(2, student.getGender());
        stmt.setString(3, student.getStudentName());
        stmt.executeUpdate();
    }

    public void updateStudent(Student student) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE student SET gender = ?, name = ? WHERE student_id = ?");
        stmt.setString(1, student.getGender());
        stmt.setString(2, student.getStudentName());
        stmt.setString(3, student.getStudentID());
        stmt.executeUpdate();
    }

    public void deleteStudent(String studentID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM student WHERE student_id = ?");
        stmt.setString(1, studentID);
        stmt.executeUpdate();
    }

    public Student getStudentByID(String studentID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM student WHERE student_id = ?");
        stmt.setString(1, studentID);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String gender = rs.getString("gender");
            String studentName = rs.getString("name");
            return new Student(studentID, gender, studentName);
        } else {
            return null;
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM student");
        while (rs.next()) {
            String studentID = rs.getString("student_id");
            String gender = rs.getString("gender");
            String studentName = rs.getString("name");
            students.add(new Student(studentID, gender, studentName));
        }
        return students;
    }

    public List<Session> getRegisteredSessions(Student student) {
        return new CourseDAO(conn).getRegisteredSessions(student);
    }

    public void addSession(Student student, Session sessionToAdd) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO enrollment(student_id, course_code, session_id) VALUES (?, ?, ?)");
        stmt.setString(1, student.getStudentID());
        stmt.setString(2, sessionToAdd.getCourseCode());
        stmt.setString(3, sessionToAdd.getSessionID());
        stmt.executeQuery();

    }

    public void dropSession(Student student, Session sessionToDrop) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM enrollment WHERE student_id = ? AND course_code = ? AND session_id = ?");
        stmt.setString(1, student.getStudentID());
        stmt.setString(2, sessionToDrop.getCourseCode());
        stmt.setString(3, sessionToDrop.getSessionID());
        stmt.executeQuery();

    }
}