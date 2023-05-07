package hsuadddrop.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseName;
    private String courseCode;
    private List<Session> sessions;
    
    //Create a empty Course object to transfter courseName, courseCode and sessions ArrayList<>;

    public Course(String courseCode, String courseName) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.sessions = new ArrayList<>();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    @Override
    public String toString() {
        return "Course {" + "courseCode = " + courseCode + ", courseName = " + courseName + ", session=" + sessions + '}';
    }
}
