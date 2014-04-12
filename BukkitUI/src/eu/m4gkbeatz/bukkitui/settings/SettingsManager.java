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

package eu.m4gkbeatz.bukkitui.settings;

import eu.m4gkbeatz.bukkitui.io.JarFilter;
import java.io.*;
import java.net.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Manages the settings used in and by BukkitUI.
 * @author beatsleigher
 */
@SuppressWarnings({"UnusedAssignment", "ConvertToTryWithResources", "UnnecessaryContinue"})
public final class SettingsManager {
    
    // Files and directories
    File prefsDir = null;
    File prefs = null;
    // Actual settings
    boolean startServerAutomatically = true;
    boolean autoDetectNewPlugins = true;
    boolean checkForUpdates = true;
    URL primeLocation = SettingsManager.class.getProtectionDomain().getCodeSource().getLocation();
    File serverDir = new File(primeLocation.getFile());
    File craftbukkit = null;
    BukkitUI_Layout layout = BukkitUI_Layout.BukkitUI;
    File java = new File(System.getProperty("java.home") + "/bin/java");
    //# =============== Webserver settings =============== #\\
    boolean useWebServer = false;
    int maxNumberOfUsers = 1;
    String IP = "127.0.0.1";
    String users[] =     {"BukkitUI"};
    String passwords[] = {"LinuxDeathMachine"};
    File webServerSettings = null;
    
    /**
     * Collects information from variables and saves them into a String.
     * @return String containing variables.
     */
    private String prefs() {
        return    "############################## BukkitUI User Preferences ##############################\n"
                + "#                                                                                     #\n"
                + "# ===== Index =====                                                                   #\n" 
                + "# [#] » Comment. Everything behing a hash will be ignored.                            #\n"
                + "# [### EOF ###] » End of File. Once this stand-a-lone line was detected,              #\n"
                + "#                 the file will be closed.                                            #\n"
                + "# [pref::] » Preference-start. Indicates a preference is to be found.                 #\n"
                + "# [name=] » Preference name. This indicates the preference's name.                    #\n"
                + "# [value=] » Preference value. The value of the preference.                           #\n"
                + "# ===== Index =====                                                                   #\n"
                + "#                                                                                     #\n"
                + "############################## BukkitUI User Preferences ##############################\n\n"
                //
                + "#########################################################\n"
                + "# Current settings location: " + prefsDir + "           #\n"
                + "# This has no impact on BukkitUI.                       #\n"
                + "#########################################################\n\n"
                //
                + "##################################################\n"
                + "# Preference \"startServerAutomatically\"        #\n"
                + "# If true, then the server will be automatically #\n"
                + "# started once BukkitUI has loaded.              #\n"
                + "##################################################\n"
                + "pref::[name=startServerAutomatically, value=" + startServerAutomatically + "]\n\n"
                //
                + "##################################################\n"
                + "# Preference \"autoDetectNewPlugins\"            #\n"
                + "# If true, BukkitUI will notify the user that a  #\n"
                + "# new plugin has been detected, and will ask     #\n"
                + "# the server should be restarted or not.         #\n"
                + "##################################################\n"
                + "pref::[name=autoDetectNewPlugins, value=" + autoDetectNewPlugins + "]\n\n"
                //
                + "##################################################\n"
                + "# Preference \"checkForUpdates\"                 #\n"
                + "# If true, then BukkitUI will have a seperate    #\n"
                + "# thread (mini-process) running in the back-     #\n"
                + "# ground, which will check for updates for       #\n"
                + "# BukkitUI AND Craftbukkit.                      #\n"
                + "##################################################\n"
                + "pref::[name=checkForUpdates, value=" + checkForUpdates + "]\n\n"
                //
                + "##################################################\n"
                + "# Preference \"serverDir\"                       #\n"
                + "# This setting is a vital part of BukkitUI. If   #\n"
                + "# this setting is incorrect, then the entire     #\n"
                + "# program will fail.                             #\n"
                + "##################################################\n"
                + "pref::[name=serverDir, value=" + serverDir + "]\n\n"
                //
                + "##################################################\n"
                + "# Preference \"craftbukkit\"                     #\n"
                + "# This setting contains the filename of the      #\n"
                + "# Bukkit server JAR. If this setting is wrong,   #\n"
                + "# then the program will be flooded with errors.  #\n"
                + "##################################################\n"
                + "pref::[name=craftbukkit, value=" + craftbukkit + "]\n\n"
                //
                + "##################################################\n"
                + "# Preference \"layout\"                          #\n"
                + "# This setting controls the look of BukkitUI's   #\n"
                + "# main user interface.                           #\n"
                + "##################################################\n"
                + "pref::[name=layout, value=" + layout + "]\n\n"
                //
                + "##################################################\n"
                + "# Preference \"java\"                            #\n"
                + "# This preference is for the JVM and should NOT  #\n"
                + "# be changed unless it is absolutely necessary!  #\n"
                + "##################################################\n"
                + "pref::[name=java, value=" + java + "]\n\n"
                //
                + "##################################################\n"
                + "# Preference \"useWebServer\"                    #\n"
                + "# This setting is for remote users, or if you're #\n"
                + "# away from your computer when using BukkitUI!   #\n"
                + "# When true, you must supply the server with a   #\n"
                + "# working directory (workspace) where all the    #\n"
                + "# files will be installed to. You can NEVER      #\n"
                + "# the contents of this folder, or you'll break   #\n"
                + "# the web server!                                #\n"
                + "# Also, you must provide an IP address and a     #\n"
                + "# username, with password. (Single user).        #\n"
                + "##################################################\n"
                + "pref::[name=useWebServer, value=" + useWebServer + "]\n\n"
                //
                + "### EOF ###";
    }
    
