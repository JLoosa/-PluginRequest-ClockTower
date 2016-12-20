package com.gmail.jacob6816.clocktower;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class SimpleTimer {

	private long		seconds;
	private boolean		paused;
	private Scoreboard	scoreboard;
	private Objective	ocjective;
	private String		timeString	= ChatColor.GREEN + "00:00";

	public SimpleTimer(long seconds) {
		this.seconds = Math.max(0, seconds);
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		ocjective = scoreboard.registerNewObjective("Time", "dummy");
		ocjective.setDisplayName(ChatColor.AQUA + "Time Left: ");
		ocjective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	public void onSecond() {
		if (paused) return;
		seconds = Math.max(0, seconds - 1);
		scoreboard.resetScores(timeString);
		updateTimeString();
		ocjective.getScore(timeString).setScore(1);
	}

	private void updateTimeString() {
		long minutes = TimeUnit.SECONDS.toMinutes(seconds);
		long remainSec = seconds - TimeUnit.MINUTES.toSeconds(minutes);
		timeString = ChatColor.GREEN + String.format("%02d:%02d", minutes, remainSec);
	}

	public long getSeconds() {
		return seconds;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public Scoreboard getScoreboard() {
		return scoreboard;
	}

	public void setTime(long seconds) {
		this.seconds = Math.max(0, seconds);
	}

	public void addTime(long seconds) {
		this.seconds += seconds;
		this.seconds = Math.max(0, this.seconds);
	}

	public String getTimeString() {
		return this.timeString;
	}

	public boolean isPaused() {
		return paused;
	}
}
