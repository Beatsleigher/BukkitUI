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

package eu.m4gkbeatz.bukkitui.webserver;

import java.net.*;
import java.io.*;

import eu.m4gkbeatz.bukkitui.settings.SettingsManager;
import eu.m4gkbeatz.bukkitui.ui.BukkitUI;

/**
 * A simple web server, to allow for remote server controlling.
 * @author beatsleigher
 */
@SuppressWarnings({"StaticNonFinalUsedInInitialization"})
public final class WebServer {
    
    //# =============== BukkitUI Stuff =============== #\\
    private SettingsManager settings = null;
    private BukkitUI bukkitUI = null;
    private static boolean EXIT = false;
    //# =============== Webserver Stuff =============== #\\
    private static int SERVER_PORT = 23287;
    private static File SERVER_ROOT;  // = new File(".web/server/admin/remoteaccess");
    private static File SERVER_HOME; // = new File(SERVER_ROOT.getAbsolutePath() + "/index.html");
    private static File SERVER_ICON;// = new File(SERVER_ROOT.getAbsolutePath() + "/img/icon.png");
    private static File SERVER_LOG;// = new File(SERVER_ROOT.getAbsolutePath()  + "/log/websrv-" + new Random().nextInt(4096) + "-log.log");
    
    //# =============== Constructor(s) =============== #\\
    public WebServer() {}
    
    public WebServer(SettingsManager settings) { this.settings = settings; load(); }
    
    public WebServer(SettingsManager settings, BukkitUI bukkitUI) { this.settings = settings; this.bukkitUI = bukkitUI; load(); }
    
    //# =============== Loading Methods =============== #\\
    private void load() {
        SERVER_PORT = settings.webServer_getPort();
        SERVER_ROOT = settings.webServer_getServerRoot();
    }
}
