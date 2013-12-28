/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.team.m4gkbeatz.BeatBukkitUI.settings;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.*;

/**
 *
 * @author Simon
 */
public class SettingsUI extends javax.swing.JFrame {
    
    //<editor-fold defaultstate="collapsed" desc="Variables">
    File basicSettings;
    File serverSettings;
    File advancedSettings;
    BufferedReader reader = null;
    BufferedWriter writer = null;
    //</editor-fold>

    /**
     * Creates new form SettingsUI
     */
    public SettingsUI() {
        System.out.println("SETTINGS * Initializing components...");
        initComponents();
        System.out.println("SETTINGS * Setting frame icon...");
        this.setIconImage(new ImageIcon(this.getClass().getResource("/eu/team/m4gkbeatz/BeatBukkitUI/resources/misc.png")).getImage());
        System.out.append("SETTINGS * Setting frame location (Centre of screen)");
        this.setLocationRelativeTo(null);
        try {
            System.out.println("SETTINGS * Loading program settings...");
            loadSettings();
        } catch (IOException ex) {
            System.out.println("ERROR: Error while loading program settings!");
            System.out.println("ERROR: " + ex);
            JOptionPane.showMessageDialog(null, "ERROR: Error while loading settings!\n" + ex + "\nPlease report this error to the developer/s!", "Error Loading Settings!", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(null, "WARNING: This program will exit in 10 seconds of dismissing this message prompt.", "Program will exit in t- 10", JOptionPane.WARNING_MESSAGE);
            System.out.println("Program exiting in 10 seconds....");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex1) {
                Logger.getLogger(SettingsUI.class.getName()).log(Level.SEVERE, null, ex1);
            }
            System.out.println("Program exiting in 5 seconds...");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException ex1) {
                Logger.getLogger(SettingsUI.class.getName()).log(Level.SEVERE, null, ex1);
            }
            System.out.println("Program exiting in 1 second...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex1) {
                Logger.getLogger(SettingsUI.class.getName()).log(Level.SEVERE, null, ex1);
            }
            System.out.println("Program is exiting now!");
            System.exit(0);
        }
    }
    
    /**
     * Loads settings from all different settings files ending with .cbs (CreepyBeast Servers).
     * This method is called from within the constructor and does need not to be called afterwards.
     */
    private void loadSettings() throws IOException {
        System.out.println("SETTINGS * Declaring files...");
        this.basicSettings = new File("BBUI_settings.cbs");
        this.serverSettings= new File("BBUI_serverSettings.cbs");
        this.advancedSettings = new File("BBUI_advancedSettings.cbs");
        String line;
        //<editor-fold defaultstate="collapsed" desc="Basic Program Settings">
        System.out.println("SETTINGS * Declaring file reader/s...");
        reader = new BufferedReader(new FileReader(basicSettings));
        System.out.println("SETTINGS * Reading file 1...");
        while ((line = reader.readLine()) != null) {
            //
            if (line.startsWith("useDefaults=")) {
                if (line.endsWith("true"))  {useDefaults(); break; } else {
                    System.out.println("Using custom settings. (Does not apply to server settings)");
                 }
            }
            //
            if (line.startsWith("frameLaf=")) {
                if (line.endsWith("useNimbus")) jComboBox1.setSelectedIndex(0);
                if (line.endsWith("useMetal")) jComboBox1.setSelectedIndex(1);
                if (line.endsWith("useNativeLook")) jComboBox1.setSelectedIndex(2); else {
                    System.out.println("SETTINGS * LAF Selection: Couldn't parse input. Did the user edit the file? Using default preference.");
                    jComboBox1.setSelectedIndex(0);
                }
            }
            //
            // Theme settings
        }
        reader.close();
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Server settings">
        System.out.println("SETTINGS * Reading file 2...");
        reader = new BufferedReader(new FileReader(serverSettings));
        while ((line = reader.readLine()) != null) {
           if (line.startsWith("bootOnStart=")) {
               if (line.endsWith("true")) {
                   jCheckBox2.setSelected(true);
               } else {
                   jCheckBox2.setSelected(false);
               }
           }
           if (line.startsWith("serverType=")) {
               if (line.endsWith("vanilla")) jComboBox3.setSelectedIndex(0);
               if (line.endsWith("tekkitClassic")) jComboBox3.setSelectedIndex(1);
               if (line.endsWith("tekkitLite")) jComboBox3.setSelectedIndex(2);
               if (line.endsWith("tekkitMain")) jComboBox3.setSelectedIndex(3);
               if (line.endsWith("hexxit")) jComboBox3.setSelectedIndex(4);
               if (line.endsWith("voltz")) jComboBox3.setSelectedIndex(5);
               if (line.endsWith("bigDig")) jComboBox3.setSelectedIndex(6);
               if (line.endsWith("ftbUnleashed")) jComboBox3.setSelectedIndex(7);
               if (line.endsWith("ftbUnhinged")) jComboBox3.setSelectedIndex(8);
               if (line.endsWith("direwolf")) jComboBox3.setSelectedIndex(9);
               if (line.endsWith("ftbUltimate")) jComboBox3.setSelectedIndex(10);
               if (line.endsWith("mindcrack")) jComboBox3.setSelectedIndex(11);
               if (line.endsWith("yogscraft")) jComboBox3.setSelectedIndex(12);
               if (line.endsWith("other")) jComboBox3.setSelectedIndex(13); else {
                   System.out.println("SETTINGS * ServerType: Could not parse input. Did the user edit the file? Using default value.");
                   jComboBox3.setSelectedIndex(0);
               }
           }
           if (line.startsWith("fileName=")) {
               if (line.endsWith(".jar")) {
                   String[] _line = line.split("=");
                   jTextField1.setText(_line[1]);
               }
           }
        }
        reader.close();
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Advanced Settings">
        System.out.println("SETTINGS * Reading file 2...");
        reader = new BufferedReader(new FileReader(advancedSettings));
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("uiLevel=")) {
                if (line.endsWith("basic")) jComboBox4.setSelectedIndex(0);
                if (line.endsWith("advanced")) jComboBox4.setSelectedIndex(1); else {
                    System.out.println("SETTINGS * UI Level: Could not parse input. Did the user edit the file? Using default value.");
                    jComboBox4.setSelectedIndex(0);
                }
            }
        }
        reader.close();
        //</editor-fold>
    }
    
    /**
     * Sets all settings to default values (Does not impact server settings).
     */
    private void useDefaults() {
        System.out.println("SETTINGS * Using default settings... (Does not apply to server settings)");
        jCheckBox1.setSelected(true);
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
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
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("BeatBukkitUI Settings");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Defaults"));

        jCheckBox1.setText("Use default settings (Overrides any other settings)");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox1)
                .addContainerGap(9, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jCheckBox1)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Look and Feel"));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nimbus (Recommended and Default)", "Metal (Standard Java LaF)", "Native UI (Mimics OS's design)" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Theme"));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "KDE (Default)", "OneBit", "Ubuntu", "Metro", "Android" }));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(255, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Basic Settings", jPanel2);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Startup"));

        jCheckBox2.setText("Start server on program-boot");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jCheckBox2)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jCheckBox2)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Server Details"));

        jLabel1.setText("Server Type:");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vanilla Minecraft", "Tekkit Classic (3.1.2)", "Tekkit Lite (0.6.5)", "Tekkit (1.1.10)", "Hexxit (1.0.10)", "Voltz (2.0.4)", "Big Dig (1.3.9)", "Feed the Beast: Unleashed (1.1.2)", "Feed the Beast: Unhinged (n/a)", "Direwolf (n/a)", "Feed the Beast: Ultimate (n/a)", "Mindcrack (n/a)", "Yogscraft (1.0.0)", "Other" }));

        jLabel2.setText("File Name:");

        jTextField1.setText("craftbukkit.jar");

        jLabel3.setText("Plugins:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Filename"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel4.setText("Mods:");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Filename"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1)
                            .addComponent(jComboBox3, 0, 275, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Server Settings", jPanel3);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("UI Selection"));

        jLabel5.setText("UI to load:");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Basic UI", "Advanced UI (Advanced Users Only!)" }));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel5)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(542, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(335, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Advanced Settings", jPanel4);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Info"));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/team/m4gkbeatz/BeatBukkitUI/resources/messagebox_info.png"))); // NOI18N
        jButton1.setText("About");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/team/m4gkbeatz/BeatBukkitUI/resources/1388116531_Copyright.png"))); // NOI18N
        jButton2.setText("Licenses");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Credits (See Licenses for more information)"));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"NuvoX (KDE-Theme)", null},
                {"OneBit", null},
                {"Windows Metro Icons", null},
                {"Humanity", null},
                {"Oxygen", null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Icon Packs", "Software"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Other", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Event gets fired when JFrame form is closing.
     * Saves all settings to respective files and proceeds to exit window.
     * @param evt 
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        System.out.println("SETTINGS * Preparing to save settings...");
        System.out.println("SETTINGS * Declaring files...");
        this.basicSettings = new File("BBUI_settings.cbs");
        this.serverSettings= new File("BBUI_serverSettings.cbs");
        this.advancedSettings = new File("BBUI_advancedSettings.cbs");
        System.out.println("SETTINGS * Gathering information...");
        //<editor-fold defaultstate="collapsed" desc="Basic Server Settings">
        boolean useDefaults = jCheckBox1.isSelected();
        String frameLaf = "";
        if (jComboBox1.getSelectedIndex() == 0) frameLaf = "useNimbus";
        if (jComboBox1.getSelectedIndex() == 1) frameLaf = "useMetal";
        if (jComboBox1.getSelectedIndex() == 2) frameLaf = "useNativeLook";
        // Theme settings
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Server Settings">
        boolean bootOnStart = jCheckBox2.isSelected();
        String serverType = "";
        if (jComboBox2.getSelectedIndex() == 0) serverType = "vanilla";
        if (jComboBox2.getSelectedIndex() == 1) serverType = "tekkitClassic";
        if (jComboBox2.getSelectedIndex() == 2) serverType = "tekkitLite";
        if (jComboBox2.getSelectedIndex() == 3) serverType = "tekkitMain";
        if (jComboBox2.getSelectedIndex() == 4) serverType = "hexxit";
        if (jComboBox2.getSelectedIndex() == 5) serverType = "voltz";
        if (jComboBox2.getSelectedIndex() == 6) serverType = "bigDig";
        if (jComboBox2.getSelectedIndex() == 7) serverType = "ftbUnleashed";
        if (jComboBox2.getSelectedIndex() == 8) serverType = "ftbUnhinged";
        if (jComboBox2.getSelectedIndex() == 9) serverType = "direwolf";
        if (jComboBox2.getSelectedIndex() == 10) serverType = "ftbUltimate";
        if (jComboBox2.getSelectedIndex() == 11) serverType = "mindcrack";
        if (jComboBox2.getSelectedIndex() == 12) serverType = "yogscraft";
        if (jComboBox2.getSelectedIndex() == 13) serverType = "other";
        String fileName = "";
        if (jTextField1.getText().endsWith(".jar")) fileName = jTextField1.getText();
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Advanced Settings">
        String uiLevel = "";
        if (jComboBox4.getSelectedIndex() == 0) uiLevel = "basic"; else uiLevel = "advanced";
        //</editor-fold>
        System.out.println("SETTINGS * All information was gathered. Saving files...");
        try {
        saveSettings(useDefaults, frameLaf, "kde", bootOnStart, serverType, fileName, uiLevel);
        } catch (IOException ex) {
            System.err.println("ERROR: Error while saving settings!\n" + ex + "\nPlease report this error to the developer!");
            JOptionPane.showMessageDialog(null, "ERROR: Error while saving settings:\n" + ex + "\nPlease report this error to the developer!", "Error Saving Settings", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_formWindowClosing

    /**
     * Gets called by formWindowClosing event when JFrame form is closing.
     * Saves all settings into the respective files.
     */
    private void saveSettings(boolean useDefaults, String frameLaf, String theme, boolean bootOnStart, String serverType, String fileName, String uiLevel) throws IOException {
        System.out.println("SETTINGS * Please wait. Retrieving files...");
        this.basicSettings = new File("BBUI_settings.cbs");
        this.serverSettings= new File("BBUI_serverSettings.cbs");
        this.advancedSettings = new File("BBUI_advancedSettings.cbs");
        // Write file 01
        System.out.print("SETTINGS * Declaring writers and settings...");
        writer = new BufferedWriter(new FileWriter(basicSettings));
        String settings = 
                "### BeatBukkitUI Settings File ###\n"
                + "### Please do not mess with these settings if you do not know what you are doing.\n"
                + "### These settings are provided as-is and with no warranty.\n"
                + "### Everything you do here (in- or outside the aplication, you do on your own behalf!\n"
                + "### Use with care.\n\n"
                + //
                "### Leave this set to false if you're happy the way everything is working!\n"
                + "useDefaults=" + useDefaults + "\n\n"
                + //
                "### Design of the UI ###\n"
                + "# Choose from:\n"
                + "# useNimbus\n"
                + "# useMetal\n"
                + "# useNativeLook\n"
                + "###                     ###\n"
                + "frameLaf=" + frameLaf + "\n\n"
                + //
                "### Advanced users only!\n"
                + "javaPath=winDir/system32/java.exe\n\n"
                + //
                "### Change the program's theme ###\n"
                + "# Choose from:\n"
                + "# kde\n"
                + "# onebit\n"
                + "# ubuntu\n"
                + "# metro\n"
                + "# android\n"
                + "###              ###\n"
                + "theme=" + theme + " ## Please ignore this setting for now. It's only here so I don't forget it.";
        System.out.println("SETTINGS * Writing file: " + basicSettings.toString() + ".");
        writer.write(settings);
        writer.flush();
        // Write file 02
        settings = 
                "### BeatBukkitUI Server Settings ###\n"
                + "# This file contains all the settings used by the program in order to exit the server process properly.\n"
                + "# Please only edit the settings in this file if you know what you are doing.\n"
                + "# If I, Beatsleigher, were a bad developer, I'd just let the program crash if you input a wrong setting.\n"
                + "# Luckily for you, however, I'm a good developer and will just set the default values in case you mess anything up.\n"
                + "# This, however, I cannot guarantee that it'll be the same for the Bukkit server you will be configuring at one moment in time, or another.\n"
                + "# at one point in time, depending on how well this program goes, maybe I'll pre-read and configure any values that are wrong,\n"
                + "# before the server is started. Who knows? Maybe it's happened already!\n"
                + "###                               ###\n\n"
                //
                + "# Determines whether the server should be started on program-load.\n"
                + "# The default value is false so you can still configure things before (for example) initial start.\n"
                + "bootOnStart=" + bootOnStart + "\n\n"
                //
                + "## Server type. ##\n"
                + "# This is where the server's type is set.                            #\n"
                + "# You can choose between following server types:                     #\n"
                + "# vanilla                                                            #\n"
                + "# tekkitClassic                                                      #\n"
                + "# tekkitLite                                                         #\n"
                + "# tekkitMain                                                         #\n"
                + "# hexxit                                                             #\n"
                + "# voltz                                                              #\n"
                + "# bigDig                                                             #\n"
                + "# ftbUnleashed                                                       #\n"
                + "# ftbUnhinged                                                        #\n"
                + "# direwolf                                                           #\n"
                + "# ftbUltimate                                                        #\n"
                + "# mindcrack                                                          #\n"
                + "# yogscraft                                                          #\n"
                + "# other                                                              #\n"
                + "## /Server Type ##\n"
                + "serverType=" + serverType + "\n\n"
                //
                + "# This determines the server's filename. For example: craftbukkit.jar\n"
                + "# Please make sure BeatBukkitUI is in the SAME folder as your server filename!\n"
                + "fileName=" + fileName + "\n\n"
                //
                + "### EOF ###";
        System.out.println("SETTINGS * Writing file: " + serverSettings.toString() + ".");
        writer.write(settings);
        writer.flush();
        // Write file 03
        writer = new BufferedWriter(new FileWriter(advancedSettings));
        settings = 
                "### BeatBukkitUI Advanced Properties ###\n"
                + "# Only change these values if you know what you're doing!\n"
                + "# Some of these settings may be experimental and may break the program,\n"
                + "# or worse, your server!\n\n"
                //
                + "# Defines the UI to be loaded.\n"
                + "uiLevel=basic\n\n"
                //
                + "### EOF ###";
        writer.write(settings);
        System.out.println("SETTINGS * Writing file: " + advancedSettings.toString() + ".");
        writer.close();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
