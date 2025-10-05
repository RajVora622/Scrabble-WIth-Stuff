package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.cs.cs214.hw4.core.tile.*;
import edu.cmu.cs.cs214.hw4.core.GameChangeListener;
import edu.cmu.cs.cs214.hw4.core.Player;

public class Game {

	private Board board;
	private Player[] players;
	private TileBag tileBag;
	private Dictionary dictionary;
	private int totalPlayers;
	private Player currentPlayer;
	private boolean firstRound;
	private boolean byPass;
	private Location[] currentTurnPlayedLocations;
	private AbstractTile tileOnHand;
	private SpecialTile currentSpecialTile;
	private int currentPlayedLocationsCount;
	private final List<GameChangeListener> gameChangeListeners;
	private boolean turnSubmitted;
	private boolean readyToExchange;
	private ArrayList<AbstractTile> exchangeTiles;

	/**
	 * Initiates the game creating a new board, tile bag and dictionary
	 */
	public Game() {
		board = new Board();
		tileBag = new TileBag();
		dictionary = new Dictionary();
		totalPlayers = 0;
		gameChangeListeners = new ArrayList<GameChangeListener>();
		setByPass(false);

	}

	/**
	 * Adds player to the game
	 * 
	 * @param numberOfPlayers
	 * 
	 *            the number of players to be added
	 */
	public void addPlayers(int numberOfPlayers) {

		totalPlayers = numberOfPlayers;
		players = new Player[numberOfPlayers];

		for (int i = 1; i <= numberOfPlayers; i++) {
			players[i - 1] = new Player(i);
		}

		startGame();
	}

	/**
	 * Starts a new game by deciding the first player and starting a new turn
	 */
	public void startGame() {

		currentPlayer = players[0];
		firstRound = true;

		startNewTurn();
	}

	/**
	 * Starts a new turn by refilling the current player's tile rack
	 */
	public void startNewTurn() {
		exchangeTiles = new ArrayList<AbstractTile>();
		setTurnSubmitted(false);
		setReadyToExchange(false);
		int balanceTiles = Player.MAX_TILES - currentPlayer.getTileCount();
		currentPlayer.addTiles(tileBag.takeTiles(balanceTiles));
		currentTurnPlayedLocations = new Location[Player.MAX_TILES];
		currentPlayedLocationsCount = 0;

	}

	public void addToExchangeTileList(AbstractTile tile) {
		exchangeTiles.add(tile);
	}

	public AbstractTile removeFromExchangeTileList(int index) {
		return exchangeTiles.remove(index);
	}

	public ArrayList<AbstractTile> getExchangeTiles() {
		return exchangeTiles;
	}

	public AbstractTile getCurrentTile() {
		return tileOnHand;
	}

	public SpecialTile getCurrentSpecialTile() {
		return currentSpecialTile;
	}

