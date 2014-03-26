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

package eu.m4gkbeatz.bukkitui.logging;

import java.util.Date;
import java.io.*;

/**
 * Logs all occurrences in BukkitUI.
 * @author beatsleigher
 */
@SuppressWarnings({"StringConcatenationInsideStringBufferAppend"})
public class Logger {
    
    // Constants
    static final Object FINE = "[FINE]", FINER = "[FINER]", FINEST = "[FINEST]", INFO = "[INFO]", SEVERE = "[SEVERE]", WARNING = "[WARNING]";
    static final File logDir = new File(System.getProperty("user.home") + "/.bukkitui/logs");
    
    StringBuilder log = null;
    Date d = new Date();
    
    public Logger() {
        log = new StringBuilder();
        if (!logDir.exists())
            logDir.mkdirs();
    }
    
    
    public void log(Object lvl, String msg) {
        log.append("→ [" + d.toString() + "] :: " + lvl + " :: " + msg + "\n");
    }
    
    public void save() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(logDir.getAbsolutePath() + "/" + d.getDate() + "-log.log")));
        writer.write(log.toString());
        writer.close();
    }
    
}
