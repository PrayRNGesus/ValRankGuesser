package me.pray.commands.misc;

import static com.mongodb.client.model.Filters.eq;

import me.pray.Main;
import me.pray.templates.HelpTemplates;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelpCommand extends ListenerAdapter implements HelpTemplates {

	private Main main;

	public HelpCommand(Main main) {
		this.main = main;
	}

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

		String content = event.getMessage().getContentRaw();
		String[] args = content.split("\\s+");
		var query = eq("Guild", event.getGuild().getIdLong());
		String prefix = main.prefixCollection.find(query).first().getString("Prefix");

		if (content.startsWith(prefix + "help")) {

			if (args.length == 1) {
				event.getChannel().sendMessageEmbeds(HelpTemplates.mainHelp(event, prefix)).queue();
			} else if (args.length == 2) {
				if (args[1].equalsIgnoreCase("game")) {
					event.getChannel().sendMessageEmbeds(HelpTemplates.gameHelp(event, prefix)).queue();
				} else if (args[1].equalsIgnoreCase("misc")) {
					event.getChannel().sendMessageEmbeds(HelpTemplates.miscHelp(event, prefix)).queue();
				} else {
					event.getChannel().sendMessageEmbeds(HelpTemplates.mainHelp(event, prefix)).queue();
				}

			} else if (args.length > 2) {
				event.getChannel().sendMessageEmbeds(HelpTemplates.mainHelp(event, prefix)).queue();
				return;
			}
		}
	}
}
