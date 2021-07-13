package me.pray.commands.misc;

import static com.mongodb.client.model.Filters.eq;

import me.pray.Main;
import me.pray.templates.Templates;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MentionSupplyPrefix extends ListenerAdapter implements Templates {

	private Main main;

	public MentionSupplyPrefix(Main main) {
		this.main = main;
	}

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		var query = eq("Guild", event.getGuild().getIdLong());
		String prefix = main.prefixCollection.find(query).first().getString("Prefix");

		if (event.getMessage().getMentionedMembers().size() == 1) {
			if (event.getMessage().getMentionedMembers().get(0).getIdLong() == event.getJDA().getSelfUser().getIdLong()) {
				event.getChannel().sendMessageEmbeds(Templates.serverPrefix(event, prefix)).queue();
			}
		}
	}

}
