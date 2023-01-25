package com.trainingBot.api.util;

import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class ChannelUtil {
	public boolean textChannelExist(TextChannel tc) {
		return false;
	}
	
	public static boolean isChannelExist(Category category, String channelName) {
		for(Channel channel : category.getChannels()) {
			if(channel.getName().equals(channelName)) {
				return true;
			}
		}
		return false;
	}
}
