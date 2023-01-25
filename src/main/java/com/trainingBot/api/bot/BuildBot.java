package com.trainingBot.api.bot;

import com.trainingBot.api.Listener.EmojiReactionListener;
import com.trainingBot.api.Listener.MessageListener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class BuildBot {

	public static JDA jda;
	
	public static void main(String[] args) {
		
		try {
			JDABuilder builder = JDABuilder.createDefault("MTA1MzE3MDUwMTc3NTc5ODMxMg.GsrKz6.hnZA_fu7nq0sDMlah7K3YrNM4r7rTJqPVvFkcQ");
			builder.setEnabledIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_EMOJIS_AND_STICKERS, GatewayIntent.SCHEDULED_EVENTS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_TYPING, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_EMOJIS_AND_STICKERS, GatewayIntent.GUILD_MESSAGE_REACTIONS);
			builder.addEventListeners(new MessageListener());
			builder.addEventListeners(new EmojiReactionListener());
			builder.setAutoReconnect(true);
			builder.build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
