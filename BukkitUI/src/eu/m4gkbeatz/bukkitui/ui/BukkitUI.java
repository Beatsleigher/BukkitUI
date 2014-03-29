/*
 * Copyright (C) 2014 beatsleigher.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package eu.m4gkbeatz.bukkitui.ui;

import javax.swing.*;
import java.io.*;
import java.awt.Point;
import java.awt.Component;
import java.util.*;

import eu.m4gkbeatz.bukkitui.logging.Logger;
import eu.m4gkbeatz.bukkitui.settings.*;
import eu.m4gkbeatz.bukkitui.io.*;
import eu.m4gkbeatz.bukkitui.server.players.Player;

/**
 *
 * @author beatsleigher
 */
@SuppressWarnings({"TooBroadCatch", "ConvertToTryWithResources", "UnusedAssignment", "UseSpecificCatch", "SleepWhileInLoop"})
public class BukkitUI extends javax.swing.JFrame {

    //============ Constant Values ============\\
    private final ImageIcon ONLINE = new ImageIcon(this.getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/circle_green_24.png"));
    private final ImageIcon OFFLINE = new ImageIcon(this.getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/circle_red_24.png"));
    private final ImageIcon WORKING = new ImageIcon(this.getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/circle_orange_24.png"));

    /**
     * Enumeration containing possible server states.
     */
    enum ServerState {

        OFFLINE, ONLINE, STARTING, INSTALLING, REBOOTING
    }

    Map<String, ImageIcon> imageMap = null;
    Point origin = this.getLocation();
    SettingsManager settings = null;
    Logger log = null;
    //=============================================\\
    File serverDir = null;
    File craftbukkit = null;
    ProcessBuilder process = null;
    Process pr = null;
    BufferedReader processReader = null;
    BufferedWriter processWriter = null;
    List<String> processArgs = new ArrayList();
    ServerState serverState = ServerState.OFFLINE;
    List<Player> listOfPlayers = new ArrayList<>();

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * Creates new JFrame BukkitUI.
     *
     * @param settings
     * @param log
     */
    public BukkitUI(SettingsManager settings, Logger log) {
        this.settings = settings;
        this.log = log;
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(this.getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/icon.png")).getImage());
        initComponents();
        backgroundImage.setIcon(new ImageIcon(this.getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/design_layout/" + settings.getLayout() + ".png")));
        applySettings();
        loadServerInfo();
    }
    //</editor-fold>

    private void applySettings() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (Object obj : BukkitUI_Layout.values()) {
            model.addElement(obj);
        }
        jComboBox1.setModel(model);
        switch (settings.getLayout()) {
            case "BukkitUI":
                jComboBox1.setSelectedIndex(0);
                break;
            case "BukkitUI_blue":
                jComboBox1.setSelectedIndex(1);
                break;
            case "BukkitUI_green":
                jComboBox1.setSelectedIndex(2);
                break;
            case "BukkitUI_orange":
                jComboBox1.setSelectedIndex(3);
                break;
        }

        craftbukkitLocation.setText(settings.getCraftbukkit().getAbsolutePath());

        startServerAutomaticallyCheckbox.setSelected(settings.startServerAutomatically());

        autoDetectNewPluginsCheckbox.setSelected(settings.autoDetectNewPlugins());

        checkForUpdatesCheckbox.setSelected(settings.checkForUpdates());
    }

    private void loadServerInfo() {
        //========== Server Properties ==========\\
        File fileToRead = null;
        BufferedReader reader = null;
        String line = "";
        String[] array = null;
        //<editor-fold defaultstate="collapsed" desc="      ">
        try {
            fileToRead = new File(settings.getServerDir() + "/server.properties");
            if (fileToRead.exists()) {
                reader = new BufferedReader(new FileReader(fileToRead));
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("allow-nether=")) {
                        array = line.split("=");
                        allowNether.setSelected(Boolean.valueOf(array[1]));
                    }
                    if (line.startsWith("level-name=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            levelName.setText(array[1]);
                        }
                    }
                    if (line.startsWith("enable-query=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            enableQuery.setSelected(Boolean.valueOf(array[1]));
                        }
                    }
                    if (line.startsWith("allow-flight=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            allowFlight.setSelected(Boolean.valueOf(array[1]));
                        }
                    }
                    if (line.startsWith("server-port=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            serverPort.setText(array[1]);
                        }
                    }
                    if (line.startsWith("level-type=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            levelType.setSelectedItem(array[1]);
                        }
                    }
                    if (line.startsWith("allow-rcon=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            enableRcon.setSelected(Boolean.valueOf(array[1]));
                        }
                    }
                    if (line.startsWith("level-seed=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            levelSeed.setText(array[1]);
                        }
                    }
                    if (line.startsWith("server-ip=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            serverIP.setText(array[1]);
                        }
                    }
                    if (line.startsWith("max-build-hight=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            maxBuildHight.setText(array[1]);
                        }
                    }
                    if (line.startsWith("spawn-npcs=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            spawnNPCs.setSelected(Boolean.valueOf(array[1]));
                        }
                    }
                    if (line.startsWith("white-list=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            whiteList.setSelected(Boolean.valueOf(array[1]));
                        }
                    }
                    if (line.startsWith("spawn-animals=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            spawnAnimals.setSelected(Boolean.valueOf(array[1]));
                        }
                    }
                    if (line.startsWith("online-mode=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            onlineMode.setSelected(Boolean.valueOf(array[1]));
                        }
                    }
                    if (line.startsWith("pvp=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            enablePVP.setSelected(Boolean.valueOf(array[1]));
                        }
                    }
                    if (line.startsWith("difficulty=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            difficulty.setText(array[1]);
                        }
                    }
                    if (line.startsWith("gamemode=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            gameMode.setSelectedIndex(Integer.valueOf(array[1]));
                        }
                    }
                    if (line.startsWith("max-players=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            maxPlayers.setText(array[1]);
                        }
                    }
                    if (line.startsWith("spawn-monsters=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            spawnMonsters.setSelected(Boolean.valueOf(array[1]));
                        }
                    }
                    if (line.startsWith("generate-structures=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            generateStructures.setSelected(Boolean.valueOf(array[1]));
                        }
                    }
                    if (line.startsWith("view-distance=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            viewDistance.setText(array[1]);
                        }
                    }
                    if (line.startsWith("motd=")) {
                        array = line.split("=");
                        if (array.length != 1) {
                            motd.setText(array[1]);
                        }
                    }

                }
                reader.close();
            } else {
                buildServerProps();
            }
        } catch (Exception ex) {
            System.err.println("ERROR: Error while reading server.properties!\n" + ex.toString());
            ex.printStackTrace(System.err);
        }
        //</editor-fold>
        //========== Bukkit Properties ==========\\
        //<editor-fold defaultstate="collapsed" desc="      ">
        try {
            fileToRead = new File(settings.getServerDir() + "bukkit.yml");
            if (fileToRead.exists()) {
                reader = new BufferedReader(new FileReader(fileToRead));
                while ((line = reader.readLine()) != null) {
                    //===== Settings =====\\
                    if (line.contains("allow-end:")) {
                        array = line.split(":");
                        allowEnd.setSelected(Boolean.valueOf(array[1]));
                    }
                    if (line.contains("warn-on-overload:")) {
                        array = line.split(":");
                        warnOnOverload.setSelected(Boolean.valueOf(array[1]));
                    }
                    if (line.contains("permissions-file:")) {
                        array = line.split(":");
                        permsFileTxtBox.setText(array[1]);
                    }
                    if (line.contains("update-folder:")) {
                        array = line.split(":");
                        updateFolderTxtBox.setText(array[1]);
                    }
                    if (line.contains("ping-packet-limit:")) {
                        array = line.split(":");
                        pingLimitTxtBox.setText(array[1]);
                    }
                    if (line.contains("use-exact-login-location:")) {
                        array = line.split(":");
                        useExactLocation.setSelected(Boolean.valueOf(array[1]));
                    }
                    if (line.contains("plugin-profiling:")) {
                        array = line.split(":");
                        pluginProfiling.setSelected(Boolean.valueOf(array[1]));
                    }
                    if (line.contains("connection-throttle:")) {
                        array = line.split(":");
                        connectionThrottleTxtBox.setText(array[1]);
                    }
                    if (line.contains("query-plugins:")) {
                        array = line.split(":");
                        queryPlugins.setSelected(Boolean.valueOf(array[1]));
                    }
                    if (line.contains("deprecated-verbose:")) {
                        array = line.split(":");
                        deprecatedVerbose.setSelectedItem(array[1]);
                    }
                    if (line.contains("shutdown-message:")) {
                        array = line.split(":");
                        shutdownMessageTxtBox.setText(array[1]);
                    }
                    //===== Spawn Limits =====\\
                    if (line.contains("monsters:")) {
                        array = line.split(":");
                        monsterLimitTxtBox.setText(array[1]);
                    }
                    if (line.contains("animals:")) {
                        array = line.split(":");
                        animalLimitTxtBox.setText(array[1]);
                    }
                    if (line.contains("water-animals:")) {
                        array = line.split(":");
                        waterAnimalLimitTxtBox.setText(array[1]);
                    }
                    if (line.contains("ambient:")) {
                        array = line.split(":");
                        ambientLimitTxtBox.setText(array[1]);
                    }
                    //===== Chunk GC =====\\
                    if (line.contains("period-in-ticks:")) {
                        array = line.split(":");
                        gcPeriodInTicksTxtBox.setText(array[1]);
                    }
                    if (line.contains("load-treshold:")) {
                        array = line.split(":");
                        loadThresholdTxtBox.setText(array[1]);
                    }
                    //===== Ticks Per... =====\\
                    if (line.contains("animal-spawns:")) {
                        array = line.split(":");
                        ticksPerAnimalSpawnTxtBox.setText(array[1]);
                    }
                    if (line.contains("monster-spawns:")) {
                        array = line.split(":");
                        ticksPerMonsterSpawnTxtBox.setText(array[1]);
                    }
                    if (line.contains("autosave:")) {
                        array = line.split(":");
                        ticksPerAutoSave.setText(array[1]);
                    }
                    //===== Auto Updaters =====\\
                    if (line.contains("enabled:")) {
                        array = line.split(":");
                        autoUpdaterEnabled.setSelected(Boolean.valueOf(array[1]));
                    }
                    if (line.contains("host:")) {
                        array = line.split(":");
                        autoUpdaterHost.setText(array[1]);
                    }

                }
                reader.close();
            } else {
                buildBukkitYml(fileToRead);
            }
        } catch (Exception ex) {
            System.err.println("ERROR: Error while reading server.properties!\n" + ex.toString());
            ex.printStackTrace(System.err);
        }
        //</editor-fold>
        //===== Banned IPs =====\\
        try {
            fileToRead = new File(settings.getServerDir() + "/banned-ips.txt");
            if (fileToRead.exists()) {
                DefaultListModel model = new DefaultListModel();
                reader = new BufferedReader(new FileReader(fileToRead));
                while ((line = reader.readLine()) != null) {
                    model.addElement(line);
                }
                reader.close();
                bannedIPList.setModel(model);
            }
        } catch (Exception ex) {
            System.err.println("ERROR: Error while reading banned IPs!\n" + ex.toString());
            ex.printStackTrace(System.err);
        }
        //===== Banned Players =====\\
        try {
            fileToRead = new File(settings.getServerDir() + "/banned-players.txt");
            if (fileToRead.exists()) {
                DefaultListModel model = new DefaultListModel();
                reader = new BufferedReader(new FileReader(fileToRead));
                while ((line = reader.readLine()) != null) {
                    model.addElement(line);
                }
                reader.close();
                bannedPlayersList.setModel(model);
            }
        } catch (Exception ex) {
            System.err.println("ERROR: Error while reading banned players!\n" + ex.toString());
            ex.printStackTrace(System.err);
        }
        //===== Operators =====\\
        try {
            fileToRead = new File(settings.getServerDir() + "/ops.txt");
            if (fileToRead.exists()) {
                DefaultListModel model = new DefaultListModel();
                reader = new BufferedReader(new FileReader(fileToRead));
                while ((line = reader.readLine()) != null) {
                    model.addElement(line);
                }
                reader.close();
                operatorsList.setModel(model);
            }
        } catch (Exception ex) {
            System.err.println("ERROR: Error while reading OPs!\n" + ex.toString());
            ex.printStackTrace(System.err);
        }
        //===== Whitelist =====\\
        try {
            fileToRead = new File(settings.getServerDir() + "/white-list.txt");
            if (fileToRead.exists()) {
                DefaultListModel model = new DefaultListModel();
                reader = new BufferedReader(new FileReader(fileToRead));
                while ((line = reader.readLine()) != null) {
                    model.addElement(line);
                }
                reader.close();
                whitelist.setModel(model);
            }
        } catch (Exception ex) {
            System.err.println("ERROR: Error while reading whitelist!\n" + ex.toString());
            ex.printStackTrace(System.err);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Some Generated Code. Nothing too Important...">
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        mainPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        startServerBtn = new javax.swing.JButton();
        stopServerBtn = new javax.swing.JButton();
        restartServerBtn = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        updateServerBtn = new javax.swing.JButton();
        deleteServerBtn = new javax.swing.JButton();
        serverPropertiesBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cpuUsageProgressBar = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        maxMemLabel = new javax.swing.JLabel();
        usedMemProgressBar = new javax.swing.JProgressBar();
        totalMemLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        playerList = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        consoleLog = new javax.swing.JTextArea();
        jPanel9 = new javax.swing.JPanel();
        executeCmdTxtField = new javax.swing.JTextField();
        executeCmdBtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        allowNether = new javax.swing.JCheckBox();
        enableQuery = new javax.swing.JCheckBox();
        allowFlight = new javax.swing.JCheckBox();
        enableRcon = new javax.swing.JCheckBox();
        spawnNPCs = new javax.swing.JCheckBox();
        whiteList = new javax.swing.JCheckBox();
        spawnAnimals = new javax.swing.JCheckBox();
        onlineMode = new javax.swing.JCheckBox();
        enablePVP = new javax.swing.JCheckBox();
        spawnMonsters = new javax.swing.JCheckBox();
        generateStructures = new javax.swing.JCheckBox();
        jPanel21 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        levelName = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        serverPort = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        levelType = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        serverIP = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        maxBuildHight = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        difficulty = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        gameMode = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        maxPlayers = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        viewDistance = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        motd = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        levelSeed = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        bannedIPList = new javax.swing.JList();
        jTextField1 = new javax.swing.JTextField();
        addBannedIP = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        addBannedPlayer = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        bannedPlayersList = new javax.swing.JList();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        operatorsList = new javax.swing.JList();
        jTextField3 = new javax.swing.JTextField();
        addOperator = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        whitelist = new javax.swing.JList();
        jTextField4 = new javax.swing.JTextField();
        addWhiteList = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        allowEnd = new javax.swing.JCheckBox();
        warnOnOverload = new javax.swing.JCheckBox();
        useExactLocation = new javax.swing.JCheckBox();
        jLabel25 = new javax.swing.JLabel();
        permsFileTxtBox = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        updateFolderTxtBox = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        pingLimitTxtBox = new javax.swing.JTextField();
        pluginProfiling = new javax.swing.JCheckBox();
        jLabel28 = new javax.swing.JLabel();
        connectionThrottleTxtBox = new javax.swing.JTextField();
        queryPlugins = new javax.swing.JCheckBox();
        jLabel29 = new javax.swing.JLabel();
        deprecatedVerbose = new javax.swing.JComboBox();
        jLabel30 = new javax.swing.JLabel();
        shutdownMessageTxtBox = new javax.swing.JTextField();
        jPanel24 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        monsterLimitTxtBox = new javax.swing.JTextField();
        animalLimitTxtBox = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        waterAnimalLimitTxtBox = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        ambientLimitTxtBox = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        gcPeriodInTicksTxtBox = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        loadThresholdTxtBox = new javax.swing.JTextField();
        jPanel26 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        ticksPerAnimalSpawnTxtBox = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        ticksPerMonsterSpawnTxtBox = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        ticksPerAutoSave = new javax.swing.JTextField();
        jPanel27 = new javax.swing.JPanel();
        autoUpdaterEnabled = new javax.swing.JCheckBox();
        jLabel40 = new javax.swing.JLabel();
        autoUpdaterHost = new javax.swing.JTextField();
        saveBukkitYml = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel10 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        craftbukkitLocation = new javax.swing.JTextField();
        craftbukkitLocationBtn = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        startServerAutomaticallyCheckbox = new javax.swing.JCheckBox();
        autoDetectNewPluginsCheckbox = new javax.swing.JCheckBox();
        jPanel13 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        checkForUpdatesCheckbox = new javax.swing.JCheckBox();
        serverStatusLabel = new javax.swing.JLabel();
        closeBtn = new javax.swing.JButton();
        minimizeBtn = new javax.swing.JButton();
        serverRuntime = new javax.swing.JLabel();
        backgroundImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BukkitUI");
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setOpaque(false);
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setToolTipText("");

        mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mainPanel.setOpaque(false);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.setOpaque(false);

        startServerBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_controls/play-32.png"))); // NOI18N
        startServerBtn.setText("Start Server");
        startServerBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        startServerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startServerBtnActionPerformed(evt);
            }
        });

        stopServerBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_controls/stop-32.png"))); // NOI18N
        stopServerBtn.setText("Stop Server");
        stopServerBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        stopServerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopServerBtnActionPerformed(evt);
            }
        });

        restartServerBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_controls/synchronize-32.png"))); // NOI18N
        restartServerBtn.setText("Restart Server");
        restartServerBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        restartServerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restartServerBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(restartServerBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(stopServerBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(startServerBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(startServerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stopServerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(restartServerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setOpaque(false);

        updateServerBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/installing_updates-32.png"))); // NOI18N
        updateServerBtn.setText("Install/Update");
        updateServerBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        updateServerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateServerBtnActionPerformed(evt);
            }
        });

        deleteServerBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/delete-32.png"))); // NOI18N
        deleteServerBtn.setText("Delete");
        deleteServerBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        deleteServerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteServerBtnActionPerformed(evt);
            }
        });

        serverPropertiesBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/server_administration/settings2-32.png"))); // NOI18N
        serverPropertiesBtn.setText("Properties");
        serverPropertiesBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        serverPropertiesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serverPropertiesBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(updateServerBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteServerBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(serverPropertiesBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(updateServerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteServerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(serverPropertiesBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Basic Server Controls");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Server IO");

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setOpaque(false);

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("CPU Usage:");

        cpuUsageProgressBar.setStringPainted(true);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Memory:");

        maxMemLabel.setForeground(new java.awt.Color(255, 255, 255));
        maxMemLabel.setText("Max Memory: 0MB");

        usedMemProgressBar.setString("Used Memory: 0MB");
        usedMemProgressBar.setStringPainted(true);

        totalMemLabel.setForeground(new java.awt.Color(255, 255, 255));
        totalMemLabel.setText("Total Memory: 0MB");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(maxMemLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalMemLabel))
                    .addComponent(usedMemProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel1))
                .addGap(358, 358, 358))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(cpuUsageProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cpuUsageProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maxMemLabel)
                    .addComponent(totalMemLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usedMemProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Server Monitor");

        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Online Players");

        jPanel22.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel22.setOpaque(false);

        jScrollPane6.setViewportView(playerList);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("", new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/design_layout/main_tab.png")), mainPanel); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setOpaque(false);

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.setOpaque(false);

        consoleLog.setEditable(false);
        consoleLog.setBackground(new java.awt.Color(0, 101, 255));
        consoleLog.setColumns(20);
        consoleLog.setForeground(new java.awt.Color(255, 255, 255));
        consoleLog.setRows(5);
        consoleLog.setText("-- Not Running --");
        jScrollPane1.setViewportView(consoleLog);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.setOpaque(false);

        executeCmdBtn.setText("Execute");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(executeCmdTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(executeCmdBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(executeCmdTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(executeCmdBtn))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Console");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Controls");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("", new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/design_layout/console_tab.png")), jPanel2); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setOpaque(false);

        jPanel14.setOpaque(false);

        jPanel20.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel20.setOpaque(false);

        allowNether.setForeground(new java.awt.Color(255, 255, 255));
        allowNether.setSelected(true);
        allowNether.setText("Allow Nether");

        enableQuery.setForeground(new java.awt.Color(255, 255, 255));
        enableQuery.setText("Enable Query");

        allowFlight.setForeground(new java.awt.Color(255, 255, 255));
        allowFlight.setText("Allow Flight");

        enableRcon.setForeground(new java.awt.Color(255, 255, 255));
        enableRcon.setText("Enable RCON");

        spawnNPCs.setForeground(new java.awt.Color(255, 255, 255));
        spawnNPCs.setSelected(true);
        spawnNPCs.setText("Spawn NPCs");

        whiteList.setForeground(new java.awt.Color(255, 255, 255));
        whiteList.setText("White List");

        spawnAnimals.setForeground(new java.awt.Color(255, 255, 255));
        spawnAnimals.setSelected(true);
        spawnAnimals.setText("Spawn Animals");

        onlineMode.setForeground(new java.awt.Color(255, 255, 255));
        onlineMode.setSelected(true);
        onlineMode.setText("Online Mode");

        enablePVP.setForeground(new java.awt.Color(255, 255, 255));
        enablePVP.setSelected(true);
        enablePVP.setText("PVP");

        spawnMonsters.setForeground(new java.awt.Color(255, 255, 255));
        spawnMonsters.setSelected(true);
        spawnMonsters.setText("Spawn Monsters");

        generateStructures.setForeground(new java.awt.Color(255, 255, 255));
        generateStructures.setSelected(true);
        generateStructures.setText("Generate Structures");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(allowNether)
                    .addComponent(enableQuery)
                    .addComponent(allowFlight)
                    .addComponent(enableRcon)
                    .addComponent(spawnNPCs)
                    .addComponent(whiteList)
                    .addComponent(spawnAnimals)
                    .addComponent(onlineMode)
                    .addComponent(enablePVP))
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(generateStructures)
                    .addComponent(spawnMonsters))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(allowNether)
                    .addComponent(spawnMonsters))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enableQuery)
                    .addComponent(generateStructures))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(allowFlight)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(enableRcon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spawnNPCs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(whiteList)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spawnAnimals)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(onlineMode)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(enablePVP)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel21.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel21.setOpaque(false);

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Level Name:");

        levelName.setText("world");

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Server Port:");

        serverPort.setText("25565");

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Level Type:");

        levelType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DEFAULT", "FLAT", "LARGEBIOMES", "AMPLIFIED" }));

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Server IP:");

        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Max Build Hight:");

        maxBuildHight.setText("265");

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Difficulty:");

        difficulty.setText("1");

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Game Mode:");

        gameMode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SURVIVAL", "CREATIVE", "ADVENTURE" }));

        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Max Players:");

        maxPlayers.setText("20");

        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("View Distance:");

        viewDistance.setText("10");

        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Motto:");

        motd.setText("Powered by BukkitUI");

        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Level Seed:");

        levelSeed.setText("PoweredByBukkitUI");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23))
                .addGap(18, 18, 18)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(viewDistance)
                    .addComponent(maxPlayers)
                    .addComponent(gameMode, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(difficulty)
                    .addComponent(maxBuildHight)
                    .addComponent(serverIP)
                    .addComponent(levelType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(serverPort)
                    .addComponent(levelName)
                    .addComponent(motd, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                    .addComponent(levelSeed))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(levelName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(serverPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(levelType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(serverIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(levelSeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(maxBuildHight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(difficulty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(gameMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(maxPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(viewDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(motd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Server Properties", jPanel14);

        jPanel16.setOpaque(false);

        jScrollPane2.setViewportView(bannedIPList);

        addBannedIP.setText("Add...");
        addBannedIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBannedIPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addBannedIP)))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addBannedIP))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Banned IPs", jPanel16);

        jPanel17.setOpaque(false);

        addBannedPlayer.setText("Add...");
        addBannedPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBannedPlayerActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(bannedPlayersList);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jTextField2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addBannedPlayer)))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addBannedPlayer))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Banned Players", jPanel17);

        jPanel18.setOpaque(false);

        jScrollPane4.setViewportView(operatorsList);

        addOperator.setText("Add...");
        addOperator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOperatorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jTextField3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addOperator)))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addOperator))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Operators", jPanel18);

        jPanel19.setOpaque(false);

        jScrollPane5.setViewportView(whitelist);

        addWhiteList.setText("Add...");
        addWhiteList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addWhiteListActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jTextField4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addWhiteList)))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addWhiteList))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Whitelist", jPanel19);

        jPanel15.setOpaque(false);

        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder("Settings"));
        jPanel23.setOpaque(false);

        allowEnd.setForeground(new java.awt.Color(255, 255, 255));
        allowEnd.setSelected(true);
        allowEnd.setText("Allow End");

        warnOnOverload.setForeground(new java.awt.Color(255, 255, 255));
        warnOnOverload.setSelected(true);
        warnOnOverload.setText("Warn on Overload");

        useExactLocation.setForeground(new java.awt.Color(255, 255, 255));
        useExactLocation.setText("Use Exact Login Location");

        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Perms File:");

        permsFileTxtBox.setText("permissions.yml");

        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Update Folder:");

        updateFolderTxtBox.setText("update");

        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Ping Packet Limit:");

        pingLimitTxtBox.setText("100");

        pluginProfiling.setForeground(new java.awt.Color(255, 255, 255));
        pluginProfiling.setText("Plugin Profiling");

        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Connection Throttle:");

        connectionThrottleTxtBox.setText("4000");

        queryPlugins.setForeground(new java.awt.Color(255, 255, 255));
        queryPlugins.setSelected(true);
        queryPlugins.setText("Query Plugins");

        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Deprecated Verbose:");

        deprecatedVerbose.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "default", "true", "false" }));

        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Shutdown Message:");

        shutdownMessageTxtBox.setText("Server Closed. Powered by BukkitUI");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27)
                            .addComponent(jLabel29)
                            .addComponent(jLabel30))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(shutdownMessageTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(deprecatedVerbose, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(connectionThrottleTxtBox)
                            .addComponent(pingLimitTxtBox)
                            .addComponent(updateFolderTxtBox)
                            .addComponent(permsFileTxtBox)))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pluginProfiling)
                            .addComponent(useExactLocation)
                            .addComponent(queryPlugins)
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addComponent(allowEnd)
                                .addGap(18, 18, 18)
                                .addComponent(warnOnOverload)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(allowEnd)
                    .addComponent(warnOnOverload))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(permsFileTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(updateFolderTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(pingLimitTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(useExactLocation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pluginProfiling)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(connectionThrottleTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(queryPlugins)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(deprecatedVerbose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(shutdownMessageTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder("Spawn Limits"));
        jPanel24.setOpaque(false);

        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Monsters:");

        monsterLimitTxtBox.setText("70");

        animalLimitTxtBox.setText("15");

        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Animals:");

        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Water Animals:");

        waterAnimalLimitTxtBox.setText("15");

        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Ambient:");

        ambientLimitTxtBox.setText("15");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(43, 43, 43)
                        .addComponent(monsterLimitTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel32))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(waterAnimalLimitTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel34)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ambientLimitTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(animalLimitTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(monsterLimitTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(animalLimitTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(ambientLimitTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(waterAnimalLimitTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder("Chunk GC"));
        jPanel25.setOpaque(false);

        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("Period in Ticks:");

        gcPeriodInTicksTxtBox.setText("6000");

        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Load Threshold: ");

        loadThresholdTxtBox.setText("0");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loadThresholdTxtBox)
                    .addComponent(gcPeriodInTicksTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(gcPeriodInTicksTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(loadThresholdTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel26.setBorder(javax.swing.BorderFactory.createTitledBorder("Ticks Per..."));
        jPanel26.setOpaque(false);

        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Animal Spawn:");

        ticksPerAnimalSpawnTxtBox.setText("400");

        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Monster Spawn:");

        ticksPerMonsterSpawnTxtBox.setText("400");

        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Auto Save:");

        ticksPerAutoSave.setText("6000");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ticksPerAnimalSpawnTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ticksPerMonsterSpawnTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ticksPerAutoSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel37)
                .addComponent(ticksPerAnimalSpawnTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ticksPerMonsterSpawnTxtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(ticksPerAutoSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder("Auto Updater"));
        jPanel27.setOpaque(false);

        autoUpdaterEnabled.setForeground(new java.awt.Color(255, 255, 255));
        autoUpdaterEnabled.setSelected(true);
        autoUpdaterEnabled.setText("Enabled");

        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Host: ");

        autoUpdaterHost.setText("dl.bukkit.org");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(autoUpdaterEnabled)
                .addGap(18, 18, 18)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(autoUpdaterHost)
                .addGap(18, 18, 18))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(autoUpdaterEnabled)
                .addComponent(jLabel40)
                .addComponent(autoUpdaterHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        saveBukkitYml.setText("Save...");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(saveBukkitYml)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveBukkitYml)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Bukkit Config", jPanel15);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        jTabbedPane1.addTab("", new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/design_layout/bukkit_tab.png")), jPanel3); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setOpaque(false);

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("BukkitUI Settings");

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel11.setOpaque(false);

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("BukkitUI Background");

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel10.setOpaque(false);

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Craftbukkit Location:");

        craftbukkitLocationBtn.setText("...");
        craftbukkitLocationBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                craftbukkitLocationBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(craftbukkitLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(craftbukkitLocationBtn)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(craftbukkitLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(craftbukkitLocationBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel12.setOpaque(false);

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Server Settings");

        startServerAutomaticallyCheckbox.setForeground(new java.awt.Color(255, 255, 255));
        startServerAutomaticallyCheckbox.setSelected(true);
        startServerAutomaticallyCheckbox.setText("Start Server Automatically");
        startServerAutomaticallyCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startServerAutomaticallyCheckboxActionPerformed(evt);
            }
        });

        autoDetectNewPluginsCheckbox.setForeground(new java.awt.Color(255, 255, 255));
        autoDetectNewPluginsCheckbox.setSelected(true);
        autoDetectNewPluginsCheckbox.setText("Auto-Detect new Plugins");
        autoDetectNewPluginsCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoDetectNewPluginsCheckboxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startServerAutomaticallyCheckbox)
                            .addComponent(autoDetectNewPluginsCheckbox))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startServerAutomaticallyCheckbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(autoDetectNewPluginsCheckbox))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel13.setOpaque(false);

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Updates");

        checkForUpdatesCheckbox.setForeground(new java.awt.Color(255, 255, 255));
        checkForUpdatesCheckbox.setSelected(true);
        checkForUpdatesCheckbox.setText("Automatically Check for Updates");
        checkForUpdatesCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkForUpdatesCheckboxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(checkForUpdatesCheckbox)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkForUpdatesCheckbox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 494, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("", new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/design_layout/settings_tab.png")), jPanel4); // NOI18N

        serverStatusLabel.setForeground(new java.awt.Color(255, 255, 255));
        serverStatusLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/circle_red_24.png"))); // NOI18N
        serverStatusLabel.setText("Status: OFFLINE");

        closeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/windows_controls/close_window-32.png"))); // NOI18N
        closeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeBtnActionPerformed(evt);
            }
        });

        minimizeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/windows_controls/minimize_window-32.png"))); // NOI18N
        minimizeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minimizeBtnActionPerformed(evt);
            }
        });

        serverRuntime.setForeground(new java.awt.Color(255, 255, 255));
        serverRuntime.setText("Runtime: 00:00:00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(serverStatusLabel)
                        .addGap(18, 18, 18)
                        .addComponent(serverRuntime)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(minimizeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(minimizeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serverStatusLabel)
                    .addComponent(serverRuntime))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 860, 580));

        backgroundImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/design_layout/BukkitUI.png"))); // NOI18N
        getContentPane().add(backgroundImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Other Unimportant Stuff">
    /**
     * Moves frame to new location, depending on where the user drags mouse to.
     *
     * @param evt
     */
    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged

        int frameX = this.getLocation().x;
        int frameY = this.getLocation().y;

        int xMoved = (frameX + evt.getX()) - (frameX + origin.x);
        int yMoved = (frameY + evt.getY()) - (frameY + origin.y);

        int x = frameX + xMoved;
        int y = frameY + yMoved;

        this.setLocation(x, y);

    }//GEN-LAST:event_jPanel1MouseDragged

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        origin = evt.getPoint();
        getComponentAt(origin);
    }//GEN-LAST:event_jPanel1MouseClicked

    private void closeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeBtnActionPerformed
        try {
            switch (serverState) {
                case INSTALLING:
                    JOptionPane.showMessageDialog(null, "WARNING: The server is being installed. Please wait until the process has finished!", "Server is Installing!", JOptionPane.WARNING_MESSAGE);
                    return;
                case ONLINE:
                    JOptionPane.showMessageDialog(null, "WARNING: The server is still running! Please stop the server, before exiting!", "Server is Running!", JOptionPane.WARNING_MESSAGE);
                    return;
                case STARTING:
                    JOptionPane.showMessageDialog(null, "WARNING: The server is currenty starting! Please stop the server, before exiting!", "Server is Running!", JOptionPane.WARNING_MESSAGE);
                    return;
                case REBOOTING:
                    JOptionPane.showMessageDialog(null, "WARNING: The server is rebooting! Please stop the server, before exiting!", "Server is Running!", JOptionPane.WARNING_MESSAGE);
                    return;
            }
            settings.save();
            System.exit(1024);
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_closeBtnActionPerformed

    private void minimizeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minimizeBtnActionPerformed
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_minimizeBtnActionPerformed
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Server Controls">
    //========== Server Controls (Basic) ==========\\

    private void startServerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startServerBtnActionPerformed
        startServer();
    }//GEN-LAST:event_startServerBtnActionPerformed

    private void stopServerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopServerBtnActionPerformed
        stopServer();
    }//GEN-LAST:event_stopServerBtnActionPerformed

    private void restartServerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restartServerBtnActionPerformed
        try {
            stopServer();
            Thread.sleep(500);
            startServer();
        } catch (InterruptedException ex) {
            startServer();
        }
    }//GEN-LAST:event_restartServerBtnActionPerformed

    //========== Server Controls (Advanced) ==========\\

    private void updateServerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateServerBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_updateServerBtnActionPerformed

    private void deleteServerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteServerBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteServerBtnActionPerformed

    private void serverPropertiesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serverPropertiesBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serverPropertiesBtnActionPerformed
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Preferences">
    //========== BukkitUI Settings ==========\\

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        settings.setLayout(BukkitUI_Layout.valueOf(jComboBox1.getSelectedItem().toString()));
        this.backgroundImage.setIcon(new ImageIcon(this.getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/design_layout/" + settings.getLayout() + ".png")));
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void craftbukkitLocationBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_craftbukkitLocationBtnActionPerformed
        JFileChooser jarChooser = new JFileChooser();
        jarChooser.setDialogTitle("Select Your Server.Jar");
        jarChooser.setMultiSelectionEnabled(false);
        jarChooser.setApproveButtonText("Use this Jar");
        jarChooser.setFileFilter(new JarFilter());
        int dialogResult = jarChooser.showOpenDialog(null);
        if (dialogResult == JOptionPane.OK_OPTION) {
            craftbukkitLocation.setText(jarChooser.getSelectedFile().getAbsolutePath());
            settings.setCraftbukkit(jarChooser.getSelectedFile());
            settings.setServerDir(jarChooser.getSelectedFile().getParentFile());
        }
    }//GEN-LAST:event_craftbukkitLocationBtnActionPerformed

    private void startServerAutomaticallyCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startServerAutomaticallyCheckboxActionPerformed
        settings.setStartServerAutomatically(startServerAutomaticallyCheckbox.isSelected());
    }//GEN-LAST:event_startServerAutomaticallyCheckboxActionPerformed

    private void autoDetectNewPluginsCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoDetectNewPluginsCheckboxActionPerformed
        settings.setAutoDetectNewPlugins(autoDetectNewPluginsCheckbox.isSelected());
    }//GEN-LAST:event_autoDetectNewPluginsCheckboxActionPerformed

    private void checkForUpdatesCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkForUpdatesCheckboxActionPerformed
        settings.setCheckForUpdates(checkForUpdatesCheckbox.isSelected());
    }//GEN-LAST:event_checkForUpdatesCheckboxActionPerformed
    //<< End of Control Methods >>\\
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Listing Methods">
    private void addBannedIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBannedIPActionPerformed
        ListModel model = bannedIPList.getModel();
        DefaultListModel newModel = new DefaultListModel();
        if (jTextField1.getText() == null || jTextField1.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter an IP address.", "Enter IP Address", JOptionPane.WARNING_MESSAGE);
        } else {
            for (int i = 0; i < model.getSize(); i++) {
                newModel.addElement(model.getElementAt(i));
            }
            newModel.addElement(jTextField1.getText());
            bannedIPList.setModel(newModel);
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(settings.getServerDir() + "/banned-ips.txt")));
            for (int i = 0; i < newModel.getSize(); i++) {
                writer.write(newModel.getElementAt(i).toString() + "\n");
            }
            writer.close();
        } catch (Exception ex) {
            System.err.println("ERROR: Error while writing new banned IP to file!\n" + ex.toString());
            ex.printStackTrace(System.err);
        }
    }//GEN-LAST:event_addBannedIPActionPerformed

    private void addBannedPlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBannedPlayerActionPerformed
        ListModel model = bannedPlayersList.getModel();
        DefaultListModel newModel = new DefaultListModel();
        if (jTextField2.getText() == null || jTextField2.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter a player name.", "Enter Player Name", JOptionPane.WARNING_MESSAGE);
        } else {

            for (int i = 0; i < model.getSize(); i++) {
                newModel.addElement(model.getElementAt(i));
            }
            newModel.addElement(jTextField2.getText());
            bannedPlayersList.setModel(newModel);
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(settings.getServerDir() + "/banned-players.txt")));
            for (int i = 0; i < newModel.getSize(); i++) {
                writer.write(newModel.getElementAt(i).toString() + "\n");
            }
            writer.close();
        } catch (Exception ex) {
            System.err.println("ERROR: Error while writing new banned IP to file!\n" + ex.toString());
            ex.printStackTrace(System.err);
        }
    }//GEN-LAST:event_addBannedPlayerActionPerformed

    private void addOperatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOperatorActionPerformed
        ListModel model = operatorsList.getModel();
        DefaultListModel newModel = new DefaultListModel();
        if (jTextField3.getText() == null || jTextField3.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter a player name.", "Enter Player Name", JOptionPane.WARNING_MESSAGE);
        } else {

            for (int i = 0; i < model.getSize(); i++) {
                newModel.addElement(model.getElementAt(i));
            }
            newModel.addElement(jTextField3.getText());
            operatorsList.setModel(newModel);
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(settings.getServerDir() + "/ops.txt")));
            for (int i = 0; i < newModel.getSize(); i++) {
                writer.write(newModel.getElementAt(i).toString() + "\n");
            }
            writer.close();
        } catch (Exception ex) {
            System.err.println("ERROR: Error while writing new banned IP to file!\n" + ex.toString());
            ex.printStackTrace(System.err);
        }
    }//GEN-LAST:event_addOperatorActionPerformed

    private void addWhiteListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addWhiteListActionPerformed
        ListModel model = whitelist.getModel();
        DefaultListModel newModel = new DefaultListModel();
        if (jTextField4.getText() == null || jTextField4.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter a player name.", "Enter Player Name", JOptionPane.WARNING_MESSAGE);
        } else {
            for (int i = 0; i < model.getSize(); i++) {
                newModel.addElement(model.getElementAt(i) + "\n");
            }
            newModel.addElement(jTextField4.getText());
            whitelist.setModel(newModel);
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(settings.getServerDir() + "/white-list.txt")));
            for (int i = 0; i < newModel.getSize(); i++) {
                writer.write(newModel.getElementAt(i).toString());
            }
            writer.close();
        } catch (Exception ex) {
            System.err.println("ERROR: Error while writing new banned IP to file!\n" + ex.toString());
            ex.printStackTrace(System.err);
        }
    }//GEN-LAST:event_addWhiteListActionPerformed
    //</editor-fold>

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        playerIO();
    }//GEN-LAST:event_formWindowActivated

    //<< Start of Server Methods and Vars >>\\
    boolean runServer = true;

    //<editor-fold defaultstate="collapsed" desc="Server Methods">
    /**
     * Starts Bukkit Server
     */
    //<editor-fold defaultstate="collapsed" desc="Big Methods">
    private void startServer() {
        serverRuntime.setText("Runtime: 00:00:00");
        try {
            if (serverState != ServerState.OFFLINE) {
                JOptionPane.showMessageDialog(null, "ERROR: The server is not offline! Please make sure the server is offline, before trying to start it.",
                        "Server is not offline!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            System.out.println("Starting Craftbukkit...");
            serverStatusLabel.setIcon(WORKING);
            System.out.println("Checking plugins...");
            serverStatusLabel.setText("Starting...");
            serverState = ServerState.STARTING;

            File plugins = new File(settings.getServerDir().getAbsolutePath() + "/plugins");
            File jvmMonitor = new File(plugins.getAbsolutePath() + "/JVMMonitor_BukkitUI.jar");
            if (!plugins.exists()) {
                plugins.mkdir();
            }
            if (!jvmMonitor.exists()) {
                InputStream input = this.getClass().getResourceAsStream("/eu/m4gkbeatz/bukkitui/resources/plugins/JVMMonitor_BukkitUI.jar");
                OutputStream output = new FileOutputStream(jvmMonitor);
                int readBytes = 0;
                byte[] buffer = new byte[4096];
                while ((readBytes = input.read(buffer)) > 0) {
                    output.write(buffer, 0, readBytes);
                }
                input.close();
                output.close();
            }

            System.out.println("Checking server files...");
            File serverProps = new File(settings.getServerDir() + "/server.properties");
            if (!serverProps.exists()) {
                buildServerProps();
            }

            File bukkitConf = new File(settings.getServerDir() + "/bukkit.yml");
            if (!bukkitConf.exists()) {
                buildBukkitYml(bukkitConf);
            }

            playerIO();

            consoleLog.setText("");
            System.out.println("Preparing arguments...");
            processArgs.clear();
            processArgs.add(settings.getJava().getAbsolutePath());
            processArgs.add("-Xms1536M");
            processArgs.add("-Xmx2048M");
            processArgs.add("-jar");
            processArgs.add(settings.getCraftbukkit().getAbsolutePath());
            processArgs.add("--nojline");
            System.out.println("Args list:");
            for (String str : processArgs) {
                System.out.println("\t" + str);
            }

            process = new ProcessBuilder(processArgs);
            process.directory(settings.getServerDir());
            process.redirectErrorStream(true);

            System.out.println("Starting process...");
            pr = process.start();
            runServer = true;

            System.out.println("Fetching streams...");
            processReader = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            processWriter = new BufferedWriter(new OutputStreamWriter(pr.getOutputStream()));
            System.out.println("Stream fetched. Monitoring process...");
            monitorServer();
        } catch (Exception ex) {
            serverStatusLabel.setIcon(OFFLINE);
            serverStatusLabel.setText("OFFLINE");
            serverState = ServerState.OFFLINE;
            JOptionPane.showMessageDialog(null, "ERROR: Error while starting the server!\n" + ex.toString()
                    + "\n The stack trace was printed to the console.", "Error Starting Server!", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(System.err);
        }
    }

    private void monitorServer() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String line = "";
                    while ((line = processReader.readLine()) != null && runServer) {
                        if (!line.contains("[JVM]")) {
                            consoleLog.append(line + "\n");
                            System.out.print("Craftbukkit Output: " + line + "\n");
                            if (line.toLowerCase().contains("done")) {
                                serverStatusLabel.setIcon(ONLINE);
                                serverStatusLabel.setText("ONLINE");
                                serverState = ServerState.ONLINE;
                                monitorServerRuntime();
                            }
                            if (line.toLowerCase().contains("logged in")) {
                                String[] arr = line.split("\\[INFO] ");
                                String[] arr0 = arr[1].split(" ");
                                String[] ip0 = arr0[1].split("\\[/"), ip1 = ip0[1].split("]");
                                System.out.println("User logged in: " + arr0[0]);
                                System.out.println("User IP-Address: " + ip1[0]);
                                listOfPlayers.add(new Player(arr0[0], false, ip1[0]));
                            }
                            if (line.toLowerCase().contains("disconnect.")) {
                                String[] arr = line.split("\\[INFO] "), arr0 = arr[1].split(" ");
                                System.out.println(arr0[0] + " has left.");
                                for (int i = 0; i < listOfPlayers.size(); i++) {
                                    if (listOfPlayers.get(i).toString().equals(arr0[0])) {
                                        listOfPlayers.remove(i);
                                        break;
                                    }
                                }
                            }
                            imageMap = createPlayerImageMap();
                            loadPlayers();
                            playerList.setCellRenderer(new PlayerListCellRenderer());

                        } else {
                            if (line.contains("processCPULoad=")) {
                                System.out.println("JVM Info: " + line);
                                String[] load = line.split("=");
                                cpuUsageProgressBar.setValue(Integer.valueOf(load[1]));
                                cpuUsageProgressBar.setString(load[1]);
                                continue;
                            }
                            if (line.contains("maxMem=")) {
                                String[] mem = line.split("=");
                                System.out.println("JVM Info: " + line);
                                maxMemLabel.setText("Max Memory: " + mem[1] + "MB");
                                usedMemProgressBar.setMaximum(Integer.valueOf(mem[1]));
                            }
                            if (line.contains("totalMem=")) {
                                String[] mem = line.split("=");
                                System.out.println("JVM Info: " + line);
                                totalMemLabel.setText("Total Memory: " + mem[1] + "MB");
                            }
                            if (line.contains("usedMem=")) {
                                String[] mem = line.split("=");
                                System.out.println("JVM Info: " + line);
                                usedMemProgressBar.setString("used Memory: " + mem[1] + "MB");
                                usedMemProgressBar.setValue(Integer.valueOf(mem[1]));
                            }

                        }
                    }
                    serverStatusLabel.setIcon(OFFLINE);
                    serverStatusLabel.setText("OFFLINE");
                    serverState = ServerState.OFFLINE;
                } catch (Exception ex) {
                    serverStatusLabel.setIcon(OFFLINE);
                    serverStatusLabel.setText("OFFLINE: ERROR");
                    serverState = ServerState.OFFLINE;
                    JOptionPane.showMessageDialog(null, "ERROR: An error occured while trying to read from the server!\n" + ex.toString() + "\nThe server will now be destroyed.", "Destroying Process!", JOptionPane.ERROR_MESSAGE);
                    System.err.println("ERROR: An error occured while trying to read from the server!\n" + ex.toString() + "\nThe server will now be destroyed.");
                    ex.printStackTrace(System.err);
                    killServer();
                }
            }
        }.start();
    }

    private void monitorServerRuntime() {
        new Thread() {
            private int seconds = 0;
            private int minutes = 0;
            private long hours = 0;

            @Override
            public void run() {
                try {
                    while (serverState == ServerState.ONLINE) {
                        Thread.sleep(1000);
                        seconds++;
                        if (seconds == 60) {
                            seconds = 0;
                            minutes++;
                        }
                        if (minutes == 60) {
                            minutes = 0;
                            hours++;
                        }
                        serverRuntime.setText("Runtime: " + hours + ":" + minutes + ":" + seconds);
                    }
                    interrupt();
                } catch (InterruptedException ex) {
                    System.err.println("ERROR: Error monitoring server up-time.");
                    System.err.println(ex.toString());
                    ex.printStackTrace(System.err);
                    serverRuntime.setText("Runtime: ERROR");
                }
            }
        }.start();
    }

    private void stopServer() {
        System.out.print("Stopping server...");
        try {
            processWriter.write("stop");
            runServer = false;
            Thread.sleep(3000);
            pr.destroy();
            serverStatusLabel.setIcon(OFFLINE);
            serverStatusLabel.setText("OFFLINE");
            serverState = ServerState.OFFLINE;
        } catch (IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: An error occured while attempting to \"kindly\" stop the server.\n" + ex.toString()
                    + "\nIt will now be destroyed.",
                    "Error Stopping Server", JOptionPane.ERROR_MESSAGE);
            killServer();
        }
    }
    //</editor-fold>

    private void killServer() {
        runServer = false;
        pr.destroy();
    }

    private void buildServerProps() {
        BufferedWriter writer = null;
        File serverProps = null;
        System.out.println(settings.getServerDir());
        serverProps = new File(settings.getServerDir() + "/server.properties");
        try {
            String props
                    = "# Minecraft Server Properties\n"
                    + "# Generated by BukkitUI\n"
                    + "# Build Date: " + new Date().toLocaleString() + "\n"
                    + "allow-nether=" + allowNether.isSelected() + "\n"
                    + "level-name=" + levelName.getText() + "\n"
                    + "enable-query=" + enableQuery.isSelected() + "\n"
                    + "allow-flight=" + allowFlight.getText() + "\n"
                    + "server-port=" + serverPort.getText() + "\n"
                    + "level-type=" + levelType.getSelectedItem() + "\n"
                    + "enable-rcon=" + enableRcon.isSelected() + "\n"
                    + "level-seed=" + this.levelSeed.getText() + "\n"
                    + "server-ip=" + serverIP.getName() + "\n"
                    + "max-build-height=" + maxBuildHight.getText() + "\n"
                    + "spawn-npcs=" + spawnNPCs.isSelected() + "\n"
                    + "white-list=" + whiteList.isSelected() + "\n"
                    + "spawn-animals=" + spawnAnimals.isSelected() + "\n"
                    + "online-mode=" + onlineMode.isSelected() + "\n"
                    + "pvp=" + enablePVP.isSelected() + "\n"
                    + "difficulty=" + difficulty.getText() + "\n"
                    + "gamemode=" + gameMode.getSelectedIndex() + "\n"
                    + "max-players=" + maxPlayers.getText() + "\n"
                    + "spawn-monsters=" + spawnMonsters.isSelected() + "\n"
                    + "generate-structures=" + generateStructures.isSelected() + "\n"
                    + "view-distance=" + viewDistance.getText() + "\n"
                    + "motd=" + motd.getName();
            serverProps.createNewFile();
            writer = new BufferedWriter(new FileWriter(serverProps));
            writer.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: An error occured while attempting to write the server properties file.\n"
                    + "The stack trace was printed to the console.", "Error Writing server.properties", JOptionPane.ERROR_MESSAGE);
            System.err.println(ex.toString());
            ex.printStackTrace(System.err);
        }
        
    }

    private void buildBukkitYml(File f) {
        try {
            f.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            writer.write(
                    "# Main Bukkit Config File\n"
                    + "# For a reference on how to configure, visit http://wiki.bukkit.org/Bukkit.yml\n"
                    + "# Generated by BukkitUI at " + new Date().toLocaleString() + "\n"
                    + "settings:\n"
                    + "  allow-end: " + allowEnd.isSelected() + "\n"
                    + "  warn-on-overload: " + warnOnOverload.isSelected() + "\n"
                    + "  permissions-file: " + permsFileTxtBox.getText() + "\n"
                    + "  update-folder: " + updateFolderTxtBox.getText() + "\n"
                    + "  ping-packet-limit: " + pingLimitTxtBox.getText() + "\n"
                    + "  use-exact-login-location: " + useExactLocation.isSelected() + "\n"
                    + "  plugin-profiling: " + pluginProfiling.isSelected() + "\n"
                    + "  connection-throttle: " + connectionThrottleTxtBox.getText() + "\n"
                    + "  query-plugins: " + queryPlugins.isSelected() + "\n"
                    + "  deprecated-verbose: " + deprecatedVerbose.getSelectedItem() + "\n"
                    + "  shutdown-message: " + shutdownMessageTxtBox.getText() + "\n"
                    + "spawn-limits:\n"
                    + "  monsters: " + monsterLimitTxtBox.getText() + "\n"
                    + "  animals: " + animalLimitTxtBox.getText() + "\n"
                    + "  water-animals: " + waterAnimalLimitTxtBox.getText() + "\n"
                    + "  ambient: " + ambientLimitTxtBox.getText() + "\n"
                    + "chunk-gc:\n"
                    + "  period-in-ticks: " + gcPeriodInTicksTxtBox.getText() + "\n"
                    + "  load-threshold: " + loadThresholdTxtBox.getText() + "\n"
                    + "ticks-per:\n"
                    + "  animal-spawns: " + ticksPerAnimalSpawnTxtBox.getText() + "\n"
                    + "  monster-spawns: " + ticksPerMonsterSpawnTxtBox.getText() + "\n"
                    + "  autosave: " + ticksPerAutoSave.getText() + "\n"
                    + "auto-updater:\n"
                    + "  enabled: " + autoUpdaterEnabled.isSelected() + "\n"
                    + "  on-broken:\n"
                    + "  - warn-console\n"
                    + "  - warn-ops\n"
                    + "  on-update:\n"
                    + "  - warn-console\n"
                    + "  - warn-ops\n"
                    + "  preferred-channel: rb\n"
                    + "  host: " + autoUpdaterHost.getText() + "\n"
                    + "  suggest-channels: true\n"
                    + "database:\n"
                    + "  username: bukkit\n"
                    + "  isolation: SERIALIZABLE\n"
                    + "  driver: org.sqlite.JDBC\n"
                    + "  password: walrus\n"
                    + "  url: jdbc:sqlite:{DIR}{NAME}.db\n");
            writer.close();
        } catch (IOException ex) {
            System.err.println("ERROR: Error while writing to bukkit.yml!\n" + ex.toString());
            ex.printStackTrace(System.err);
        }
    }

    private void playerIO() {
        Thread bannedIPs = new Thread() {
            @Override
            public void run() {
                try {
                    File bannedIPs = new File(settings.getServerDir() + "/banned-ips.txt");
                    if (bannedIPs.exists()) {
                        while (runServer) {
                            DefaultListModel model = new DefaultListModel();
                            BufferedReader reader = new BufferedReader(new FileReader(bannedIPs));
                            String line = "";
                            while ((line = reader.readLine()) != null) {
                                model.addElement(line);
                            }
                            bannedIPList.setModel(model);
                            Thread.sleep(10000);
                        }
                    }
                } catch (Exception ex) {
                    System.err.println("ERROR: An error occured while reading banned IPs! The next try will occur on next server boot...\n" + ex.toString());
                    ex.printStackTrace(System.err);
                }
            }
        };
        //===============================\\
        Thread bannedPlayers = new Thread() {
            @Override
            public void run() {
                try {
                    File bannedIPs = new File(settings.getServerDir() + "/banned-players.txt");
                    if (bannedIPs.exists()) {
                        while (runServer) {
                            DefaultListModel model = new DefaultListModel();

                            BufferedReader reader = new BufferedReader(new FileReader(bannedIPs));
                            String line = "";
                            while ((line = reader.readLine()) != null) {
                                model.addElement(line);
                            }
                            bannedPlayersList.setModel(model);
                            Thread.sleep(10000);
                        }
                    }
                } catch (Exception ex) {
                    System.err.println("ERROR: An error occured while reading banned IPs! The next try will occur on next server boot...\n" + ex.toString());
                    ex.printStackTrace(System.err);
                }
            }
        };
        //===============================\\
        Thread operators = new Thread() {
            @Override
            public void run() {
                try {
                    File bannedIPs = new File(settings.getServerDir() + "/ops.txt");
                    if (bannedIPs.exists()) {
                        while (runServer) {
                            DefaultListModel model = new DefaultListModel();

                            BufferedReader reader = new BufferedReader(new FileReader(bannedIPs));
                            String line = "";
                            while ((line = reader.readLine()) != null) {
                                model.addElement(line);
                            }
                            operatorsList.setModel(model);
                            Thread.sleep(10000);
                        }
                    }
                } catch (Exception ex) {
                    System.err.println("ERROR: An error occured while reading banned IPs! The next try will occur on next server boot...\n" + ex.toString());
                    ex.printStackTrace(System.err);
                }
            }
        };
        //===============================\\
        Thread whiteListReader = new Thread() {
            @Override
            public void run() {
                try {
                    File bannedIPs = new File(settings.getServerDir() + "/white-list.txt");
                    if (bannedIPs.exists()) {
                        while (runServer) {
                            DefaultListModel model = new DefaultListModel();

                            BufferedReader reader = new BufferedReader(new FileReader(bannedIPs));
                            String line = "";
                            while ((line = reader.readLine()) != null) {
                                model.addElement(line);
                            }
                            whitelist.setModel(model);
                            Thread.sleep(10000);
                        }
                    }
                } catch (Exception ex) {
                    System.err.println("ERROR: An error occured while reading banned IPs! The next try will occur on next server boot...\n" + ex.toString());
                    ex.printStackTrace(System.err);
                }
            }
        };
        //===============================\\
        bannedIPs.start();
        bannedPlayers.start();
        operators.start();
        whiteListReader.start();
    }
    //</editor-fold>

    //========== Player Management ==========\\
    //<editor-fold defaultstate="collapsed" desc="      ">
    private Map<String, ImageIcon> createPlayerImageMap() {
        Map<String, ImageIcon> map = new HashMap<>();
        try {
            for (Player player : listOfPlayers) {
                if (player.isAFK()) {
                    map.put(player.toString(), new ImageIcon(this.getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/player_icons/player_afk.png")));
                } else {
                    map.put(player.toString(), new ImageIcon(this.getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/player_icons/player_online.png")));
                }
            }
        } catch (Exception ex) {
        }
        return map;
    }

    private void loadPlayers() {
        DefaultListModel model = new DefaultListModel();
        int index = 0;
        for (Player player : listOfPlayers) {
            model.add(index, player.toString());
            index++;
        }
        playerList.setModel(model);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variables.">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBannedIP;
    private javax.swing.JButton addBannedPlayer;
    private javax.swing.JButton addOperator;
    private javax.swing.JButton addWhiteList;
    private javax.swing.JCheckBox allowEnd;
    private javax.swing.JCheckBox allowFlight;
    private javax.swing.JCheckBox allowNether;
    private javax.swing.JTextField ambientLimitTxtBox;
    private javax.swing.JTextField animalLimitTxtBox;
    private javax.swing.JCheckBox autoDetectNewPluginsCheckbox;
    private javax.swing.JCheckBox autoUpdaterEnabled;
    private javax.swing.JTextField autoUpdaterHost;
    private javax.swing.JLabel backgroundImage;
    private javax.swing.JList bannedIPList;
    private javax.swing.JList bannedPlayersList;
    private javax.swing.JCheckBox checkForUpdatesCheckbox;
    private javax.swing.JButton closeBtn;
    private javax.swing.JTextField connectionThrottleTxtBox;
    private javax.swing.JTextArea consoleLog;
    private javax.swing.JProgressBar cpuUsageProgressBar;
    private javax.swing.JTextField craftbukkitLocation;
    private javax.swing.JButton craftbukkitLocationBtn;
    private javax.swing.JButton deleteServerBtn;
    private javax.swing.JComboBox deprecatedVerbose;
    private javax.swing.JTextField difficulty;
    private javax.swing.JCheckBox enablePVP;
    private javax.swing.JCheckBox enableQuery;
    private javax.swing.JCheckBox enableRcon;
    private javax.swing.JButton executeCmdBtn;
    private javax.swing.JTextField executeCmdTxtField;
    private javax.swing.JComboBox gameMode;
    private javax.swing.JTextField gcPeriodInTicksTxtBox;
    private javax.swing.JCheckBox generateStructures;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
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
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField levelName;
    private javax.swing.JTextField levelSeed;
    private javax.swing.JComboBox levelType;
    private javax.swing.JTextField loadThresholdTxtBox;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField maxBuildHight;
    private javax.swing.JLabel maxMemLabel;
    private javax.swing.JTextField maxPlayers;
    private javax.swing.JButton minimizeBtn;
    private javax.swing.JTextField monsterLimitTxtBox;
    private javax.swing.JTextField motd;
    private javax.swing.JCheckBox onlineMode;
    private javax.swing.JList operatorsList;
    private javax.swing.JTextField permsFileTxtBox;
    private javax.swing.JTextField pingLimitTxtBox;
    private javax.swing.JList playerList;
    private javax.swing.JCheckBox pluginProfiling;
    private javax.swing.JCheckBox queryPlugins;
    private javax.swing.JButton restartServerBtn;
    private javax.swing.JButton saveBukkitYml;
    private javax.swing.JTextField serverIP;
    private javax.swing.JTextField serverPort;
    private javax.swing.JButton serverPropertiesBtn;
    private javax.swing.JLabel serverRuntime;
    private javax.swing.JLabel serverStatusLabel;
    private javax.swing.JTextField shutdownMessageTxtBox;
    private javax.swing.JCheckBox spawnAnimals;
    private javax.swing.JCheckBox spawnMonsters;
    private javax.swing.JCheckBox spawnNPCs;
    private javax.swing.JCheckBox startServerAutomaticallyCheckbox;
    private javax.swing.JButton startServerBtn;
    private javax.swing.JButton stopServerBtn;
    private javax.swing.JTextField ticksPerAnimalSpawnTxtBox;
    private javax.swing.JTextField ticksPerAutoSave;
    private javax.swing.JTextField ticksPerMonsterSpawnTxtBox;
    private javax.swing.JLabel totalMemLabel;
    private javax.swing.JTextField updateFolderTxtBox;
    private javax.swing.JButton updateServerBtn;
    private javax.swing.JCheckBox useExactLocation;
    private javax.swing.JProgressBar usedMemProgressBar;
    private javax.swing.JTextField viewDistance;
    private javax.swing.JCheckBox warnOnOverload;
    private javax.swing.JTextField waterAnimalLimitTxtBox;
    private javax.swing.JCheckBox whiteList;
    private javax.swing.JList whitelist;
    // End of variables declaration//GEN-END:variables
//</editor-fold>

    //========== Internal Classes ===========\\    
    //<editor-fold defaultstate="collapsed" desc="      ">
    /**
     * Custom list cell renderer for player list.
     *
     * @author beatsleigher
     */
    public class PlayerListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setIcon(imageMap.get((String) value));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            return label;
        }

    }
    //</editor-fold>

}
