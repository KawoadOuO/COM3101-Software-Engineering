package hsuadddrop.model.database;

import hsuadddrop.model.AddDropEntry;
import hsuadddrop.model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddDropEntryDAO {
    private Connection conn;

    public AddDropEntryDAO(Connection conn) {
        this.conn = conn;
    }

    public List<AddDropEntry> getAllEntries() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM add_drop_entries");
        ResultSet rs = stmt.executeQuery();
        List<AddDropEntry> entries = new ArrayList<>();
        while (rs.next()) {
            String studentId = rs.getString("student_id");
            String courseCodeToAdd = rs.getString("course_code_to_add");
            String courseCodeToDrop = rs.getString("course_code_to_drop");
            String sessionToAdd = rs.getString("session_to_add");
            String sessionToDrop = rs.getString("session_to_drop");
            String status = rs.getString("status");
            String reason = rs.getString("reason");

            Course courseToAdd = new CourseDAO(conn).getCourseByCodeSection(courseCodeToAdd, sessionToAdd);
            Course courseToDrop = new CourseDAO(conn).getCourseByCodeSection(courseCodeToDrop, sessionToDrop);

            AddDropEntry entry = new AddDropEntry(
                    new StudentDAO(conn).getStudentByID(studentId),
                    courseToAdd,
                    courseToDrop,
                    courseToAdd.getSessions().get(0),
                    courseToDrop.getSessions().get(0),
                    AddDropEntry.Status.valueOf(status),
                    reason
            );
            entries.add(entry);
        }
        return entries;
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