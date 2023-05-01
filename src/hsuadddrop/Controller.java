package hsuadddrop;


import hsuadddrop.model.AddDropEntry;
import hsuadddrop.model.Session;
import hsuadddrop.model.Student;
import hsuadddrop.model.database.AddDropEntryDAO;
import hsuadddrop.model.database.DatabaseConnection;
import hsuadddrop.view.MainUI;

import java.sql.SQLException;
import java.util.List;

public class Controller {

    MainUI view;

    public void setView(MainUI v) {
            this.view = v;        
       }

       public void processAddDropEntries() {
          try {
              List<AddDropEntry> entries =  new AddDropEntryDAO(DatabaseConnection.getInstance().getConnection()).getAllEntries();
              for (AddDropEntry entry : entries) {
                  if (entry.getStatus() != AddDropEntry.Status.PENDING) {
                      continue;
                  }
                  Student student = entry.getStudent();
                  Session sessionToAdd = entry.getSessionToAdd();
                  Session sessionToDrop = entry.getSessionToDrop();
                  if (sessionToAdd != null && sessionToDrop != null) {
                      // add/drop can fail if the target session is full
                      if (sessionToAdd.getEnrolledStudents().size() >= sessionToAdd.getCapacity()) {
                          entry.setStatus(AddDropEntry.Status.REJECTED);
                          entry.setReason("Session is full");
                      } else {
                          student.addSession(sessionToAdd);
                          student.dropSession(sessionToDrop);
                          entry.setStatus(AddDropEntry.Status.APPROVED);
                      }
                  } else if (sessionToAdd == null && sessionToDrop != null) {
                      // it is always possible to drop a session
                        student.dropSession(sessionToDrop);
                  } else if (sessionToAdd != null && sessionToDrop == null) {
                      // check the capacity only
                        if (sessionToAdd.getEnrolledStudents().size() >= sessionToAdd.getCapacity()) {
                            entry.setStatus(AddDropEntry.Status.REJECTED);
                            entry.setReason("Session is full");
                        } else {
                            student.addSession(sessionToAdd);
                            entry.setStatus(AddDropEntry.Status.APPROVED);
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
                    view.displayErrorMessage("Student is already registered for the session to add");
                     return false;
                }
              }
            // check if the student is already registered for the session to drop
            if (!student.getRegisteredSessions().contains(sessionToDrop)) {
                view.displayErrorMessage("Student is not registered for the session to drop");
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
                     view.displayErrorMessage("Student is already registered for 6 sessions");
                     return false;
                }
           }
            // show success message
            view.displaySuccessMessage("Add/drop entry created successfully");
            return true;
       }
    
}
