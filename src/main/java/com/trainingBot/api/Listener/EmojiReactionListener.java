package com.trainingBot.api.Listener;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.sticker.Sticker;
import net.dv8tion.jda.api.events.emoji.EmojiAddedEvent;
import net.dv8tion.jda.api.events.emoji.EmojiRemovedEvent;
import net.dv8tion.jda.api.events.emoji.GenericEmojiEvent;
import net.dv8tion.jda.api.events.emoji.update.EmojiUpdateNameEvent;
import net.dv8tion.jda.api.events.emoji.update.GenericEmojiUpdateEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.sticker.GenericGuildStickerEvent;
import net.dv8tion.jda.api.events.sticker.GuildStickerAddedEvent;
import net.dv8tion.jda.api.events.sticker.update.GenericGuildStickerUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EmojiReactionListener extends ListenerAdapter {
	
	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		TextChannel tc = event.getChannel().asTextChannel();
		User user = event.getUser();
		Emoji emoji = event.getEmoji();
		Guild guild = event.getGuild();
		if(tc.getName().equals("역할배정")) {
			try {
				for(Role role : guild.getRoles()) {
					if(role.getName().equals(emoji.getName())) {
						guild.addRoleToMember(user, role).complete();
						System.out.println(user.getName() + "에게 " + role.getName() + "역할 부여");
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
		TextChannel tc = event.getChannel().asTextChannel();
		Emoji emoji = event.getEmoji();
		Guild guild = event.getGuild();
		if(tc.getName().equals("역할배정")) {
			try {
				for(Role role : guild.getRoles()) {
					if(role.getName().equals(emoji.getName())) {
						guild.removeRoleFromMember(guild.retrieveMemberById(event.getUserId()).complete(), role).complete();
						System.out.println(guild.retrieveMemberById(event.getUserId()).complete().getNickname() + "의 " + role.getName() + "역할을 제거");
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
