package com.bukkit.feverdream.noon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/*
 *
# Set the duration length for each part of the day (default: 1000)
day-length=2000
sunset-length=1000
night-length=500
sunrise-length=1000

# Set the overall cycle speed (default: 100)
time-speed=100

# Set the starting point of the cycle (default: day)
starting-point=night

# If true, the time is synchronize with real time (default: false)
sync-time=false
 */
public class Settings {    
    public static String config_file = "noon.settings";
    public static String config_comment = "Noon Control Configuration File.";
    public static String config_value_start_day  = "always-time";
    public static int config_value_start_day_default= 0;
    public static String config_value_start_day_default_str= "day";
    
    public int dayStart = config_value_start_day_default;;
    
    public void saveDefaultSettings(){
        Properties props = new Properties();
        
        props.setProperty( config_value_start_day, ""+config_value_start_day_default_str );
                
        try{
            OutputStream propOut = new FileOutputStream(new File(config_file));
            props.store(propOut, config_comment);
        } catch (IOException ioe) {
            System.out.print(ioe.getMessage());
        }
    }

    public void saveSettings(){
        Properties props = new Properties();
        
        if( dayStart == 0) {
            props.setProperty(config_value_start_day, "day" );
        }else if( dayStart == 12000) {
            props.setProperty(config_value_start_day, "sunset" );            
        }else if( dayStart == 13800) {
            props.setProperty(config_value_start_day, "night" );
        }else if( dayStart == 22200) {
            props.setProperty(config_value_start_day, "sunrise" );
        }else {
            props.setProperty(config_value_start_day,  "day" );
        }
                                   
        try{
            OutputStream propOut = new FileOutputStream(new File(config_file));
            props.store(propOut, config_comment);
        } catch (IOException ioe) {
            System.out.print(ioe.getMessage());
        }
    }
    
    public Boolean loadSettings(){
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(config_file));

            String tmp = props.getProperty(config_value_start_day , config_value_start_day_default_str).trim().toLowerCase();
            
            if( tmp.equalsIgnoreCase("day")) {
                dayStart = 0;
            }else if( tmp.equalsIgnoreCase("sunset")) {
                dayStart = 12000;
            }else if( tmp.equalsIgnoreCase("night")) {
                dayStart = 13800;
            }else if( tmp.equalsIgnoreCase("sunrise")) {
                dayStart = 22200;
            }else {
                dayStart = 0;
            }
            
            return true;
        } catch (IOException ioe) {
            props.clear();
            this.saveDefaultSettings();
            return false;
        }
    }
}
