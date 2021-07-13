package me.pray.commands.misc;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import me.pray.Main;
import me.pray.templates.PrefixTemplates;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PrefixCommand extends ListenerAdapter implements PrefixTemplates {

	private Main main;
	
	public PrefixCommand(Main main) {
		this.main = main;
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String content = event.getMessage().getContentRaw();
		String[] args = content.split("\\s+");
		var query = eq("Guild", event.getGuild().getIdLong());
		String prefix = main.prefixCollection.find(query).first().getString("Prefix");
		
		if(content.startsWith(prefix + "prefix")) {
			
			if(!event.getMember().getPermissions().contains(Permission.ADMINISTRATOR)) {
				event.getChannel().sendMessageEmbeds(PrefixTemplates.noAdminPermissions(event)).queue();
				return;
			}
			
			if(args.length != 2) {
				event.getChannel().sendMessageEmbeds(PrefixTemplates.invalidFormat(event, prefix)).queue();
			} else {
				String oldPrefix = prefix;
				String newPrefix = args[1];
				var update = set("Prefix", newPrefix);

				main.prefixCollection.updateOne(query, update);
				
				event.getChannel().sendMessageEmbeds(PrefixTemplates.prefixChange(event, oldPrefix, newPrefix)).queue();
				
			}
			
		}
		
	}
	
}
