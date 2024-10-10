package me.pray.commands.misc;

import static com.mongodb.client.model.Filters.eq;

import me.pray.Main;
import me.pray.templates.Templates;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class InviteCommand extends ListenerAdapter implements Templates {

	private Main main;
	
	public InviteCommand(Main main) {
		this.main = main;
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String content = event.getMessage().getContentRaw();
		var query = eq("Guild", event.getGuild().getIdLong());
		String prefix = main.prefixCollection.find(query).first().getString("Prefix");
		
		if(content.equalsIgnoreCase(prefix + "invite")) {
			event.getChannel().sendMessageEmbeds(Templates.invite(event)).queue();
		}
		
	}
	
}
