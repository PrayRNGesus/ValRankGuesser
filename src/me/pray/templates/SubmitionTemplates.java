package me.pray.templates;

import java.awt.Color;

import me.pray.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface SubmitionTemplates {

	public static MessageEmbed invalidFormat(GuildMessageReceivedEvent e, String prefix) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.RED).setAuthor("Invalid Format", null, url)
				.setDescription("Invalid format, please use `" + prefix + "sumbit [link]`").setFooter(Main.madeBy)
				.setTimestamp(Templates.getTime(e)).build();
	}
	
	public static MessageEmbed submitionReceived(GuildMessageReceivedEvent e, String userMention) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.ORANGE).setAuthor("Submition Received", null, url)
				.setDescription("Thanks for your submission, " + userMention).setFooter(Main.madeBy)
				.setTimestamp(Templates.getTime(e)).build();
	}
	
	public static MessageEmbed invalidUrl(GuildMessageReceivedEvent e, String prefix) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.RED).setAuthor("Invalid Url", null, url)
				.setDescription("Uh oh, it seems that the url you provided is not valid, please make sure it is a valid url and try again. \nIf you think this is incorrect, use `" + prefix + "report invalid url [your-url]`").setFooter(Main.madeBy)
				.setTimestamp(Templates.getTime(e)).build();
	}
	
}
