package hsuadddrop.model.database;

import hsuadddrop.model.*;
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
        String sql = "INSERT INTO course (course_code, course_name, teacher, weekday, time) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);

        //set the values for the parameters
        stmt.setString(1, course.getCourseCode());
        stmt.setString(2, course.getCourseName());
        stmt.setString(3, "");
        stmt.setString(4, "");
        stmt.setString(5, "");

        //execute the insert statement
        stmt.executeUpdate();
    }

    public void addSession(Session session, String courseCode) throws SQLException {
        //prepare the insert statement
        String sql = "INSERT INTO course (course_code, course_name, teacher, weekday, time) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);

        //set the values for the parameters
        stmt.setString(1, courseCode);
        stmt.setString(2, "");
        stmt.setString(3, session.getTeacher());
        stmt.setString(4, session.getWeekday().toString());
        stmt.setString(5, session.getTime().toString());

        //execute the insert statement
        stmt.executeUpdate();
    }

    public Course getCourseByCourseCode(String courseCode) throws SQLException {
        //prepare the select statement
        String sql = "SELECT * FROM course WHERE course_code = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, courseCode);

        //execute the select statement
        ResultSet rs = statement.executeQuery();

        //execute the delete statement
        if (rs.next()) {
            String courseName = rs.getString("course_name");
            Course course = new Course(courseCode, courseName);
            return course;
        }
        return null;
    }

    public void removeCourse(Course course) throws SQLException {
        //prepare the delete statement
        String sql = "DELETE FROM course WHERE course_code = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);

        //set the values for the parameters
        stmt.setString(1, course.getCourseCode());

        //execute the delete statement
        stmt.executeUpdate();
    }

    public List<Course> getAllCourses() throws SQLException {
        //prepare the select statement
        String sql = "SELECT * FROM course";
        PreparedStatement stmt = connection.prepareStatement(sql);

        //execute the select statement
        ResultSet rs = stmt.executeQuery();

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

    public List<Course> getCoursesWithSessions() throws SQLException {
        //prepare the select statement
        String sql = "SELECT * FROM course c, session s WHERE c.course_code = s.course_code";
        PreparedStatement stmt = connection.prepareStatement(sql);

        //execute the select statement
        ResultSet rs = stmt.executeQuery();

        //create a list of Course objects and return it
        List<Course> courses = new ArrayList<>();
        while (rs.next()) {

            String courseCode = rs.getString("course_code");
            String courseName = rs.getString("course_name");

            Course course = new Course(courseName, courseCode);

            //get the sessions for this course and add them to the course object
            List<Session> sessions = new SessionDAO(connection).getSessionsForCourse(courseCode);
            course.setSessions(sessions);

            courses.add(course);
        }

        return courses;
    }

    public Course getCourseByCodeSection(String courseCode, String sessionID) throws SQLException {
        //prepare the select statement
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM course WHERE course_code = ?");
        stmt.setString(1, courseCode);
        stmt.setString(2, sessionID);

        //execute the select statement
        ResultSet rs = stmt.executeQuery();

        //create a list of Course objects and return it
        List<Course> courses = new ArrayList<>();
        while (rs.next()) {
            courseCode = rs.getString("course_code");
            String courseName = rs.getString("course_name");
            Course course = new Course(courseName, courseCode);

            course.setSessions(new SessionDAO(connection).getSessionsForCourse(courseCode));
            courses.add(course);
        }
        return null;

    }

    public List<Student> getEnrolledStudents(Session session) {
        //create a list of Student objects and return it
        List<Student> students = new ArrayList<>();
        try {
            //prepare the select statement
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM enrollment WHERE session_id = ? AND course_code = ?");
             stmt.setString(1, session.getSessionID());
             stmt.setString(2, session.getCourseCode());
            //execute the select statement
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String studentID = rs.getString("student_id");
                Student student = new StudentDAO(connection).getStudentByID(studentID);
                students.add(student);
            }
        } catch (SQLException e) {
        }
        return students;
    }
}