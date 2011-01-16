package com.bukkit.feverdream.murder;

import java.io.File;
import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Murder for Bukkit
 *
 * @author Feverdream
 */
public class Murder extends JavaPlugin {
	private String name;
	private String version;	
    private final MPlayerListener playerListener = new MPlayerListener(this);

    public Murder(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin,  ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);
        
        name = "Murder";
    	version = "v1.0 (Dexter)";
    }

    public void onEnable() {
		System.out.println(name + " " + version + " initializing...");
	    getServer().getPluginManager().registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Priority.Normal, this);
    	playerListener.loadSettings();
		System.out.println(name + " initialized!");
    }
    
    public void onDisable() {
		System.out.println(name + " " + version + " disabled.");
    }
}