package me.pray.handlers;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import me.pray.Main;
import me.pray.templates.Templates;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JoinHandler extends ListenerAdapter implements Templates {

	private Main main;

	public JoinHandler(Main main) {
		this.main = main;
	}

	@Override
	public void onGuildJoin(GuildJoinEvent event) {
		
		var query = eq("Guild", event.getGuild().getIdLong());
		
		if(main.prefixCollection.find(query).first() == null ) {
			Document addGuild = new Document();
			addGuild.append("Guild", event.getGuild().getIdLong());
			addGuild.append("Prefix", "v!");
			addGuild.append("NeedsRole", false);
			main.prefixCollection.insertOne(addGuild);
			
			event.getGuild().getDefaultChannel().sendMessageEmbeds(Templates.guildJoin(event, "v!")).queue();
			
		}
		
		
		
	}
	
}
