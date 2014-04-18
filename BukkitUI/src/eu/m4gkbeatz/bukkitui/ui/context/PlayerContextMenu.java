/*
 * Copyright (C) 2014 beatsleigher
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.m4gkbeatz.bukkitui.ui.context;

import eu.m4gkbeatz.bukkitui.ui.dialogs.WhiteListDialog;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author beatsleigher
 */
public class PlayerContextMenu extends javax.swing.JFrame {
    
    public static int instances = 0;
    private BufferedWriter writer = null;
    private String playerName;

    /**
     * 
     * @param playerName
     * @param location 
     */
    public PlayerContextMenu(String playerName, Point location, BufferedWriter writer) {
        initComponents();
        this.setLocation(location);
        instances++;
        this.writer = writer;
        this.playerName = playerName;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setType(java.awt.Window.Type.POPUP);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/addOP_highlighted.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/addOP_highlighted.png"))); // NOI18N
        jButton1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/addOP_highlighted.png"))); // NOI18N
        jButton1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/addOP_highlighted.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton7KeyPressed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/banIP_highlighted.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/banIP_highlighted.png"))); // NOI18N
        jButton2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/banIP_highlighted.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jButton2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton7KeyPressed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/banUser_highlighted.png"))); // NOI18N
        jButton3.setBorderPainted(false);
        jButton3.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/banUser_highlighted.png"))); // NOI18N
        jButton3.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/banUser_highlighted.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jButton3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton7KeyPressed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/giveItem_highlighted.png"))); // NOI18N
        jButton4.setBorderPainted(false);
        jButton4.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/giveItem_highlighted.png"))); // NOI18N
        jButton4.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/giveItem_highlighted.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jButton4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton7KeyPressed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/removeOP_highlighted.png"))); // NOI18N
        jButton5.setBorderPainted(false);
        jButton5.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/removeOP_highlighted.png"))); // NOI18N
        jButton5.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/removeOP_highlighted.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jButton5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton7KeyPressed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/sendMessage_highlighted.png"))); // NOI18N
        jButton6.setBorderPainted(false);
        jButton6.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/sendMessage_highlighted.png"))); // NOI18N
        jButton6.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/sendMessage_highlighted.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jButton6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton7KeyPressed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/whitelist_highlighted.png"))); // NOI18N
        jButton7.setBorderPainted(false);
        jButton7.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/whitelist_highlighted.png"))); // NOI18N
        jButton7.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/player_context/whitelist_highlighted.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jButton7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton7KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                close();
            case KeyEvent.VK_E:
                close();
            default: return;
        }
    }//GEN-LAST:event_formKeyPressed

    private void jButton7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton7KeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                close();
            case KeyEvent.VK_E:
                close();
            default: return;
        }
    }//GEN-LAST:event_jButton7KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (writer != null) {
            try {
                writer.write("/op " + playerName);
                writer.flush();
                close();
            } catch (IOException ex) {
                System.err.println("ERROR: Error while writing to process output stream!");
                ex.printStackTrace(System.err);
            }
        }
        close();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (writer != null) {
            try {
                writer.write("/ban-ip " + playerName);
                writer.flush();
                close();
            } catch (IOException ex) {
                System.err.println("ERROR: Error while writing to process output stream!");
                ex.printStackTrace(System.err);
            }
        }
        close();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (writer != null) {
            try {
                writer.write("/ban " + playerName);
                writer.flush();
                close();
            } catch (IOException ex) {
                System.err.println("ERROR: Error while writing to process output stream!");
                ex.printStackTrace(System.err);
            }
        }
        close();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (writer != null) {
            try {
                String itemID = JOptionPane.showInputDialog(null, "INFORMATION: Enter the desired item name or ID and the amount below.\n"
                        + "E.g.: 1 64", "Enter Item ID or Name", JOptionPane.INFORMATION_MESSAGE);
                writer.write("/give " + playerName + " " + itemID);
                writer.flush();
                close();
            } catch (IOException ex) {
                System.err.println("ERROR: Error while writing to process output stream!");
                ex.printStackTrace(System.err);
            }
        }
        close();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (writer != null) {
            try {
                writer.write("/deop " + playerName);
                writer.flush();
                close();
            } catch (IOException ex) {
                System.err.println("ERROR: Error while writing to process output stream!");
                ex.printStackTrace(System.err);
            }
        }
        close();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (writer != null) {
            try {
                String msg = JOptionPane.showInputDialog(null, "Please enter the message below.", "Enter Message to Send to " + playerName, JOptionPane.INFORMATION_MESSAGE);
                writer.write("/msg " + playerName + " " + msg);
                writer.flush();
                close();
            } catch (IOException ex) {
                System.err.println("ERROR: Error while writing to process output stream!");
                ex.printStackTrace(System.err);
            }
        }
        close();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (writer != null) {
            try {
                writer.write("/whitelist " + WhiteListDialog.showOptionDialog() + " " + playerName);
                writer.flush();
                close();
            } catch (IOException ex) {
                System.err.println("ERROR: Error while writing to process output stream!");
                ex.printStackTrace(System.err);
            }
        }
        close();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void close() {
        setVisible(false);
        instances--;
        this.dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    // End of variables declaration//GEN-END:variables
}
