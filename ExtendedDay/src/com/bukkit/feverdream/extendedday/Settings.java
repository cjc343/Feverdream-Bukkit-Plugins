package com.bukkit.feverdream.extendedday;
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
    public static String config_file = "extendday.settings";
    public static String config_comment = "Extend Day Control Configuration File.";
    public static String config_value_day_length= "daylight-days-long";
    public static String config_value_start_day  = "start-time";
    public static int config_value_start_day_default= 0;
    public static String config_value_start_day_default_str= "day";
    public static int config_value_day_length_default = 1;
    
    public int dayLength = config_value_day_length_default;;
    public int dayStart = config_value_start_day_default;;
    
    public void saveDefaultSettings(){
        Properties props = new Properties();
        
        props.setProperty(config_value_day_length, ""+config_value_day_length_default);
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
        props.setProperty(config_value_day_length, ""+dayLength );
        
        if( dayStart == 0) {
            props.setProperty(config_value_start_day, "day" );
        }else if( dayStart == 12000) {
            props.setProperty(config_value_start_day, "sunset" );            
        }else if( dayStart == 13800) {
            props.setProperty(config_value_start_day, "night" );
        }else if( dayStart == 22200) {
            props.setProperty(config_value_start_day, "sunrise" );
        }else {
            props.setProperty(config_value_start_day,  ""+dayStart );
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
            dayLength = (int)Long.parseLong( props.getProperty(config_value_day_length, ""+config_value_day_length_default) );
            if(dayLength < 1){
                System.out.print("[EXTENDED-DAY PLUGIN NOTICE] Configuration value was under 0 (Zero). Setting to default of 1. Check your configuration file!");
                dayLength = 1;
            }

            String tmp = props.getProperty(config_value_start_day , config_value_start_day_default_str).toLowerCase().trim();
            
            if( tmp == config_value_start_day_default_str) { // day
                dayStart = 0;
            }else if( tmp == "sunset") {
                dayStart = 12000;
            }else if( tmp == "night") {
                dayStart = 13800;
            }else if( tmp == "sunrise") {
                dayStart = 22200;
            }else {
                try {
                    dayStart = Integer.parseInt(tmp);
                }catch(NumberFormatException nfe) {
                    dayStart = 0;
                }
            }
            
            return true;
        } catch (IOException ioe) {
        	props.clear();
            this.saveDefaultSettings();
            return false;
        }
    }
}
