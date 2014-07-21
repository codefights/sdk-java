package lt.visma.codefights.sdk.samples;

import java.util.Random;

import lt.visma.codefights.sdk.model.Area;
import lt.visma.codefights.sdk.model.IFighter;
import lt.visma.codefights.sdk.model.Move;

public class Boxer implements IFighter {
	
	private Area attack1 = Area.NOSE;
	private Area attack2 = Area.JAW;
	private Area defence = Area.NOSE;

	private int myScoreTotal = 0;
	private int opponentScoreTotal = 0;

	public Move makeNextMove(Move opponentLastMove, int myLastScore, int oppLastScore) {
		myScoreTotal += myLastScore;
		opponentScoreTotal += oppLastScore;

		Move move = new Move().addAttack(attack1).addAttack(attack2);

		if (opponentLastMove != null)
			if (opponentLastMove.getAttacks().contains(defence) == false)
				defence = changeDefence(defence);

		if (myScoreTotal >= opponentScoreTotal)
			move.addAttack(createRandomAttack()); // 3 attacks, 0 defence
		else
			move.addBlock(defence); // 2 attacks, 1 defence

		return move;
	}

	private Area changeDefence(Area oldDefence) {
		return (oldDefence == Area.NOSE) ? Area.JAW : Area.NOSE;
	}

	private Area createRandomAttack() {
		return new Random().nextDouble() > 0.5d ? Area.BELLY : Area.JAW;
	}

}
