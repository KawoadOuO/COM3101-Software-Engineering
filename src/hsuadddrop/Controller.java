package hsuadddrop;

import hsuadddrop.model.*;
import hsuadddrop.model.database.AddDropEntryDAO;
import hsuadddrop.model.database.CourseDAO;
import hsuadddrop.model.database.DatabaseConnection;
import hsuadddrop.model.database.EnrollmentDAO;
import hsuadddrop.model.database.SessionDAO;
import hsuadddrop.model.database.StaffDAO;
import hsuadddrop.model.database.StudentDAO;
import hsuadddrop.model.database.data.DataImport;
import hsuadddrop.view.MainUI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

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

    public void displayStudentInfo(String message, JPanel panel) {
        JOptionPane.showMessageDialog(view, panel, message, JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayCourseInfo(String message, JPanel panel) {
        JOptionPane.showMessageDialog(view, panel, message, JOptionPane.INFORMATION_MESSAGE);
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
        try {
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
            for (Session session : allsession) {
                String courseCode = session.getCourseCode();
                String sessionID = session.getSessionID();
                String courseName = new CourseDAO(conn).getCourseByCourseCode(courseCode).getCourseName();
                String time = session.getTime().toString();
                String weekday = session.getWeekday().toString();
                String teacher = session.getTeacher();
                int capacity = session.getCapacity();
                String[] row = {courseCode, sessionID, courseName, time, weekday, teacher, Integer.toString(capacity)};
                model.addRow(row);
            }
            view.setTableModel(model);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
            try {
                Connection conn = DatabaseConnection.getInstance().getConnection();

                if (result == JOptionPane.OK_OPTION) {
                    String input = tf_input.getText().toUpperCase();
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

                    if (new CourseDAO(conn).getCourseByCourseCode(courseCode) == null) {
                        displayErrorMessage("There is no this course with course Code : " + courseCode);
                        continue;
                    }
                    if (details[1].isEmpty()) {
                        displayErrorMessage("Session can't be empty");
                        continue;
                    }
                    String session = details[1];

                    addModuleDisplayed = true;

                    Student student = new StudentDAO(conn).getStudentByID(view.getStudentID());
                    Session sessionDAO = new SessionDAO(conn).getSessionByID(courseCode, session);

                    String checkMessage = new EnrollmentDAO(conn).checkALL(student, sessionDAO, courseCode, session);
                    if (checkMessage.equals("All checks passed.")) {
                        new EnrollmentDAO(conn).addEnrollment(student, sessionDAO);
                        displaySuccessMessage("Add Module Successfully for " + view.getStudentID()
                                + "\nModule Code: " + courseCode
                                + "\nModule Name: " + new CourseDAO(conn).getCourseByCourseCode(courseCode).getCourseName()
                                + "\nSession: " + sessionDAO);
                        break;
                    } else {
                        displayErrorMessage(checkMessage);
                        continue;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);

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
            try {
                Connection conn = DatabaseConnection.getInstance().getConnection();
                if (result == JOptionPane.OK_OPTION) {
                    String input = tf_input.getText().toUpperCase();
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

                    if (new CourseDAO(conn).getCourseByCourseCode(courseCode) == null) {
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

                    Student student = new StudentDAO(conn).getStudentByID(view.getStudentID());
                    Session sessionDAO = new SessionDAO(conn).getSessionByID(courseCode, session);

                    new EnrollmentDAO(conn).dropEnrollment(student, sessionDAO);
                    try{displaySuccessMessage("Drop Module Successfully for " + view.getStudentID()
                            + "\nModule Code: " + courseCode
                            + "\nModule Name: " + new CourseDAO(conn).getCourseByCourseCode(courseCode).getCourseName()
                            + "\nSession: " + session);}
                
                catch(SQLException e){
                    throw new RuntimeException(e);
                    }
                        
                    }

                    
                    break;

                
            } 
            catch(SQLException e){
                    throw new RuntimeException(e);
                    }

        }
    }
    

    public void addDropCourse() throws SQLException {

        boolean addDropModuleDisplayed = false;
        if (addDropModuleDisplayed) {
        }

        while (true) {
            JPanel panel = new JPanel(new BorderLayout(5, 5));
            JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
            label.add(new JLabel("Add Course Code - Session", SwingConstants.RIGHT));
            label.add(new JLabel("eg.COM1000-L01", SwingConstants.RIGHT));
            label.add(new JLabel("------------------", SwingConstants.RIGHT));
            label.add(new JLabel("Drop Course Code - Session", SwingConstants.RIGHT));
            label.add(new JLabel("eg.COM1000-L01", SwingConstants.RIGHT));
            panel.add(label, BorderLayout.WEST);

            JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
            JTextField tf_add_input = new JTextField();
            JTextField tf_drop_input = new JTextField();
            tf_add_input.setColumns(20);
            tf_drop_input.setColumns(20);
            controls.add(tf_add_input);
            controls.add(tf_drop_input);
            panel.add(controls, BorderLayout.CENTER);

            JFrame frame = null;
            int result = JOptionPane.showConfirmDialog(frame, panel, "Add/Drop Module", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.CLOSED_OPTION) {
                displayErrorMessage("Add/Drop Module Cancelled");
                break;
            }

            if (result == JOptionPane.CANCEL_OPTION) {
                displayErrorMessage("Add/Drop Module Cancelled");
                break;
            }
            try {
                Connection conn = DatabaseConnection.getInstance().getConnection();
                if (result == JOptionPane.OK_OPTION) {

                    String add_input = tf_add_input.getText().toUpperCase();
                    String[] add_details = add_input.split("-");

                    if (add_details.length < 2) {
                        displayErrorMessage("Add Wrong input format, example : COM1000-L01");
                        continue;
                    }
                    if (add_details[0].isEmpty()) {
                        displayErrorMessage("Add Course Code can't be empty");
                        continue;
                    }
                    String add_courseCode = add_details[0];
                    
                    if (add_details[1].isEmpty()) {
                        displayErrorMessage("Add Session can't be empty");
                        continue;
                    }
                    String add_session = add_details[1];
                    if (new SessionDAO(conn).getSessionByID(add_courseCode, add_session) == null) {
                        displayErrorMessage("There is no this course with course Code : " + add_courseCode);
                        continue;
                    }

                    String drop_input = tf_drop_input.getText().toUpperCase();
                    String[] drop_details = drop_input.split("-");
                    if (drop_details.length < 2) {
                        displayErrorMessage("Drop wrong input format, example : COM1000-L01");
                        continue;
                    }
                    if (drop_details[0].isEmpty()) {
                        displayErrorMessage("Drop Course Code can't be empty");
                        continue;
                    }
                    String drop_courseCode = drop_details[0];
                    if (drop_details[1].isEmpty()) {
                        displayErrorMessage("Drop Session can't be empty");
                        continue;
                    }
                    String drop_session = drop_details[1];
                    if (new SessionDAO(conn).getSessionByID(drop_courseCode, drop_session) == null) {
                        displayErrorMessage("There is no this course with course Code : " + drop_courseCode);
                        continue;
                    }
                    

                    addDropModuleDisplayed = true;

                    Student student = new StudentDAO(conn).getStudentByID(view.getStudentID());
                    
                    Session session_add = new SessionDAO(conn).getSessionByID(add_courseCode, add_session);
                    Session session_drop = new SessionDAO(conn).getSessionByID(drop_courseCode, drop_session);
                    String checkMessage = new EnrollmentDAO(conn).checkALL(student, session_add, add_courseCode, add_session);
                    

                    if (checkMessage.equals("All checks passed.")) {
                        
                        
                        new EnrollmentDAO(conn).addEnrollment(student, session_add); 
                        new EnrollmentDAO(conn).dropEnrollment(student, session_drop);
                        
                        //new AddDropEntryDAO(conn).addEntry(new AddDropEntry(student, session_add, session_drop));
                        view.displaySuccessMessage("Add/Drop Module Request Made Successfully for " + view.getStudentID()
                                + "\nAdd Module Code: " + add_courseCode
                                + "\nAdd Module Name: " + new CourseDAO(conn).getCourseByCourseCode(add_courseCode).getCourseName()
                                + "\nAdd Session: " + add_session
                                + "\n"
                                + "\nDrop Module Code: " + drop_courseCode
                                + "\nDrop Module Name: " + new CourseDAO(conn).getCourseByCourseCode(drop_courseCode).getCourseName()
                                + "\nDrop Session: " + drop_session);
                        break;
                    } else {
                        displayErrorMessage(checkMessage);
                        continue;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void importData() {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        DataImport dataimport = new DataImport(conn);
        dataimport.importData();

    }

    public void showStudentInfo() throws SQLException {

        Connection conn = DatabaseConnection.getInstance().getConnection();
        DefaultTableModel model = (DefaultTableModel) new DefaultTableModel();
        model.addColumn("Course Code");
        model.addColumn("Session ID");
        model.addColumn("Course Name");
        model.addColumn("Time");
        model.addColumn("Weekday");
        model.addColumn("Teacher");

        String studentID = view.getStudentID();
        Student student = new StudentDAO(conn).getStudentByID(studentID);
        try {
            if (student == null) {
                displayErrorMessage("There is no this student with student ID : " + studentID);
            } else {
                String name = new StudentDAO(conn).getStudentByID(studentID).getStudentName();
                String gender = new StudentDAO(conn).getStudentByID(studentID).getGender();

                if (student != null) {
                    List<Session> allsession = new SessionDAO(conn).getRegisteredSessions(student);
                    for (Session session : allsession) {
                        String courseCode = session.getCourseCode();
                        String sessionID = session.getSessionID();
                        String courseName = new CourseDAO(conn).getCourseByCourseCode(courseCode).getCourseName();
                        String time = session.getTime().toString();
                        String weekday = session.getWeekday().toString();
                        String teacher = session.getTeacher();
                        String[] row = {courseCode, sessionID, courseName, time, weekday, teacher};
                        model.addRow(row);
                    }
                    JTable table = new JTable(model);
                    TableColumn courseNameColumn = table.getColumnModel().getColumn(2);
                    courseNameColumn.setPreferredWidth(250);
                    JScrollPane scrollPane = new JScrollPane(table);
                    scrollPane.setPreferredSize(new Dimension(600, 200));
                    String message = "<html>Student Info: <br/>"
                            + "Student ID: " + studentID + "<br/>"
                            + "Student Name: " + name + "<br/>"
                            + "Gender: " + gender + "</html>";
                    JLabel messagelabel = new JLabel(message);
                    JPanel panel = new JPanel();
                    panel.add(messagelabel);
                    panel.add(scrollPane);
                    panel.add(Box.createVerticalStrut(5));

                    displayStudentInfo("Student Info of " + studentID, panel);
                }
            }

        } catch (SQLException e) {
            displayErrorMessage("Wrong input of Student ");
        }

    }

    public void showCourseInfo() throws SQLException {

        boolean addModuleDisplayed = false;
        if (addModuleDisplayed) {
        }
        while (true) {
            JPanel panel_1 = new JPanel(new BorderLayout(5, 5));
            JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
            label.add(new JLabel("Course Code - Session", SwingConstants.RIGHT));
            label.add(new JLabel("eg.COM1000-L01", SwingConstants.RIGHT));
            panel_1.add(label, BorderLayout.WEST);

            JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
            JTextField tf_input = new JTextField();
            tf_input.setColumns(20);
            controls.add(tf_input);
            panel_1.add(controls, BorderLayout.CENTER);

            JFrame frame = null;
            int result = JOptionPane.showConfirmDialog(frame, panel_1, "Check Module Status", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.CLOSED_OPTION) {
                displayErrorMessage("Check Module Status Cancelled");
                break;
            }

            if (result == JOptionPane.CANCEL_OPTION) {
                displayErrorMessage("Check Module Status  Cancelled");
                break;
            }
            try {
                if (result == JOptionPane.OK_OPTION) {
                    String input = tf_input.getText().toUpperCase();
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
                    String course_code = details[0];

                    Connection conn = DatabaseConnection.getInstance().getConnection();
                    DefaultTableModel model = (DefaultTableModel) new DefaultTableModel();
                    model.addColumn("Student ID");
                    model.addColumn("Name");
                    model.addColumn("Gender");

                    if (new CourseDAO(conn).getCourseByCourseCode(course_code) == null) {
                        displayErrorMessage("There is no this course with course Code : " + course_code);
                        continue;
                    }
                    if (details[1].isEmpty()) {
                        displayErrorMessage("Session can't be empty");
                        continue;
                    }
                    String session_id = details[1];

                    addModuleDisplayed = true;

                    Session session = new SessionDAO(conn).getSessionByID(course_code, session_id);

                    if (!new CourseDAO(conn).getCoursesWithSessions().isEmpty()) {

                        List<Course> course = new CourseDAO(conn).getCoursesWithSessions();
                        // List<Student> allstudent = new StudentDAO(conn).getAllStudents().stream().filter(s -> s.getRegisteredSessions().contains(session)).collect(Collectors.toList());
                        List<Student> allstudent2 = new StudentDAO(conn).getAllStudents().stream().collect(Collectors.toList());
                        List<Student> allstudent = new ArrayList<>();
                        for (Student student : allstudent2){
                            for(Session s :student.getRegisteredSessions() ){
                                if(s.getCourseCode().equals(course_code)&&s.getSessionID().equals(session_id)){
                                    allstudent.add(student);
                                }
                            }
                        }
                        
                        System.out.println("student:");
                        System.out.println(allstudent);
                        if (!course.isEmpty()) {
                                
                            String course_name = new CourseDAO(conn).getCourseByCourseCode(course_code).getCourseName();
                            Weekday weekday = session.getWeekday();
                            TimeOfDay time = session.getTime();
                            String teacher = session.getTeacher();
                            int capacity = session.getCapacity();
                            System.out.println("add row???????");
                            for (Student student : allstudent) {
                                String student_id = student.getStudentID();
                                String student_name = student.getStudentName();
                                String student_gender = student.getGender();
                                String[] row = {student_id, student_name, student_gender};
                                model.addRow(row);
                                System.out.println("add row?");
                            }
                            JTable table = new JTable(model);
                            TableColumn courseNameColumn = table.getColumnModel().getColumn(2);
                            courseNameColumn.setPreferredWidth(250);
                            JScrollPane scrollPane = new JScrollPane(table);
                            scrollPane.setPreferredSize(new Dimension(600, 200));
                            String message = "<html>Course Info: <br/>"
                                    + "Course Code: " + course_code + "<br/>"
                                    + "Course Name: " + course_name + "<br/>"
                                    + "Weekday: " + weekday + "<br/>"
                                    + "Time: " + time + "<br/>"
                                    + "Teacher: " + teacher + "<br/>"
                                    + "Capacity: " + capacity + "</html>";
                            JLabel messagelabel = new JLabel(message);
                            JPanel panel = new JPanel();
                            panel.add(messagelabel);
                            panel.add(scrollPane);
                            panel.add(Box.createVerticalStrut(5));

                            displayCourseInfo("Course Info of " + course_code, panel);
                            break;

                        }
                    } else {
                        displayErrorMessage("No courses found with that sessions");
                    }
                }
            } catch (SQLException e) {
            }
        }
    }
}
