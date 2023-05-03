package hsuadddrop.model.database;

import hsuadddrop.model.Course;
import hsuadddrop.model.Session;
import hsuadddrop.model.Staff;
import hsuadddrop.model.Weekday;
import hsuadddrop.model.TimeOfDay;
import hsuadddrop.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SessionDAO {
    private Connection conn;

    public SessionDAO(Connection connection) {
        this.conn = connection;
    }
    
public Session findSessionByCode(String courseCode) throws SQLException{
    List<Session> sessionList = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM session WHERE course_code = "+courseCode);
        //PreparedStatement stmt = conn.prepareStatement("SELECT * FROM staff WHERE course_code = ?");
        stmt.setString(1, courseCode);
        ResultSet rs = stmt.executeQuery();
        
    if (rs.next()) {
            String course_code = rs.getString("course_code");
            String session_id = rs.getString("session_id");
            String teacher = rs.getString("teacher");
            //Weekday weekday = rs.getString("weekday");
            Weekday weekday = Weekday.valueOf(rs.getString("weekday"));
            //TimeOfDay time = rs.getTime("time");
            TimeOfDay time = TimeOfDay.valueOf(rs.getString("time"));
            int capacity = rs.getInt("capacity");
            //Session(String sessionID, String courseCode, String teacher, int capacity, Weekday weekday, TimeOfDay time)
            Session session = new Session(session_id,course_code, teacher, capacity, weekday, time);
            sessionList.add(session);
            
        } 
        return null;

}

public List<Session> getAllSession() throws SQLException{
    List<Session> sessionList = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM session");
        //PreparedStatement stmt = conn.prepareStatement("SELECT * FROM staff WHERE course_code = ?");
        ResultSet rs = stmt.executeQuery();
        
    if (rs.next()) {
            String course_code = rs.getString("course_code");
            String session_id = rs.getString("session_id");
            String teacher = rs.getString("teacher");
            //Weekday weekday = rs.getString("weekday");
            Weekday weekday = Weekday.valueOf(rs.getString("weekday"));
            //TimeOfDay time = rs.getTime("time");
            TimeOfDay time = TimeOfDay.valueOf(rs.getString("time"));
            int capacity = rs.getInt("capacity");
            //Session(String sessionID, String courseCode, String teacher, int capacity, Weekday weekday, TimeOfDay time)
            Session session = new Session(session_id,course_code, teacher, capacity, weekday, time);
            sessionList.add(session);
            
        } 
        return sessionList;

}

    
    
}
