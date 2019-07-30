package student_player;

import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoMove;

/**
 * 
 * @author irmakpakis
 * 
 * Stores States of the board and moves
 *
 */

public class PentagoStateMove {
	private PentagoBoardState pbs;
	private PentagoMove move;
	public PentagoBoardState getPbs() {
		return pbs;
	}
	public void setPbs(PentagoBoardState pbs) {
		this.pbs = pbs;
	}
	public PentagoMove getMove() {
		return move;
	}
	public void setMove(PentagoMove move) {
		this.move = move;
	}
	public PentagoStateMove(PentagoBoardState pbs, PentagoMove move) {
		super();
		this.pbs = pbs;
		this.move = move;
	}
}
