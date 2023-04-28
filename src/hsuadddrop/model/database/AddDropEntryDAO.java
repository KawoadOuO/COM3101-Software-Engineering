package hsuadddrop.model.database;

import hsuadddrop.model.AddDropEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddDropEntryDAO {
    private Connection conn;

    public AddDropEntryDAO(Connection conn) {
        this.conn = conn;
    }

    public void addEntry(AddDropEntry entry) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO add_drop_entries (student_id, course_code_to_add, course_code_to_drop, session_to_add, session_to_drop) VALUES (?, ?, ?, ?, ?)");
        stmt.setString(1, entry.getStudent().getStudentID());
        stmt.setString(2, entry.getCourseToAdd().getCourseCode());
        stmt.setString(3, entry.getCourseToDrop().getCourseCode());
        stmt.setString(4, entry.getSessionToAdd().getSessionID());
        stmt.setString(5, entry.getSessionToDrop().getSessionID());
        stmt.executeUpdate();
    }

    public void updateEntryStatus(AddDropEntry entry) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE add_drop_entries SET status = ?, reason = ? WHERE student_id = ? AND course_code_to_add = ? AND course_code_to_drop = ? AND session_to_add = ? AND session_to_drop = ?");
        stmt.setString(1, entry.getStatus().toString());
        stmt.setString(2, entry.getReason());
        stmt.setString(3, entry.getStudent().getStudentID());
        stmt.setString(4, entry.getCourseToAdd().getCourseCode());
        stmt.setString(5, entry.getCourseToDrop().getCourseCode());
        stmt.setString(6, entry.getSessionToAdd().getSessionID());
        stmt.setString(7, entry.getSessionToDrop().getSessionID());
        stmt.executeUpdate();
    }
}