package hsuadddrop;

import hsuadddrop.model.*;
import hsuadddrop.model.database.AddDropEntryDAO;
import hsuadddrop.model.database.CourseDAO;
import hsuadddrop.model.database.DatabaseConnection;
import hsuadddrop.model.database.SessionDAO;
import hsuadddrop.model.database.StaffDAO;
import hsuadddrop.view.MainUI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class Controller {

    MainUI view;
    public Component rootPane;

    public void setView(MainUI v) {
        this.view = v;
    }

    public void processAddDropEntries() {
        try {
            List<AddDropEntry> entries = new AddDropEntryDAO(DatabaseConnection.getInstance().getConnection()).getAllEntries();
            for (AddDropEntry entry : entries) {
                if (entry.getStatus() != Status.PENDING) {
                    continue;
                }
                Student student = entry.getStudent();
                Session sessionToAdd = entry.getSessionToAdd();
                Session sessionToDrop = entry.getSessionToDrop();
                if (sessionToAdd != null && sessionToDrop != null) {
                    // add/drop can fail if the target session is full
                    if (sessionToAdd.getEnrolledStudents().size() >= sessionToAdd.getCapacity()) {
                        entry.setStatus(Status.REJECTED);
                        entry.setReason("Session is full");
                    } else {
                        student.addSession(sessionToAdd);
                        student.dropSession(sessionToDrop);
                        entry.setStatus(Status.APPROVED);
                    }
                } else if (sessionToAdd == null && sessionToDrop != null) {
                    // it is always possible to drop a session
                    student.dropSession(sessionToDrop);
                } else if (sessionToAdd != null && sessionToDrop == null) {
                    // check the capacity only
                    if (sessionToAdd.getEnrolledStudents().size() >= sessionToAdd.getCapacity()) {
                        entry.setStatus(Status.REJECTED);
                        entry.setReason("Session is full");
                    } else {
                        student.addSession(sessionToAdd);
                        entry.setStatus(Status.APPROVED);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean canMakeAddDropEntry(Student student, Session sessionToAdd, Session sessionToDrop) {
        // check if the student is already registered for the session to add
        for (Session session : student.getRegisteredSessions()) {
            if (sessionToAdd.equals(session)) {
                displayErrorMessage("Student is already registered for the session to add");
                return false;
            }
        }
        // check if the student is already registered for the session to drop
        if (!student.getRegisteredSessions().contains(sessionToDrop)) {
            displayErrorMessage("Student is not registered for the session to drop");
            return false;
        }
        // there is 3 case for checking the registration limit
        // 1. if the student is adding a session
        // 2. if the student is dropping a session
        // 3. if the student is swapping a session
        // check if the student is adding a session
        if (sessionToAdd != null && sessionToDrop == null) {
            // check if the student is already registered 6 sessions
            if (student.getRegisteredSessions().size() >= 6) {
                displayErrorMessage("Student is already registered for 6 sessions");
                return false;
            }
        }
        // show success message
        displaySuccessMessage("Add/drop entry created successfully");
        return true;
    }

    public boolean login(String staffID, String password) {
        // check credentials in database
        // if valid, return true
        // else return false
        try {
            Staff loginStaff = new StaffDAO(DatabaseConnection.getInstance().getConnection()).getStaff(staffID);
            if (loginStaff != null) {
                if (loginStaff.getPassword().equals(password)) {

                    view.setWelcomeText("Staff : " + getStaffName(staffID));

                    int day, month, year;
                    int second, minute, hour;

                    GregorianCalendar gc = new GregorianCalendar();
                    day = gc.get(Calendar.DAY_OF_MONTH);
                    month = gc.get(Calendar.MONTH) + 1;
                    year = gc.get(Calendar.YEAR);
                    second = gc.get(Calendar.SECOND);
                    minute = gc.get(Calendar.MINUTE);
                    hour = gc.get(Calendar.HOUR_OF_DAY);

                    String time = "Login Time : " + year + "/" + month + "/" + day + "   " + hour + ":" + minute + ":" + second;
                    view.setTimeText(time);

                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    String getStaffName(String staffID) throws SQLException {
        try {
            Staff loginStaff = new StaffDAO(DatabaseConnection.getInstance().getConnection()).getStaff(staffID);
            if (loginStaff != null) {
                String staffName = loginStaff.getName();
                return staffName;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(rootPane, message);
    }

    public void displaySuccessMessage(String message) {
        JOptionPane.showMessageDialog(rootPane, message);
    }

    public void login() {
        boolean loginPageDisplayed = false;
        if (loginPageDisplayed) {
            // Return the login information without displaying the login page again
        }
        while (true) {
            JPanel panel = new JPanel(new BorderLayout(5, 5));
            JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
            label.add(new JLabel("Staff ID", SwingConstants.RIGHT));
            label.add(new JLabel("Password", SwingConstants.RIGHT));
            panel.add(label, BorderLayout.WEST);

            JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
            JTextField staffid = new JTextField();
            controls.add(staffid);
            JTextField password = new JTextField();
            controls.add(password);
            panel.add(controls, BorderLayout.CENTER);
            JFrame frame = null;
            int result = JOptionPane.showConfirmDialog(frame, panel, "Login", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.CLOSED_OPTION) {
                displayErrorMessage("Login Cancelled");
                System.exit(0);
            }

            if (result == JOptionPane.CANCEL_OPTION) {
                displayErrorMessage("Login Cancelled");
                System.exit(0);
            }
            if (result == JOptionPane.OK_OPTION) {
                String id = staffid.getText();
                String pw = password.getText();
                if (id.isEmpty() || pw.isEmpty()) {
                    displayErrorMessage("Can't be empty");
                    continue;
                } else {
                    if (login(id, pw)) {
                        loginPageDisplayed = true;
                        break;
                    } else {
                        displayErrorMessage("Invalid credentials");
                        continue;
                    }
                }

            }
        }
    }


    public void updateCourse() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        DefaultTableModel model = (DefaultTableModel) new DefaultTableModel();
            model.addColumn("Course Code");
            model.addColumn("Session ID");
            model.addColumn("Course Name");
            model.addColumn("Time");
            model.addColumn("Weekday");
            model.addColumn("Teacher");
            model.addColumn("Capacity");
            
        List<Session> allsession = new SessionDAO(conn).getAllSession();
        System.out.println(allsession.size());
        for (Session session : allsession) {
            String courseCode = session.getCourseCode();
            String sessionID = session.getSessionID();
            String courseName = new CourseDAO(conn).getCourseByCourseCode(courseCode).getCourseName();
            String time = session.getTime().toString();
            String weekday = session.getWeekday().toString();
            String teacher = session.getTeacher();
            int capacity = session.getCapacity();
            String[] row = {courseCode, sessionID, courseName,time, weekday, teacher,Integer.toString(capacity)};
            model.addRow(row);
        }
        view.setTableModel(model);
    }

    public void addCourse() throws SQLException {
        boolean addModuleDisplayed = false;
        if (addModuleDisplayed) {
        }
        while (true) {
            JPanel panel = new JPanel(new BorderLayout(5, 5));
            JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
            label.add(new JLabel("Course Code - Session", SwingConstants.RIGHT));
            label.add(new JLabel("eg.COM1000-L01", SwingConstants.RIGHT));
            panel.add(label, BorderLayout.WEST);

            JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
            JTextField tf_input = new JTextField();
            tf_input.setColumns(20);
            controls.add(tf_input);
            panel.add(controls, BorderLayout.CENTER);

            JFrame frame = null;
            int result = JOptionPane.showConfirmDialog(frame, panel, "Add Module", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.CLOSED_OPTION) {
                displayErrorMessage("Add Module Cancelled");
                break;
            }

            if (result == JOptionPane.CANCEL_OPTION) {
                displayErrorMessage("Add Module Cancelled");
                break;
            }
            Connection connection = DatabaseConnection.getInstance().getConnection();

            if (result == JOptionPane.OK_OPTION) {
                String input = tf_input.getText();
                if (input.isEmpty()) {
                    displayErrorMessage("Input can't be empty");
                    continue;
                }

                String[] details = input.split("-");
                if (details.length < 2) {
                    displayErrorMessage("Wrong input format, example : COM1000-L01");
                    continue;
                }
                if (details[0].isEmpty()) {
                    displayErrorMessage("Course Code can't be empty");
                    continue;
                }
                String courseCode = details[0];

                if (new CourseDAO(connection).getCourseByCourseCode(courseCode) == null) {
                    displayErrorMessage("There is no this course with course Code : " + courseCode);
                    continue;
                }
                if (details[1].isEmpty()) {
                    displayErrorMessage("Session can't be empty");
                    continue;
                }
                String session = details[1];

                addModuleDisplayed = true;

                displaySuccessMessage("Add Module Successfully for " + view.getStudentID()
                        + "\nModule Code: " + courseCode
                        + "\nModule Name: " + new CourseDAO(connection).getCourseByCourseCode(courseCode).getCourseName()
                        + "\nSession: " + session);
                break;

            }
        }

    }

    public void dropCourse() throws SQLException {
        boolean dropModuleDisplayed = false;
        if (dropModuleDisplayed) {
        }

        while (true) {
            JPanel panel = new JPanel(new BorderLayout(5, 5));
            JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
            label.add(new JLabel("Course Code - Session", SwingConstants.RIGHT));
            label.add(new JLabel("eg. COM1000-L01", SwingConstants.RIGHT));
            panel.add(label, BorderLayout.WEST);

            JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
            JTextField tf_input = new JTextField();
            tf_input.setColumns(20);
            controls.add(tf_input);
            panel.add(controls, BorderLayout.CENTER);
            JFrame frame = null;
            int result = JOptionPane.showConfirmDialog(frame, panel, "Drop Module", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.CLOSED_OPTION) {
                displayErrorMessage("Drop Module Cancelled");
                break;
            }

            if (result == JOptionPane.CANCEL_OPTION) {
                displayErrorMessage("Drop Module Cancelled");
                break;
            }
            Connection connection = DatabaseConnection.getInstance().getConnection();
            if (result == JOptionPane.OK_OPTION) {
                String input = tf_input.getText();
                String[] details = input.split("-");
                if (details.length < 2) {
                    displayErrorMessage("Wrong input format, example : COM1000-L01");
                    continue;
                }

                if (details[0].isEmpty()) {
                    displayErrorMessage("Course Code can't be empty");
                    continue;
                }
                String courseCode = details[0];
                if (new CourseDAO(connection).getCourseByCourseCode(courseCode) == null) {
                    displayErrorMessage("There is no this course with course Code : " + courseCode);
                    continue;
                }
                if (details[1].isEmpty()) {
                    displayErrorMessage("Session can't be empty");
                    continue;
                }
                String session = details[1];

                //controller.processAddDropEntries();
                dropModuleDisplayed = true;

                displaySuccessMessage("Drop Module Successfully for " + view.getStudentID()
                        + "\nModule Code: " + courseCode
                        + "\nModule Name: " + new CourseDAO(connection).getCourseByCourseCode(courseCode).getCourseName()
                        + "\nSession: " + session);
                break;

            }
        }
    }

    public void showAllSession() {

    }

}
