
package hsuadddrop.model;

public class Session {
        private String sessionID;
        private String teacher;
        private String weekday;
        private TimeOfDay time;

        public Session(String sessionID, String teacher, String weekday, TimeOfDay time) {
            this.sessionID = sessionID;
            this.teacher = teacher;
            this.weekday = weekday;
            this.time = time;
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

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getWeekday() {
            return weekday;
        }

        public void setWeekday(String weekday) {
            this.weekday = weekday;
        }

        public TimeOfDay getTime() {
            return time;
        }

        public void setTime(TimeOfDay time) {
            this.time = time;
        }
    }
