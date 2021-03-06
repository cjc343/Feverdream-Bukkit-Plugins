//This file is GNU AGPL Version 3 or higher. - Feverdream
//this file was modified by cjc on feb 6
//this file was modified by cjc on feb 7
//this file was modified by cjc on feb 10
//this file was modified by cjc on feb 28
package cjcfork.feverdream.extendedday;

//import java.io.File;
import java.util.Timer;
import org.bukkit.Server;
//import org.bukkit.plugin.PluginDescriptionFile;
//import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.TimerTask;

class SunShine extends TimerTask {
	public Server server = null;
	//no longer public
	public int daysLeft = -1;
	public Settings settings;

	public void run() {
        long time = server.getWorlds().get(0).getTime();
        long relativeTime = time % 24000;
        long startOfDay = time - relativeTime;
		// Day lasts 13000 ticks, so half that is Noon.
        // There are 20 ticks per real-life second,
        // making the day/night cycle 1200 seconds (or 20 minutes) long..
        // and 24000 ticks per Minecraft day
        // Noon is late in MC.
        // 24000 ticks is 20 minutes
        // 0 appears to be sunrise
        // 12000 sunset and
        // 24000 sunrise again.
        // so..
        // daytime lasts 10 minutes, or 600 seconds, or 12000 ticks
        // Sunset lasts 90 seconds, or 1800 ticks. SetTime Operations during sunset create massive lag. Don't do it.
        // Nights lasts 7 minutes or 420 seconds, or 8400 ticks
        // Sunrise 90 seconds or 1800 ticks. SetTime Operations during sunrise create massive lag. Don't do it.
        //
        // Time can be set into 40 different 30 second slots.

        if( relativeTime == 0 || (relativeTime > 0 && relativeTime <= 12000)){
   
			// Day
        	
         	//added by cjc, feb 7
        	//no longer needed feb 10?
//			if (daysLeft == -1) {
//				daysLeft = this.settings.dayLength;
//			}

        	// Do nothing.
        	// server.setTime(startOfDay);
        }else if(relativeTime > 12000 && relativeTime <= 13800){
        	// sunset
        	if(daysLeft-- > 1){
        		server.getWorlds().get(0).setTime(startOfDay);
        	}
        }else if(relativeTime > 13800 && relativeTime <= 22200){
        	// nights
        	// daysLeft is 0x00000000 at this stage.
        	daysLeft = this.settings.dayLength;
        }else if(relativeTime > 22200 && relativeTime <= 24000){
            // sunrise
        	// Do nothing.. for now.
        }
	}
}

public class ExtendedDay extends JavaPlugin {

	private Timer tick = null;
	private int rate = 1000; // 1 second.
	private String name;
	private String version;	



	public void onEnable() {
		 name = "ExtendFork";
	    version = "a fork of ExtendDay by feverdream";
		System.out.println(name + " " + version + " initialized. This is a DEV build by cjc for QQ bukkit unk. recent standard for other plugins.");
		SunShine ss = new SunShine();
		ss.server = getServer();
		ss.settings = new Settings();
		if( ss.settings.loadSettings()){
	        long time = ss.server.getWorlds().get(0).getTime();
	        
	        // Set day start from configuration.
	        ss.server.getWorlds().get(0).setTime( (time - (time % 24000)) + ss.settings.dayStart);

	        //cjc more efficient setting test feb 10
	        ss.daysLeft = ss.settings.dayLength;
	        
			tick = new Timer();
			tick.schedule(ss, 0, rate);
		}else{
			System.out.println("======================= Extend-Day Plugin Notice =======================");
			System.out.println("Please setup your configuration file ("+ Settings.config_file+") so that");
			System.out.println("the key value '"+ Settings.config_value_day_length+"' is set to a number");
            System.out.println("telling the plugin the number of day iterations you want before each night.");
            System.out.println("and set the key value '"+Settings.config_value_start_day+ "'to set the time your server should start in.");
            System.out.println("Valid '"+Settings.config_value_start_day+ "' values are day,night, sunset, sunrise, or a raw numarical time in the RAWTIME format.");            
			System.out.println("========================================================================");
		}
	}

	public void onDisable() {
		if (tick != null) {
			tick.cancel();
			tick = null;
		}
		System.out.println(name + " " + version + " disabled.");
	}	
}
