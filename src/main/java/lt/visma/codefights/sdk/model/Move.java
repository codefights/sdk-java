package lt.visma.codefights.sdk.model;

import java.util.LinkedList;
import java.util.List;

public class Move {
	private List<Area> attacks = new LinkedList<Area>();

	private List<Area> blocks = new LinkedList<Area>();

	private String comment;

	public List<Area> getAttacks() {
		return this.attacks;
	}

	public List<Area> getBlocks() {
		return this.blocks;
	}

	public String getComment() {
		return comment;
	}

	public Move setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public Move addAttack(Area area) {
		attacks.add(area);
		return this;
	}

	public Move addBlock(Area area) {
		blocks.add(area);
		return this;
	}

	public String toString() {
		StringBuilder rez = new StringBuilder("Move ");

		for (Area attack : attacks)
			rez.append(" ATTACK " + attack);

		for (Area defence : blocks)
			rez.append(" BLOCK " + defence);

		if (comment != null && comment.isEmpty() == false)
			rez.append(" COMMENT " + comment);

		return rez.toString();
	}
}
