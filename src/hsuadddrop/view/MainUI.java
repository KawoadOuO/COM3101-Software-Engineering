/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hsuadddrop.view;

import hsuadddrop.model.*;
import static hsuadddrop.model.TimeOfDay.*;
import static hsuadddrop.model.Weekday.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Hashtable;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import hsuadddrop.Controller;
import hsuadddrop.model.database.StaffDAO;

/**
 *
 * @author lukaon
 */
public class MainUI extends javax.swing.JFrame {

    private Controller controller;

    public void setController(Controller c) {
        this.controller = c;
    }

    public MainUI() {

        setController(new Controller());

        initComponents();
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bt_drop = new javax.swing.JButton();
        dt_adddrop = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        label_welcome = new javax.swing.JLabel();
        label_sid = new javax.swing.JLabel();
        tf_studentid = new javax.swing.JTextField();
        bt_checkcourse = new javax.swing.JButton();
        label_AHCC = new javax.swing.JLabel();
        bt_logout = new javax.swing.JButton();
        bt_add = new javax.swing.JButton();
        label_time = new javax.swing.JLabel();
        bt_checkstudent = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bt_drop.setText("Drop Module");
        bt_drop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_dropActionPerformed(evt);
            }
        });

        dt_adddrop.setText("Add/Drop");
        dt_adddrop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dt_adddropActionPerformed(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Module Code", "Name", "Session", "Teacher", "Weekday", "Time", "Capacity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);

        label_welcome.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 14)); // NOI18N
        label_welcome.setText("Welcome, (Staff Name/ID)");

        label_sid.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 14)); // NOI18N
        label_sid.setText("Student ID:");

        bt_checkcourse.setText("Check Module Status");
        bt_checkcourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_checkcourseActionPerformed(evt);
            }
        });

        label_AHCC.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        label_AHCC.setForeground(new java.awt.Color(0, 204, 102));
        label_AHCC.setText("AHCC Student Registration System");

        bt_logout.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        bt_logout.setForeground(new java.awt.Color(255, 51, 51));
        bt_logout.setText("Logout");
        bt_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_logoutActionPerformed(evt);
            }
        });

        bt_add.setText("Add Module");
        bt_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_addActionPerformed(evt);
            }
        });

        label_time.setText("2023/5/2 12:53");

        bt_checkstudent.setText("Check  Student Info");
        bt_checkstudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_checkstudentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label_AHCC, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bt_logout))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label_time, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label_welcome, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(label_sid, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(tf_studentid, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(bt_add)
                                        .addGap(18, 18, 18)
                                        .addComponent(bt_drop)))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(bt_checkstudent, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(113, 113, 113))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(dt_adddrop)
                                        .addGap(32, 32, 32)
                                        .addComponent(bt_checkcourse, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bt_logout)
                        .addGap(18, 18, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tf_studentid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label_sid, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bt_checkstudent, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(label_AHCC, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_welcome, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_add, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_drop, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dt_adddrop, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_checkcourse, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label_time, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dt_adddropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dt_adddropActionPerformed
        if (checkStudent() == true) {
            JFrame frame = null;
            clickAddDrop(frame);
        }
    }//GEN-LAST:event_dt_adddropActionPerformed

    private void bt_checkcourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_checkcourseActionPerformed

    }//GEN-LAST:event_bt_checkcourseActionPerformed

    private void bt_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_logoutActionPerformed
        controller.login();
    }//GEN-LAST:event_bt_logoutActionPerformed

    private void bt_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_addActionPerformed
        if (checkStudent() == true) {
            controller.AddCourse();
        }
    }//GEN-LAST:event_bt_addActionPerformed

    private void bt_checkstudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_checkstudentActionPerformed


    }//GEN-LAST:event_bt_checkstudentActionPerformed

    private void bt_dropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_dropActionPerformed
        if (checkStudent() == true) {
            controller.DropCourse();
        }
    }//GEN-LAST:event_bt_dropActionPerformed

