package me.pray.commands.game;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.inc;
import static com.mongodb.client.model.Updates.set;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.bson.Document;

import me.pray.Main;
import me.pray.clips.Ranks;
import me.pray.templates.RankGuesserTemplates;
import me.pray.templates.Templates;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;

public class RankGuesser extends ListenerAdapter implements Templates, RankGuesserTemplates {

	private Main main;

	public RankGuesser(Main main) {
		this.main = main;
	}

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String content = event.getMessage().getContentRaw();
		String[] args = content.split("\\s+");
		var query = eq("Guild", event.getGuild().getIdLong());
		String prefix = main.prefixCollection.find(query).first().getString("Prefix");

		var userIdQuery = eq("UserId", event.getAuthor().getIdLong());

		if (args.length == 1 && args[0].equalsIgnoreCase(prefix + "start")) {
			event.getChannel().sendMessageEmbeds(RankGuesserTemplates.invalidFormat(event, prefix)).queue();
			return;
		}

		if (args.length == 2 && args[0].equalsIgnoreCase(prefix + "start")) {
			
			String difficulty = "";

			if (args[1].equalsIgnoreCase("easy")) {
				difficulty = "EASY";
			} else if (args[1].equalsIgnoreCase("hard")) {
				difficulty = "HARD";
			} else {
				event.getChannel().sendMessageEmbeds(RankGuesserTemplates.invalidFormat(event, prefix)).queue();
				return;
			}

			if (main.pointsCollection.find(userIdQuery).first() == null) {
				Document doc = new Document();
				doc.append("UserId", event.getAuthor().getIdLong());
				doc.append("Ingame", true);
				doc.append("Points", 0);
				doc.append("LinkId", "placeholder");
				doc.append("EmbedId", "placeholder");
				doc.append("Highscore", 0);
				doc.append("Difficulty", difficulty);
				main.pointsCollection.insertOne(doc);
				sendRandomGame(event);
			} else {
				if (main.pointsCollection.find(userIdQuery).first().getBoolean("Ingame") == true) {
					event.getChannel().sendMessageEmbeds(RankGuesserTemplates.inGame(event, prefix)).queue(msg -> {
						event.getMessage().delete().queue();
						msg.delete().queueAfter(5, TimeUnit.SECONDS);
					});
					return;
				} else if (main.pointsCollection.find(userIdQuery).first().getBoolean("Ingame") == false) {
					var updateGameStatus = set("Ingame", true);
					var updateDifficulty = set("Difficulty", difficulty);
					main.pointsCollection.findOneAndUpdate(userIdQuery, updateGameStatus);
					main.pointsCollection.findOneAndUpdate(userIdQuery, updateDifficulty);
					sendRandomGame(event);
				}
			}

		}
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {

		var userIdQuery = eq("UserId", event.getUser().getIdLong());

		var query = eq("Guild", event.getGuild().getIdLong());
		String prefix = main.prefixCollection.find(query).first().getString("Prefix");

		if (main.pointsCollection.find(userIdQuery).first().getLong("LinkId") != event.getMessage().getIdLong()) {
			event.deferReply();
			event.getChannel()
					.sendMessageEmbeds(RankGuesserTemplates.playYourOwnGame(event, event.getUser().getAsMention()))
					.queue(msg -> {
						msg.delete().queueAfter(4, TimeUnit.SECONDS);
					});
			return;
		}

		if (main.pointsCollection.find(userIdQuery) == null) {
			event.getChannel().sendMessageEmbeds(RankGuesserTemplates.joinAGame(event, prefix)).queue();
		}

		if (event.getComponentId().equalsIgnoreCase("YES")) {
			event.deferEdit();
			sendRandomGame(event);
		} else if (event.getComponentId().equalsIgnoreCase("NO")) {

			event.getChannel().retrieveMessageById(main.pointsCollection.find(userIdQuery).first().getLong("EmbedId"))
					.queue(msg -> {

						int points = main.pointsCollection.find(userIdQuery).first().getInteger("Points");
						int highScore = main.pointsCollection.find(userIdQuery).first().getInteger("Highscore");

						int displayHighScore = highScore;

						if (points > highScore) {
							var saveHighScore = set("Highscore", points);
							main.pointsCollection.findOneAndUpdate(userIdQuery, saveHighScore);

							displayHighScore = points;

						}

						event.getMessage().delete().queue();

						msg.editMessageEmbeds(
								RankGuesserTemplates.displayThanksMessage(event, points, displayHighScore))
								.setActionRow(Button.success("YES_DISABLED", "YES").asDisabled(),
										Button.danger("NO_DISABLED", "NO").asDisabled())
								.queueAfter(50, TimeUnit.MILLISECONDS);

						var resetEmbedId = set("EmbedId", "placeholder");
						main.pointsCollection.findOneAndUpdate(userIdQuery, resetEmbedId);

						var resetLinkId = set("LinkId", "placeholder");
						main.pointsCollection.findOneAndUpdate(userIdQuery, resetLinkId);

						var resetPoints = set("Points", 0);
						main.pointsCollection.findOneAndUpdate(userIdQuery, resetPoints);

						var resetGameStatus = set("Ingame", false);
						main.pointsCollection.findOneAndUpdate(userIdQuery, resetGameStatus);

						var resetDifficulty = set("Difficulty", "placeholder");
						main.pointsCollection.findOneAndUpdate(userIdQuery, resetDifficulty);
						
						event.deferEdit();
					});

		}

		var urlQuery = eq("URL", event.getMessage().getContentRaw());
		String answer = main.clipsCollection.find(urlQuery).first().getString("Rank");

		switch (event.getComponentId().toUpperCase()) {
		case "IRON":
			checkAnswer(event, answer.toUpperCase(), "IRON");
			event.deferEdit();
			break;
		case "BRONZE":
			checkAnswer(event, answer.toUpperCase(), "BRONZE");
			event.deferEdit();
			break;
		case "SILVER":
			checkAnswer(event, answer.toUpperCase(), "SILVER");
			event.deferEdit();
			break;
		case "GOLD":
			checkAnswer(event, answer.toUpperCase(), "GOLD");
			event.deferEdit();
			break;
		case "PLAT":
			checkAnswer(event, answer.toUpperCase(), "PLAT");
			event.deferEdit();
			break;
		case "DIAMOND":
			checkAnswer(event, answer.toUpperCase(), "DIAMOND");
			event.deferEdit();
			break;
		case "IMMORTAL":
			checkAnswer(event, answer.toUpperCase(), "IMMORTAL");
			event.deferEdit();
			break;
		case "RADIANT":
			checkAnswer(event, answer.toUpperCase(), "RADIANT");
			event.deferEdit();
			break;
		default:
			break;
		}

	}

