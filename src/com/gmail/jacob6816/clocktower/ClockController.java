package com.gmail.jacob6816.clocktower;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

public class ClockController implements CommandExecutor {

	private HashMap<UUID, SimpleTimer>	activeTimers;

	public ClockController(ClockTower clockTower) {
		activeTimers = new HashMap<UUID, SimpleTimer>();
		new BukkitRunnable() {
			public void run() {
				if (activeTimers.isEmpty()) return;
				for (SimpleTimer st : activeTimers.values())
					st.onSecond();
				updateScoreboards();
			}
		}.runTaskTimer(clockTower, 20, 20);
	}

	public void updateScoreboards() {
		for (UUID id : activeTimers.keySet()) {
			Bukkit.getPlayer(id).setScoreboard(activeTimers.get(id).getScoreboard());
		}
	}

	public void removeTimer(Player player) {
		player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		activeTimers.remove(player.getUniqueId());
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command commandBase, String cmd, String[] args) {
		if (!commandBase.getName().equals("ClockTower")) return false;
		if (args.length < 1) {
			sender.sendMessage("Missing arguments");
			return true;
		}
		if (sender instanceof Player) {
			sender.sendMessage("Commands may not be sent by players");
			return true;
		}
		cmd = args[0];
		args = removeArg(args);
		if (cmd.equals("stopall")) {
			if (activeTimers.isEmpty()) return true;
			Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
			for (UUID id : activeTimers.keySet())
				Bukkit.getPlayer(id).setScoreboard(sb);
			activeTimers.clear();
			return true;
		}

		if (args.length < 1) {
			sender.sendMessage("Missing arguments");
			return true;
		}

		if (cmd.equals("pause")) {
			args = NotationHandler.appendNotations(sender, args, activeTimers.keySet());
			boolean unpause = args[0].equalsIgnoreCase("-u");
			for (int i = (unpause ? 0 : 1); i < args.length; i++) {
				Player player = Bukkit.getPlayer(args[i]);
				if (!activeTimers.containsKey(player.getUniqueId())) continue;
				activeTimers.get(player.getUniqueId()).setPaused(!unpause);
			}
			return true;
		}
		if (cmd.equals("stop")) {
			args = NotationHandler.appendNotations(sender, args, activeTimers.keySet());
			for (int i = 0; i < args.length; i++) {
				Player player = Bukkit.getPlayer(args[i]);
				if (activeTimers.containsKey(player.getUniqueId())) {
					player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
					activeTimers.remove(player);
				}
			}
			return true;
		}

		if (args.length < 2) {
			sender.sendMessage("Missing arguments");
			return true;
		}

		if (cmd.equals("start")) {
			args = NotationHandler.appendNotations(sender, args, activeTimers.keySet());
			long seconds = Long.parseLong(args[0]);
			for (int i = 1; i < args.length; i++) {
				Player _p = Bukkit.getPlayer(args[i]);
				if (_p == null) continue;
				UUID id = _p.getUniqueId();
				if (!activeTimers.containsKey(id)) {
					SimpleTimer st = new SimpleTimer(seconds);
					activeTimers.put(id, st);
					continue;
				}
				activeTimers.get(id).setTime(seconds);
			}
			return true;
		}
		if (cmd.equals("add")) {
			args = NotationHandler.appendNotations(sender, args, activeTimers.keySet());
			long seconds = Long.parseLong(args[0]);
			for (int i = 1; i < args.length; i++) {
				Player _p = Bukkit.getPlayer(args[i]);
				if (_p == null) continue;
				UUID id = _p.getUniqueId();
				if (!activeTimers.containsKey(id)) {
					SimpleTimer st = new SimpleTimer(seconds);
					activeTimers.put(id, st);
					continue;
				}
				activeTimers.get(id).addTime(seconds);
			}
			return true;
		}
		if (cmd.equals("set")) {
			args = NotationHandler.appendNotations(sender, args, activeTimers.keySet());
			long seconds = Long.parseLong(args[0]);
			for (int i = 1; i < args.length; i++) {
				Player _p = Bukkit.getPlayer(args[i]);
				if (_p == null) continue;
				UUID id = _p.getUniqueId();
				if (!activeTimers.containsKey(id)) {
					SimpleTimer st = new SimpleTimer(seconds);
					activeTimers.put(id, st);
					continue;
				}
				activeTimers.get(id).setTime(seconds);
			}
			return true;
		}
		if (cmd.equals("get")) {
			args = NotationHandler.appendNotations(sender, args, activeTimers.keySet());
			Player player = Bukkit.getPlayer(args[0]);
			if (player == null) return true;
			for (UUID id : activeTimers.keySet()) {
				Player temp = Bukkit.getPlayer(id);
				String name = temp.getName();
				StringBuilder builder = new StringBuilder(name);
				for (int i = 0; i < (17 - name.length()); i++)
					builder.append(" ");
				builder.append(" | ");
				SimpleTimer st = activeTimers.get(id);
				builder.append(st.getTimeString());
				if (st.isPaused()) builder.append(" [paused]");
				player.sendMessage(builder.toString().trim());
			}
			return true;
		}
		return false;
	}

	private String[] removeArg(String[] args) {
		return Arrays.copyOfRange(args, 1, args.length);
	}
}
