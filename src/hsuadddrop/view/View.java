/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hsuadddrop.view;


import javax.swing.JOptionPane;

import hsuadddrop.Controller;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lukaon
 */
public class View extends javax.swing.JFrame {

    private Controller controller;

    public void setController(Controller c) {
        this.controller = c;
    }

    public View() {

        setController(new Controller());

        initComponents();
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bt_drop = new javax.swing.JButton();
        dt_adddrop = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        label_name = new javax.swing.JLabel();
        label_sid = new javax.swing.JLabel();
        tf_studentid = new javax.swing.JTextField();
        bt_checkcourse = new javax.swing.JButton();
        label_AHCC = new javax.swing.JLabel();
        bt_logout = new javax.swing.JButton();
        bt_add = new javax.swing.JButton();
        label_time = new javax.swing.JLabel();
        bt_checkstudent = new javax.swing.JButton();
        bt_import = new javax.swing.JButton();
        bt_edit = new javax.swing.JButton();
        bt_pending = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bt_drop.setText("Drop Module");
        bt_drop.setMaximumSize(new java.awt.Dimension(87, 23));
        bt_drop.setMinimumSize(new java.awt.Dimension(87, 23));
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
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Course Code", "Course Name", "Session ID", "Teacher", "Weekday", "Time", "Capacity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        table.setAutoscrolls(false);
        jScrollPane1.setViewportView(table);

        label_name.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 14)); // NOI18N
        label_name.setText("(Staff Name)");

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
        label_AHCC.setText("AHCC Student Registration System (Group 11)");

        bt_logout.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        bt_logout.setForeground(new java.awt.Color(255, 51, 51));
        bt_logout.setText("Logout");
        bt_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_logoutActionPerformed(evt);
            }
        });

        bt_add.setText("Add Module");
        bt_add.setMaximumSize(new java.awt.Dimension(87, 23));
        bt_add.setMinimumSize(new java.awt.Dimension(87, 23));
        bt_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_addActionPerformed(evt);
            }
        });

        label_time.setText("(Login Time)");

        bt_checkstudent.setText("Check  Student Info");
        bt_checkstudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_checkstudentActionPerformed(evt);
            }
        });

        bt_import.setText("Import Data (.CSV)");
        bt_import.setMaximumSize(new java.awt.Dimension(128, 23));
        bt_import.setMinimumSize(new java.awt.Dimension(128, 23));
        bt_import.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_importActionPerformed(evt);
            }
        });

        bt_edit.setText("Import Data(SQL)");
        bt_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_editActionPerformed(evt);
            }
        });

        bt_pending.setText("Pending List");
        bt_pending.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_pendingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(656, 656, 656)
                .addComponent(bt_import, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(bt_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_name, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label_time, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label_AHCC, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(label_sid, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tf_studentid))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(bt_add, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(bt_drop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(dt_adddrop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bt_checkcourse, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(bt_checkstudent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bt_pending, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(bt_logout)))))
                .addGap(46, 46, 46))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_AHCC, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_logout))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label_name, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(label_time, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tf_studentid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(label_sid, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(bt_checkstudent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bt_add, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_drop, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bt_checkcourse, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dt_adddrop, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addComponent(bt_pending, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_edit)
                    .addComponent(bt_import, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dt_adddropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dt_adddropActionPerformed
        if (checkStudent() == true) {
            try {
                controller.addDropCourse();
            } catch (SQLException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_dt_adddropActionPerformed

    private void bt_checkcourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_checkcourseActionPerformed
        try {
            controller.showCourseInfo();
        } catch (SQLException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_checkcourseActionPerformed

    private void bt_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_logoutActionPerformed
        controller.login();
    }//GEN-LAST:event_bt_logoutActionPerformed

    private void bt_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_addActionPerformed
        if (checkStudent() == true) {
            try {
                controller.addCourse();
            } catch (SQLException e) {
            }
        }
    }//GEN-LAST:event_bt_addActionPerformed

    private void bt_checkstudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_checkstudentActionPerformed

        try {
            controller.showStudentInfo();
        } catch (SQLException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_checkstudentActionPerformed

    private void bt_dropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_dropActionPerformed
        if (checkStudent() == true) {
            try {
                controller.dropCourse();
            } catch (SQLException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_bt_dropActionPerformed

    private void bt_importActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_importActionPerformed
        // TODO add your handling code here:
        controller.importData();
        try {
            controller.updateCourse();
        } catch (SQLException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_importActionPerformed

    private void bt_pendingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_pendingActionPerformed
        try {
            controller.showPendingCourse();
        } catch (SQLException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_pendingActionPerformed

    private void bt_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_editActionPerformed
        try {
            // TODO add your handling code here:
            controller.debug();
        } catch (SQLException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_editActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_add;
    private javax.swing.JButton bt_checkcourse;
    private javax.swing.JButton bt_checkstudent;
    private javax.swing.JButton bt_drop;
    private javax.swing.JButton bt_edit;
    private javax.swing.JButton bt_import;
    private javax.swing.JButton bt_logout;
    private javax.swing.JButton bt_pending;
    private javax.swing.JButton dt_adddrop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_AHCC;
    private javax.swing.JLabel label_name;
    private javax.swing.JLabel label_sid;
    private javax.swing.JLabel label_time;
    private javax.swing.JTable table;
    private javax.swing.JTextField tf_studentid;
    // End of variables declaration//GEN-END:variables

    public boolean checkStudent() {
        String student_id = tf_studentid.getText();
        if (student_id.isEmpty()) {
            displayErrorMessage("Please input Student ID!");
            return false;
        } else {
            return true;
        }
    }

    public void setNameText(String s) {
        label_name.setText(s);
    }

    public void setTimeText(String time) {
        label_time.setText(time);
    }
    
    public void setTableModel(DefaultTableModel model){
            table.setModel(model);            
    }
    
    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(rootPane, message);
    }

    public void displaySuccessMessage(String message) {
        JOptionPane.showMessageDialog(rootPane, message);
    }
    
    public String getStudentID() {
        String studentid = tf_studentid.getText().toUpperCase();
        return studentid;
    }
}
