package hsuadddrop.model.database;

import hsuadddrop.model.AddDropEntry;
import hsuadddrop.model.Session;
import hsuadddrop.model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddDropEntryDAO {
    private final Connection conn;

    public AddDropEntryDAO(Connection conn) {
        this.conn = conn;
    }

    public List<AddDropEntry> getAllEntries() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM add_drop_entry");
        ResultSet rs = stmt.executeQuery();
        List<AddDropEntry> entries = new ArrayList<>();
        while (rs.next()) {
            String studentId = rs.getString("student_id");
            String courseCodeToAdd = rs.getString("course_code_to_add");
            String courseCodeToDrop = rs.getString("course_code_to_drop");
            String sessionIDToAdd = rs.getString("session_to_add");
            String sessionIDToDrop = rs.getString("session_to_drop");
            String status = rs.getString("status");
            String reason = rs.getString("reason");

            Session sessionToAdd = new SessionDAO(conn).getSessionByID(courseCodeToAdd, sessionIDToAdd);
            Session sessionToDrop = new SessionDAO(conn).getSessionByID(courseCodeToDrop, sessionIDToDrop);

            AddDropEntry entry = new AddDropEntry(
                    new StudentDAO(conn).getStudentByID(studentId),
                    sessionToAdd,
                    sessionToDrop,
                    Status.fromDisplayName(status),
                    reason
            );
            entries.add(entry);
        }
        return entries;
    }

    public void addEntry(AddDropEntry entry) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO add_drop_entry(student_id, course_code_to_add, course_code_to_drop, session_to_add, session_to_drop, status, reason) VALUES (?, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, entry.getStudent().getStudentID());
        stmt.setString(2, entry.getSessionToAdd().getCourseCode());
        stmt.setString(3, entry.getSessionToDrop().getCourseCode());
        stmt.setString(4, entry.getSessionToAdd().getSessionID());
        stmt.setString(5, entry.getSessionToDrop().getSessionID());
        stmt.setString(6, entry.getStatus().toString());
        stmt.setString(7, entry.getReason());
        stmt.executeUpdate();
    }

    public void updateEntryStatus(AddDropEntry entry) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE add_drop_entry SET status = ? WHERE student_id = ? AND course_code_to_add = ? AND course_code_to_drop = ? AND session_to_add = ? AND session_to_drop = ?");
        stmt.setString(1, entry.getStatus().toString());
        stmt.setString(2, entry.getStudent().getStudentID());
        stmt.setString(3, entry.getSessionToAdd().getCourseCode());
        stmt.setString(4, entry.getSessionToDrop().getCourseCode());
        stmt.setString(5, entry.getSessionToAdd().getSessionID());
        stmt.setString(6, entry.getSessionToDrop().getSessionID());
        stmt.executeUpdate();
    }
}