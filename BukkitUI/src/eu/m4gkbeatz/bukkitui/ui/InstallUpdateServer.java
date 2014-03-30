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

package eu.m4gkbeatz.bukkitui.ui;

import java.net.*;
import java.util.*;
import javax.swing.*;
import java.io.*;

import eu.m4gkbeatz.bukkitui.html.HTMLParser;
import eu.m4gkbeatz.bukkitui.settings.SettingsManager;


/**
 *
 * @author beatsleigher
 */
public class InstallUpdateServer extends javax.swing.JFrame {
    
    BukkitUI bukkitUI = null;
    SettingsManager settings = null;
    HashMap<String, URL> linkMap = null;

    /**
     * Creates new form InstallUpdateServer
     * @param bukkitUI
     * @param settings
     */
    public InstallUpdateServer(BukkitUI bukkitUI, SettingsManager settings) {
        initComponents();
        this.setIconImage(new ImageIcon(this.getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/installing_updates-32.png")).getImage());
        this.bukkitUI = bukkitUI;
        this.settings = settings;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("BukkitUI | Install/Update Server");

        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Install Server", jPanel1);

        jProgressBar1.setString("");
        jProgressBar1.setStringPainted(true);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new Thread() {
            @Override
            public void run() {
                try {
                    jProgressBar1.setIndeterminate(true);
                    jLabel1.setText("Parsing data. Please wait...");
                    linkMap = new HTMLParser(new URL("http://dl.bukkit.org/downloads/bukkit/")).parseFile("title=\"Download ", ".jar");
                    DefaultListModel model = new DefaultListModel();
                    
                    for (String str : linkMap.keySet()) {
                        model.addElement(str);
                    }
                    model.trimToSize();
                    
                    jList1.setModel(model);
                    jProgressBar1.setIndeterminate(false);
                    jLabel1.setText("");
                } catch (Exception ex) {
                    System.err.println("ERROR: Error while parsing data from http://dl.bukkit.org/downloads/bukkit/!\n" + ex.toString());
                    ex.printStackTrace(System.err);
                    JOptionPane.showMessageDialog(null, "ERROR: Error while parsing data!\n"
                            + ex.toString() + "\n"
                            + "The stack trace was printed to the console.", "Error While Parsing Data", JOptionPane.ERROR_MESSAGE);
                }
                interrupt();
            }
        }.start();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        new Thread() {
            @Override
            @SuppressWarnings("FinalizeCalledExplicitly")
            public void run() {
                jLabel1.setText("Downloading server...");
                jProgressBar1.setIndeterminate(true);
                try {
                    if (settings.getCraftbukkit().exists())
                        settings.getCraftbukkit().delete();
                    BufferedInputStream input = new BufferedInputStream(linkMap.get(jList1.getSelectedValue().toString()).openStream());
                    FileOutputStream output = new FileOutputStream(settings.getServerDir().getAbsolutePath() + "/craftbukkit.jar");
                    System.out.println("Downloading new server to: " + settings.getServerDir().getAbsolutePath() + "/craftbukkit.jar");
                    if (bukkitUI.serverState != BukkitUI.ServerState.OFFLINE)
                        bukkitUI.stopServerBtn.doClick();
                    final byte[] data = new byte[1024];
                    int count = 0;
                    while ((count = input.read(data, 0, 1024)) != -1) {
                        output.write(data, 0, count);
                        System.out.println("Count: " + count);
                    }
                    input.close();
                    output.close();
                    jLabel1.setText("Installing server...");
                    settings.setCraftbukkit(new File(settings.getServerDir().getAbsolutePath() + "/craftbukkit.jar")); 
                    jLabel1.setText("");
                    jProgressBar1.setIndeterminate(false);
                    int result = JOptionPane.showConfirmDialog(null, "INFORMATION: The server was successfully installed!\n"
                            + "Do you want to start it now?", "Start Server?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (result == JOptionPane.YES_OPTION)
                        bukkitUI.startServerBtn.doClick(); 
                } catch (Exception ex) {
                    System.err.println("ERROR: Error while downloading and installing the server!");
                    ex.printStackTrace(System.err);
                    JOptionPane.showMessageDialog(null, "ERROR: Error while downloading and installing the server!\n"
                            + ex.toString() + "\n"
                            + "The stack trace was printed to the console...", "Error Installing Server!", JOptionPane.ERROR_MESSAGE);
                }
                interrupt();
            }
        }.start();
    }//GEN-LAST:event_jList1ValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}