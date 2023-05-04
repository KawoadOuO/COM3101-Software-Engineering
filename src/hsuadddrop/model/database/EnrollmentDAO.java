package hsuadddrop.model.database;

import hsuadddrop.model.Course;
import hsuadddrop.model.Enrollment;
import hsuadddrop.model.Session;
import hsuadddrop.model.Staff;
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
        List<Session> sessions = new ArrayList<>();
        try {
            //String sql = "SELECT * FROM course c, session s WHERE c.course_code = s.course_code";
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM enrollment e, student st, session se "
                    + "WHERE e.student_id = st.student_id, e.Session = se.Session");
            statement.setString(1, student.getStudentID());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String sessionID = rs.getString("session_id");
                String courseCode = rs.getString("course_code");
                Session session = new SessionDAO(conn).getSessionByID(courseCode, sessionID);
                sessions.add(session);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

        PreparedStatement statement = conn.prepareStatement("SELECT * FROM enrollment WHERE session = ?");
        statement.setString(1, session.getCourseCode());
        ResultSet rs = statement.executeQuery();

        List<Enrollment> enrollments = new ArrayList<>();
        while (rs.next()) {
            String student_id = rs.getString("student_id");
            Enrollment enrollment = new Enrollment(student_id, courseCode, sessionID);
            enrollments.add(enrollment);
        }
        return enrollments;
    }

//        if (!new StudentDAO(conn).checkRegistered(session)) {
//
//        }
    public void addEnrollment(Student student, Session session) throws SQLException {
        try {
            if (!checkNotSame(student, session.getCourseCode(), session.getSessionID())) {
                throw new SQLException("The student is already enrolled in this course");

            } else {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO enrollment(student_id, course_code, session_id) VALUES (?,?,?)");
                stmt.setString(1, student.getStudentID());
                stmt.setString(2, session.getCourseCode());
                stmt.setString(3, session.getSessionID());
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("The student is already enrolled in this course");

        }

    }

    public void dropEnrollment(Student student, Session session) throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM enrollment WHERE student_id = ? AND course_code = ? AND session_id = ?");
            stmt.setString(1, student.getStudentID());
            stmt.setString(2, session.getCourseCode());
            stmt.setString(3, session.getSessionID());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String checkALL(Student student, Session session, String course_code, String session_id) throws SQLException {
        boolean pass = true;
        String errorMessage = "";

        if (!checkNotSame(student, course_code, session_id)) {
            pass = false;
            errorMessage = "The student is already enrolled in this course";
        }
        if (!checkNotFull(student, session, course_code, session_id)) {
            pass = false;
            errorMessage = "The esssion is full";
        }

        if (!checkNotOverThree(student)) {
            pass = false;
            errorMessage = "The student has exceeded the maximum course enrollment limit.";
        }

        if (!checkNotConflict(student, session, course_code, session_id)) {
            pass = false;
            errorMessage = "There is a conflict with another enrolled course (weekday and time)";
        }
        if (pass) {
            return "All checks passed.";
        } else {
            return errorMessage;
        }
    }

    public boolean checkNotSame(Student student, String course_code, String session_id) throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) AS Have FROM enrollment WHERE student_id = ? AND course_code = ? AND session_id = ?");
            stmt.setString(1, student.getStudentID());
            stmt.setString(2, course_code);
            stmt.setString(3, session_id);

            ResultSet rs = stmt.executeQuery();

            // i) He is not already enrolled in that course
            while (rs.next()) {
                int HaveSame = rs.getInt("Have");
                if (HaveSame > 0) {
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean checkNotFull(Student student, Session session, String course_code, String session_id) throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement capacityStmt = conn.prepareStatement("SELECT capacity FROM session WHERE course_code = ? AND session_id = ?");
            //stmt.setString(1, student.getStudentID());
            capacityStmt.setString(1, course_code);
            capacityStmt.setString(2, session_id);
            ResultSet capacityRs = capacityStmt.executeQuery();
            if (capacityRs.next()) {
                int capacity = capacityRs.getInt("capacity");
                PreparedStatement enrollmentStmt = conn.prepareStatement("SELECT COUNT(*) AS enrolled_count FROM enrollment WHERE course_code = ? AND session_id = ?");

                enrollmentStmt.setString(1, course_code);
                enrollmentStmt.setString(2, session_id);
                ResultSet enrollmentRs = enrollmentStmt.executeQuery();
                if (enrollmentRs.next()) {
                    int enrolledCount = enrollmentRs.getInt("enrolled_count");
                    if (enrolledCount >= capacity) {
                        return false; // session is full
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean checkNotOverThree(Student student) throws SQLException {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) AS Number FROM enrollment WHERE student_id = ?");
            stmt.setString(1, student.getStudentID());

            ResultSet rs = stmt.executeQuery();

            // iii) The student has not exceeded his three courses quota and 
            while (rs.next()) {
                int number = rs.getInt("Number");
                System.out.println(number);

                if (number > 3) {
                    return false;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean checkNotConflict(Student student, Session session, String course_code, String session_id) throws SQLException {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * from enrollment WHERE student_id = ?");

            //"SELECT session.weekday, session.time FROM enrollment WHERE session.course_code = ? AND session.session_id = ?" 
            stmt.setString(1, student.getStudentID());
            ResultSet rs = stmt.executeQuery();

            // iv) There is no conflict in the course day and time with other courses that he has enrolled in.
            while (rs.next()) {

                String courseCode = rs.getString("course_code");
                session_id = rs.getString("session_id");

                
                session = new SessionDAO(conn).getSessionByID(courseCode, session_id);
                TimeOfDay time = session.getTime();
                Weekday week = session.getWeekday();

                if (!week.equals(session.getWeekday()) || !time.equals(session.getTime())) {
                    return false; // course time and weekday are confused
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
