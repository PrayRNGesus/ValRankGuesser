package me.pray.templates;

import java.awt.Color;

import me.pray.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface HelpTemplates {

	public static MessageEmbed mainHelp(GuildMessageReceivedEvent e, String prefix) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();
		String rickRoll = "https://bit.ly/3AsYTz8";

		return new EmbedBuilder().setColor(Color.ORANGE).setAuthor("Valorant Rank Guesser Commands", rickRoll, url)
				.setThumbnail(url).addField("**Game commands**", "`" + prefix + "help game`", true)
				.addField("**Misc commands**", "`" + prefix + "help misc`", true).setFooter(Main.madeBy)
				.setTimestamp(Templates.getTime(e)).build();

	}

	public static MessageEmbed gameHelp(GuildMessageReceivedEvent e, String prefix) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();
		String rickRoll = "https://bit.ly/3AsYTz8";

		return new EmbedBuilder().setColor(Color.ORANGE).setAuthor("Game Commands", rickRoll, url).setThumbnail(url)
				.setDescription(
						  "\n`" + prefix + "start [easy|hard]`" + "\nStarts a new game\n"
						+ "\n`" + prefix + "quit`" + "\nQuits from your current game\n"
						+ "\n`" + prefix + "highscore (optional member)`" + "\nShows the highscore of anyone who has played\n"
						+ "\n`" + prefix + "leaderboards`" + "\nDisplay's the leaderboards")

				.setFooter(Main.madeBy).setTimestamp(Templates.getTime(e)).build();

	}

	public static MessageEmbed miscHelp(GuildMessageReceivedEvent e, String prefix) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();
		String rickRoll = "https://bit.ly/3AsYTz8";

		return new EmbedBuilder().setColor(Color.ORANGE).setAuthor("Game Commands", rickRoll, url).setThumbnail(url)
				.setDescription("`" + prefix + "invite`" + "\nProvides an invite link for the bot"
				+ "\n\n`" + prefix + "prefix [new-prefix]`" + "\nChanges the prefix for this server (admin only)" 
				+ "\n\n`" + prefix + "help (optional page)`" + "\nShows you the help page" 
				+ "\n\n`" + prefix + "report [bug-description]`" + "\nReport a bug" 
				+ "\n\n`" + prefix + "sumbit [clip-link]`" + "\nSubmits a link to be added to the bot"
				+ "\n\n`" + prefix + "about`" + "\nProvides cool information about the bot"
				+ "\n\n`" + prefix + "suggest [feature-description]`" + "\nSend a feature suggestion to the bot dev"
				+ "\n\n`" + prefix + "tos`" + "\nProvides the TOS of the bot")
				.setFooter(Main.madeBy).setTimestamp(Templates.getTime(e)).build();

	}

}
