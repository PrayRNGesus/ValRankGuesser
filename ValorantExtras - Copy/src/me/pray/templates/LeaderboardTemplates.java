package me.pray.templates;

import java.awt.Color;

import me.pray.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface LeaderboardTemplates {

	public static MessageEmbed invalidFormat(GuildMessageReceivedEvent e, String prefix) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.RED).setAuthor("Invalid Format", null, url)
				.setDescription("Invalid format, use `" + prefix + "leaderboards`").setFooter(Main.madeBy)
				.setTimestamp(Templates.getTime(e)).build();
	}

	public static MessageEmbed fetchingLeaderboards(GuildMessageReceivedEvent e) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.ORANGE).setAuthor("Fetching", null, url)
				.setDescription("Fetching the leaderboards...").setFooter(Main.madeBy)
				.setTimestamp(Templates.getTime(e)).build();
	}

	public static MessageEmbed unavaiableLeaderboards(GuildMessageReceivedEvent e) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.RED).setAuthor("Uh oh", null, url)
				.setDescription("The leaderboard system is currently **unavaiable!**").setFooter(Main.madeBy)
				.setTimestamp(Templates.getTime(e)).build();
	}

	public static MessageEmbed displayLeaderboards(GuildMessageReceivedEvent e, String firstPlaceName,
			int firstPlaceScore, String secondPlaceName, int secondPlaceScore, String thirdPlaceName,
			int thirdPlaceScore) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.ORANGE).setAuthor("Current Leaderboards", null, url).setThumbnail(url)
				.setDescription(
						"<:radiant1stplace:861008192851148821> 1st place: " + firstPlaceName + " with a score of **"
								+ firstPlaceScore + "**\n\n<:immortal2ndplace:861008275964035083> 2nd place: "
								+ secondPlaceName + " with a score of **" + secondPlaceScore
								+ "**\n\n<:diamond3rdplace:861008290016133161> 3rd place: " + thirdPlaceName
								+ " with a score of **" + thirdPlaceScore + "**")
				.setFooter(Main.madeBy).setTimestamp(Templates.getTime(e)).build();
	}

}
