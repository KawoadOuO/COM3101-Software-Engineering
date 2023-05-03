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

    public void addEnrollment(Student student, Session session) throws SQLException {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO enrollment(student_id, course_code, session_id) VALUES (?,?,?)");
            stmt.setString(1, student.getStudentID());
            stmt.setString(2, session.getCourseCode());
            stmt.setString(3, session.getSessionID());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeEnrollment(Student student, Session session) throws SQLException {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM enrollment(student_id, course_code, session_id) VALUES (?,?,?)");
            stmt.setString(1, student.getStudentID());
            stmt.setString(2, session.getCourseCode());
            stmt.setString(3, session.getSessionID());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean checkNotOverThree(Student student) throws SQLException {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) AS Number FROM enrollment WHERE student_id = ?");
            stmt.setString(1, student.getStudentID());

            ResultSet rs = stmt.executeQuery();

            // iii) The student has not exceeded his three courses quota and 
            while (rs.next()) {
                int number = rs.getInt("Number");

                if (number > 3) {
                    return false;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

     
    public boolean checkNotSame(Student student, String course_code, String session_id) throws SQLException {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) AS Have FROM enrollment WHERE student_id = ? AND course_code = ? AND session_id = ?");
            stmt.setString(1, student.getStudentID());
            stmt.setString(2, course_code);
            stmt.setString(3, session_id);

            ResultSet rs = stmt.executeQuery();

            // i) He is not already enrolled in that course
            while (rs.next()) {
                int HaveSame = rs.getInt("Have");
                if (HaveSame >0 ) {
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean checkNotConflict(Student student, Session session, String course_code, String session_id) throws SQLException {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT s.course_code, COUNT(DISTINCT s.session_id, e.student_id) "
                    + "AS Have FROM session s LEFT JOIN enrollment e "
                    + "ON s.course_code = e.course_code AND s.course_code = e.course_code "
                    + "GROUP BY s.course_code "
                    + "WHERE student_id = ? AND course_code = ? AND session_id = ?");
            stmt.setString(1, student.getStudentID());
            stmt.setString(2, course_code);
            stmt.setString(3, session_id);

            ResultSet rs = stmt.executeQuery();

            // i) iv) There is no conflict in the course day and time with other courses that he has enrolled in.
            while (rs.next()) {
                int HaveConflict = rs.getInt("Have");
                if (HaveConflict >0 ) {
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean checkNotFull(Student student, Session session, String course_code, String session_id) throws SQLException {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM enrollment WHERE course_code = ? AND session_id = ?");
            //SELECT * FROM session WHERE course_code = "
            
            //stmt.setString(1, student.getStudentID());
            stmt.setString(1, course_code);
            stmt.setString(2, session_id);

            ResultSet rs = stmt.executeQuery();

            // ii) The session is not full,
            while (rs.next()) {
                String capacity = rs.getString("capacity");
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    

}