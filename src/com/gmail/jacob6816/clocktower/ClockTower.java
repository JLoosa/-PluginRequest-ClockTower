package com.gmail.jacob6816.clocktower;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ClockTower extends JavaPlugin implements Listener {

	private ClockController	cc;

	@Override
	public void onEnable() {
		cc = new ClockController(this);
		getCommand("ClockTower").setExecutor(cc);
		super.onEnable();
	}

	@Override
	public void onDisable() {
		HandlerList.unregisterAll((Plugin) this);
		if (!Bukkit.getOnlinePlayers().isEmpty())
			for (Player player : Bukkit.getOnlinePlayers())
				cc.removeTimer(player);
		super.onDisable();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onDisconnect(PlayerQuitEvent event) {
		cc.removeTimer(event.getPlayer());
	}

}
