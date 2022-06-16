package com.roka.api.bot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BuildBot extends ListenerAdapter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			JDA jda = JDABuilder.createDefault("OTg2NTU1MDc1MDYzMzQxMTMw.Gh4Jy7.2dwwGSaDm8a09w8oam02AQ2ZQCczcnmkBZW01U").build();
			jda.addEventListener(new BuildBot());
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		if(event.getMessage().getContentRaw().equals("테스트")) {
			event.getChannel().sendMessage("테스트중");
		}
	}

}
