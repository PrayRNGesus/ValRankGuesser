package me.pray.templates;

import java.awt.Color;

import me.pray.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface PrefixTemplates {

	public static MessageEmbed prefixChange(GuildMessageReceivedEvent e, String oldPrefix, String newPrefix) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.ORANGE).setAuthor("Prefix Changed", null, url)
				.setDescription("Prefix updated from `" + oldPrefix + "` to `" + newPrefix + "`").setFooter(Main.madeBy)
				.setTimestamp(Templates.getTime(e)).build();

	}

	public static MessageEmbed noAdminPermissions(GuildMessageReceivedEvent e) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.RED).setAuthor("No Permission", null, url)
				.setDescription("Uh oh, it looks like you don't have permission to do that!").setFooter(Main.madeBy)
				.setTimestamp(Templates.getTime(e)).build();

	}
	
	public static MessageEmbed invalidFormat(GuildMessageReceivedEvent e, String prefix) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.RED).setAuthor("Invalid Format", null, url)
				.setDescription("Invalid format, use `" + prefix + "prefix [new-prefix]`").setFooter(Main.madeBy)
				.setTimestamp(Templates.getTime(e)).build();

	}

	
	
	
}
