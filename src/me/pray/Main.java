package me.pray;

import static com.mongodb.client.model.Filters.eq;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import me.pray.clips.RegisterClips;
import me.pray.commands.db_senders.ReportBug;
import me.pray.commands.db_senders.SubmitClip;
import me.pray.commands.db_senders.SuggestCommand;
import me.pray.commands.game.Leaderboards;
import me.pray.commands.game.QuitGame;
import me.pray.commands.game.RankGuesser;
import me.pray.commands.game.ScoreCommand;
import me.pray.commands.misc.About;
import me.pray.commands.misc.HelpCommand;
import me.pray.commands.misc.InviteCommand;
import me.pray.commands.misc.MentionSupplyPrefix;
import me.pray.commands.misc.PrefixCommand;
import me.pray.commands.misc.TOS;
import me.pray.gui.GUI;
import me.pray.gui.Prompt;
import me.pray.handlers.JoinHandler;
import me.pray.handlers.LeaveHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {

	private String token;
	private JDA jda;
	public static String madeBy = "Made by: Pray#0001";
	public static String version = "1.4";
	
	public MongoClient mongoClient;
	public MongoDatabase db;
	public MongoCollection<Document> prefixCollection;
	public MongoCollection<Document> pointsCollection;
	public MongoCollection<Document> clipsCollection;
	public MongoCollection<Document> submitionsCollection;
	public MongoCollection<Document> bugsCollection;
	public MongoCollection<Document> suggestionCollection;
	public Document prefixCollDoc;
	public Document pointsCollDoc;
	public Document clipsCollDoc = new Document();

	private static Main main = new Main();
	static RegisterClips rc = new RegisterClips(main);
	
	private GUI gui;
	private boolean shuttingDown = false;
	
	public static void main(String[] args) throws LoginException, InterruptedException {
		main.startBot();
		rc.registerClips();
	}

	private void startBot() throws LoginException, InterruptedException {
		
		Prompt prompt = new Prompt("Valorant RG Bot",
				"Switching to nogui mode. You can manually start in nogui mode by including the -Dnogui=true flag.");

		if (!prompt.isNoGUI()) {
			try {
				GUI gui = new GUI(main);
				main.setGUI(gui);
				gui.init();
			} catch (Exception e) {
				System.out.println("Could not start GUI. If you are "
						+ "running on a server or in a location where you cannot display a "
						+ "window, please run in nogui mode using the -Dnogui=true flag.");
			}
		}
		
		token = castToken();

		mongoClient = MongoClients.create();
		db = mongoClient.getDatabase("ValorantExtras");
		
		prefixCollection = db.getCollection("Prefixs");
		pointsCollection = db.getCollection("Points");
		clipsCollection = db.getCollection("Clips");
		submitionsCollection = db.getCollection("Submitions");
		bugsCollection = db.getCollection("Bug_Reports");
		suggestionCollection = db.getCollection("Suggestions");
		
		JDABuilder api = JDABuilder.createDefault(token);
		api.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_VOICE_STATES);
		api.setStatus(OnlineStatus.ONLINE);
		api.setActivity(Activity.watching("random clips!"));
		api.addEventListeners(
				new RankGuesser(main), 
				new QuitGame(main),
				new HelpCommand(main),
				new ScoreCommand(main),
				new Leaderboards(main),
				new InviteCommand(main),
				new PrefixCommand(main),
				new ReportBug(main),
				new SubmitClip(main),
				new JoinHandler(main),
				new LeaveHandler(main),
				new About(main),
				new SuggestCommand(main),
				new MentionSupplyPrefix(main),
				new TOS(main));
		jda = api.build().awaitReady();

		System.out.println("\n---------------------------------------------------");
		System.out.println("Hey there, the current version is: " + version);
		System.out.println("---------------------------------------------------\n");
		
		
		// connecting to database
		

		// checking if the guild's prefix exist's in the database, if not it creates one
		for (Guild guild : jda.getGuilds()) {
			var query = eq("Guild", guild.getIdLong());

			if (prefixCollection.find(query).first() == null) {
				prefixCollDoc = new Document();
				prefixCollDoc.append("Guild", guild.getIdLong());
				prefixCollDoc.append("Prefix", "v!");
				prefixCollDoc.append("NeedsRole", false);
				prefixCollection.insertOne(prefixCollDoc);
			} 
				
			
		}
	}

	// casting the token to the token inside of the token.txt file
	// if no file exists, it attempts to create one
	private String castToken() {
		File file = new File("token.txt");
		Scanner sc;

		try {
			sc = new Scanner(file);
			while (sc.hasNext()) {
				token = sc.next();
			}
		} catch (FileNotFoundException exc) {
			File tokenFile = new File("token.txt");
			System.out.println("token.txt file not found... creating one");
			try {
				tokenFile.createNewFile();
				System.out.println("token.txt created successfully.  Please add your token to it, then start the bot");
				System.exit(0);
			} catch (IOException e) {
				System.out.println("Failed to create token.txt, please create it manually.");
				System.exit(0);
			}
		}
		return token;
	}
	
	public void shutdown() {
		if (shuttingDown)
			return;
		shuttingDown = true;
		if (jda.getStatus() != JDA.Status.SHUTTING_DOWN) {
			jda.shutdown();
		}
		if (gui != null)
			gui.dispose();
		System.exit(0);
	}

	public void setGUI(GUI gui) {
		this.gui = gui;
	}

}
