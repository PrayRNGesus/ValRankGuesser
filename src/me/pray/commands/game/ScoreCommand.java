package me.pray.commands.game;

import static com.mongodb.client.model.Filters.eq;

import me.pray.Main;
import me.pray.templates.ScoreTemplates;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ScoreCommand extends ListenerAdapter implements ScoreTemplates {

	private Main main;

	public ScoreCommand(Main main) {
		this.main = main;
	}

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String content = event.getMessage().getContentRaw();
		String[] args = content.split("\\s+");
		var query = eq("Guild", event.getGuild().getIdLong());
		String prefix = main.prefixCollection.find(query).first().getString("Prefix");
		Long userToCheckId = null;

		if (content.startsWith(prefix + "highscore") || content.startsWith(prefix + "score") || content.startsWith(prefix + "points")) {
			
			if (args.length == 1) {
				userToCheckId = event.getAuthor().getIdLong();
			} else if (args.length == 2) {
				
				try {
					userToCheckId = event.getMessage().getMentionedMembers().get(0).getIdLong();
				} catch (Exception exc) {
					
					try {	
						userToCheckId = Long.parseLong(args[1]);
					} catch (Exception exc2) {
						event.getChannel().sendMessageEmbeds(ScoreTemplates.invalidFormat(event, prefix)).queue();
						return;
					}
				}
			} else {
				event.getChannel().sendMessageEmbeds(ScoreTemplates.invalidFormat(event, prefix)).queue();
				return;
			}
			
			event.getJDA().retrieveUserById(userToCheckId).queue(user -> {
				event.getChannel().sendMessageEmbeds(ScoreTemplates.highscoreMessage(event, prefix,
						user.getAsMention(), retrieveHighscore(user.getIdLong()))).queue();
			}, failure -> {
				event.getChannel().sendMessageEmbeds(ScoreTemplates.invalidFormat(event, prefix)).queue();
				return;
			});
			
		}
	}

	public int retrieveHighscore(Long userIdLong) {
		var query = eq("UserId", userIdLong);

		if (main.pointsCollection.find(query).first() == null) {
			return 0;
		} else {
			return main.pointsCollection.find(query).first().getInteger("Highscore");
		}
	}

}
