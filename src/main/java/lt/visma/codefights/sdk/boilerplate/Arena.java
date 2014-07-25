package lt.visma.codefights.sdk.boilerplate;

import java.util.HashMap;
import java.util.Map;

import lt.visma.codefights.sdk.model.GameScoringRules;
import lt.visma.codefights.sdk.model.IFighter;
import lt.visma.codefights.sdk.model.Move;

public class Arena {
	
	private Map<String, IFighter> fighters = new HashMap<String, IFighter>();

	private Commentator commentator = new Commentator();

	public Arena registerFighter(IFighter fighter, String name) {
		fighters.put(name, fighter);
		return this;
	}

	public void stageFight() {
		if (fighters.size() != 2)
			throw new IllegalArgumentException("Must be 2 fighters!");

		String f1name = fighters.keySet().iterator().next();
		IFighter fighter1 = fighters.get(f1name);
		fighters.remove(f1name);

		String f2name = fighters.keySet().iterator().next();
		IFighter fighter2 = fighters.get(f2name);
		fighters.remove(f2name);

		commentator.setFighterNames(f1name, f2name);

		Move f1Move = null;
		Move f2Move = null;

		int score1 = 0;
		int score2 = 0;

		int f1Lifepoints, f2Lifepoints = f1Lifepoints = GameScoringRules.LIFEPOINTS;

		while (f1Lifepoints > 0 && f2Lifepoints > 0) {
			Move move1 = fighter1.makeNextMove(f2Move, score1, score2);
			if (GameScoringRules.isMoveLegal(move1)==false)
				throw new IllegalArgumentException(f1name+" made an illegal move: "+move1);

			Move move2 = fighter2.makeNextMove(f1Move, score2, score1);
			if (GameScoringRules.isMoveLegal(move2)==false)
				throw new IllegalArgumentException(f2name+" made an illegal move: "+move2);

			score1 = GameScoringRules.calculateScore(move1.getAttacks(),
					move2.getBlocks());
			score2 = GameScoringRules.calculateScore(move2.getAttacks(),
					move1.getBlocks());

			commentator.describeRound(move1, move2, score1, score2);

			f1Lifepoints -= score2;
			f2Lifepoints -= score1;

			f1Move = move1;
			f2Move = move2;
		}

		commentator.gameOver(f1Lifepoints, f2Lifepoints);
	}

	public Arena setCommentator(Commentator c) {
		this.commentator = c;
		return this;
	}

}
