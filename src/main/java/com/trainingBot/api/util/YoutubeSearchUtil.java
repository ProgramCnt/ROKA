package com.trainingBot.api.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

public class YoutubeSearchUtil {

	static String SearchApi = "https://www.googleapis.com/youtube/v3/search";

	public static String search(String search) {
		try {
			SearchApi += "?key=AIzaSyCWy9KABoWStRcDbUx6WI3mkcH1ltcb80E";
			SearchApi += "&part=snippet&type=video&maxResults=10&videoEmbeddable=true";
			SearchApi += "&q="+URLEncoder.encode(search,"UTF-8");
			
			URL url = new URL(SearchApi);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			
			JSONObject jsonObject = new JSONObject(response.toString());
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			String videoId = jsonArray.getJSONObject(0).getJSONObject("id").get("videoId").toString();
			System.out.println(jsonArray.toString());
			System.out.println(videoId);
			
			return response.toString();	
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
