/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.team.m4gkbeatz.BeatBukkitUI.main;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import eu.team.m4gkbeatz.BeatBukkitUI.splash.*;
import javax.swing.*;
import eu.team.m4gkbeatz.BeatBukkitUI.UI.*;
import java.text.*;
import eu.team.m4gkbeatz.BeatBukkitUI.console.*;

/**
 *
 * @author Simon
 */
public class Main {

    static File settings = new File("BBUI_settings.cbs");
    static File serverSettings = new File("BBUI_serverSettings.cbs");
    static File advancedSettings = new File("BBUI_advancedSettings.cbs");
    public static eu.team.m4gkbeatz.BeatBukkitUI.console.Console console = new eu.team.m4gkbeatz.BeatBukkitUI.console.Console();

    static String setDefs() {
        return "### BeatBukkitUI Settings File ###\n"
                + "### Please do not mess with these settings if you do not know what you are doing.\n"
                + "### These settings are provided as-is and with no warranty.\n"
                + "### Everything you do here (in- or outside the aplication, you do on your own behalf!\n"
                + "### Use with care.\n\n"
                + //
                "### Leave this set to false if you're happy the way everything is working!\n"
                + "useDefaults=true\n\n"
                + //
                "### Design of the UI ###\n"
                + "# Choose from:\n"
                + "# useNimbus\n"
                + "# useMetal\n"
                + "# useNativeLook\n"
                + "###                     ###\n"
                + "frameLaf=useNimbus\n\n"
                + //
                "### Advanced users only!\n"
                + "javaPath=winDir/system32/java.exe\n\n"
                + //
                "### Change the UI to load\n"
                + "uiLevel=basic\n\n"
                + //
                "### Change the program's theme ###\n"
                + "# Choose from:\n"
                + "# kde\n"
                + "# onebit\n"
                + "# ubuntu\n"
                + "# metro\n"
                + "# android\n"
                + "###              ###\n"
                + "theme=kde ## Please ignore this setting for now. It's only here so I don't forget it.";

    }

    static String setServerDefs() {
        return "### BeatBukkitUI Server Settings ###\n"
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
                + "bootOnStart=false\n\n"
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
                + "serverType=vanilla\n\n"
                //
                + "# This determines the server's filename. For example: craftbukkit.jar\n"
                + "# Please make sure BeatBukkitUI is in the SAME folder as your server filename!\n"
                + "fileName=craftbukkit.jar\n\n"
                //
                + "### EOF ###";

    }

    static String setAdvancedDefs() {
        return "### BeatBukkitUI Advanced Properties ###\n"
                + "# Only change these values if you know what you're doing!\n"
                + "# Some of these settings may be experimental and may break the program,\n"
                + "# or worse, your server!\n\n"
                //
                + "# Defines the UI to be loaded.\n"
                + "uiLevel=basic\n\n"
                //
                + "### EOF ###";
    }
    static Splash splash;
    static BasicUI basic;
    static AdvancedUI advanced = new AdvancedUI();
    
    /**
     * Program entry point.
     * @param args
     * @throws IOException 
     */
    public static void main(String args[]) throws IOException {
        // Set console visibility...
        console.append("All variables declared. Starting console...");
        console.setVisible(true);
        console.append("Console started. Continuing boot.");
        //splash.setVisible(true);
        console.append("Loading BeatBukkitUI...");
        // Load settings
        console.append("Loading and preparing server settings...");
        if (!serverSettings.exists()) {
           console.append("Server settings not found. Setting up default settings...");
            console.append("Creating file...");
            serverSettings.createNewFile();
            console.append("Writing settings...");
            writeDefs(serverSettings, setServerDefs());
            console.append("Settings were written. Continuing startup...");
        }
        console.append("Preparing advanced settings...");
        if (!advancedSettings.exists()) {
            console.append("Advanced settings file doesn't exist.");
            console.append("Creating new file...");
            advancedSettings.createNewFile();
            console.append("Writing default settings...");
            writeDefs(advancedSettings, setAdvancedDefs());
            console.append("Settings were written. Continuing startup...");
        }
        console.append("Loading preferences...");
        if (!settings.exists()) {
            settings.createNewFile();
            console.append("No preferences were found. Using default prefs.");
            int defaultSettings = writeDefs(settings, setDefs());
            if (defaultSettings == 0) {
                // Error occured
                JOptionPane.showMessageDialog(null, "ERROR: There was an error while writing the default settings to the file: \"" + settings.toString() + "\". Is the file being used by another process?", "Error Occurred While Writing Default Settings", JOptionPane.ERROR_MESSAGE);
                JOptionPane.showMessageDialog(null, "This application will now exit.", "Application will now Exit", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            } else {
                try {
                    // Settings were written correctly
                    loadUI();
                } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException | ParseException ex) {
                   console.append("ERROR: " + ex);
                }
            }
        } else {
            console.append("Preferences were found. Loading...");
            try {
                loadUI();
            } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException | ParseException ex) {
                console.append("ERROR: " + ex);
            }
        }
    }

    /**
     * Loads settings needed for UI then set UI visibility to true.
     */
    static void loadUI() throws IOException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        console.append("Loading splash screen...");
        //splash.setVisible(false);
        console.append("Declaring readers...");
        BufferedReader reader = new BufferedReader(new FileReader(settings));
        String readLine = null;
        console.append("Reading file...");
        while ((readLine = reader.readLine()) != null) {

            if (readLine.startsWith("useDefaults=")) {
                if (readLine.endsWith("true")) {
                   console.append("Using default settings.. Overriding custom preferences.");
                    console.append("Setting LAF...");
                    UIManager.setLookAndFeel(new com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel());
                    console.append("Loading UI...");
                    console.append("Declaring UI...");
                    basic = new BasicUI();
                    basic.useDefaults = true;
                    console.append("Setting visibility...");
                    basic.setVisible(true);
                    boolean isVisible = basic.isVisible();
                    console.append("UI loaded...");
                    return;
                }
            }
            if (readLine.startsWith("frameLaf=")) {
                if (readLine.endsWith("useNimbus")) {
                    UIManager.setLookAndFeel(new com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel());
                }
                if (readLine.endsWith("useMetal")) {
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                }
                if (readLine.endsWith("useNativeLook")) {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } else {
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                }
            }
        }
        reader.close();

        reader = new BufferedReader(new FileReader(advancedSettings));
        while ((readLine = reader.readLine()) != null) {
            if (readLine.startsWith("uiLevel=")) {
                if (readLine.endsWith("basic")) {
                    basic = new BasicUI();
                    basic.setVisible(true);
                } else if (readLine.endsWith("advanced")) {
                    advanced = new AdvancedUI();
                    advanced.setVisible(true);
                } else {
                    basic = new BasicUI();
                    basic.setVisible(true);
                }

            }
        }
    }

    /**
     * Writes default settings to settings file. File is always located in same
     * directory as program.
     *
     * @return
     */
    static int writeDefs(File file, String settings) {
        int returnVal = 0;
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            console.append("Please wait... Writing default settings to file...");
            writer.write(settings);
            console.append("Default settings written successfully.");
            returnVal = 1;
        } catch (Exception ex) {
            // Nothing to do here.
            // returnVal stays 0 = Error
        } finally {
            try {
                writer.close(); // Close writer to prevent memory leaks

            } catch (IOException ex) {
                console.append("ERROR: Error while writing default preferences!\n" + ex + "\nPlease report this error to the developer!");
            }
        }

        return returnVal;
    }

}
