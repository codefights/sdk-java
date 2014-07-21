package lt.visma.codefights.sdk.boilerplate.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import lt.visma.codefights.sdk.model.Area;
import lt.visma.codefights.sdk.model.Move;

public class Protocol {

	public static final String HANDSHAKE = "I-AM ready";

	public static final String REQUEST_HEADER = "";

	public static final String YOUR_SCORE = "YOUR-SCORE";
	public static final String OPPONENT_SCORE = "OPPONENT-SCORE";
	public static final String ENEMY_MOVE = "ENEMY-MOVE";
	public static final String MOVE_COMMENT = "COMMENT";

	public static class ServerResponse {
		public Move move;
		public int score1;
		public int score2;
	}

	private PrintStream outStream;
	private BufferedReader inStream;

	public Protocol(BufferedReader inStream, PrintStream outStream) {
		this.outStream = outStream;
		this.inStream = inStream;
	}

	public void handshake() {
		outStream.println(HANDSHAKE);
	}

	public void sendRequest(Move move) {
		outStream.println(REQUEST_HEADER + serializeMove(move));
	}

	public ServerResponse readResponse() throws IOException {
		return parse(inStream.readLine());
	}

	public static String serializeMove(Move move) {
		String rez = "";

		for (Area attack : move.getAttacks())
			rez += "a" + attack.toString().substring(0, 1);

		for (Area defence : move.getBlocks())
			rez += "b" + defence.toString().substring(0, 1);

		if (move.getComment() != null
				&& move.getComment().trim().isEmpty() == false)
			rez += "c" + sanitizeComment(move.getComment());

		return rez.toLowerCase();
	}
	
	public static Move parseMove(String input) {
		Move rez = new Move();

		int index = 0;
		while (index < input.length()) {
			char type = input.charAt(index++);

			switch (type) {
				case 'a': rez.addAttack(getArea(input, index++)); break;
				case 'b': rez.addBlock(getArea(input, index++)); break;
				case '.':
				case 'c': rez.setComment(input.substring(index)); 
						  index = input.length() + 1;
				break;
				case ' ':
				case '\t':
				case '\r':
				case '\n':
					continue;
				default:
					throw new IllegalArgumentException("Unrecognized input: " + type+ " "+(int)type);
			}
		}
		return rez;
	}

	public static String sanitizeComment(String comment){
		if (comment == null || comment.isEmpty())
			return null;
		
		String result = comment
							.replace("\t", " ")
							.replace("\n", " ")
							.replace("\"", "`")
							.trim();
		
		if (result.length() > 150)
			result = result.substring(0, 150);
		
		return result;
	}
	
	protected static ServerResponse parse(String line) {
		ServerResponse result = new ServerResponse();

		String[] words = line.split(" ");
		int index = 0;
		
		while (index < words.length) {
			String firstKeyword = words[index++];

			if (index >= words.length)
				throw new IllegalArgumentException(
						"Insufficient params. Syntax is [YOUR-SCORE area] [OPPONENT-SCORE area] [ENEMY-MOVE move]");

			String nextKeyword = words[index++];

			if (YOUR_SCORE.equals(firstKeyword))
				result.score1 = Integer.parseInt(nextKeyword);
			else 
			if (OPPONENT_SCORE.equals(firstKeyword))
				result.score2 = Integer.parseInt(nextKeyword);
			else 
			if (ENEMY_MOVE.equals(firstKeyword))
				result.move = parseMove(nextKeyword);
			else
				throw new IllegalArgumentException(
						"invalid keyword "
								+ firstKeyword
								+ ". Syntax is [YOUR-SCORE area] [OPPONENT-SCORE area] [ENEMY-MOVE move]");
		}
		return result;
	}

	@SuppressWarnings("unused")
	private static String mergeStrings(String[] words, int i) {
		String rez = "";
		for (int j = i; j < words.length; j++)
			rez += words[j] + " ";

		return rez.trim();
	}

	private static Area getArea(String line, int index) {
		if (index >= line.length())
			throw new IllegalArgumentException("Must also specify attack/defence area!");

		switch (line.charAt(index)) {
		case 'n':
			return Area.NOSE;
		case 'j':
			return Area.JAW;
		case 'b':
			return Area.BELLY;
		case 'g':
			return Area.GROIN;
		case 'l':
			return Area.LEGS;
		default:
			throw new IllegalArgumentException("Unrecognized area: " + line.charAt(index));
		}
	}
	
}
