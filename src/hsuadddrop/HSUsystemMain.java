package hsuadddrop;

import hsuadddrop.model.AddDropEntry;
import hsuadddrop.model.Course;
import hsuadddrop.model.Session;
import hsuadddrop.model.Staff;
import hsuadddrop.model.Student;
import hsuadddrop.model.database.AddDropEntryDAO;
import hsuadddrop.model.database.CourseDAO;
import hsuadddrop.model.database.DatabaseConnection;
import hsuadddrop.model.database.SessionDAO;
import hsuadddrop.model.database.StaffDAO;
import hsuadddrop.model.database.StudentDAO;
import hsuadddrop.view.MainUI;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HSUsystemMain {

    private Controller controller;
    private MainUI view;

    public void setController(Controller c) {
        this.controller = c;
    }

    public void setView(MainUI v) {
        this.view = v;
    }

    public static void main(String[] args) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
        /* Create and display the form */
//
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                Controller controller = new Controller();
                MainUI view = new MainUI();

                view.setController(controller);
                if (view == null) {
                    System.out.println("viwe is null");
                }
                //controller.processAddDropEntries();

                controller.setView(view);
                view.setVisible(true);
                //controller.login();
                try {
                    controller.updateCourse() ;
                } catch (SQLException ex) {
                    Logger.getLogger(HSUsystemMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        
//        try {
//            Connection connection = DatabaseConnection.getInstance().getConnection();
//            System.out.println("Staff");
//            List<Staff> staffs = new StaffDAO(connection).getAllStaff();
//            for(Staff staff : staffs){
//                System.out.println(staff);}
//            
//            System.out.println("Stduent");
//            List<Student> students = new StudentDAO(connection).getAllStudents();
//            for (Student student : students) {
//                System.out.println(student);
//            }
//            System.out.println("Course");
//            List<Course> courses = new CourseDAO(connection).getCoursesWithSessions();
//            for (Course course : courses) {
//                System.out.println(course);
//            }
//            System.out.println("");
//            System.out.println("Session");
//            List<Session> sessions = new SessionDAO(connection).getAllSession();
//            for (Session session : sessions) {
//                System.out.println(session);
//            }
//            System.out.println("");
//            System.out.println("AddDropEntry");
//            List<AddDropEntry> entries = new AddDropEntryDAO(connection).getAllEntries();
//            for (AddDropEntry entry : entries) {
//                System.out.println(entry);
//            }
//            
//            
//            
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    
        
         
    }
}
