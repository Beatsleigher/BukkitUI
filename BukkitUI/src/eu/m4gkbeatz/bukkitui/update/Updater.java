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

package eu.m4gkbeatz.bukkitui.update;

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

/**
 * BukkitUI Updater class.
 * @author beatsleigher
 */
public class Updater {
    
    URL curVerFile = null;
    URL latestVerFile = null;
    BufferedReader fileReader = null;
    //========== Integers ==========\\
    double ver = 0;
    //\\
    double newVer = 0;
    
    public Updater() throws MalformedURLException {
        curVerFile = this.getClass().getResource("/eu/m4gkbeatz/bukkitui/resources/versionControl/bukkitui.ver");
        latestVerFile = new URL("https://raw.githubusercontent.com/Beatsleigher/BukkitUI/master/BukkitUI/versionControl/bukkitUI.ver");
        
    }
    
    public void checkForUpdates() throws IOException {
        System.out.println("Reading internal file...");
        fileReader = new BufferedReader(new InputStreamReader(curVerFile.openStream()));
        String line = fileReader.readLine();
        String[] array = line.split(": ");
        ver = Double.valueOf(array[1]);
        System.out.println("Data parsed...");
        System.out.println("Reading external file...");
        fileReader.close();
        fileReader = new BufferedReader(new InputStreamReader(latestVerFile.openStream()));
        line = fileReader.readLine();
        array = line.split(": ");
        newVer = Double.valueOf(array[1]);
        fileReader.close();
        System.out.println("All data read...");
        if (ver >= newVer) {
            System.out.println("No newer versions available...");
        } else {
            System.out.println("New version available!\nCurrent Version: " + ver + "\nNew Version: " + newVer);
            int res = JOptionPane.showConfirmDialog(null, "INFORMATION: A new update to BukkitUI is available!\n"
                    + "This update contains bug fixes, new features and (possibly) speed improvement. Do you wish to install?\n"
                    + "(This message will annoy you on every startup until you update... :p)", "Update BukkitUI?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (res == JOptionPane.OK_OPTION) 
                downloadUpdate();
        }
    }
    
    private void downloadUpdate() {
        System.out.println("Downloading update. Please wait...");
        
    }
    
}
