package connect4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Game {
	//able to do 7 iterations within a second?
	
	static Board board = new Board();
	
	static BufferedReader reader = new BufferedReader( 
            new InputStreamReader(System.in)); 
	
	public static void main(String[] args) {
		int nextx = 0;
		int player = 1;
		
		show(board.getBoard());
		System.out.println("1  2  3  4  5  6  7");
		System.out.println("input row:");
		
		while(true) {	
			//input
			if(player == 1) {
				try {
					nextx = Integer.parseInt(reader.readLine())-1;
				} catch (Exception e) {
					continue;
				}
			} else {
				nextx = AI.getBestOption(board,-1, 9);
			}
			
			//process inputs
			if(!board.update(nextx, player)) {
				continue;				
			}			
			
			//update board
			System.out.println();
			show(board.getBoard());
			System.out.println("1  2  3  4  5  6  7");
			if(player == -1)
				System.out.println("input row:");
			//System.out.println("best option: " + (AI.getBestOption(board, player*-1, 8)+1));
			
			//check for winner
			if(board.isGameOver()) {
				System.out.println("Player " + ((player == 1) ? "1" : "2") + " wins");
				break;
			}
			if(board.getAvailableSpaces().size() == 0) {
				System.out.println("draw");
				break;
			}
			
			//setup next turn
			player = player * -1;
		}
	}
	
	private static void show(int[][] board) {
		for (int y = board[0].length-1; y >= 0; y--) {
			for (int x = 0; x < board.length; x++) {
				switch (board[x][y]) {
				case 1:					
					System.out.print("<> ");
					break;
				case -1:				
					System.out.print(">< ");
					break;
				default:
					System.out.print("[] ");
				}
			}
			System.out.println("");
		}
	}
	
	

}
