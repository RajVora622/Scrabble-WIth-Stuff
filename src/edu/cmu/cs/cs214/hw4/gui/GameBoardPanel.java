package edu.cmu.cs.cs214.hw4.gui;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.GameChangeListener;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.tile.SpecialTile;
import edu.cmu.cs.cs214.hw4.core.tile.AbstractTile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GameBoardPanel extends JFrame implements GameChangeListener {

	/**
	 * A game board panel which interacts with the core implementation of the
	 * core directly for performing relevant actions
	 */
	private static final long serialVersionUID = 1L;
	private final Game game;

	private BoardGUI boardGUI;
	private static final int WIDTH = BoardGUI.LENGTH + ButtonGUI.WIDTH * 2;
	private static final int HEIGHT = BoardGUI.LENGTH + 2 * SquareGUI.LENGTH;

	private GameBoardPanel[] playerWindows;
	private ButtonGUI submit;
	private ButtonGUI skip;
	private ButtonGUI exchange;
	private ButtonGUI purchase;
	private ButtonGUI end;

	private JTextField remainingTiles;
	private JTextField score;
	private SpecialRackGUI specialRack;
	private RackGUI rack;

	/**
	 * Setting up all the elements of the game for a particular player
	 * 
	 * @param g
	 *            The core implementation
	 * @param playerNo
	 *            The player number of the player for which the board is set up
	 */
	public GameBoardPanel(Game g, int playerNo) {

		game = g;
		game.addGameChangeListener(this);

		setLayout(null);

		setSize(WIDTH, HEIGHT);

		skip = new ButtonGUI("Skip");
		skip.setLocation(SquareGUI.LENGTH * 7 + ButtonGUI.WIDTH,
				BoardGUI.LENGTH);
		exchange = new ButtonGUI("Exchange");
		exchange.setLocation(SquareGUI.LENGTH * 7 + ButtonGUI.WIDTH * 2,
				BoardGUI.LENGTH);
		submit = new ButtonGUI("Submit");
		submit.setLocation(SquareGUI.LENGTH * 7, BoardGUI.LENGTH);
		purchase = new ButtonGUI("Purchase");
		purchase.setLocation(BoardGUI.LENGTH, 350 - ButtonGUI.HEIGHT);
		purchase.setSize(ButtonGUI.WIDTH * 2, ButtonGUI.HEIGHT);
		end = new ButtonGUI("Done !");
		end.setLocation(SquareGUI.LENGTH * 7 + ButtonGUI.WIDTH * 3,
				BoardGUI.LENGTH);

		score = new JTextField("Score: 0");
		score.setSize(ButtonGUI.WIDTH, ButtonGUI.HEIGHT);
		score.setLocation(BoardGUI.LENGTH, 0);
		remainingTiles = new JTextField("");
		remainingTiles.setSize(ButtonGUI.WIDTH, ButtonGUI.HEIGHT);
		remainingTiles.setLocation(BoardGUI.LENGTH, ButtonGUI.HEIGHT);

		// set up rack and special tile rack
		rack = new RackGUI(game);
		rack.setLocation(0, BoardGUI.LENGTH);

		specialRack = new SpecialRackGUI(game);
		specialRack.setLocation(BoardGUI.LENGTH, 350);

		boardGUI = new BoardGUI(game, playerNo);
		boardGUI.setLocation(0, 0);
		
		submit.setEnabled(false);
		end.setEnabled(false);
		skip.setEnabled(true);
		exchange.setEnabled(true);

		add(skip);
		add(submit);
		add(rack);
		add(specialRack);
		add(exchange);
		add(score);
		add(end);
		add(boardGUI);

		add(purchase);
		add(remainingTiles);
		end.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				endTurnGame();
			}
		});
		exchange.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (!game.isReadyToExchange())// start selecting
				{
					game.setReadyToExchange(true);
					exchange.setText("Confirm");
				} else// finish selecting
				{

					if (game.getExchangeTiles().size() == 0)// no tile selected
					{
						JOptionPane.showMessageDialog(null,
								"No Tile selected!", "Error",
								JOptionPane.PLAIN_MESSAGE);
						game.setReadyToExchange(false);
						exchange.setText("Exchange");
						return;
					}
					AbstractTile[] toExchange = new AbstractTile[game
							.getExchangeTiles().size()];
					for (int i = 0; i < game.getExchangeTiles().size(); i++)
						toExchange[i] = game.getExchangeTiles().get(i);
					if (game.exchangeTiles(toExchange))// successfully exchanged
					{
						// exchangeTiles.clear();// clear the list
						game.setReadyToExchange(false);
						exchange.setText("Exchange");
						endTurnGame();
					}
				}
			}

		});

		purchase.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					String message = "Select the type of special tile to purchase: \n"

							+ (SpecialTile.NEGATIVEPOINT + 1)
							+ " for Negative Point Tile\n"
							+ (SpecialTile.BOOMTILE + 1)
							+ " for Boom Tile\n"

							+ (SpecialTile.REVERSEORDER + 1)
							+ " for Reverse Order Tile\n"
							+ (SpecialTile.DOUBLETILEOWNER + 1)
							+ " for Double Owner Score Tile\n"
							+ (SpecialTile.EXTRATURN + 1)
							+ " for Extra Turn Tile\n";

					int type = Integer.parseInt(JOptionPane
							.showInputDialog(message));
					if (game.purchase(type - 1))// if successfully purchased
					{
						// update score and special tile rack
						resetScore();
						specialRack.resetSpecialTiles(game.getCurrentPlayer()
								.getSpecialTiles());
					} else
						JOptionPane.showMessageDialog(null,
								"fail to purchase special tiles");
				} catch (NumberFormatException exc) {
					JOptionPane.showMessageDialog(null, "Invalid format");
				}
			}

		});

		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (game.submitMove())// successful
				{

					// submitted = true;
					resetScore();
					boardGUI.repaint();
				} else
					JOptionPane.showMessageDialog(null, "Not a word");

			}
		});

		skip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				endTurnGame();

			}

		});

	}

	/**
	 * gives access to the list of player windows to know which window to show
	 * at the end of the turn
	 */
	public void givePlayerWindows(GameBoardPanel[] playerWindows) {
		this.playerWindows = playerWindows;
	}

	/**
	 * reset the score on the GUI to the current player's score
	 */
	private void resetScore()// refresh player's score
	{
		score.setText("Score: " + game.getCurrentPlayer().getScore());
	}

	/**
	 * Starting a new turn by showing the current player's game window and
	 * resetting the GUI elements specific to the current player
	 */
	public void startTurn() {
		
		submit.setEnabled(false);
		end.setEnabled(false);
		skip.setEnabled(true);
		exchange.setEnabled(true);
		setTitle("Scrabble - Player "
				+ (game.getCurrentPlayer().getPlayerNumber()));
		setVisible(true);

		resetScore();
		remainingTiles.setText("Tiles: " + game.getBag().getTileCount());
		rack.resetTiles(game.getCurrentPlayer().getTileRack());

		for (SpecialTile st : game.getCurrentPlayer().getSpecialTilesOnBoard())
			st.setRelocatable(true);
		specialRack
				.resetSpecialTiles(game.getCurrentPlayer().getSpecialTiles());

	}

	/**
	 * Ending a turn and showing the winner if the game is over, else showing
	 * the next player's window
	 */
	private void endTurnGame() {
		setVisible(false);

		game.endTurn();
		System.out.println(game.getCurrentPlayer().getPlayerNumber());

		
		int position = 0;
		for (int i = 0; i < game.getPlayers().length; i++) {
			if (game.getCurrentPlayer() == game.getPlayers()[i])
				position = i;
		}
		playerWindows[position].startTurn();

	}

	@Override
	public void moveMade( ) {
		
		if (game.getCurrentTile()!=null){
			submit.setEnabled(false);
			skip.setEnabled(false);
			end.setEnabled(false);
			exchange.setEnabled(false);
		}
		
		else{
			if(game.getPlayedCount()==0){
				submit.setEnabled(false);
				end.setEnabled(false);
				skip.setEnabled(true);
				exchange.setEnabled(true);
			}
			
			else{
				submit.setEnabled(true);
				skip.setEnabled(false);
				exchange.setEnabled(false);
				end.setEnabled(false);
			}
		}
		
		if (game.isTurnSubmitted()){
			submit.setEnabled(false);
			skip.setEnabled(false);
			exchange.setEnabled(false);
			end.setEnabled(true);
		}
		
		

	}

	

	@Override
	public void gameEnded(Player winner) {
		JOptionPane.showMessageDialog(null, "Game over! Winner is Player "
				+ winner.getPlayerNumber());

	}

}
