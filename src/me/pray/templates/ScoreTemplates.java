package me.pray.templates;

import java.awt.Color;

import me.pray.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface ScoreTemplates {

	public static final Main main = new Main();

	public static MessageEmbed invalidFormat(GuildMessageReceivedEvent e, String prefix) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.RED).setAuthor("Error occured", null, url)
				.setDescription("Invalid format, use `" + prefix + "score (optional member)`").setFooter(Main.madeBy)
				.setTimestamp(Templates.getTime(e)).build();
	}

	public static MessageEmbed highscoreMessage(GuildMessageReceivedEvent e, String prefix, String userMention,
			int highscore) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.ORANGE).setAuthor("Highscore", null, url)
				.setDescription("Highscore of " + userMention + " is " + highscore).setFooter(Main.madeBy)
				.setTimestamp(Templates.getTime(e)).build();
	}

}
