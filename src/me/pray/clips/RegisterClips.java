package me.pray.clips;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import me.pray.Main;

public class RegisterClips {

	private Main main;

	public RegisterClips(Main main) {
		this.main = main;
	}

	public void registerClips() {
		addClip("https://youtu.be/ylPxKfZaxNQ", Ranks.SILVER);
		addClip("https://youtu.be/M31MUmv-vJA", Ranks.IRON);
		addClip("https://youtu.be/9yuPF3019Nc", Ranks.SILVER);
		addClip("https://youtu.be/8mJ4zaPK-S4", Ranks.SILVER);
		addClip("https://youtu.be/ULawZWF9lKQ", Ranks.DIAMOND);
		addClip("https://youtu.be/U7WMxuVlYho", Ranks.SILVER);
		addClip("https://youtu.be/5B3FaFd4zb4", Ranks.GOLD);
		addClip("https://youtu.be/OAy1QBB5OBY", Ranks.SILVER);
		addClip("https://youtu.be/utfRjRb2nAU", Ranks.PLAT);
		addClip("https://youtu.be/qdztVe_CDO0", Ranks.IRON);
		addClip("https://youtu.be/wXU-n-EUhK4", Ranks.PLAT);
		addClip("https://youtu.be/tekQJnmeZtg", Ranks.SILVER);
		addClip("https://youtu.be/SVCjDJ0aSOY", Ranks.SILVER);
		addClip("https://youtu.be/sMKzHD9LPQQ", Ranks.GOLD);
		addClip("https://youtu.be/2WKCgIF32zw", Ranks.DIAMOND);
		addClip("https://youtu.be/BnpF2Db3QPw", Ranks.DIAMOND);
		addClip("https://youtu.be/31vIpRBA4aY", Ranks.PLAT);
		addClip("https://youtu.be/P0wEkbiyVvE", Ranks.DIAMOND);
		addClip("https://youtu.be/oj2vn98ZWx4", Ranks.GOLD);
		addClip("https://youtu.be/tZNVH1Jwo3U", Ranks.IMMORTAL);
		addClip("https://youtu.be/i7DUNXK9TJc", Ranks.BRONZE);
		addClip("https://youtu.be/YI9uu0uIZpY", Ranks.PLAT);
		addClip("https://youtu.be/vAXOXcEtRZE", Ranks.SILVER);
		
		
		
		removeClip("https://youtu.be/-GbBH1izQP0");
	}

	public void addClip(String url, Ranks rank) {
		var query = eq("URL", url);

		if (main.clipsCollection.find(query).first() != null) {
			return;
		} else {
			Document addUrl = new Document();
			addUrl.append("URL", url).append("Rank", rank.toString());
			main.clipsCollection.insertOne(addUrl);
			System.out.println("Added: " + addUrl.toJson());
		}
	}
	
	public void removeClip(String url) {
		var query = eq("URL", url);

		if (main.clipsCollection.find(query).first() != null) {
			System.out.println("Removed: " + main.clipsCollection.find(query).first().toJson());
			main.clipsCollection.findOneAndDelete(query);
		} else {
			System.out.println("Unable to find: " + url);
		}
	}
	
}
