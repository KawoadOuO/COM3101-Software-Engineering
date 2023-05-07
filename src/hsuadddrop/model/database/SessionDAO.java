package hsuadddrop.model.database;

import hsuadddrop.model.Session;
import hsuadddrop.model.Weekday;
import hsuadddrop.model.TimeOfDay;
import hsuadddrop.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SessionDAO {

    private final Connection conn;

    public SessionDAO(Connection connection) {
        this.conn = connection;
    }

    public Session findSessionByCode(String courseCode) throws SQLException {
        //create a list of Session and return it
        List<Session> sessionList = new ArrayList<>();
        //prepare the select statement
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM session WHERE course_code = " + courseCode);
        stmt.setString(1, courseCode);
        //execute the select statement
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String course_code = rs.getString("course_code");
            String session_id = rs.getString("session_id");
            String teacher = rs.getString("teacher");
            Weekday weekday = Weekday.fromDisplayName(rs.getString("weekday"));
            TimeOfDay time = TimeOfDay.fromDisplayName(rs.getString("time"));
            int capacity = rs.getInt("capacity");
            Session session = new Session(session_id, course_code, teacher, capacity, weekday, time);
            sessionList.add(session);
        }
        return null;
    }

    //get session data from database
    public List<Session> getAllSession() throws SQLException {
        //create a list of Session and return it
        List<Session> sessionList = new ArrayList<>();
         //prepare the select statement
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM session");
        //execute the select statement
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            //get value from database
            String course_code = rs.getString("course_code");
            String session_id = rs.getString("session_id");
            String teacher = rs.getString("teacher");
            Weekday weekday = Weekday.fromDisplayName(rs.getString("weekday"));
            TimeOfDay time = TimeOfDay.fromDisplayName(rs.getString("time"));
            int capacity = rs.getInt("capacity");
            Session session = new Session(session_id, course_code, teacher, capacity, weekday, time);
            sessionList.add(session);

        }
        return sessionList;

    }

    public void removeSession(Session session, String courseCode) throws SQLException {
        //prepare the delete statement
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM session WHERE course_code = ? AND session_id = ?");

        //set the values for the parameters
        stmt.setString(1, courseCode);
        stmt.setString(2, session.getSessionID());

        //execute the delete statement
        stmt.executeUpdate();
    }

    public List<Session> getSessionsForCourse(String courseCode) throws SQLException {
        //prepare the select statement
        String sql = "SELECT * FROM session WHERE course_code = ? AND session_id <> ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        //set the values for the parameters
        stmt.setString(1, courseCode);
        stmt.setString(2, "");

        //execute the select statement
        ResultSet rs = stmt.executeQuery();

        //create a list of Session objects and return it
        List<Session> sessions = new ArrayList<>();
        while (rs.next()) {
            String sessionID = rs.getString("session_id");
            String teacher = rs.getString("teacher");
            int capacity = rs.getInt("capacity");
            Weekday weekday = Weekday.fromDisplayName(rs.getString("weekday"));
            TimeOfDay time = TimeOfDay.fromDisplayName(rs.getString("time"));
            Session session = new Session(sessionID, courseCode, teacher, capacity, weekday, time);
            sessions.add(session);
        }

        return sessions;
    }

    public Session getSessionByID(String courseCode, String sessionID) throws SQLException {
        //prepare the select statement
        String sql = "SELECT * FROM session WHERE course_code = ? AND session_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, courseCode);
        stmt.setString(2, sessionID);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
        //execute the select statement
            String teacher = rs.getString("teacher");
            int capacity = rs.getInt("capacity");
            Weekday weekday = Weekday.fromDisplayName(rs.getString("weekday"));
            TimeOfDay time = TimeOfDay.fromDisplayName(rs.getString("time"));
            return new Session(sessionID, courseCode, teacher, capacity, weekday, time);
        }
        return null;
    }

    public List<Session> getRegisteredSessions(Student student) {
        //create a list of Session and return it
        List<Session> sessions = new ArrayList<>();
        try {
            //perpare the select statement
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM enrollment WHERE student_id = ?");
            stmt.setString(1, student.getStudentID());
            //execute the select statment
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String sessionID = rs.getString("session_id");
                String courseCode = rs.getString("course_code");
                Session session = getSessionByID(courseCode, sessionID);
                sessions.add(session);
            }
        } catch (SQLException e) {
        }
        return sessions;
    }

}