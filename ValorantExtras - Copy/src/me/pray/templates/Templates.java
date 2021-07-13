package me.pray.templates;

import java.awt.Color;
import java.time.OffsetDateTime;

import me.pray.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface Templates {

	public static MessageEmbed scorecard(GuildMessageReceivedEvent e, int currentScore) {

		return new EmbedBuilder().setColor(Color.RED).setTitle("Score: " + currentScore)
				.setDescription("Watch the video below, then decide what rank you think they are!")
				.setFooter(Main.madeBy).setTimestamp(getTime(e)).build();

	}

	public static MessageEmbed scorecard(ButtonClickEvent e, int currentScore) {

		return new EmbedBuilder().setColor(Color.RED).setTitle("Score: " + currentScore)
				.setDescription("Watch the video below, then decide what rank you think they are!")
				.setFooter(Main.madeBy).setTimestamp(getTime(e)).build();

	}

	public static MessageEmbed invite(GuildMessageReceivedEvent e) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.RED).setAuthor("Invite link", "https://bit.ly/3hdPyUk", url)
				.setDescription("Click [here](https://bit.ly/3hdPyUk), or go to https://bit.ly/3hdPyUk")
				.setFooter(Main.madeBy).setTimestamp(getTime(e)).build();

	}

	public static MessageEmbed invalidBugReportFormat(GuildMessageReceivedEvent e, String prefix) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.RED).setAuthor("Invalid Format", null, url)
				.setDescription("Invalid format, use `" + prefix + "report [bug-description]`").setFooter(Main.madeBy)
				.setTimestamp(Templates.getTime(e)).build();
	}

	public static MessageEmbed bugReportReceived(GuildMessageReceivedEvent e) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.ORANGE).setAuthor("Success!", null, url)
				.setDescription("Your bug report was received, thank you!").setFooter(Main.madeBy)
				.setTimestamp(getTime(e)).build();
	}

	public static MessageEmbed guildJoin(GuildJoinEvent e, String prefix) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.ORANGE).setAuthor("Valorant Guesser", null, url).setThumbnail(url)
				.setDescription("Thank you for inviting me to your server! \nMy default prefix is `v!` \nFor help, use "
						+ prefix + "help")
				.setFooter(Main.madeBy).build();
	}

	public static MessageEmbed aboutMessage(GuildMessageReceivedEvent e, String prefix) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.ORANGE).setAuthor("About Valorant Guesser", null, url)
				.setThumbnail(url)
				.setDescription(
						"Hello, I am Valorant Rank Guesser. I was made by **@Pray#0001** on July 3rd, 2021. Have you ever seen YouTuber's playing guess the rank? Well that's what my purpose is!  Using the prefix `"
								+ prefix + "`, you can run tons of commands (`" + prefix
								+ "help`)! I am constantely being updated!  As of now, my version is `" + Main.version
								+ "`. If you like to request a feature, use the command `" + prefix
								+ "suggest [feature-description]`! Anyways, I hope you enjoy the bot, and as always... best of luck! \n"
								+ "\n*Sincerely, Valorant Rank Guesser*")
				.setFooter(Main.madeBy).setTimestamp(getTime(e)).build();
	}

	public static MessageEmbed invalidSuggestionFormat(GuildMessageReceivedEvent e, String prefix) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.RED).setAuthor("Invalid Format", null, url)
				.setDescription("Invalid format, use `" + prefix + "suggest [feature-description]`")
				.setFooter(Main.madeBy).setTimestamp(Templates.getTime(e)).build();
	}

	public static MessageEmbed suggestionReceived(GuildMessageReceivedEvent e) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.ORANGE).setAuthor("Success!", null, url)
				.setDescription("Your suggestion was received, thank you!").setFooter(Main.madeBy)
				.setTimestamp(getTime(e)).build();
	}

	public static MessageEmbed serverPrefix(GuildMessageReceivedEvent e, String prefix) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.ORANGE).setAuthor("Hey there!", null, url)
				.setDescription("This server's prefix is: `" + prefix + "`").setFooter(Main.madeBy)
				.setTimestamp(getTime(e)).build();
	}

	public static MessageEmbed tosEmbed(GuildMessageReceivedEvent e, String prefix) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.ORANGE).setAuthor("Valorant Rank Guesser - TOS", null, url).setThumbnail(url)
				.setDescription("By using this bot you will not: " + "\nCheat, "
						+ "attempt to \"spoof\" buttons in any way, " + "attempt to lease, " + "sell, " + "copy, "
						+ "sublicense, " + "transfer, " + "or assign any information, " + "intellectual property, "
						+ "goods, or services provided by the bot. "
						+ "You will not gain unauthorized permission to the bot's data or the data of any other users.  "
						+ "You may not beach, " + "or attempt to breach anything related to the bot. "
						+ "Performing any of these actions will result in a permanent ban from using our bot.  "
						+ "These bans **cannot** be appealed!")
				.setFooter(Main.madeBy).setTimestamp(getTime(e)).build();
	}

	public static OffsetDateTime getTime(ButtonClickEvent e) {
		return e.getMessage().getTimeCreated();
	}

	public static OffsetDateTime getTime(GuildMessageReceivedEvent e) {
		return e.getMessage().getTimeCreated();
	}

}
