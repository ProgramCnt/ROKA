package com.trainingBot.api.manager;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.trainingBot.api.handler.AudioPlayerSendHandler;
import com.trainingBot.api.scheduler.TrackScheduler;

public class GuildMusicManager {
	public final AudioPlayer player;
	/**
	 * Track scheduler for the player.
	 */
	public final TrackScheduler scheduler;
	/**
	 * Creates a player and a track scheduler.
	 * @param manager Audio player manager to use for creating the player.
	 */
	public GuildMusicManager(AudioPlayerManager manager) {
	  player = manager.createPlayer();
	  scheduler = new TrackScheduler(player);
	  player.addListener(scheduler);
	}
	
	/**
	 * @return Wrapper around AudioPlayer to use it as an AudioSendHandler.
	 */
	public AudioPlayerSendHandler getSendHandler() {
	  return new AudioPlayerSendHandler(player);
	}
}
