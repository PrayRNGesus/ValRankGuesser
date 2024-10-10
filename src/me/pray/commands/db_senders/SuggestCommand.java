package me.pray.commands.db_senders;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import me.pray.Main;
import me.pray.templates.Templates;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SuggestCommand extends ListenerAdapter implements Templates {

	private Main main;

	public SuggestCommand(Main main) {
		this.main = main;
	}

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String content = event.getMessage().getContentRaw();
		String[] args = content.split("\\s+");
		var query = eq("Guild", event.getGuild().getIdLong());
		String prefix = main.prefixCollection.find(query).first().getString("Prefix");

		if (content.startsWith(prefix + "suggest")) {
			if (args.length == 1) {
				event.getChannel().sendMessageEmbeds(Templates.invalidSuggestionFormat(event, prefix)).queue();
			} else {
				String suggestionMsg = "";

				for (int i = 1; i < args.length; i++) {
					suggestionMsg += args[i] + " ";
				}

				Document suggestion = new Document();
				suggestion.append("Description", suggestionMsg);
				suggestion.append("Reporter_Name", event.getAuthor().getAsTag());
				suggestion.append("Reporter_Id", event.getAuthor().getIdLong());
				main.suggestionCollection.insertOne(suggestion);

				event.getChannel().sendMessageEmbeds(Templates.suggestionReceived(event)).queue();
			}
		}

	}
	
}
