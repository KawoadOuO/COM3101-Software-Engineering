package hsuadddrop.model;

import java.util.List;

public class Session {
    private String sessionID;
    private String courseCode;
    private String teacher;
    private Weekday weekday;
    private TimeOfDay time;
    private List<Student> students;

    public Session(String sessionID, String courseCode, String teacher, Weekday weekday, TimeOfDay time) {
        this.sessionID = sessionID;
        this.courseCode = courseCode;
        this.teacher = teacher;
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
}
