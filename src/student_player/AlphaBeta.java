package student_player;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoMove;

public class AlphaBeta {
	static final double MIN = -9999;
	static final double MAX = 9999;
	static final int DEPTH_LIMIT = 1;
	static final int winValue = 10;
	static final int drawValue = 3;
	static final int loseValue = -10;

	public int irmak;
	public int oponnet;

	public AlphaBeta(int irmak, int oponent) {
		this.irmak = irmak;
		this.oponnet = oponent;
	}

	public PentagoMove getBestMove(PentagoBoardState pbs) {
		//stateMoves list contains all the child states
		List<PentagoStateMove> stateMoves = generateAllChildStates(pbs);
		//secretSauce provides the value and the index of the best move
		ValueIndex secretSauce = minimax(0, 0, pbs, MIN, MAX);
		//returns the best move out that minimax algorith provided
		return stateMoves.get(secretSauce.getIndex()).getMove();
	}

	// helper function- generates all possiable states
	private static List<PentagoStateMove> generateAllChildStates(PentagoBoardState pbs) {
		//Gets all possiable moves
		List<PentagoMove> moves = pbs.getAllLegalMoves();
		List<PentagoStateMove> stateMoves = new ArrayList<>();
		//add all possiable moves to previous state and put each one in a list
		for (PentagoMove move : moves) {
			PentagoBoardState child = (PentagoBoardState) pbs.clone();
			child.processMove(move);
			stateMoves.add(new PentagoStateMove(child, move));
		}

		return stateMoves;
	}

	// Simulates a random playout
	private int simulateRandomPlayout(PentagoBoardState state) {
		state = (PentagoBoardState) state.clone();
		while (!state.gameOver()) {
			state.processMove((PentagoMove) state.getRandomMove());
		}
		return state.getWinner();
	}

	//Alpha beta prunning algorithm 
	private ValueIndex minimax(int depth, int index, PentagoBoardState pbs, double alpha, double beta) {
		//if game is over or depth limit is reached then return value and index
		if (pbs.gameOver() || depth >= DEPTH_LIMIT) {
			double acc = 0;
			int winner = simulateRandomPlayout(pbs);
			if (winner == this.irmak) {
				acc += winValue;
			} else if (winner == Board.DRAW) {
				acc += drawValue;
			} else if (winner == this.oponnet) {
				acc += loseValue;
			}
			return new ValueIndex(acc, index);
		}

		if (pbs.getTurnNumber() == this.irmak) { // max player
			double best = MIN;
			int tracker = -1;
			List<PentagoStateMove> stateMoves = generateAllChildStates(pbs);
			for (int childIndex = 0; childIndex < stateMoves.size(); childIndex++) {
				PentagoStateMove child = stateMoves.get(childIndex);
				double val = minimax(depth + 1, childIndex, child.getPbs(), alpha, beta).getValue();
				if (val > best) {
					tracker = childIndex;
					best = val;
				}
				alpha = Math.max(alpha, best);

				if (beta <= alpha)
					break;
			}
			return new ValueIndex(best, tracker);
		} else { // min player
			double best = MAX;
			int tracker = -1;
			List<PentagoStateMove> stateMoves = generateAllChildStates(pbs);
			for (int childIndex = 0; childIndex < stateMoves.size(); childIndex++) {
				PentagoStateMove child = stateMoves.get(childIndex);
				double val = minimax(depth + 1, childIndex, child.getPbs(), alpha, beta).getValue();
				if (val < best) {
					tracker = childIndex;
					best = val;
				}
				beta = Math.min(beta, best);

				if (beta <= alpha)
					break;
			}
			return new ValueIndex(best, tracker);
		}
	}
}