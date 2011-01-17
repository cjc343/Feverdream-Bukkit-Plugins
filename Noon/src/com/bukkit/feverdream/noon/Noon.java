package com.bukkit.feverdream.noon;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import org.bukkit.Server;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.*;

class SunShine extends TimerTask {
	public Server server = null;
    public Settings settings = null;
	public long wantedTime = 0;

	public void run() {
        long time = server.getTime();
        long relativeTime = time % 24000;
        long startOfDay = time - relativeTime;
		// Day lasts 13000 ticks, so half that is Noon.
        // There are 20 ticks per real-life second,
        // making the day/night cycle 1200 seconds (or 20 minutes) long.
        // and 24000 ticks per Minecraft day
        // 24000 ticks is 20 minutes
        // 0 appears to be sunrise
        // 12000 sunset and
        // 24000 sunrise again.
        // so..
        // daytime lasts 10 minutes
        // Sunset lasts 90 seconds
        // Nights lasts 7 minutes
        // Sunrise 90 seconds
        if( relativeTime > 12000 && settings.dayStart == 0) { // if day just finished and settings are day
            server.setTime(startOfDay + 0); // set day
        }else if(relativeTime > 13800 && settings.dayStart == 12000) { // if sunset just finished and settings are sunset
            // set sunset
            server.setTime(startOfDay+12000);
        }else if(relativeTime > 22200 && settings.dayStart == 13800) {// if night just finished and settings are night
            // set night
            server.setTime(startOfDay+13800);
        }else if(relativeTime > 0 && settings.dayStart == 22200) {// if sunrise just finished and settings are sunrise
            // set sunrise
            server.setTime(startOfDay+22200);
        }
         
	}	
}

public class Noon extends JavaPlugin {
//    private final NPlayerListener playerListener = new NPlayerListener(this);
    private Timer tick = null;
	private int rate = 1000;
   
	public Noon(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin,  ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);        
	}

	public void onEnable() {
		SunShine ss = new SunShine();
		ss.server = getServer();
		ss.settings = new Settings();
		
		if(ss.settings.loadSettings()) {
		    tick = new Timer();
		    tick.schedule(ss, 0, rate);
		    //getServer().getPluginManager().registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Priority.Normal, this);

		    System.out.println("Noon (v1.2 by Feverdream) is on.");
		}else {
            System.out.println("Configure your Noon.settings file and restart.");
            System.out.println("The key you need to configure is called 'always-time'.");
            System.out.println("The key values Noon supports are day,sunset,night, and sunrise.");
		}
	}

	public void onDisable() {
		if (tick != null) {
			tick.cancel();
			tick = null;
		}
		System.out.println("Noon (v1.2 by Feverdream) is off.");
	}	
}
