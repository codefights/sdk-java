package lt.visma.codefights.sdk.model;

import java.util.List;

public class GameScoringRules {

	public static int LIFEPOINTS = 150;

	public static int calculateScore(List<Area> attackAreas, List<Area> blockAreas) {
		int rez = 0;

		if (attackAreas == null)
			return rez;

		for (Area attack : attackAreas)
			if (blockAreas.contains(attack) == false)
				rez += attack.getValue();

		return rez;
	}

	public static boolean isMoveLegal(Move move) {
		return ( move.getAttacks().size() + move.getBlocks().size() <= 3 );
	}

}
