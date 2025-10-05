package edu.cmu.cs.cs214.hw4.gui;

import javax.swing.JPanel;

import edu.cmu.cs.cs214.hw4.core.Board;
import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Location;

public class BoardGUI extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4704951396197173842L;
	public static final int LENGTH = (Board.LENGTH + 1) * SquareGUI.LENGTH;

	/**
	 * This constructor initializes the board for a particular player creating
	 * all the squares and their locations
	 * 
	 * @param game
	 *            a reference to the core implementation of the game
	 * @param playerNO
	 *            The player for which the board is set up
	 */
	public BoardGUI(Game game, int playerNO) {
		Board b = game.getBoard();
		int len = Board.LENGTH;
		setLayout(null);
		setSize(LENGTH, LENGTH);
		for (int x = 0; x <= len; x++) {
			for (int y = 0; y <= len; y++) {

				SquareGUI temp = new SquareGUI(game, playerNO,
						b.getSquare(new Location(x, y)));
				temp.setLocation(SquareGUI.LENGTH * x, SquareGUI.LENGTH * y);
				add(temp);

			}
		}
	}
}
