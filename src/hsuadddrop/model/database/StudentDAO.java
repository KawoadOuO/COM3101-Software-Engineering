package hsuadddrop.model.database;

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
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO students(student_id, gender, student_name) VALUES (?, ?, ?)");
        stmt.setString(1, student.getStudentID());
        stmt.setString(2, student.getGender());
        stmt.setString(3, student.getStudentName());
        stmt.executeUpdate();
    }

    public void updateStudent(Student student) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE students SET gender = ?, student_name = ? WHERE student_id = ?");
        stmt.setString(1, student.getGender());
        stmt.setString(2, student.getStudentName());
        stmt.setString(3, student.getStudentID());
        stmt.executeUpdate();
    }

    public void deleteStudent(String studentID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM students WHERE student_id = ?");
        stmt.setString(1, studentID);
        stmt.executeUpdate();
    }

    public Student getStudentByID(String studentID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM students WHERE student_id = ?");
        stmt.setString(1, studentID);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String gender = rs.getString("gender");
            String studentName = rs.getString("student_name");
            return new Student(studentID, gender, studentName);
        } else {
            return null;
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM students");
        while (rs.next()) {
            String studentID = rs.getString("student_id");
            String gender = rs.getString("gender");
            String studentName = rs.getString("student_name");
            students.add(new Student(studentID, gender, studentName));
        }
        return students;
    }
}