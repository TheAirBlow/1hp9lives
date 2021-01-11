package net.theairblow.catsurv.listeners;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.theairblow.catsurv.Main;
import net.theairblow.catsurv.Manager;

public class PlayerJoinListener implements Listener {
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        if (Main.isEnabled)
        {
        	Player player = event.getPlayer();
        	player.setHealth(Main.hp);
        	player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Main.hp);
        	
        	if (Manager.players.get(player.getName()).equals(null)) {
        		Manager.players.put(player.getName(), Main.lives);
    			Main.main.getConfig().set("players." + player.getName(), Main.lives);
        	}
        	
        	player.sendMessage(ChatColor.AQUA + "Welcome! You have " + Manager.players.get(player.getName()) + " lives left.");
        }
    }
}