	public void checkAnswer(ButtonClickEvent event, String answer, String correctAnswer) {

		var userIdQuery = eq("UserId", event.getUser().getIdLong());
		var incQuery = inc("Points", 1);

		if (main.pointsCollection.find(userIdQuery).first().getLong("LinkId") != event.getMessage().getIdLong()) {
			event.deferReply();
			event.getChannel().sendMessageEmbeds(RankGuesserTemplates.playYourOwnGame(event, correctAnswer))
					.queue(msg -> {
						msg.delete().queueAfter(4, TimeUnit.SECONDS);
					});
			return;
		}

		if (answer.equals(correctAnswer)) {
			event.editMessage("Nice one, would you like to continue?")
					.setActionRow(Button.success("YES", "YES"), Button.danger("NO", "NO")).queue();

			main.pointsCollection.updateOne(userIdQuery, incQuery);
			Long embedLong = main.pointsCollection.find(userIdQuery).first().getLong("EmbedId");
			int points = main.pointsCollection.find(userIdQuery).first().getInteger("Points");

			event.getChannel().retrieveMessageById(embedLong).queue(embed -> {
				embed.editMessageEmbeds(Templates.scorecard(event, points)).queue();
			});

		} else {

			Long embedId = main.pointsCollection.find(userIdQuery).first().getLong("EmbedId");

			event.getChannel().retrieveMessageById(embedId).queue(msg -> {
				int points = main.pointsCollection.find(userIdQuery).first().getInteger("Points");
				int highScore = main.pointsCollection.find(userIdQuery).first().getInteger("Highscore");

				int displayHighScore = highScore;

				if (points > highScore) {
					var saveHighScore = set("Highscore", points);
					main.pointsCollection.findOneAndUpdate(userIdQuery, saveHighScore);

					displayHighScore = points;
				}

				event.getMessage().delete().queue();

				msg.editMessageEmbeds(RankGuesserTemplates.incorrectAnswer(event, points, displayHighScore))
						.setActionRows(ActionRow.of(Button.danger("GG", "GG").asDisabled())).queue();

				var resetEmbedId = set("EmbedId", "placeholder");
				main.pointsCollection.findOneAndUpdate(userIdQuery, resetEmbedId);

				var resetLinkId = set("LinkId", "placeholder");
				main.pointsCollection.findOneAndUpdate(userIdQuery, resetLinkId);

				var resetPoints = set("Points", 0);
				main.pointsCollection.findOneAndUpdate(userIdQuery, resetPoints);

				var resetGameStatus = set("Ingame", false);
				main.pointsCollection.findOneAndUpdate(userIdQuery, resetGameStatus);

				var resetDifficulty = set("Difficulty", "placeholder");
				main.pointsCollection.findOneAndUpdate(userIdQuery, resetDifficulty);
				
				return;
			});

		}
	}

