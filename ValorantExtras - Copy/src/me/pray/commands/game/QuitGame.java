package me.pray.commands.game;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import me.pray.Main;
import me.pray.templates.RankGuesserTemplates;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class QuitGame extends ListenerAdapter implements RankGuesserTemplates {

	private Main main;

	public QuitGame(Main main) {
		this.main = main;
	}

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String content = event.getMessage().getContentRaw();
		var query = eq("Guild", event.getGuild().getIdLong());
		String prefix = main.prefixCollection.find(query).first().getString("Prefix");
		var userIdQuery = eq("UserId", event.getAuthor().getIdLong());

		if (content.equalsIgnoreCase(prefix + "quit")) {
			if (main.pointsCollection.find(userIdQuery).first() != null) {

				
				if(main.pointsCollection.find(userIdQuery).first().getBoolean("Ingame") == false) {
					event.getChannel().sendMessageEmbeds(RankGuesserTemplates.joinAGame(event, prefix)).queue();
					return;
				}
				
				int points = main.pointsCollection.find(userIdQuery).first().getInteger("Points");
				int highScore = main.pointsCollection.find(userIdQuery).first().getInteger("Highscore");

				if (points > highScore) {
					var saveHighScore = set("Highscore", points);
					main.pointsCollection.findOneAndUpdate(userIdQuery, saveHighScore);
				}

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
				
				event.getChannel().sendMessageEmbeds(RankGuesserTemplates.quitGame(event, prefix)).queue();
			} else {
				event.getChannel().sendMessageEmbeds(RankGuesserTemplates.joinAGame(event, prefix)).queue();
			}
		}

	}

}
