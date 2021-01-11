package net.theairblow.catsurv;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class Manager {
	public static Map<String, Integer> players = new HashMap<String, Integer>();
	
	@SuppressWarnings("unchecked")
	public static void loadHashMap()
	{
		try {
			players = (Map<String, Integer>) Main.main.getConfig().getMapList("players");
		} catch (Exception e) {
			Main.main.getLogger().warning("Can't load players' lives HashMap!");
		}
	}
	
	public static void start()
	{
		for (Player plr : Bukkit.getServer().getOnlinePlayers())
		{
			if (plr.isOp()) continue;
			plr.setHealth(Main.hp);
			plr.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Main.hp);
			players.put(plr.getName(), Main.lives);
			Main.main.getConfig().set("players." + plr.getName(), Main.lives);
		}
	}
	
	public static void stop()
	{
		players.clear();
		
		for (Player plr : Bukkit.getServer().getOnlinePlayers())
		{
			plr.setHealth(20);
			plr.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
		}
	}
}
