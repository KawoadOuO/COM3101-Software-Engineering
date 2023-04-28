package hsuadddrop.model.database;

import hsuadddrop.model.Course;
import hsuadddrop.model.Course.Session;
import hsuadddrop.model.TimeOfDay;
import hsuadddrop.model.Weekday;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    private final Connection connection;

    public CourseDAO(Connection connection) {
        this.connection = connection;
    }

    public void addCourse(Course course) throws SQLException {
        //prepare the insert statement
        String sql = "INSERT INTO courses (course_code, session_id, course_name, teacher, weekday, time) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        //set the values for the parameters
        statement.setString(1, course.getCourseCode());
        statement.setString(2, "");
        statement.setString(3, course.getCourseName());
        statement.setString(4, "");
        statement.setString(5, "");
        statement.setString(6, "");

        //execute the insert statement
        statement.executeUpdate();
    }

    public void addSession(Session session, String courseCode) throws SQLException {
        //prepare the insert statement
        String sql = "INSERT INTO courses (course_code, session_id, course_name, teacher, weekday, time) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        //set the values for the parameters
        statement.setString(1, courseCode);
        statement.setString(2, session.getSessionID());
        statement.setString(3, "");
        statement.setString(4, session.getTeacher());
        statement.setString(5, session.getWeekday().toString());
        statement.setString(6, session.getTime().toString());

        //execute the insert statement
        statement.executeUpdate();
    }

    public void removeCourse(Course course) throws SQLException {
        //prepare the delete statement
        String sql = "DELETE FROM courses WHERE course_code = ? AND session_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        //set the values for the parameters
        statement.setString(1, course.getCourseCode());
        statement.setString(2, "");

        //execute the delete statement
        statement.executeUpdate();
    }

    public void removeSession(Session session, String courseCode) throws SQLException {
        //prepare the delete statement
        String sql = "DELETE FROM courses WHERE course_code = ? AND session_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        //set the values for the parameters
        statement.setString(1, courseCode);
        statement.setString(2, session.getSessionID());

        //execute the delete statement
        statement.executeUpdate();
    }

    public List<Course> getAllCourses() throws SQLException {
        //prepare the select statement
        String sql = "SELECT * FROM courses WHERE session_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        //set the value for the parameter
        statement.setString(1, "");

        //execute the select statement
        ResultSet rs = statement.executeQuery();

        //create a list of Course objects and return it
        List<Course> courses = new ArrayList<>();
        while (rs.next()) {
            String courseCode = rs.getString("course_code");
            String courseName = rs.getString("course_name");
            Course course = new Course(courseName, courseCode);
            courses.add(course);
        }

        return courses;
    }

    public List<Session> getAllSessions() throws SQLException {
        //prepare the select statement
        String sql = "SELECT * FROM courses WHERE session_id <> ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        //set the value for the parameter
        statement.setString(1, "");

        //execute the select statement
        ResultSet rs = statement.executeQuery();

        //create a list of Session objects and return it
        List<Session> sessions = new ArrayList<>();
        while (rs.next()) {
            String sessionID = rs.getString("session_id");
            String teacher = rs.getString("teacher");
            Weekday weekday = Weekday.valueOf(rs.getString("weekday"));
            TimeOfDay time = TimeOfDay.valueOf(rs.getString("time"));
            Session session = new Session(sessionID, teacher, weekday, time);
            sessions.add(session);
        }

        return sessions;
    }

    public List<Course> getCoursesWithSessions() throws SQLException {
        //prepare the select statement
        String sql = "SELECT DISTINCT course_code, course_name FROM courses WHERE session_id <> ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        //set the value for the parameter
        statement.setString(1, "");

        //execute the select statement
        ResultSet rs = statement.executeQuery();

        //create a list of Course objects and return it
        List<Course> courses = new ArrayList<>();
        while (rs.next()) {
            String courseCode = rs.getString("course_code");
            String courseName = rs.getString("course_name");
            Course course = new Course(courseName, courseCode);

            //get the sessions for this course and add them to the course object
            List<Session> sessions = getSessionsForCourse(courseCode);
            course.setSessions(sessions);

            courses.add(course);
        }

        return courses;
    }

    public List<Session> getSessionsForCourse(String courseCode) throws SQLException {
        //prepare the select statement
        String sql = "SELECT * FROM courses WHERE course_code = ? AND session_id <> ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        //set the values for the parameters
        statement.setString(1, courseCode);
        statement.setString(2, "");

        //execute the select statement
        ResultSet rs = statement.executeQuery();

        //create a list of Session objects and return it
        List<Session> sessions = new ArrayList<>();
        while (rs.next()) {
            String sessionID = rs.getString("session_id");
            String teacher = rs.getString("teacher");
            Weekday weekday = Weekday.valueOf(rs.getString("weekday"));
            TimeOfDay time = TimeOfDay.valueOf(rs.getString("time"));
            Session session = new Course.Session(sessionID, teacher, weekday, time);
            sessions.add(session);
        }

        return sessions;
    }


    public Course getCourseByCodeSection(String courseCodeToAdd, String sessionToAdd) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM courses WHERE course_code = ? AND session_id = ?");
        statement.setString(1, courseCodeToAdd);
        statement.setString(2, sessionToAdd);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            String courseCode = rs.getString("course_code");
            String courseName = rs.getString("course_name");
            Course course = new Course(courseName, courseCode);
            course.setSessions(getSessionsForCourse(courseCode));
            return course;
        }
        return null;
    }
}