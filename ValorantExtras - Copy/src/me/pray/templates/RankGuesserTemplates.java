package me.pray.templates;

import java.awt.Color;

import me.pray.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface RankGuesserTemplates {

	public static MessageEmbed inGame(GuildMessageReceivedEvent e, String prefix) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.RED).setAuthor("In Game", null, url)
				.setDescription("You are already in a game, to leave your current game, type: `" + prefix + "quit`")
				.setFooter(Main.madeBy).setTimestamp(Templates.getTime(e)).build();

	}

	public static MessageEmbed playYourOwnGame(GuildMessageReceivedEvent e, String userMention) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.RED).setAuthor("Wrong game", null, url)
				.setDescription("Uh oh " + userMention + ", you can't place someone else's game!")
				.setFooter(Main.madeBy).setTimestamp(Templates.getTime(e)).build();

	}

	public static MessageEmbed playYourOwnGame(ButtonClickEvent e, String userMention) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.RED).setAuthor("Wrong game", null, url)
				.setDescription("Uh oh " + userMention + ", you can't place someone else's game!")
				.setFooter(Main.madeBy).setTimestamp(Templates.getTime(e)).build();

	}
	
	public static MessageEmbed notInGame(ButtonClickEvent e, String userMention) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.RED).setAuthor("Wrong game", null, url)
				.setDescription("Uh oh " + userMention + ", you can't place someone else's game!")
				.setFooter(Main.madeBy).setTimestamp(Templates.getTime(e)).build();

	}

	public static MessageEmbed joinAGame(ButtonClickEvent e, String prefix) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.RED).setAuthor("Join a game", null, url)
				.setDescription("Join a game using `" + prefix + "start [easy|hard]` to play!").setFooter(Main.madeBy)
				.setTimestamp(Templates.getTime(e)).build();

	}

	public static MessageEmbed joinAGame(GuildMessageReceivedEvent e, String prefix) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.RED).setAuthor("Join a game", null, url)
				.setDescription("Join a game using `" + prefix + "start [easy|hard]` to play!").setFooter(Main.madeBy)
				.setTimestamp(Templates.getTime(e)).build();

	}

	public static MessageEmbed quitGame(GuildMessageReceivedEvent e, String prefix) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.RED).setAuthor("Quit", null, url)
				.setDescription("You quit from your current game.").setFooter(Main.madeBy)
				.setTimestamp(Templates.getTime(e)).build();

	}

	public static MessageEmbed displayThanksMessage(ButtonClickEvent e, int points, int displayHighScore) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.RED).setAuthor("Awh, thanks for playing!", null, url)
				.setDescription("Your score: **" + points + "**\nYour highscore: **" + displayHighScore + "**")
				.setFooter(Main.madeBy).setTimestamp(Templates.getTime(e)).build();

	}


	public static MessageEmbed invalidFormat(GuildMessageReceivedEvent e, String prefix) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.RED).setAuthor("Invalid Format", null, url)
				.setDescription("Invalid format, use `" + prefix + "start [easy|hard]`").setFooter(Main.madeBy)
				.setTimestamp(Templates.getTime(e)).build();
	}

	public static MessageEmbed incorrectAnswer(ButtonClickEvent e, int points, int displayHighScore) {

		String url = e.getJDA().getSelfUser().getAvatarUrl();

		return new EmbedBuilder().setColor(Color.RED).setAuthor("Incorrect!", null, url)
				.setDescription("Unlucky, thanks for playing! \nYour score: **" + points + "**\nYour highscore: **" + displayHighScore + "**")
				.setFooter(Main.madeBy).setTimestamp(Templates.getTime(e)).build();

	}

}
