package hsuadddrop.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseName;
    private String courseCode;
    private List<Session> sessions;

    public Course(String courseName, String courseCode) {
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


    public static class Session {
        private String sessionID;
        private String teacher;
        private Weekday weekday;
        private TimeOfDay time;
        private List<Student> students;

        public Session(String sessionID, String teacher, Weekday weekday, TimeOfDay time) {
            this.sessionID = sessionID;
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
    }


}
