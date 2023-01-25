package com.trainingBot.api.Listener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.dv8tion.jda.api.entities.Role;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.trainingBot.api.util.ChannelUtil;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter{
	public static JDA jda;
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		
		User user = event.getAuthor();
		TextChannel textChannel = null;
		Message message = event.getMessage();
        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();

		AudioSourceManagers.registerRemoteSources(playerManager);
		
		if(user.isBot()) {
			return;
		}
		
		if(event.isFromType(ChannelType.TEXT)) {
			textChannel = event.getChannel().asTextChannel();
			if(event.getGuildChannel().getName().equals("훈련용봇")) {
				if(message.getContentRaw().startsWith("명령어")) {
					String result = "";
					result += "랜덤라인배정 닉네임,닉네임,...." + "\n";
					result += "(탑 : TP | 정글 : JG | 미드 : MD | 원딜 : AD | 서폿 : SP)" + "\n";
					result += "롤 패치노트 (롤 패치노트 12.23)" + "\n";
					textChannel.sendMessage(result).queue();
				}else if(message.getContentRaw().startsWith("랜덤라인배정")) {
					if(message.getContentRaw().replaceFirst("랜덤라인배정", "").trim().split(",").length > 1) {
						String result = "";
						Map<String, String> line = new HashMap<String, String>();
						List<String> randomLine = new ArrayList<String>();
						Map<Integer, String> lineNum = new HashMap<Integer, String>();
						Map<String, String> lineList = new HashMap<String, String>();
						lineList.put("TP", "TP");
						lineList.put("JG", "JG");
						lineList.put("MD", "MD");
						lineList.put("AD", "AD");
						lineList.put("SP", "SP");
						int num = 1;
						
						for(String name : message.getContentRaw().replaceFirst("랜덤라인배정", "").trim().split(",")) {
							if(name.contains("TP : ")) {
								result += "TP : " + name.replace("TP : ", "") + "\n";
								line.put("TP", name);
								lineList.remove("TP");
							}else if(name.contains("JG : ")) {
								result += "JG : " + name.replace("JG : ", "") + "\n";
								line.put("JG", name);
								lineList.remove("JG");
							}else if(name.contains("MD : ")) {
								result += "MD : " + name.replace("MD : ", "") + "\n";
								line.put("MD", name);
								lineList.remove("MD");
							}else if(name.contains("AD : ")) {
								result += "AD : " + name.replace("AD : ", "") + "\n";
								line.put("AD", name);
								lineList.remove("AD");
							}else if(name.contains("SP : ")) {
								result += "SP : " + name.replace("SP : ", "") + "\n";
								line.put("SP", name);
								lineList.remove("SP");
							}else {
								randomLine.add(name.trim());
							}
						}
						
						for(String lineName : lineList.keySet()) {
							lineNum.put(num++, lineName);
						}
						
						if(lineNum.size() > 1) {
							for(int i = 0; i < randomLine.size(); i++) {
								int idx = random.nextInt(lineNum.size()) + 1;
								if(lineNum.get(idx) != null) {
									result += lineNum.get(idx) + " : " + randomLine.get(i) + "\n";
									lineNum.remove(idx);
								}else {
									i--;
								}
							}
						}
						
						if(!result.equals("")) {
							textChannel.sendMessage(result).queue();
						}
					}
				}else if(message.getContentRaw().startsWith("롤 패치노트")) {
					try {
						URL url = new URL("https://ddragon.leagueoflegends.com/api/versions.json");
						HttpURLConnection con = (HttpURLConnection) url.openConnection();
						con.setRequestMethod("GET");
						
						BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
						String inputLine;
						StringBuffer response = new StringBuffer();
						while((inputLine = br.readLine()) != null) {
							response.append(inputLine);
						}
						br.close();
						
						JSONArray jsonArray = new JSONArray(response.toString());
						String lastversion = jsonArray.get(0).toString();
						
						if(message.getContentRaw().replaceFirst("롤 패치노트", "").trim().equals("")) {
							if(lastversion != null) {
								String patchUrl = "https://www.leagueoflegends.com/ko-kr/news/game-updates/patch-"+ lastversion.substring(0,lastversion.indexOf(".")) +"-"+ lastversion.substring(lastversion.indexOf(".") + 1,lastversion.lastIndexOf(".")) + "-notes/";
								URL patch = new URL(patchUrl);
								HttpURLConnection connection = (HttpURLConnection) patch.openConnection();
								if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
									textChannel.sendMessage("https://www.leagueoflegends.com/ko-kr/news/game-updates/patch-"+ lastversion.substring(0,lastversion.indexOf(".")) +"-"+ lastversion.substring(lastversion.indexOf(".") + 1,lastversion.lastIndexOf(".")) +"-notes/").queue();
								}else {
									textChannel.sendMessage("존재하지 않는 페이지입니다.");
								}
							}else {
								textChannel.sendMessage("에러");
							}
						}else {
							String version = message.getContentRaw().replaceFirst("롤 패치노트", "").trim();
							String patchUrl = "https://www.leagueoflegends.com/ko-kr/news/game-updates/patch-"+ version.substring(0,lastversion.indexOf(".")) +"-"+ version.substring(lastversion.indexOf(".") + 1,version.length()) +"-notes/";
							URL patch = new URL(patchUrl);
							HttpURLConnection connection = (HttpURLConnection) patch.openConnection();
							if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
								if(version.length() > 1) {
									textChannel.sendMessage("https://www.leagueoflegends.com/ko-kr/news/game-updates/patch-"+ version.substring(0,lastversion.indexOf(".")) +"-"+ version.substring(lastversion.indexOf(".") + 1,version.length()) +"-notes/").queue();
								}else {
									textChannel.sendMessage("버전의 형식이 맞지 않습니다.").queue();
								}
							}else {
								textChannel.sendMessage("존재하지 않는 페이지입니다.");
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(message.getContentRaw().startsWith("역할부여")) {
					Guild guild = event.getGuild();
					List<Role> roles = guild.getRoles();
					try {
						for(Role role : roles) {
							if(!role.isPublicRole()) {
								if(role.getName().equals(message.getContentRaw().replaceFirst("역할부여", "").trim())) {
									guild.addRoleToMember(user, role).complete();
									textChannel.sendMessage("역할부여 " + user.getAsMention() + ": " + role.getName()).queue();
									System.out.println("역할부여 " + user.getName() + " " + role.getName());
								}
							}
						}
					}catch (Exception e) {
						textChannel.sendMessage("역할부여 에러").queue();
						e.printStackTrace();
					}
				}else if(message.getContentRaw().startsWith("역할제거")) {
					Guild guild = event.getGuild();
					Member member = message.getMember();
					List<Role> roles = member.getRoles();
					try {
						for(Role role : roles) {
							if(!role.isPublicRole()) {
								if(role.getName().equals(message.getContentRaw().replaceFirst("역할제거", "").trim())) {
									guild.removeRoleFromMember(user, role).complete();
									textChannel.sendMessage("역할제거 " + user.getAsMention() + ": " + role.getName()).queue();
									System.out.println("역할제거 " + user.getName() + " " + role.getName());
								}
							}
						}
					}catch (Exception e) {
						textChannel.sendMessage("역할제거 에러").queue();
						e.printStackTrace();
					}
				}else if(message.getContentRaw().startsWith("!역할배정생성")) {
					TextChannel tc = null;
					String contentOption = message.getContentRaw().split("-")[1];
					Guild guild = event.getGuild();
					if(!ChannelUtil.isChannelExist(message.getCategory(), "역할배정")) {
						tc = guild.createTextChannel("역할배정",message.getCategory()).complete();
						tc.sendMessage(contentOption).queue();
					}else {
						for(TextChannel textCh : guild.getTextChannels()) {
							if(textCh.getName().equals("역할배정")) {
								textCh.sendMessage(contentOption).queue();
							}
						}
					}
				}else if(message.getContentRaw().startsWith("역할추가")) {
					String[] emojiNames = message.getContentRaw().replaceFirst("역할추가", "").trim().split(" ");
					Guild guild = event.getGuild();
					if(emojiNames.length > 0) {
						for(int i = 0; i < emojiNames.length; i++) {
							for(Emoji emoji : guild.getEmojisByName(emojiNames[i], false)) {
								for(TextChannel tc : guild.getTextChannelsByName("역할배정", false)) {
									tc.addReactionById(tc.getLatestMessageId(), emoji).complete();
								}
							}
						}
					}
				}else if(message.getContentRaw().startsWith("역할삭제")) {
					String[] emojiNames = message.getContentRaw().replaceFirst("역할삭제", "").trim().split(" ");
					Guild guild = event.getGuild();
					if(emojiNames.length > 0) {
						for(int i = 0; i < emojiNames.length; i++) {
							for(Emoji emoji : guild.getEmojisByName(emojiNames[i], false)) {
								for(TextChannel tc : guild.getTextChannelsByName("역할배정", false)) {
									tc.clearReactionsById(tc.getLatestMessageId(), emoji).complete();
								}
							}
						}
					}
				}
			}
		}
	}
}
