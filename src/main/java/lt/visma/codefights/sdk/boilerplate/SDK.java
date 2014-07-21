package lt.visma.codefights.sdk.boilerplate;

import java.io.IOException;

import lt.visma.codefights.sdk.MyFighter;
import lt.visma.codefights.sdk.boilerplate.server.ServerMode;
import lt.visma.codefights.sdk.model.IFighter;
import lt.visma.codefights.sdk.samples.Boxer;
import lt.visma.codefights.sdk.samples.Kickboxer;

public class SDK {
	public static String FIGHT_HUMAN_SWITCH = "--fight-me";
	public static String FIGHT_BOT_SWITCH = "--fight-bot";
	public static String RUN_ON_SERVER_SWITCH = "--fight-on-server";

	public static String USAGE_INSTRUCTIONS = FIGHT_HUMAN_SWITCH
			+ "\t\truns your bot against you in interactive mode\n"
			+ FIGHT_BOT_SWITCH
			+ " boxer\truns your bot against a built-in boxer bot\n"
			+ FIGHT_BOT_SWITCH
			+ " kickboxer\truns your bot against a built-in kickboxer bot\n"
			+ RUN_ON_SERVER_SWITCH
			+ "\truns your bot in codefights engine environment";

	public static void main(String[] args) throws IOException {
		if (isFightHumanMode(args))
			new Arena()
					.registerFighter(new Human(), "You")
					.registerFighter(new MyFighter(), "Your bot")
					.stageFight();
		else 
		if (isFightBotMode(args))
			new Arena()
					.registerFighter(new MyFighter(), "Your bot")
					.registerFighter(createBot(args), args[1])
					.stageFight();
		else 
		if (isRunInServerMode(args))
			new ServerMode()
				.run(new MyFighter());
		else
			printUsageInstructions(args);
	}

	private static boolean isRunInServerMode(String[] args) {
		return args.length == 1
				&& args[0].equalsIgnoreCase(RUN_ON_SERVER_SWITCH);
	}

	private static boolean isFightBotMode(String[] args) {
		return args.length >= 2 && args[0].equalsIgnoreCase(FIGHT_BOT_SWITCH);
	}

	private static boolean isFightHumanMode(String[] args) {
		return args.length == 1 && args[0].equalsIgnoreCase(FIGHT_HUMAN_SWITCH);
	}

	private static void printUsageInstructions(String[] args) {
		if (args.length > 0) {
			System.out.print("unrecognized option(s): ");

			for (String arg : args)
				System.out.print(arg + " ");

			System.out.println();
		}
		System.out.println(USAGE_INSTRUCTIONS);
	}

	private static IFighter createBot(String[] args) {
		if ("boxer".equalsIgnoreCase(args[1]))
			return new Boxer();

		if ("kickboxer".equalsIgnoreCase(args[1]))
			return new Kickboxer();

		throw new IllegalArgumentException("unrecognized built-in bot: " + args[1]);
	}
}
