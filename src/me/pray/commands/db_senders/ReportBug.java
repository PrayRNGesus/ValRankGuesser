package me.pray.commands.db_senders;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import me.pray.Main;
import me.pray.templates.Templates;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReportBug extends ListenerAdapter implements Templates {

	private Main main;

	public ReportBug(Main main) {
		this.main = main;
	}

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String content = event.getMessage().getContentRaw();
		String[] args = content.split("\\s+");
		var query = eq("Guild", event.getGuild().getIdLong());
		String prefix = main.prefixCollection.find(query).first().getString("Prefix");

		if (content.startsWith(prefix + "report")) {
			if (args.length == 1) {
				event.getChannel().sendMessageEmbeds(Templates.invalidBugReportFormat(event, prefix)).queue();
			} else {
				String bugReportMsg = "";

				for (int i = 1; i < args.length; i++) {
					bugReportMsg += args[i] + " ";
				}

				Document report = new Document();
				report.append("Description", bugReportMsg);
				report.append("Reporter_Name", event.getAuthor().getAsTag());
				report.append("Reporter_Id", event.getAuthor().getIdLong());
				main.bugsCollection.insertOne(report);

				event.getChannel().sendMessageEmbeds(Templates.bugReportReceived(event)).queue();
			}
		}

	}

}
