package lt.visma.codefights.sdk.boilerplate;

import java.io.PrintStream;

import lt.visma.codefights.sdk.boilerplate.server.Protocol;
import lt.visma.codefights.sdk.model.Area;
import lt.visma.codefights.sdk.model.GameScoringRules;
import lt.visma.codefights.sdk.model.Move;

public class Commentator {

	private String fighter1 = "Fighter1";
	private String fighter2 = "Fighter2";

	private PrintStream outStream = System.out;

	private int lp1 = GameScoringRules.LIFEPOINTS;
	private int lp2 = GameScoringRules.LIFEPOINTS;

	public void setFighterNames(String fighter1name, String fighter2name) {
		fighter1 = fighter1name;
		fighter2 = fighter2name;
	}

	public void describeRound(Move move1, Move move2, int score1, int score2) {
		describeMove(fighter1, move1, score1, move2);
		describeMove(fighter2, move2, score2, move1);

		lp1 -= score2;
		lp2 -= score1;

		outStream.println(fighter1 + " vs " + fighter2 + ": " + lp1 + " to " + lp2);
		outStream.println();
	}

	public void gameOver(int f1Lifepoints, int f2Lifepoints) {
		outStream.println("FIGHT OVER");
		
		if (f1Lifepoints > f2Lifepoints)
			outStream.println("THE WINNER IS " + fighter1);
		else 
		if (f2Lifepoints > f1Lifepoints)
			outStream.println("THE WINNER IS " + fighter2);
		else
			outStream.println("IT'S A DRAW!!!");
	}

	private void describeMove(String fighterName, Move move, int score,	Move counterMove) {
		outStream.println(fighterName
				+ describeAttacks(move, counterMove, score)
				+ describeDefences(move)
				+ describeComment(move));
	}

	private static String describeComment(Move move){
		String comment = Protocol.sanitizeComment(move.getComment());
		if (comment == null)
			return "";
		
		return " saying \""+comment+"\"";
	}
	
	private static String describeAttacks(Move move, Move counterMove, int score) {
		if (move.getAttacks().size() <= 0)
			return " did NOT attack at all ";

		String rez = " attacked ";
		
		for (Area attack : move.getAttacks()) {
			rez += attack;
			if (counterMove.getBlocks().contains(attack))
				rez += "(-), ";
			else
				rez += "(+), ";
		}
		return rez += " scoring " + score;
	}

	private static String describeDefences(Move move) {
		if (move.getBlocks().size() <= 0)
			return "  and was NOT defending at all.";

		String rez = " while defending ";
		for (Area defence : move.getBlocks())
			rez += defence + ", ";

		return rez;
	}

}
