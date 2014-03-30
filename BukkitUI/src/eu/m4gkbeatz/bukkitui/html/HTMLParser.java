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

package eu.m4gkbeatz.bukkitui.html;

import java.net.URL;
import java.io.*;
import java.util.*;

/**
 * HTML Web Page Parser.
 * @author beatsleigher
 */
public class HTMLParser {
    
    private URL url = null;
    private BufferedReader pageReader = null;
    private int noOfPages = 1;
    
    public HTMLParser() {}
    
    public HTMLParser(URL url) {
        this.url = url;
    }
    
    /**
     * Parses HTML web page and returns a {@code java.util.HashMap} containing the data from found matches.
     * @param match1 A string containing match no. 1
     * @param match2 A string containing match no. 2 (for more accurate matches)
     * @return Returns a {@code java.util.HashMap} containing the data from the matches
     * @throws IOException In the rare case that something goes wrong (It turns out to be more common when you have a typo in the URL).
     */
    public HashMap<String, URL> parseFile(String match1, String match2) throws IOException {
        HashMap<String, URL> map = new HashMap<>();
        String line = null;
        
        pageReader = new BufferedReader(new InputStreamReader(url.openStream()));
        while ((line = pageReader.readLine()) != null) {
            if (line.contains(match1) && line.contains(match2)) {
                String[] array = line.split("title=\"");
                array = array[1].split("\"");
                String[] array0 = line.split("href=\"");
                array0 = array0[1].split("\"");
                map.put(array[0], new URL("http://dl.bukkit.org" + array0[0]));
            }
            if (line.startsWith("        <a href=\"?page=") && line.contains("class=\" page\">") && line.endsWith("</a>")) {
                noOfPages++;
                System.out.println("Found page " + noOfPages);
            }
        }
        System.out.println("Pages found: " + noOfPages);
        String oldURL = url.toString();
        for (int i = 2; i < noOfPages; i++) {
            url = new URL(oldURL + "?page=");
            url = new URL(url.toString() + i);
            System.out.println("Parsing information from page " + url.toString());
            pageReader = new BufferedReader(new InputStreamReader(url.openStream()));
        while ((line = pageReader.readLine()) != null) {
            if (line.contains(match1) && line.contains(match2)) {
                String[] array = line.split("title=\"");
                array = array[1].split("\"");
                String[] array0 = line.split("href=\"");
                array0 = array0[1].split("\"");
                map.put(array[0], new URL("http://dl.bukkit.org" + array0[0]));
            }
        }
        }
        
        return map;
    }
    
}
