
package com.bukkit.feverdream.noon;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;


public class NPlayerListener extends PlayerListener{
    private final Noon plugin;

    public NPlayerListener(Noon instance) {
        plugin = instance;
    }

    public void onPlayerCommand(PlayerChatEvent event)
    {
        plugin.toString();
        return;
    }
}
