package hsuadddrop.model.database;

import hsuadddrop.model.Enrollment;
import hsuadddrop.model.Session;
import hsuadddrop.model.Student;
import hsuadddrop.model.TimeOfDay;
import hsuadddrop.model.Weekday;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {

    private Connection conn;

    public EnrollmentDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Session> getEnrolledList(Student student) throws SQLException {
        //create a list of Session object and return it
        List<Session> sessions = new ArrayList<>();
        try {
            //prepare the select statement
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM enrollment e, student st, session se "
                    + "WHERE e.student_id = st.student_id, e.Session = se.Session");
            statement.setString(1, student.getStudentID());
            //execute the select statement
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String sessionID = rs.getString("session_id");
                String courseCode = rs.getString("course_code");
                Session session = new SessionDAO(conn).getSessionByID(courseCode, sessionID);
                sessions.add(session);
            }
        } catch (SQLException e) {
        }
        return sessions;
    }

    public List<Session> getEnrollmentByStudentID(Student student) throws SQLException {
        List<Session> sessions = new ArrayList<>();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM enrollment WHERE student_id = ?");
        statement.setString(1, student.getStudentID());
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            String sessionID = rs.getString("session_id");
            String courseCode = rs.getString("course_code");
            Session session = new SessionDAO(conn).getSessionByID(courseCode, sessionID);
            sessions.add(session);
        }
        return sessions;
    }

    public List<Enrollment> getEnrollmentBySession(Student student, Session session) throws SQLException {

        String courseCode = session.getCourseCode();
        String sessionID = session.getSessionID();

        PreparedStatement statement = conn.prepareStatement("SELECT * FROM enrollment WHERE course_code = ? AND session_id = ? ");

        statement.setString(1, session.getCourseCode());
        statement.setString(2, session.getSessionID());
        ResultSet rs = statement.executeQuery();

        List<Enrollment> enrollments = new ArrayList<>();
        while (rs.next()) {
            String student_id = rs.getString("student_id");
            Enrollment enrollment = new Enrollment(student_id, courseCode, sessionID);
            enrollments.add(enrollment);
        }
        return enrollments;
    }

    public void addEnrollment(Student student, Session session) throws SQLException {
        try {
                //prepare the insert statement
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO enrollment(student_id, course_code, session_id) VALUES (?,?,?)");
                stmt.setString(1, student.getStudentID());
                stmt.setString(2, session.getCourseCode());
                stmt.setString(3, session.getSessionID());
                //execute the select statement
                stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("The student is already enrolled in this course");
        }

    }

    public void dropEnrollment(Student student, Session session) throws SQLException {
            try {
                //prepare the delete statement
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM enrollment WHERE student_id = ? AND course_code = ? AND session_id = ? ");
                stmt.setString(1, student.getStudentID());
                stmt.setString(2, session.getCourseCode());
                stmt.setString(3, session.getSessionID());
                //execute the update statement
                stmt.executeUpdate();
            } catch (SQLException e) {
            }
    }

    public void InputData(String input) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(input);
        stmt.executeUpdate();
    }

}