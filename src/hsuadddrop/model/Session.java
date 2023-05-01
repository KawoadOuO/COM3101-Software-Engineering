package hsuadddrop.model;

import hsuadddrop.model.database.CourseDAO;
import hsuadddrop.model.database.DatabaseConnection;

import java.util.Collection;
import java.util.List;

public class Session {
    private String sessionID;
    private String courseCode;
    private String teacher;
    private Weekday weekday;
    private TimeOfDay time;
    private List<Student> students;

    public int getCapacity() {
        return capacity;
    }

    private int capacity;

    public Session(String sessionID, String courseCode, String teacher, int capacity, Weekday weekday, TimeOfDay time) {
        this.sessionID = sessionID;
        this.courseCode = courseCode;
        this.teacher = teacher;
        this.capacity = capacity;
        this.weekday = weekday;
        this.time = time;
    }
    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getTeacher() {
        return teacher;
    }

    public List<Student> getStudents() {
        return students;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public void setWeekday(Weekday weekday) {
        this.weekday = weekday;
    }

    public TimeOfDay getTime() {
        return time;
    }

    public void setTime(TimeOfDay time) {
        this.time = time;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public List<Student> getEnrolledStudents() {
        return new CourseDAO(DatabaseConnection.getInstance().getConnection()).getEnrolledStudents(this);
    }
}
