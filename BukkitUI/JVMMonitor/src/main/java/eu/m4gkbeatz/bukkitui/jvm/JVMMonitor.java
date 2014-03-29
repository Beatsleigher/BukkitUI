package eu.m4gkbeatz.bukkitui.jvm;

import java.lang.management.ManagementFactory;

import org.bukkit.plugin.java.JavaPlugin;

import com.sun.management.OperatingSystemMXBean;

public class JVMMonitor extends JavaPlugin {
	
	////////////////
	// Variables///
	//////////////
	private boolean run = false;
	@SuppressWarnings("restriction")
	OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
	Runtime runtime = Runtime.getRuntime();
	long FREE_MEMORY = 0;
	long MAX_MEMORY = 0;
	long TOTAL_MEMORY = 0;
	long USED_MEMORY = 0;
	
	@Override
	public void onEnable() {
		getLogger().info("BukkitUI Plugin Enabled: JVM Monitor.");
		getLogger().fine("JVM Monitor starting logging...\nAny logs starting with [[JVM]] will be ignored by BukkitUI...");
		run = true;
		monitor();
	}
	
	private void monitor() {
		new Thread() {
                    @Override
                    @SuppressWarnings({"LoggerStringConcat", "SleepWhileInLoop"})
                    public void run() {
                        Runtime r = Runtime.getRuntime();
                        try {
                            while (run) {
                                print("[JVM] processCPULoad=" + (int) osBean.getSystemCpuLoad());
                                FREE_MEMORY = runtime.freeMemory() / 1024 / 1024;
                                MAX_MEMORY = runtime.maxMemory() / 1024 / 1024;
                                TOTAL_MEMORY = runtime.totalMemory() / 1024 / 1024;
                                USED_MEMORY = (TOTAL_MEMORY - FREE_MEMORY);
                                print("[JVM] freeMem=" + FREE_MEMORY);
                                print("[JVM] maxMem=" + MAX_MEMORY);
                                print("[JVM] totalMem=" + TOTAL_MEMORY);
                                print("[JVM] usedMem=" + USED_MEMORY);
                                Thread.sleep(2000);
                            }
                        } catch (InterruptedException ex) {
                            getLogger().severe("Error while monitoring Java virtual machine! Stack trace will be printed...\n" + ex.toString());
                            ex.printStackTrace(System.err);
                        }
                    }
		}.start();
	}
	
	@Override
	public void onDisable() {
            getLogger().fine("Disabling JVM Monitor...");
            run = false;
	}
	
	private void print(Object obj) {
            System.out.println(obj);
	}

}
