/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.m4gkbeatz.bukkitui.main;

import eu.m4gkbeatz.bukkitui.logging.Logger;
import eu.m4gkbeatz.bukkitui.settings.SettingsManager;
import eu.m4gkbeatz.bukkitui.ui.BukkitUI;
import eu.m4gkbeatz.bukkitui.update.Updater;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import joxy.JoxyLookAndFeel;

/**
 * BukkitUI's entry-class.
 * Everything from settings management and dependency installation is handled here.
 * @author beatsleigher
 */
@SuppressWarnings({"TooBroadCatch", "UseSpecificCatch"})
public class Main {
    
    public static void main(String[] args) {
        try {
            UIManager.put("TabbedPane.contentOpaque", false);
            UIManager.setLookAndFeel("joxy.JoxyLookAndFeel");
            JFrame.setDefaultLookAndFeelDecorated(false);
            JDialog.setDefaultLookAndFeelDecorated(true);
            System.setProperty("jsse.enableSNIExtension", "false");
            
            Logger log = new Logger();
            SettingsManager settings = new SettingsManager();
            
            try {
            if (settings.checkForUpdates())
                new Updater().checkForUpdates();
            } catch (Exception ex) { System.err.println("ERROR: Could not download updates!"); ex.printStackTrace(System.err); }
            
            BukkitUI ui = new BukkitUI(settings, log);
            ui.setVisible(true);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "ERROR: Error while starting BukkitUI!\n"
                    + "Type of error: " + ex.toString() + "\n"
                    + "BukkitUI will now exit.", "Error While Starting", JOptionPane.ERROR_MESSAGE);
            System.err.println("ERROR: Error while starting BukkitUI!");
            System.err.println(ex.toString());
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }
    
}
