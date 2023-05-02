package hsuadddrop.model.database;

import hsuadddrop.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {
    private Connection conn;

    public StaffDAO(Connection conn) {
        this.conn = conn;
    }


    public Staff findStaffById(String staffId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM staff WHERE staff_id = ?");
        stmt.setString(1, staffId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String name = rs.getString("name");
            String password = rs.getString("password");
            return new Staff(staffId, name, password);
        } else {
            return null;
        }
    }


    public boolean authenticateStaff(String staffId, String password) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM staff WHERE staff_id = ? AND password = ?");
        stmt.setString(1, staffId);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }


    public List<Staff> getAllStaff() throws SQLException {
        List<Staff> staffList = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM staff");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String staffId = rs.getString("staff_id");
            String name = rs.getString("name");
            String password = rs.getString("password");
            Staff staff = new Staff(staffId, name, password);
            staffList.add(staff);
        }

        return staffList;
    }


    public void addStaff(Staff staff) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO staff (staff_id, name, password) VALUES (?, ?, ?)");
        stmt.setString(1, staff.getStaffID());
        stmt.setString(2, staff.getName());
        stmt.setString(3, staff.getPassword());
        stmt.executeUpdate();
    }


    public void updateStaff(Staff staff) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE staff SET name = ?, password = ? WHERE staff_id = ?");
        stmt.setString(1, staff.getName());
        stmt.setString(2, staff.getPassword());
        stmt.setString(3, staff.getStaffID());
        stmt.executeUpdate();
    }


    public void deleteStaff(Staff staff) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM staff WHERE staff_id = ?");
        stmt.setString(1, staff.getStaffID());
        stmt.executeUpdate();
    }

    public Staff getStaff(String username) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM staff WHERE staff_id = ?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String staffId = rs.getString("staff_id");
            String name = rs.getString("name");
            String password = rs.getString("password");
            return new Staff(staffId, name, password);
        } else {
            return null;
        }
    }
}