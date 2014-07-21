package lt.visma.codefights.sdk.model;

public interface IFighter {
	
	Move makeNextMove(Move opponentsLastMove, int myLastScore, int opponentsLastScore);

}
