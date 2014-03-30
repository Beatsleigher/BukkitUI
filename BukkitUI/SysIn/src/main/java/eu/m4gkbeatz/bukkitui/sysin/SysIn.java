/**
 * 
 */
package eu.m4gkbeatz.bukkitui.sysin;

import java.io.*;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

/**
 * @author beatsleigher
 *
 */
public class SysIn extends JavaPlugin {
	
	boolean receiveInput = true;
	
	@Override
	public void onEnable() {
		getLogger().info("Collecting standard input!");
		receiveInput = true;
		recordInput();
	}

	
	@Override
	public void onDisable() {
		getLogger().info("Ignoring standard input!");
		receiveInput = false;
	}
	
	private void recordInput() {
		new Thread() {
			@Override
                        @SuppressWarnings({"LoggerStringConcat", "SleepWhileInLoop", "BroadCatchBlock", "TooBroadCatch", "UnusedAssignment", "UseSpecificCatch", "ConvertToTryWithResources"})
			public void run() {
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
					String line = "";
					while (receiveInput) {
						line = reader.readLine();
						if (line != null) {
							if (line.startsWith("/")) {
								line = line.replace("/", "");
							}
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), line);
                                                        getLogger().finest("User Command: " + line);
						}
                                                Thread.sleep(200);
					}
					reader.close(); 
					line = null;
				} catch (Exception ex) {
					getLogger().severe("ERROR: An error occured while reading standard input!\n" + ex.toString());
					ex.printStackTrace(System.err);
				}
			}
		}.start();
	}
}