//    public static void main(String args[]) {
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
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new MainUI().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_add;
    private javax.swing.JButton bt_checkcourse;
    private javax.swing.JButton bt_checkstudent;
    private javax.swing.JButton bt_drop;
    private javax.swing.JButton bt_logout;
    private javax.swing.JButton dt_adddrop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_AHCC;
    private javax.swing.JLabel label_sid;
    private javax.swing.JLabel label_time;
    private javax.swing.JLabel label_welcome;
    private javax.swing.JTable table;
    private javax.swing.JTextField tf_studentid;
    // End of variables declaration//GEN-END:variables

    public boolean checkStudent() {
        boolean student_not_empty = false;
        String student_id = tf_studentid.getText();
        if (student_id.isEmpty()) {
            displayErrorMessage("Please input Student ID!");
            return false;
        } else {
            return true;
        }
    }

    public void setWelcomeText(String s) {
        label_welcome.setText(s);
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(rootPane, message);
    }

    public void displaySuccessMessage(String message) {
        JOptionPane.showMessageDialog(rootPane, message);
    }

    /*
    public void clickAdd(JFrame frame) {
        boolean addModuleDisplayed = false;
        if (addModuleDisplayed) {
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

            int result = JOptionPane.showConfirmDialog(frame, panel, "Add Module", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.CLOSED_OPTION) {
                displayErrorMessage("Add Module Cancelled");
                break;
            }

            if (result == JOptionPane.CANCEL_OPTION) {
                displayErrorMessage("Add Module Cancelled");
                break;
            }
            if (result == JOptionPane.OK_OPTION) {
                String input = tf_input.getText();
                String[] details = input.split("-");
                String courseCode = details[0];
                String session = details[1];
                if (input.isEmpty() || session.isEmpty()||courseCode.isEmpty()) {
                    displayErrorMessage("Can't be empty");
                    continue;
                } else {

                    addModuleDisplayed = true;
                    displaySuccessMessage("Add Module Successfully for " + getStudentID() + "\nModule Code: " + courseCode + "\nSession: " + session);
                    break;
                }
            }
        }
    }
     */
    public Hashtable<String, String> clickAddDrop(JFrame frame) {
        boolean addDropModuleDisplayed = false;
        Hashtable<String, String> addDropModuleData = new Hashtable<String, String>();
        if (addDropModuleDisplayed) {
            // Return the login information without displaying the login page again
            return addDropModuleData;
        }
        while (true) {
            JPanel panel = new JPanel(new BorderLayout(5, 5));
            JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
            label.add(new JLabel("Add Module Code", SwingConstants.RIGHT));
            label.add(new JLabel("Session", SwingConstants.RIGHT));
            label.add(new JLabel("Drop Module Code", SwingConstants.RIGHT));
            label.add(new JLabel("Session", SwingConstants.RIGHT));
            panel.add(label, BorderLayout.WEST);

            JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
            JTextField tf_add_module_code = new JTextField();
            controls.add(tf_add_module_code);
            JTextField tf_add_session = new JTextField();
            controls.add(tf_add_session);
            JTextField tf_drop_module_code = new JTextField();
            controls.add(tf_drop_module_code);
            JTextField tf_drop_session = new JTextField();
            controls.add(tf_drop_session);
            panel.add(controls, BorderLayout.CENTER);

            int result = JOptionPane.showConfirmDialog(frame, panel, "Add Module", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.CLOSED_OPTION) {
                displayErrorMessage("Add Module Cancelled");
                break;
            }

            if (result == JOptionPane.CANCEL_OPTION) {
                displayErrorMessage("Add Module Cancelled");
                break;
            }
            if (result == JOptionPane.OK_OPTION) {
                String module_code = tf_add_module_code.getText();
                String session = tf_add_session.getText();
                if (module_code.isEmpty()) {
                    displayErrorMessage("Module Code can't be empty");
                    continue;
                }
                if (session.isEmpty()) {
                    displayErrorMessage("Session can't be empty");
                    continue;
                }

                addDropModuleData.put("Module_Code", module_code);
                addDropModuleData.put("Session", session);
                addDropModuleDisplayed = true;
                displaySuccessMessage("Add Module Successfully for " + getStudentID() + "\nModule Code: " + module_code + "\nSession: " + session);
                break;
            }

        }
        return addDropModuleData;
    }

    public String getStudentID() {
        String studentid = tf_studentid.getText();
        return studentid;
    }
}
