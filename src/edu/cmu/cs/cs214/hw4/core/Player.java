package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;

import edu.cmu.cs.cs214.hw4.core.tile.AbstractTile;
import edu.cmu.cs.cs214.hw4.core.tile.SpecialTile;

public class Player {
	public static final int MAX_TILES = 7;
	public static final int MAX_SPECIAL_TILES = 5;
	private AbstractTile[] tileRack;
	private int tileCount;
	private int playerNumber;
	private ArrayList<SpecialTile> specialTiles;
	private int score;
	private ArrayList<SpecialTile> specialTilesOnBoard;

	public Player(int playerNumber) {

		this.playerNumber = playerNumber;
		tileCount = 0;
		tileRack = new AbstractTile[MAX_TILES];
		specialTiles = new ArrayList<SpecialTile>(MAX_SPECIAL_TILES);
		specialTilesOnBoard = new ArrayList<SpecialTile>(MAX_SPECIAL_TILES);

		score = 0;

	}

	/**
	 * Adds an array of tiles to the player's tile rack (used during exchanging
	 * tiles)
	 * 
	 * @param tiles
	 */
	public void addTiles(AbstractTile[] tiles) {
		for (AbstractTile t : tiles)
			addTile(t);
	}

	/**
	 * Adds a single tile to the player's tile rack
	 * 
	 * @param tileToAddToRack
	 */
	public void addTile(AbstractTile tileToAddToRack) {
		for (int i = 0; i < MAX_TILES; i++) {
			if (tileRack[i] == null)// empty space
			{
				tileRack[i] = tileToAddToRack;
				tileCount++;
				return;
			}
		}
	}

	/**
	 * Removes a particular tile from the player's rack
	 * 
	 * @param tileToRemoveFromRack
	 * @return
	 */
	public void removeTile(AbstractTile tileToRemoveFromRack) {

		for (int i = 0; i < MAX_TILES; i++) {
			if (tileRack[i] == tileToRemoveFromRack) {
				tileRack[i] = null;
				tileCount--;
				return;
			}
		}
	}

	/**
	 * Updates the player's score by adding the score on a turn
	 * 
	 * @param turnScore
	 *            The score to be added to the player's score
	 */
	public void updateScore(int turnScore) {

		score += turnScore;
	}

	
	public SpecialTile[] getSpecialTiles() {
		SpecialTile[] temp = new SpecialTile[specialTiles.size()];
		int c = 0;
		for (SpecialTile t : specialTiles) {
			temp[c] = t;
			c++;
		}
		return temp;
	}

	public int getScore() {
		return score;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public AbstractTile[] getTileRack() {
		return tileRack;

	}

	public void removeSpecialTile(SpecialTile t) {
		if (getSpecialTileCount() == 0)// shouldn't reach here
			return;
		specialTiles.remove(t);
		specialTilesOnBoard.add(t);
	}

	public void useSpecialTile(SpecialTile st) {
		specialTilesOnBoard.remove(st);
	}

	private int getSpecialTileCount() {

		return specialTiles.size() + specialTilesOnBoard.size();
	}

	
	public void addSpecialTile(SpecialTile t) {
		if (getSpecialTileCount() > MAX_SPECIAL_TILES)
			return;
		specialTiles.add(t);
	}

	public ArrayList<SpecialTile> getSpecialTilesOnBoard() {
		return specialTilesOnBoard;
	}

	public void removeSpecialTileFromBoard(SpecialTile st) {
		specialTilesOnBoard.remove(st);
	}

	public int getTileCount() {
		return tileCount;
	}

	public boolean purchaseSpecialTile(SpecialTile s) {
		if (getSpecialTileCount() == MAX_SPECIAL_TILES)
			return false;
		int price = s.getPrice();
		if (score < price)
			return false;
		score -= price;
		specialTiles.add(s);
		return true;
	}

}
