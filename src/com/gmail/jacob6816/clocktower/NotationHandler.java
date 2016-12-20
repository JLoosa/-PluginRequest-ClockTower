package com.gmail.jacob6816.clocktower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.github.ZombieStriker.CommandUtils;

public class NotationHandler {

	private static Random	random;

	static {
		random = new Random();
	}

	private NotationHandler() {
	}

	public static String[] appendNotations(CommandSender sender, String[] args, Set<UUID> playerSet) {
		int lastPos = args.length - 1;
		if (!args[lastPos].startsWith("@")) return args;
		String[] tempArr = filterToPlayers(CommandUtils.getTargets(sender, args[lastPos]));
		if (tempArr == null || tempArr.length == 0) {
			char target = args[lastPos].charAt(1);
			tempArr = getReturnArray(target, playerSet);
		}
		if (tempArr == null) return args;
		String[] newArr = new String[args.length + tempArr.length - 1];
		int pos = 0;
		for (String s : args) {
			newArr[pos] = s;
			pos++;
		}
		pos = lastPos;
		for (String s : tempArr) {
			newArr[pos] = s;
			pos++;
		}
		return newArr;
	}

	public static String[] filterToPlayers(Entity... entities) {
		List<String> list = new ArrayList<String>();
		for (Entity e : entities)
			if (e instanceof Player)
				list.add(((Player) e).getName());
		return list.toArray(new String[list.size()]);
	}

	public static String[] getReturnArray(char key, Set<UUID> playerSet) {
		if (key == 'g') return getLimitedPlayerList(playerSet);
		if (key == 'G') return getPlayerList();
		if (key == 't') return getRandomLimitedPlayer(playerSet);
		if (key == 'T') return getRandomPlayer();
		return null;
	}

	private static String[] getLimitedPlayerList(Set<UUID> playerSet) {
		if (playerSet.isEmpty()) return null;
		String[] playerArr = new String[playerSet.size()];
		int pos = 0;
		for (UUID id : playerSet) {
			playerArr[pos] = Bukkit.getOfflinePlayer(id).getName();
			pos++;
		}
		return playerArr;
	}

	private static String[] getPlayerList() {
		String[] playerArr = new String[Bukkit.getOnlinePlayers().size()];
		int pos = 0;
		for (Player player : Bukkit.getOnlinePlayers()) {
			playerArr[pos] = player.getName();
			pos++;
		}
		return playerArr;
	}

	private static String[] getRandomLimitedPlayer(Set<UUID> playerSet) {
		if (playerSet.isEmpty()) return null;
		UUID[] ids = playerSet.toArray(new UUID[playerSet.size()]);
		int playerInt = random.nextInt(ids.length);
		return new String[] { Bukkit.getOfflinePlayer(ids[playerInt]).getName() };
	}

	private static String[] getRandomPlayer() {
		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		Player[] ids = players.toArray(new Player[players.size()]);
		int playerInt = random.nextInt(ids.length);
		return new String[] { ids[playerInt].getName() };
	}
}
