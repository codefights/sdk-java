package lt.visma.codefights.sdk.samples;

import java.util.Random;

import lt.visma.codefights.sdk.model.Area;
import lt.visma.codefights.sdk.model.IFighter;
import lt.visma.codefights.sdk.model.Move;

public class Kickboxer implements IFighter {
	
    private Area attack1 = Area.JAW;
    private Area attack2 = Area.NOSE;
    private Area defence = Area.NOSE;

    public Move makeNextMove(Move opponentLastMove, int myLastScore, int oppLastScore) 
    {
	    if (opponentLastMove != null)
            if (opponentLastMove.getBlocks().contains(this.attack1))
                this.attack1 = CreateRandomArea();
    
        this.attack2 = CreateRandomArea();
    
        return new Move()
                    .addAttack(attack1)
                    .addAttack(attack2)
                    .addBlock(defence);
    }

    private Area CreateRandomArea() 
    {
        double random = new Random().nextDouble();
        if (random<0.3)
            return Area.NOSE;

        if (random<0.7)
            return Area.JAW;

        if (random<0.9)
            return Area.GROIN; // oh yeah

        return Area.BELLY;
    }	

}