	public int getPlayedCount() {
		return currentPlayedLocationsCount;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Setting the letter for the blank tile
	 * 
	 * @param blankTile
	 *            The tile for which the letter has to be set
	 * @param c
	 *            The letter to be set
	 */
	public void setBlankTileLetter(AbstractTile blankTile, char c) {
		blankTile.setLetter(c);
	}

	/**
	 * Exchanges a particular list of tiles
	 * 
	 * @param tileList
	 *            The list of tiles that have to be exchanged
	 * @return true, if the exchange is successful , false otherwise.
	 */
	public boolean exchangeTiles(AbstractTile[] tileList) {
		int amount = tileList.length;
		AbstractTile[] newTiles = tileBag.takeTiles(amount);
		for (AbstractTile tile : tileList)

			currentPlayer.removeTile(tile);
		for (AbstractTile tile : newTiles)

			currentPlayer.addTile(tile);
		tileBag.recycleTiles(tileList);
		return true;
	}

	/**
	 * When the current player selects a tile on his rack it is removed from his
	 * rack.
	 * 
	 * @param tileToReceive
	 */
	public void receiveTile(AbstractTile tileToReceive) {
		tileOnHand = tileToReceive;
		currentPlayer.removeTile(tileToReceive);
		notifyMoveMade();
	}

	/**
	 * When a player is trying to remove a tile from a location on the board the
	 * tileOnHand is set to that tile.
	 * 
	 * @param loc
	 *            The location from which the tile is to be removed
	 * @return
	 */
	public boolean takeTileAt(Location loc) {
		if (currentPlayedLocationsCount == 0)
			return false;

		if (!currentTurnPlayedLocations[currentPlayedLocationsCount - 1]
				.equals(loc))
			return false;
		tileOnHand = board.removeTileFrom(loc);
		if (tileOnHand == null)
			return false;
		currentTurnPlayedLocations[currentPlayedLocationsCount - 1] = null;
		currentPlayedLocationsCount--;
		notifyMoveMade();
		return true;

	}

	/**
	 * When the tile that is on hand is put back to the tile rack it is added to
	 * the players tile rack.
	 */
	public void dropCurrentTile() {
		currentPlayer.addTile(tileOnHand);
		tileOnHand = null;
		notifyMoveMade();
	}

	/**
	 * When the player tried to put the special tile on hand to the special tile
	 * rack
	 */
	public void dropCurrentSpecialTile() {
		currentPlayer.addSpecialTile(currentSpecialTile);
		currentSpecialTile = null;
	}

	/**
	 * Checks if the tiles played on that location are collinear horizontally
	 * 
	 * @param loc
	 *            The location at which collinearity is to be checked
	 * @return True if collinear, false otherwise
	 */
	private boolean isCollinearRow(Location loc) {

		int y = loc.getyPos();
		int left = loc.getxPos();
		int right = left;
		for (int i = 0; i < currentPlayedLocationsCount; i++) {
			if (currentTurnPlayedLocations[i] != null) {
				if (currentTurnPlayedLocations[i].getyPos() != y)
					return false;
				else if (currentTurnPlayedLocations[i].getxPos() < left)
					left = currentTurnPlayedLocations[i].getxPos();
				else if (currentTurnPlayedLocations[i].getxPos() > right)
					right = currentTurnPlayedLocations[i].getxPos();
			}
		}
		for (int i = left + 1; i < right; i++)
			if (!board.getSquare(new Location(i, y)).isHasTile())
				return false;
		return true;
	}

	/**
	 * Checks if the tiles played on that location are collinear vertically
	 * 
	 * @param loc
	 *            The location at which collinearity is to be checked
	 * @return True if collinear, false otherwise
	 */
	private boolean isCollinearColumn(Location loc) {

		int x = loc.getxPos();
		int top = loc.getyPos();
		int bottom = top;
		for (int i = 0; i < currentPlayedLocationsCount; i++) {
			if (currentTurnPlayedLocations[i] != null) {
				if (currentTurnPlayedLocations[i].getxPos() != x)
					return false;
				else if (currentTurnPlayedLocations[i].getyPos() < top)
					top = currentTurnPlayedLocations[i].getyPos();
				else if (currentTurnPlayedLocations[i].getyPos() > bottom)
					bottom = currentTurnPlayedLocations[i].getyPos();
			}
		}
		for (int i = top + 1; i < bottom; i++)
			if (!board.getSquare(new Location(x, i)).isHasTile())
				return false;
		return true;
	}

	/**
	 * Adds a tile to a specified location on the board. Can only play if it is
	 * collinear with other tiles that have been played.
	 * 
	 * @param loc
	 *            The location on the board to which a tile is to be added
	 * @return
	 */

	public boolean addTileAt(Location loc) {

		if (currentPlayedLocationsCount != 0
				&& !(isCollinearRow(loc) || isCollinearColumn(loc)))
			return false;
		if (board.addTileTo(tileOnHand, loc) && tileOnHand != null) {
			tileOnHand = null;
			currentTurnPlayedLocations[currentPlayedLocationsCount] = loc;
			currentPlayedLocationsCount++;
			notifyMoveMade();
			return true;
		}
		return false;
	}

	/**
	 * Behaves the same as receiving a normal tile but removes it from the
	 * special tile rack.
	 * 
	 * @param specialTileToReceive
	 */
	public void receiveSpecialTile(SpecialTile specialTileToReceive) {
		currentSpecialTile = specialTileToReceive;
		currentPlayer.removeSpecialTile(specialTileToReceive);
	}

	public boolean takeSpecialTileAt(Location loc) {
		// not its owner or no special tile
		if (currentPlayer != board.getSpecialTileOwner(loc))
			return false;
		if (!board.getSquare(loc).getSpecialTile().isRelocatble())
			return false;
		currentSpecialTile = board.removeSpecialTileFrom(loc);
		currentSpecialTile.getOwner().removeSpecialTileFromBoard(
				currentSpecialTile);
		return true;
	}

	public boolean addSpecialTileAt(Location loc) {
		if (board.addSpecialTileTo(currentSpecialTile, loc)) {
			currentSpecialTile.setRelocatable(false);
			currentSpecialTile = null;
			return true;
		}
		return false;
	}

	public boolean purchase(int type) {
		if (type == SpecialTile.BOOMTILE)
			return currentPlayer
					.purchaseSpecialTile(new BoomTile(currentPlayer));
		else if (type == SpecialTile.NEGATIVEPOINT)
			return currentPlayer.purchaseSpecialTile(new NegativePointTile(
					currentPlayer));
		else if (type == SpecialTile.REVERSEORDER)
			return currentPlayer.purchaseSpecialTile(new ReverseOrderTile(
					currentPlayer));
		else if (type == SpecialTile.DOUBLETILEOWNER)
			return currentPlayer
					.purchaseSpecialTile(new DoubleTileOwnerScoreTile(
							currentPlayer));
		else if (type == SpecialTile.EXTRATURN)
			return currentPlayer.purchaseSpecialTile(new ExtraTurnTile(
					currentPlayer));

		else
			return false;
	}

	/**
	 * When the submit button is pressed this method is called. Checks for
	 * validity of the move and changes the score for the round if it is a valid
	 * word. Updates the score of the player as well
	 * 
	 * @return true if it is a valid move , false otherwise.
	 */

	public boolean submitMove() {

		// trivial checks for valid move

		if (firstRound && currentPlayedLocationsCount <= 1)
			return false;
		if (currentPlayedLocationsCount == 0)
			return false;

		Location firstPlayedLoc = currentTurnPlayedLocations[0];
		int score = 0;
		// Checks if the main word played is a Row word
		boolean mainWordOnRow = isCollinearRow(firstPlayedLoc);
		if (currentPlayedLocationsCount == 1) {
			if (dictionary.isWord(board.getRowWord(firstPlayedLoc)))
				mainWordOnRow = true;
			else
				mainWordOnRow = false;
		}

		// If the main word is on the row checks if the word is actually a word
		// and calculates its score

		if (mainWordOnRow) {
			String word = board.getRowWord(firstPlayedLoc);
			if (!dictionary.isWord(word))// is it a word?
				return false;

			score = board.getRowScore(firstPlayedLoc);

			// Checking and calculating score for other words formed

			for (Location loc : currentTurnPlayedLocations) {
				if (loc == null)
					continue;
				String secondary = board.getColWord(loc);
				if (secondary.length() > 1 && dictionary.isWord(secondary))
					score += board.getColScore(loc);
			}

			// If the main word is on the column does the same thing as above
		} else if (isCollinearColumn(firstPlayedLoc)) {

			String word = board.getColWord(firstPlayedLoc);
			if (!dictionary.isWord(word))
				return false;
			score = board.getColScore(firstPlayedLoc);

			for (Location loc : currentTurnPlayedLocations) {
				if (loc == null)
					continue;
				String secondary = board.getRowWord(loc);
				if (secondary.length() > 1 && dictionary.isWord(secondary))
					score += board.getRowScore(loc);
			}
		} else
			return false;

		// Applying effect of special tiles for all special tiles that have been
		// activated as a result of the move
		for (Location loc : currentTurnPlayedLocations) {
			if (loc == null || !board.hasSpecialTileAt(loc))
				continue;
			SpecialTile st = board.removeSpecialTileFrom(loc);
			score = st.applyEffect(this, loc, score);
			st.getOwner().useSpecialTile(st);
		}

		currentPlayer.updateScore(score);
		setTurnSubmitted(true);
		notifyMoveMade();
		return true;
	}

	public TileBag getBag() {
		return tileBag;
	}

	/**
	 * Changes the current player if the turn has ended and also executes the
	 * beginning of the next turn
	 */
	public void endTurn() {

		if (!isByPass()) {

			if (firstRound)
				firstRound = false;
			int currentPosition = 0;
			for (int i = 0; i < players.length; i++) {
				if (players[i] == currentPlayer)
					currentPosition = i;
			}

			if (currentPosition < players.length - 1)
				currentPlayer = players[currentPosition + 1];

			else
				currentPlayer = players[0];
		}

		else
			setByPass(false);

		if (!gameEnded())
			startNewTurn();

	}

	public void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}