    public SettingsManager() { init(); }
    
    /**
     * Initializes everything needed to perform correctly.
     */
    private void init() {
        System.out.println("Initializing preferences...");
        try {
            prefsDir = new File(".bukkitui/prefs");
            prefs = new File(prefsDir.getAbsolutePath() + "/settings.bin");
            webServerSettings = new File(prefsDir.getAbsolutePath() + "webServer.bin");
            System.out.println("Loading preferences...");
            load();
        } catch (IOException ex) {
            System.err.println("ERROR: Error while loading settings!\n");
            System.err.println(ex.toString());
            ex.printStackTrace(System.err);
        }
    }
    
    private void load() throws IOException {
        BufferedReader reader = null;
        String line = "";
        if (!prefs.exists()) {
            JOptionPane.showMessageDialog(null, "INFORMATION: It seems as though you're using BukkitUI for the first time.\n"
                    + "To ensure correct functionality, after this message, you will be prompted to select the JAR-File for your Bukkit server.\n"
                    + "After you have done that, BukkitUI will take care of the rest.\n"
                    + "Enjoy :)", "Starting for the First Time, Eh?", JOptionPane.INFORMATION_MESSAGE);
            JFileChooser jarChooser = new JFileChooser();
            jarChooser.setDialogTitle("Select Your Server.Jar");
            jarChooser.setMultiSelectionEnabled(false);
            jarChooser.setApproveButtonText("Use this Jar");
            jarChooser.setFileFilter(new JarFilter());
            int dialogResult = jarChooser.showOpenDialog(null);
            if (dialogResult == JOptionPane.OK_OPTION) {
                setCraftbukkit(jarChooser.getSelectedFile());
                setServerDir(jarChooser.getSelectedFile().getParentFile());
            }
            
            System.out.println("Settings file doesn't exist. Creating...");
            prefs.getParentFile().mkdirs();
            prefs.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(prefs));
            System.out.println("Writing default preferences to file...");
            writer.write(prefs());
            writer.close();
            for (int i = 0; i != 200; i++) {} // Just do something to occupy the thread.
        }
        reader = new BufferedReader(new FileReader(prefs));
        while ((line = reader.readLine()) != null) {
            if (line.equals("### EOF ###")) { System.out.println("End of settings file!"); break; }
            if (line.startsWith("#")) continue;
            if (line.startsWith("pref::")) {
                if (line.contains("name=startServerAutomatically")) {
                    System.out.print("Found pref: startServerAutomatically");
                    String[] arr = line.split("value="), arr0 = arr[1].split("]");
                    startServerAutomatically = Boolean.valueOf(arr0[0]);
                    System.out.println(" = " + arr0[0]);
                    continue;
                }
                if (line.contains("name=autoDetectNewPlugins")) {
                    System.out.print("Found pref: autoDetectNewPlugins");
                    String[] arr = line.split("value="), arr0 = arr[1].split("]");
                    autoDetectNewPlugins = Boolean.valueOf(arr0[0]);
                    System.out.println(" = " + arr0[0]);
                    continue;
                }
                if (line.contains("name=checkForUpdates")) {
                    System.out.print("Found pref: checkForUpdates");
                    String[] arr = line.split("value="), arr0 = arr[1].split("]");
                    checkForUpdates = Boolean.valueOf(arr0[0]);
                    System.out.println(" = " + arr0[0]);
                    continue;
                }
                if (line.contains("name=serverDir")) {
                    System.out.print("Found pref: serverDir");
                    String[] arr = line.split("value="), arr0 = arr[1].split("]");
                    if (arr0[0] != null)
                        serverDir = new File(arr0[0]);
                    System.out.println(" = " + arr0[0]);
                    continue;
                }
                if (line.contains("name=craftbukkit")) {
                    System.out.print("Found pref: craftbukkit");
                    String[] arr = line.split("value="), arr0 = arr[1].split("]");
                    craftbukkit = new File(arr0[0]);
                    System.out.println(" = " + arr0[0]);
                    String os = System.getProperty("os.name");
                    if (os.toLowerCase().equals("linux") || os.toLowerCase().contains("mac")) {
                        Runtime.getRuntime().exec("chmod a+x " + craftbukkit);
                    }
                    continue;
                }
                if (line.contains("name=layout")) {
                    System.out.print("Found pref: layout");
                    String[] arr = line.split("value="), arr0 = arr[1].split("]");
                    layout = BukkitUI_Layout.valueOf(arr0[0]);
                    System.out.println(" = " + arr0[0]);
                    continue;
                }
                if (line.contains("name=java")) {
                    System.out.print("Found pref: java");
                    String[] arr = line.split("value="), arr0 = arr[1].split("]");
                    java = new File(arr0[0]);
                    System.out.println(" = " + arr0[0]);
                    continue;
                }
                if (line.contains("name=useWebServer")) {
                    System.out.print("Found pref: useWebServer");
                    String[] arr = line.split("value="), arr0 = arr[1].split("]");
                    useWebServer = Boolean.valueOf(arr0[0]);
                    System.out.println(" = " + arr0[0]);
                    if (useWebServer) {
                        System.out.println("Loading webserver components. Please be patient...");
                        loadWebServerData();
                    }
                    continue;
                }
                
            }
        }
    }
    
    public void save() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(prefs));
        writer.write(prefs());
        writer.close();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getter/Setter">
    //============ Getter Methods ============\\
    public boolean startServerAutomatically() { return startServerAutomatically; }
    
    public boolean autoDetectNewPlugins() { return autoDetectNewPlugins; }
    
    public boolean checkForUpdates() { return checkForUpdates; }
    
    public File getServerDir() { if (serverDir.toString().endsWith("BukkitUI.jar")) { return serverDir.getParentFile(); } else return serverDir; }
    
    public File getCraftbukkit() { return craftbukkit; }
    
    public String getLayout() { return layout.toString(); }
    
    public File getJava() { return java; }
    
    //============ Setter Methods ============\\
    public void setStartServerAutomatically(boolean newVal) { startServerAutomatically = newVal; }
    
    public void setAutoDetectNewPlugins(boolean newVal) { autoDetectNewPlugins = newVal; }
    
    public void setCheckForUpdates(boolean newVal) { checkForUpdates = newVal; }
    
    public void setServerDir(File newVal) { serverDir = newVal; }
    
    public void setCraftbukkit(File newVal) { craftbukkit = newVal; }
    
    public void setLayout(BukkitUI_Layout newVal) { layout = newVal; }
    
    public void setJava(File newVal) { java = newVal; }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Web Server">
    private void loadWebServerData() throws IOException {
        if (!webServerSettings.exists()) {
            saveWebServerData();
            for (int i = 0; i != 500; i++) {} // Do something to occupy the program, to ensure filesystem has enough time to do its shit. Fuck you, Windows!
        }
        BufferedReader reader = new BufferedReader(new FileReader(webServerSettings));
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (line.equals("### EOF ###")) break;
            if (line.startsWith("#")) continue;
            if (line.startsWith("pref::")) {
                if (line.contains("name=ip")) {
                    System.out.print("Found web server pref: ip");
                    String[] arr = line.split("value="), arr0 = arr[1].split("]");
                    IP = arr0[0];
                    System.out.println(" = " + arr0[0]);
                    continue;
                }
                if (line.contains("name=maxNumberOfUsers")) {
                    System.out.print("Found web server pref: maxNumberOfUsers");
                    String[] arr = line.split("value="), arr0 = arr[1].split("]");
                    maxNumberOfUsers = Integer.valueOf(arr0[0]);
                    System.out.println(" = " + arr0[0]);
                    continue;
                }
                if (line.contains("name=user") && line.endsWith("{")) {
                    System.out.println("Found user block!");
                    int i = 0;
                    do {
                        line = reader.readLine();
                        if (line.startsWith("\tuser::")) {
                            String[] arr = line.split("name="), arr0 = arr[1].split(",");
                            String[] arr1 = line.split("pass="), arr2 = arr1[1].split("]");
                            System.out.println("Found user: " + arr0[0]);
                            users[i] = arr0[0];
                            passwords[i] = arr2[0];
                            i++;
                        }
                    } while (!line.startsWith("}"));
                    continue;
                }
                
            }
        }
    }
    
    private void saveWebServerData() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(webServerSettings));
        writer.write(webServerPrefs());
        writer.flush();
        writer.close();
    }
    
    private String webServerPrefs() {
        String s= "############################## BukkitUI Web Server Preferences ##############################\n"
                + "## ========== Index ==========                                                              #\n"
                + "## [#] => Comment. These lines will be ignored.                                             #\n"
                + "## [pref::] => Pref initializer. A line starting with this will be attempted to be inter-   #\n"
                + "## \t interpreted.                                                                          #\n"
                + "## [name=] => The name of a preference. Warning: Case-sensitive!                            #\n"
                + "## [value=] => The value of a preference. Warning: Case-sensitive!                          #\n"
                + "## [### EOF ###] => End of File. When detected, the file will be closed immediately.        #\n"
                + "##############################/BukkitUI Web Server Preferences ##############################\n\n"
                //
                + "##########################################################################################\n"
                + "# Current file location: " + webServerSettings.getAbsolutePath() + "                     #\n"
                + "##########################################################################################\n\n"
                //
                + "##############################\n"
                + "# Preference \"ip\".         #\n"
                + "# The IP address of your     #\n"
                + "# webserver. Make sure it's  #\n"
                + "# the same as your server's  #\n"
                + "# IP address, for best       #\n"
                + "# results.                   #\n"
                + "# Default: 127.0.0.1         #\n"
                + "# - localhost                #\n"
                + "##############################\n"
                + "pref::[name=ip, value=" + IP + "]\n\n"
                //
                + "#########################################\n"
                + "# Preference \"maxNumberOfUsers\"       #\n"
                + "# Depending on this number, you can have#\n"
                + "# N-amount of users on this webserver.  #\n"
                + "# Default: 1                            #\n"
                + "#########################################\n"
                + "pref::[name=maxNumberOfUsers, value=" + maxNumberOfUsers + "]\n\n"
                //
                + "##############################\n"
                + "# Preference \"users\"       #\n"
                + "# This is a special type of  #\n"
                + "# preference. Please read the#\n"
                + "# rules of users:            #\n"
                + "# - No spaces!               #\n"
                + "# - Keep it Unicode!         #\n"
                + "# - Alwys use correct        #\n"
                + "# \tformatting!              #\n"
                + "# A TAB before each user!    #\n"
                + "#                            #\n"
                + "# Example:                   #\n"
                + "# user::[name=x, pass=y]     #\n"
                + "# Default user (always       #\n"
                + "# \t exists):                #\n"
                + "# user=BukkitUI,             #\n"
                + "# pass=LinuxDeathMachine     #\n"
                + "##############################\n"
                + "pref::[name=user] {\n"
                + "\tuser::[name=BukkitUI, pass=LinuxDeathMachine]\n";
        for (int i = 0; i < users.length; i++) {
            if (!users[i].equals("BukkitUI")) 
                s += "user::[name=" + users[i] + ", pass=" + passwords[i] + "]\n";
            if (i == users.length)
                s += "}\n### EOF ###";
        }
        
        return s;
    }
    //</editor-fold>
     
}
