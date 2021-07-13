package me.pray.commands.game;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.model.Sorts;

import me.pray.Main;
import me.pray.templates.LeaderboardTemplates;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Leaderboards extends ListenerAdapter implements LeaderboardTemplates {

	private Main main;

	public Leaderboards(Main main) {
		this.main = main;
	}

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String content = event.getMessage().getContentRaw();
		String[] args = content.split("\\s+");
		var query = eq("Guild", event.getGuild().getIdLong());
		String prefix = main.prefixCollection.find(query).first().getString("Prefix");

		if (content.startsWith(prefix + "leaderboards") || content.startsWith(prefix + "leaderboard")
				|| content.startsWith(prefix + "lb")) {

			if (args.length != 1) {
				event.getChannel().sendMessageEmbeds(LeaderboardTemplates.invalidFormat(event, prefix)).queue();
				return;
			}

			event.getChannel().sendMessageEmbeds(LeaderboardTemplates.fetchingLeaderboards(event)).queue(fetchMsg -> {
				var sortedQueryFirst = main.pointsCollection.find().sort(Sorts.descending("Highscore")).limit(1)
						.first();
				var sortedQuerySecond = main.pointsCollection.find().sort(Sorts.descending("Highscore")).skip(1)
						.limit(1).first();
				var sortedQueryThird = main.pointsCollection.find().sort(Sorts.descending("Highscore")).skip(2).limit(1)
						.first();

				if (sortedQueryFirst == null) {
					fetchMsg.editMessageEmbeds(LeaderboardTemplates.unavaiableLeaderboards(event)).queue();
					return;
				}

				if (sortedQuerySecond == null) {
					fetchMsg.editMessageEmbeds(LeaderboardTemplates.unavaiableLeaderboards(event)).queue();
					return;
				}

				if (sortedQueryThird == null) {
					fetchMsg.editMessageEmbeds(LeaderboardTemplates.unavaiableLeaderboards(event)).queue();
					return;
				}

//				System.out.println("passed null checks");

				int firstPlaceScore = sortedQueryFirst.getInteger("Highscore");
				Long firstPlaceUserId = sortedQueryFirst.getLong("UserId");

//				System.out.println("got first place");

				int secondPlaceScore = sortedQuerySecond.getInteger("Highscore");
				Long secondPlaceUserId = sortedQuerySecond.getLong("UserId");

//				System.out.println("got second place");

				int thirdPlaceScore = sortedQueryThird.getInteger("Highscore");
				Long thirdPlaceUserId = sortedQueryThird.getLong("UserId");

//				System.out.println("got third place");

				event.getJDA().retrieveUserById(firstPlaceUserId).queue(firstPlaceUser -> {
//					System.out.println("retreived1");
					String firstPlaceName = firstPlaceUser.getAsTag();

					event.getJDA().retrieveUserById(secondPlaceUserId).queue(secondPlaceUser -> {
//						System.out.println("retreived2");
						String secondPlaceName = secondPlaceUser.getAsTag();

						event.getJDA().retrieveUserById(thirdPlaceUserId).queue(thirdPlaceUser -> {
//							System.out.println("retreived3");
							String thirdPlaceName = thirdPlaceUser.getAsTag();

							fetchMsg.editMessageEmbeds(
									LeaderboardTemplates.displayLeaderboards(event, firstPlaceName, firstPlaceScore,
											secondPlaceName, secondPlaceScore, thirdPlaceName, thirdPlaceScore))
									.queue();

						});

					});

				});
			});

		}

	}

}
