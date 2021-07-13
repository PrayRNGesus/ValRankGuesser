package me.pray.handlers;

import static com.mongodb.client.model.Filters.eq;

import me.pray.Main;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LeaveHandler extends ListenerAdapter {

	private Main main;

	public LeaveHandler(Main main) {
		this.main = main;
	}

	@Override
	public void onGuildLeave(GuildLeaveEvent event) {
		
		var query = eq("Guild", event.getGuild().getIdLong());
		
		if(main.prefixCollection.find(query).first() != null) {
			main.prefixCollection.findOneAndDelete(query);
		}
		
	}
}
