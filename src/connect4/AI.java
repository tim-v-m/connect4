package connect4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AI {
	static Random rng = new Random();
	
	public static int getBestOption(Board board, int player, int nbIterations) {
		return getBestOptionBranch(board, player, nbIterations, true, Integer.MIN_VALUE, Integer.MAX_VALUE)[0];
	}
	
	private static int[] getBestOptionBranch(Board board, int player, int nbIterations, boolean max, int alpha, int beta) {	
		
		ArrayList<Integer> availableSpaces = board.getAvailableSpaces();	
		if(availableSpaces.size() == 0)
			return new int[] {-1,0};
		Collections.shuffle(availableSpaces);
		
		if(nbIterations == 1)
			return getBestOptionRoot(board, player, max);

		int[] bestResult = null;
		int score;		
		
		
		for (int x : availableSpaces) {			
			Board newBoard = new Board(board);
			
			if(max)
				newBoard.update(x, player);
			else
				newBoard.update(x, -player);
			
			if(max && newBoard.isGameOver())
				return new int[] {x,1000000+nbIterations};
			if(!max && newBoard.isGameOver())	
				return new int[] {x,-1000000-nbIterations};
			
			if(bestResult == null) {
				bestResult = new int[] {x,getBestOptionBranch(newBoard, player, nbIterations-1, !max, alpha, beta)[1]};
				
				if(max) {
					 alpha = bestResult[1];
					 if(alpha > beta)
						 return bestResult;
				}
				else {
					beta = bestResult[1];
					if(beta < alpha)
						 return bestResult;
				}
				
				continue;
			}
			
			if(max && bestResult[1] < (score = getBestOptionBranch(newBoard, player, nbIterations-1, !max, alpha, beta)[1])) {
				bestResult = new int[] {x,score};
				 if(alpha > beta)
					 return bestResult;
			}
			if(!max && bestResult[1] > (score = getBestOptionBranch(newBoard, player, nbIterations-1, !max, alpha, beta)[1])) {
				bestResult = new int[] {x,score};
				beta = score;
				if(beta < alpha)
					return bestResult;
			}
			
		}
		
		return bestResult;
		
	}
	
	private static int[] getBestOptionRoot(Board board, int player, boolean max) {
		int[] result = new int[] {-1, 0};
		
		
		for (int x : board.getAvailableSpaces()) {
			
			Board newBoard = new Board(board);
			int score = 0;
			
			if(max)
				newBoard.update(x, player);
			else
				newBoard.update(x, -player);
			
			if(result[0] == -1) {
				result = new int[] {x,newBoard.getScore(player)};
				continue;
			}
			if(max && result[1] < (score = newBoard.getScore(player)))
				result = new int[] {x, score};
			if(!max && result[1] > (score = newBoard.getScore(player)))
				result = new int[] {x, score};
		}
		
		return result;
	}
}
