package com.gmail.jacob6816.clocktower;

import java.util.Collection;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NotationHandler {

	private static Random	random;

	static {
		random = new Random();
	}

	private NotationHandler() {
	}

	public static String[] appendNotations(String[] args, Set<UUID> playerSet) {
		int lastPos = args.length - 1;
		if (!args[lastPos].startsWith("@")) return args;
		char target = args[lastPos].charAt(1);
		String[] tempArr = getReturnArray(target, playerSet);
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

	public static String[] getReturnArray(char key, Set<UUID> playerSet) {
		if (key == 'a') return getLimitedPlayerList(playerSet);
		if (key == 'A') return getPlayerList();
		if (key == 'r') return getRandomLimitedPlayer(playerSet);
		if (key == 'R') return getRandomPlayer();
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