	public void sendRandomGame(GuildMessageReceivedEvent event) {
		int max = (int) main.clipsCollection.countDocuments();
		Random random = new Random();
		int randomInt = random.nextInt(max);
		var randomQuery = main.clipsCollection.find().limit(-1).skip(randomInt).first();
		var randomQuerySet = randomQuery;
		String randomUrl = main.clipsCollection.find(randomQuerySet).first().getString("URL");
		String randomUrlRankString = main.clipsCollection.find(randomQuerySet).first().getString("Rank");
		String rankCaseFixed = randomUrlRankString.substring(0, 1).toUpperCase() + randomUrlRankString.substring(1);

		var userIdQuery = eq("UserId", event.getAuthor().getIdLong());

		event.getChannel().sendMessageEmbeds(Templates.scorecard(event, 0)).queue(msg -> {
			Long msgId = msg.getIdLong();
			var update = set("EmbedId", msgId);
			main.pointsCollection.updateOne(userIdQuery, update);
		});

		if (main.pointsCollection.find(userIdQuery).first().getString("Difficulty").equalsIgnoreCase("EASY")) {

			String otherRankButton = randomRank().toString();

			while (otherRankButton.equals(randomUrlRankString)) {
				otherRankButton = randomRank().toString();
			}
			

			String otherButtonFixed = otherRankButton.substring(0, 1).toUpperCase() + otherRankButton.substring(1);

			ActionRow ar = ActionRow.of(Button.primary(randomUrlRankString, rankCaseFixed),
					Button.primary(otherRankButton, otherButtonFixed));
			
			Collections.shuffle(ar.getComponents());
			
			event.getChannel().sendMessage(randomUrl)
					.setActionRows(ar)
					.queueAfter(100, TimeUnit.MILLISECONDS, msg -> {
						Long msgId = msg.getIdLong();
						var update = set("LinkId", msgId);
						main.pointsCollection.updateOne(userIdQuery, update);
					});
		} else {
			// if its hard mode (default)
			event.getChannel().sendMessage(randomUrl)
					.setActionRows(
							ActionRow.of(Button.primary("IRON", "Iron"), Button.primary("BRONZE", "Bronze"),
									Button.primary("SILVER", "Silver"), Button.primary("GOLD", "Gold"),
									Button.primary("PLAT", "Plat")),
							ActionRow.of(Button.primary("DIAMOND", "Diamond"), Button.primary("IMMORTAL", "Immortal"),
									Button.primary("RADIANT", "Radiant")))
					.queueAfter(100, TimeUnit.MILLISECONDS, msg -> {
						Long msgId = msg.getIdLong();
						var update = set("LinkId", msgId);
						main.pointsCollection.updateOne(userIdQuery, update);
					});
		}

	}

	public void sendRandomGame(ButtonClickEvent event) {
		int max = (int) main.clipsCollection.countDocuments();
		Random random = new Random();
		int randomInt = random.nextInt(max);
		var randomQuery = main.clipsCollection.find().limit(-1).skip(randomInt).first();
		var randomQuerySet = randomQuery;
		String randomUrl = main.clipsCollection.find(randomQuery).first().getString("URL");
		String randomUrlRankString = main.clipsCollection.find(randomQuerySet).first().getString("Rank").toUpperCase();
		String rankCaseFixed = randomUrlRankString.substring(0, 1).toUpperCase() + randomUrlRankString.substring(1);

		var userIdQuery = eq("UserId", event.getUser().getIdLong());

		if (main.pointsCollection.find(userIdQuery).first().getString("Difficulty").equalsIgnoreCase("EASY")) {

			String otherRankButton = randomRank().toString().toUpperCase();

			while (otherRankButton.equals(randomUrlRankString)) {
				otherRankButton = randomRank().toString();
			}

			String otherButtonFixed = otherRankButton.substring(0, 1).toUpperCase() + otherRankButton.substring(1);

			ActionRow ar = ActionRow.of(Button.primary(randomUrlRankString, rankCaseFixed),
					Button.primary(otherRankButton, otherButtonFixed));
			
			Collections.shuffle(ar.getComponents());
			
			event.editMessage(randomUrl).setActionRows(ar).queueAfter(100, TimeUnit.MILLISECONDS);
		} else {
			event.getMessage().editMessage(randomUrl)
					.setActionRows(
							ActionRow.of(Button.primary("IRON", "Iron"), Button.primary("BRONZE", "Bronze"),
									Button.primary("SILVER", "Silver"), Button.primary("GOLD", "Gold"),
									Button.primary("PLAT", "Plat")),
							ActionRow.of(Button.primary("DIAMOND", "Diamond"), Button.primary("IMMORTAL", "Immortal"),
									Button.primary("RADIANT", "Radiant")))
					.queueAfter(100, TimeUnit.MILLISECONDS);
		}

	}

	private Ranks randomRank() {
		int pick = new Random().nextInt(Ranks.values().length);
		return Ranks.values()[pick];
	}

}
