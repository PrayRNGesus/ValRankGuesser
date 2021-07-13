package me.pray.commands.db_senders;

import static com.mongodb.client.model.Filters.eq;

import org.apache.commons.validator.routines.UrlValidator;
import org.bson.Document;

import me.pray.Main;
import me.pray.templates.SubmitionTemplates;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SubmitClip extends ListenerAdapter {

	private Main main;

	public SubmitClip(Main main) {
		this.main = main;
	}

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String content = event.getMessage().getContentRaw();
		String[] args = content.split("\\s+");
		var query = eq("Guild", event.getGuild().getIdLong());
		String prefix = main.prefixCollection.find(query).first().getString("Prefix");

		UrlValidator validator = new UrlValidator();
		
		if (content.startsWith(prefix + "submit")) {
			if (args.length == 1 || args.length > 2) {
				System.out.println(args.length);
				event.getChannel().sendMessageEmbeds(SubmitionTemplates.invalidFormat(event, prefix)).queue();
			} else {
				if (validator.isValid(args[1])) {
					
					Document submission = new Document();
					submission.append("Description", args[1]);
					submission.append("Reporter_Name", event.getAuthor().getAsTag());
					submission.append("Reporter_Id", event.getAuthor().getIdLong());
					
					main.submitionsCollection.insertOne(submission);
					
					event.getChannel()
							.sendMessageEmbeds(
									SubmitionTemplates.submitionReceived(event, event.getAuthor().getAsMention()))
							.queue();
				} else {
					event.getChannel().sendMessageEmbeds(SubmitionTemplates.invalidUrl(event, prefix)).queue();
				}
			}
		}
	}

}
