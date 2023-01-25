package com.trainingBot.api.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import javax.net.ssl.HttpsURLConnection;

public class RiotApiUtil {
	
	static String API_KEY = "RGAPI-dc703727-1ee3-4e1e-b63b-e7821acf2081";
	static String SearchApi = "https://www.googleapis.com/youtube/v3/search";
	
	public static void main(String[] args) {
		System.out.println(search("멜론"));
		
	}
	
	public static String totalSearch(String url, String name) {
		HttpsURLConnection connection = null;
		String SummonerName = name.replaceAll(" ", "%20");
		String statusURL = "https://kr.api.riotgames.com/lol/status/v4/platform-data?api_key=" + API_KEY;
		String requestURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+ SummonerName + "?api_key=" + API_KEY;
		
		try {
			URL uri = new URL(statusURL);
			
			return "";
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String rotation(boolean isNoob){
		HttpsURLConnection connection = null;
		String rotationURL = "https://kr.api.riotgames.com/lol/platform/v3/champion-rotations?api_key=" + API_KEY;
		
		try {
			URL url = new URL(rotationURL);
			connection = (HttpsURLConnection)url.openConnection();
			
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			connection.setRequestProperty("Access-Control-Allow-Methods", "GET, PUT, DELETE, POST, OPTIONS");
			connection.setRequestProperty("Content-Encoding", "gzip"); 
			connection.setRequestProperty("Access-Control-Allow-Origin", "*");
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.close();
			
			InputStream is = connection.getInputStream();
			BufferedReader rd = new  BufferedReader(new InputStreamReader(is));
			
			StringBuilder response = new StringBuilder();
			String line; 
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
	}
	
	public static String search(String search) {
		try {
			SearchApi += "?key=AIzaSyCWy9KABoWStRcDbUx6WI3mkcH1ltcb80E";
			SearchApi += "&part=snippet&type=video&maxResults=20&videoEmbeddable=true";
			SearchApi += "&q="+URLEncoder.encode(search,"UTF-8");
			
			URL url = new URL(SearchApi);
			HttpURLConnection
			
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			
			return response.toString();	
		} catch (Exception e) {
			return "";
		}
		
		
	}

}
