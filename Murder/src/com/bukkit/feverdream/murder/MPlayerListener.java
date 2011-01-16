package com.bukkit.feverdream.murder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

/**
 * Handle events for all Player related events
 * @author Feverdream
 */
public class MPlayerListener extends PlayerListener {
    private final Murder plugin;
	private ArrayList<String> licensed = new ArrayList<String>();

    public MPlayerListener(Murder instance) {
        plugin = instance;
    }
	
    private static String config_comment = "Murder Configuration File.";
    
    public void saveDefaultSettings(){
		Properties props = new Properties();
		props.setProperty("can-murder","Notch,feverdream,your_usernames_replacing_all_of_this");
		try{
			OutputStream propOut = new FileOutputStream(new File("murder.properties"));
			props.store(propOut, config_comment);
		} catch (IOException ioe) {
			System.out.print(ioe.getMessage());
		}
    }
    
    public void loadSettings(){
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("murder.properties"));
			if (props.containsKey("can-murder")){
				String suspects = props.getProperty("can-murder","");
				if(suspects.length() > 0){
					String[] killers = suspects.toLowerCase().split(",");
					if (killers.length > 0)
					{
						licensed =  new ArrayList<String>(Arrays.asList(killers));
						for (String u : licensed) {
						    System.out.print("Murder: "+ u + " has a license to kill.\n");
						}
					}else{
						this.saveDefaultSettings();
					}
				}else{
					this.saveDefaultSettings();
				}
			}else{
				this.saveDefaultSettings();
			}
		} catch (IOException ioe) {
			this.saveDefaultSettings();
		}
    }
        
	public void onPlayerCommand(PlayerChatEvent event)
	{
		Player player = event.getPlayer();
		String hit[] = event.getMessage().replace("  "," ").split(" ");
		
		if(hit[0].equalsIgnoreCase("/murder"))
		{
		      if( licensed.isEmpty() ){
		            player.sendMessage("/murder called with no allowed users on record.");
		            return;
		      }

			 if( licensed.contains( player.getName().toLowerCase() ) ){
				 if(hit.length == 1){
					 player.sendMessage("Murder: No Playername given.");
				 }else{
					Player[] players = plugin.getServer().getOnlinePlayers();
					boolean killed = false;
					for( int i = 1; i < hit.length; ++i){
						killed = false;
						for (int k = 0; k < players.length; ++k) {
							if( players[k].getName().equalsIgnoreCase(hit[i].trim()) ){
								player.sendMessage("You ruthlessly murder " + players[k].getName() + ".");
								players[k].setHealth(0);
								killed = true;
							}
						}
						
						if(!killed){
							player.sendMessage("User '" + hit[i] + "' not found logged in.");
						}
					}

					players = null;
				 }
			 }else{
				 player.sendMessage("You are not licensed to kill.");
			 }			
		}
	}
}