	/**
	 * This method checks for the end of the game
	 * 
	 * @return true if the game has ended, false otherwise.
	 */
	public boolean gameEnded() {

		if (tileBag.getTileCount() == 0 && currentPlayer.getTileCount() == 0) {
			getWinner();
			return true;
		} else
			return false;
	}

	/**
	 * Computes the winner of the game
	 * 
	 * @return the index number of the winner in the players array
	 */

	public int getWinner() {
		int highest = players[0].getScore();
		int winner = 0;
		for (int i = 1; i < totalPlayers; i++) {
			int newScore = players[i].getScore();
			if (newScore > highest) {
				highest = newScore;
				winner = i;
			}
		}
		notifyGameEnd(winner);
		return winner;
	}

	public Board getBoard() {
		return board;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	/**
	 * Listeners that would subscribe to notifications from the core
	 * implementation. Here this would essentially be elements in the GUI of the
	 * game
	 * 
	 * @param listener
	 */
	public void addGameChangeListener(GameChangeListener listener) {
		gameChangeListeners.add(listener);
	}

	/**
	 * Methods to notify the GUI that certain important things have happened in
	 * the game
	 */
	private void notifyMoveMade() {
		for (GameChangeListener listener : gameChangeListeners) {
			listener.moveMade();
		}
	}

	private void notifyGameEnd(int winner) {
		for (GameChangeListener listener : gameChangeListeners) {
			listener.gameEnded(players[winner]);
		}
	}

	/**
	 * @return the turnSubmitted
	 */
	public boolean isTurnSubmitted() {
		return turnSubmitted;
	}

	/**
	 * @param turnSubmitted
	 *            the turnSubmitted to set
	 */
	public void setTurnSubmitted(boolean turnSubmitted) {
		this.turnSubmitted = turnSubmitted;
	}

	/**
	 * @return the readyToExchange
	 */
	public boolean isReadyToExchange() {
		return readyToExchange;
	}

	/**
	 * @param readyToExchange
	 *            the readyToExchange to set
	 */
	public void setReadyToExchange(boolean readyToExchange) {
		this.readyToExchange = readyToExchange;
	}

	/**
	 * @return the byPass
	 */
	public boolean isByPass() {
		return byPass;
	}

	/**
	 * @param byPass
	 *            the byPass to set
	 */
	public void setByPass(boolean byPass) {
		this.byPass = byPass;
	}

}
