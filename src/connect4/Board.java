package connect4;

import java.util.ArrayList;
import java.util.Collections;

public class Board {
	private int[][] board = new int[7][6];
	
	public Board() {
		
	}
	
	public Board(Board board) {
		this.board = board.getBoard();
				
	}
	
	public int get(int x, int y) {
		return board[x][y];
	}
	
	public void newBoard() {
		board = new int[7][6];
	}
	
	public boolean update(int x, int player) {
		int y = 0;
		
		while(y != 6 /*board size*/) {
			if(board[x][y] == 0) {
				board[x][y] = player;
				return true;
			}
			y++;
		}
		return false;
	}

	public int[][] getBoard() {
		int[][] boardClone = new int [7][6];
		for (int i = 0; i < board.length; i++) {
			boardClone[i] = board[i].clone();
		}		
		return boardClone;
	}
	
	public boolean isGameOver() {
		for (int x = 0; x < board.length-3; x++) {
			for (int y = 0; y < board[0].length; y++) {
				if(board[x][y] != 0 && board[x][y] == board[x+1][y] && board[x+1][y] == board[x+2][y] && board[x+2][y] == board[x+3][y])
					return true;
			}
		}
		
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length-3; y++) {
				if(board[x][y] != 0 && board[x][y] == board[x][y+1] && board[x][y+1] == board[x][y+2] && board[x][y+2] == board[x][y+3])
					return true;
			}
		}
		

		for (int x = 0; x < board.length-3; x++) {
			for (int y = 0; y < board[0].length-3; y++) {
				if(board[x][y] != 0 && board[x][y] == board[x+1][y+1] && board[x+1][y+1] == board[x+2][y+2] && board[x+2][y+2] == board[x+3][y+3])
					return true;				
			}
		}
		
		for (int x = 0; x < board.length-3; x++) {
			for (int y = 0; y < board[0].length-3; y++) {
				if(board[x][y+3] != 0 && board[x][y+3] == board[x+1][y+2] && board[x+1][y+2] == board[x+2][y+1] && board[x+2][y+1] == board[x+3][y])
					return true;				
			}
		}
		
		return false;
	}
	
	public int getScore(int player) {
		
		int score = 0;
		for (int x = 0; x < board.length-3; x++) {
			for (int y = 0; y < board[0].length; y++) {
				score += getTempScore(player, board[x][y], board[x+1][y], board[x+2][y], board[x+3][y])
						- 2*getTempScore(player*-1, board[x][y], board[x+1][y], board[x+2][y], board[x+3][y]);
			}
		}
		
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length-3; y++) {
				score += getTempScore(player, board[x][y], board[x][y+1], board[x][y+2], board[x][y+3])
						- 2*getTempScore(player*-1, board[x][y], board[x][y+1], board[x][y+2], board[x][y+3]);
			}
		}
		

		for (int x = 0; x < board.length-3; x++) {
			for (int y = 0; y < board[0].length-3; y++) {
				score += getTempScore(player, board[x][y], board[x+1][y+1], board[x+2][y+2], board[x+3][y+3])
						- 2*getTempScore(player*-1, board[x][y], board[x][y+1], board[x][y+2], board[x][y+3]);				
			}
		}
		
		for (int x = 0; x < board.length-3; x++) {
			for (int y = 0; y < board[0].length-3; y++) {
				score += getTempScore(player, board[x][y+3], board[x+1][y+2], board[x+2][y+1], board[x+3][y])
						- 2*getTempScore(player*-1, board[x][y], board[x][y+1], board[x][y+2], board[x][y+3]);			
			}
		}
		
		return score;
	}
	
	private int getTempScore(int player, int val1, int val2, int val3, int val4) {
		if((val1 != 0 && val1 != player) || (val2 != 0 && val2 != player) || (val3 != 0 && val3 != player) || (val4 != 0 && val4 != player) )
			return 1;
		
		int playerValues = 0;
		if(val1 == player)
			playerValues++;
		if(val2 == player)
			playerValues++;
		if(val3 == player)
			playerValues++;
		if(val4 == player)
			playerValues++;
		
		switch (playerValues) {
		case 2:
			return 1<<4;	
		case 3:
			return 1<<8;		
		case 4:
			return 1<<12;			
		default:
			return 0;
		}
	}
	
	public ArrayList<Integer> getAvailableSpaces() {
		ArrayList<Integer> options = new ArrayList<>();
		for (int x = 0; x < 7; x++) {
			if(board[x][5] == 0)
				options.add(x);
		}
		return options;
	}
}
