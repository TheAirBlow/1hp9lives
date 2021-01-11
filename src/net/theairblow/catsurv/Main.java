package net.theairblow.catsurv;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.theairblow.catsurv.listeners.PlayerDeathListener;

public class Main extends JavaPlugin {
	public static Main main;
	public static boolean isEnabled = false;
	public static boolean canGiveUp = true;
	public static int hp = 1;
	public static int lives = 9;
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
		main = this;
		saveDefaultConfig();
		isEnabled = getConfig().getBoolean("auto-start");
		canGiveUp = getConfig().getBoolean("can-give-up");
		hp = getConfig().getInt("hp");
		lives = getConfig().getInt("lives");
		Manager.loadHashMap();
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.main, new Runnable() {
            @Override
            public void run() {
                if (Main.isEnabled) {
                	for (Player plr : Bukkit.getServer().getOnlinePlayers())
            		{
                		if (plr.isOp()) continue;
                		if (Manager.players.get(plr.getName()).equals(null)) {
                			Manager.players.put(plr.getName(), Main.lives);
                			getConfig().set("players." + plr.getName(), Main.lives);
                		}
                		plr.setDisplayName(plr.getName() + ChatColor.AQUA + " " + Manager.players.get(plr.getName()));
            		}
                } else {
                	for (Player plr : Bukkit.getServer().getOnlinePlayers())
            		{
                		if (plr.isOp()) continue;
                		plr.setDisplayName(plr.getName());
            		}
                }
            }
        },0, 20*1);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("catsurv")) {
			if (args.length < 1)
			{
				sender.sendMessage(ChatColor.AQUA + "1hp9lives Commands:"
						+ "\n/catsurv start - Start 1hp9lives."
						+ "\n/catsurv stop - Stop 1hp9lives."
						+ "\n/catsurv reload - Reload 1hp9lives config."
						+ "\n/catsurv giveup - Lose all of your lives and die.");
				return true;
			}
			
			switch (args[0])
			{
				case "start":
					if (sender instanceof Player) {
						Player plr = (Player)sender;
						if (!plr.isOp()) {
							Bukkit.getServer().broadcastMessage(ChatColor.RED + "Error: You is not an operator.");
						} else {
							Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "1hp9lives challenge has been started!");
							Manager.start();
							isEnabled = true;
						}
					} else {
						Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "1hp9lives challenge has been started!");
						Manager.start();
						isEnabled = true;
					}
					break;
				case "stop":
					if (sender instanceof Player) {
						Player plr = (Player)sender;
						if (!plr.isOp()) {
							Bukkit.getServer().broadcastMessage(ChatColor.RED + "Error: You is not an operator.");
						} else {
							Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "1hp9lives challenge has been stopped!");
							Manager.stop();
							isEnabled = false;
						}
					} else {
						Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "1hp9lives challenge has been stopped!");
						Manager.stop();
						isEnabled = false;
					}
					break;
				case "reload":
					if (sender instanceof Player) {
						Player plr = (Player)sender;
						if (!plr.isOp()) {
							Bukkit.getServer().broadcastMessage(ChatColor.RED + "Error: You is not an operator.");
						} else {
							saveDefaultConfig();
							isEnabled = getConfig().getBoolean("auto-start");
							canGiveUp = getConfig().getBoolean("can-give-up");
							hp = getConfig().getInt("hp");
							lives = getConfig().getInt("lives");
							Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "Config was reloaded successfully.");
						}
					} else {
						saveDefaultConfig();
						isEnabled = getConfig().getBoolean("auto-start");
						canGiveUp = getConfig().getBoolean("can-give-up");
						hp = getConfig().getInt("hp");
						lives = getConfig().getInt("lives");
						Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "Config was reloaded successfully.");
					}
					break;
				case "giveup":
					if (sender instanceof Player) {
						Player plr = (Player)sender;
						if (isEnabled && canGiveUp) {
							Manager.players.replace(plr.getName(), 0);
							plr.setHealth(0);
							Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "Player " + plr.getName() + " gave up.");
						} else {
							Bukkit.getServer().broadcastMessage(ChatColor.RED + "Error: Game is not started yet or gave up was disabled.");
						}
					} else {
						Bukkit.getServer().broadcastMessage(ChatColor.RED + "Error: You is not a player.");
					}
					break;
			}
			
			return true;
		}
        return false;
    }
}
