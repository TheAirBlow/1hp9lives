package net.theairblow.catsurv.listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.theairblow.catsurv.Main;
import net.theairblow.catsurv.Manager;

public class PlayerDeathListener implements Listener {
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		Player player = event.getEntity();
		String name = player.getName();
		int hp = Manager.players.get(name);
		hp--;
		Main.main.getConfig().set("players." + player.getName(), hp);
		if (hp <= 0) {
			player.setGameMode(GameMode.SPECTATOR);
			event.setDeathMessage(ChatColor.RED + "Player " + name + " died and ran out of lives!");
		} else {
			event.setDeathMessage(ChatColor.RED + "Player " + name + " died. " + hp + " lives left.");
		}
	}
}
