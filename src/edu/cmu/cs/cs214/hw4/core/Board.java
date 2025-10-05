package edu.cmu.cs.cs214.hw4.core;

import edu.cmu.cs.cs214.hw4.core.square.AbstractSquare;
import edu.cmu.cs.cs214.hw4.core.square.PremiumSquare;
import edu.cmu.cs.cs214.hw4.core.square.RegularSquare;
import edu.cmu.cs.cs214.hw4.core.tile.AbstractTile;
import edu.cmu.cs.cs214.hw4.core.tile.SpecialTile;

public class Board {

	public static final int LENGTH = 14;
	private static final int SINGLE = 1;
	private static final int DOUBLE = 2;
	private static final int TRIPLE = 3;
	public AbstractSquare[][] locationGrid;
	private boolean hasCenterTile;
	public static final Location CENTER = new Location(LENGTH / 2, LENGTH / 2);

	public Board() {
		locationGrid = new AbstractSquare[LENGTH + 1][LENGTH + 1];
		setUpBoard();
	}

	/**
	 * This method creates a new square on the board unless already created
	 * 
	 * @param i
	 *            the x position of the square to create
	 * @param j
	 *            the y position of the square to create
	 * @param loc
	 * @param letterMulti
	 *            the letter multiplier if it's a premiere square
	 * @param wordMulti
	 *            the word multiplier if it's a premium square
	 */

	public void makeNew(int i, int j, Location loc, int letterMulti,
			int wordMulti) {
		if (locationGrid[i][j] == null) {
			if (letterMulti > SINGLE || wordMulti > SINGLE)
				locationGrid[i][j] = new PremiumSquare(loc, letterMulti,
						wordMulti);
			else
				locationGrid[i][j] = new RegularSquare(loc);
		}

	}

	/**
	 * This method sets up the creation of the 8 corresponding reflections in
	 * each triangle of each quarter of the board
	 * 
	 * @param i
	 *            x position of the square on the grid
	 * @param j
	 *            y position of the square on the grid
	 * @param letterMulti
	 *            the letter multiplier for the square
	 * @param wordMulti
	 *            the word multipler for the square
	 */
	public void setReflections(int i, int j, int letterMulti, int wordMulti) {

		Location loc1 = new Location(i, j);
		Location loc2 = new Location(LENGTH - i, LENGTH - j);
		Location loc3 = new Location(i, LENGTH - j);
		Location loc4 = new Location(LENGTH - i, j);
		Location loc5 = new Location(j, i);
		Location loc6 = new Location(LENGTH - j, LENGTH - i);
		Location loc7 = new Location(j, LENGTH - i);
		Location loc8 = new Location(LENGTH - j, i);

		makeNew(i, j, loc1, letterMulti, wordMulti);
		makeNew(LENGTH - i, LENGTH - j, loc2, letterMulti, wordMulti);
		makeNew(i, LENGTH - j, loc3, letterMulti, wordMulti);
		makeNew(LENGTH - i, j, loc4, letterMulti, wordMulti);
	    makeNew(j, i, loc5, letterMulti, wordMulti);
		makeNew(LENGTH - j, LENGTH - i, loc6, letterMulti, wordMulti);
		makeNew(j, LENGTH - i, loc7, letterMulti, wordMulti);
		makeNew(LENGTH - j, i, loc8, letterMulti, wordMulti);

	}

	/**
	 * This method sets up the entire board with the center square, regular
	 * squares and premium squares
	 */
	public void setUpBoard() {

		for (int i = 0; i <= 7; i++) {
			for (int j = i; j <= 7; j++) {

				if ((i == 0 && j == 0) || (i == 0 && j == 7))
					setReflections(i, j, SINGLE, TRIPLE);

				if ((i == j && i > 0 && i <= 4 )||(i==7 && j==7))
					setReflections(i, j, SINGLE, DOUBLE);

				if ((i == 0 && j == 3) || (i == 2 && j == 6)
						|| (i == 6 && j == 6) || (i == 3 && j == 7))
					setReflections(i, j, DOUBLE, SINGLE);

				if ((i == 1 && j == 5) || (i == 5 && j == 5))
					setReflections(i, j, TRIPLE, SINGLE);
				else
					setReflections(i, j, SINGLE, SINGLE);

			}
		}
	}

	/**
	 * This method returns the square at a particular location on the grid
	 * 
	 * @param loc
	 *            The location at which the square needs to be returned
	 * @return the returned square
	 */
	public AbstractSquare getSquare(Location loc) {
		return locationGrid[loc.getxPos()][loc.getyPos()];
	}

	/**
	 * 
	 * @param loc
	 *            The location at which the special tile exists
	 * @return the owner of the special tile
	 */
	public Player getSpecialTileOwner(Location loc) {
		AbstractSquare s = locationGrid[loc.getxPos()][loc.getyPos()];
		if (s.isHasSpecialTile())
			return s.getCurrentSpecial().getOwner();
		else
			return null;
	}

	/**
	 * Removes tile from location on the board. Fails if no tile exists at the
	 * position.
	 * 
	 * @param loc
	 *            The location at which the tile needs to be removed from
	 * @return The removed tile
	 */
	public AbstractTile removeTileFrom(Location loc) {
		AbstractTile t = locationGrid[loc.getxPos()][loc.getyPos()]
				.removeTile();
		if (loc.equals(CENTER))
			hasCenterTile = false;
		return t;
	}

	/**
	 * Removes special tile from location on board. Fails if no special tile
	 * exists at the position
	 * 
	 * @param loc
	 *            The location from which the special tile needs to be removed
	 * @return The removed special tile
	 */
	public SpecialTile removeSpecialTileFrom(Location loc) {
		return locationGrid[loc.getxPos()][loc.getyPos()].removeSpecialTile();
	}

	/**
	 * Adds tile to a location on the grid
	 * 
	 * @param t
	 *            The tile to be added to the board
	 * @param loc
	 *            The location on the board
	 * @return true if successful, false otherwise
	 */
	public boolean addTileTo(AbstractTile t, Location loc) {

		if (!hasCenterTile && !loc.equals(CENTER))
			return false;
		AbstractSquare s = locationGrid[loc.getxPos()][loc.getyPos()];
		if (loc.equals(CENTER)) {
			hasCenterTile = true;
			return s.setTile(t);
		} else if (hasAdjacentTiles(loc))
			return s.setTile(t);
		else
			return false;
	}

	/**
	 * Checks if a particular location has a special tile on it or not
	 * 
	 * @param loc
	 *            The location which has to be checked
	 * @return true if it has a special tile, false if not
	 */

	public boolean hasSpecialTileAt(Location loc) {
		return locationGrid[loc.getxPos()][loc.getyPos()].isHasSpecialTile();
	}

	/**
	 * Adds a special tile to the board
	 * 
	 * @param spTile
	 *            The special tile to be added on the board
	 * @param loc
	 *            The location on which the special tile is to be added
	 * @return
	 */
	public boolean addSpecialTileTo(SpecialTile spTile, Location loc) {
		AbstractSquare square = locationGrid[loc.getxPos()][loc.getyPos()];
		if (square.isHasTile())// already has a tile
			return false;
		return square.setSpecialTile(spTile);
	}

	/**
	 * Checks if a tile has adjacent tiles present
	 * 
	 * @param loc
	 *            Location for which adjacent squares are checked for tiles
	 * @return True if adjacent tile(s) exist , false otherwise.
	 */

	private boolean hasAdjacentTiles(Location loc) {
		int x = loc.getxPos();
		int y = loc.getyPos();

		for (int i = x - 1; i <= x + 1; i += 2)
			if (i >= 0 && i < LENGTH && locationGrid[i][y].isHasTile())
				return true;

		for (int j = y - 1; j <= y + 1; j += 2)
			if (j >= 0 && j < LENGTH && locationGrid[x][j].isHasTile())
				return true;
		return false;
	}

	/**
	 * Calculates the word on a particular row that a location is present
	 * 
	 * @param loc
	 *            Location at which the row word is supposed to be computed from
	 * @return The row word resulting from the location
	 */
	public String getRowWord(Location loc) {

		int x = loc.getxPos();
		int y = loc.getyPos();

		int leftMostPos;
		int rightMostPosition;
		for (leftMostPos = x; leftMostPos >= 0; leftMostPos--)
			if (!locationGrid[leftMostPos][y].isHasTile())
				break;
		for (rightMostPosition = x; rightMostPosition < LENGTH; rightMostPosition++)

			if (!locationGrid[rightMostPosition][y].isHasTile())
				break;
		leftMostPos++;
		char[] word = new char[rightMostPosition - leftMostPos];
		for (int i = leftMostPos; i < rightMostPosition; i++)

			word[i - leftMostPos] = locationGrid[i][y].getCurrentTile()
					.getLetter();
		return new String(word);
	}

	/**
	 * Calculates the word on a particular row that a location is present
	 * 
	 * @param loc
	 *            Location at which the row word is supposed to be computed from
	 * @return The row word resulting from the location
	 */
	
	public String getColWord(Location loc) {
		
		int x = loc.getxPos();
		int y = loc.getyPos();

		int topMostPos;
		int bottomMostPos;
		for (topMostPos = y; topMostPos >= 0; topMostPos--)
			if (!locationGrid[x][topMostPos].isHasTile())
				break;
		for (bottomMostPos = y; bottomMostPos < LENGTH; bottomMostPos++)

			if (!locationGrid[x][bottomMostPos].isHasTile())
				break;
		topMostPos++;
		char[] word = new char[bottomMostPos - topMostPos];
		for (int i = topMostPos; i < bottomMostPos; i++)

			word[i - topMostPos] = locationGrid[x][i].getCurrentTile()
					.getLetter();
		return new String(word);
	}

	/**
	 * Returns the score of the word formed on the row of a location
	 * 
	 * @param loc Calculates the row score for this location
	 * @return
	 */
	public int getRowScore(Location loc) {
		int x = loc.getxPos();
		int y = loc.getyPos();

		int left;
		int right;
		for (left = x; left >= 0; left--) {

			if (!locationGrid[left][y].isHasTile())

				break;
		}
		for (right = x; right < LENGTH; right++) {

			if (!locationGrid[right][y].isHasTile())
				break;
		}
		left++;

		int sum = 0;
		
		for (int i = left; i < right; i++)
			sum += locationGrid[i][y].getCurrentTile().getValue()
					* locationGrid[i][y].getLetterMultiplier();
		
		for (int i = left; i < right; i++)

			sum = locationGrid[i][y].getWordMultiplier() * sum;
		return sum;
	}

	/**
	 * Returns the score of the word formed on the column of a location
	 * 
	 * @param loc Calculates the column score for this location
	 * @return
	 */
	public int getColScore(Location loc) {
		int x = loc.getxPos();
		int y = loc.getyPos();

		int top;
		int bottom;
		for (top = y; top >= 0; top--)
			if (!locationGrid[x][top].isHasTile())
				break;
		for (bottom = y; bottom < LENGTH; bottom++)
			if (!locationGrid[x][bottom].isHasTile())
				break;
		top++;

		int sum = 0;
		
		for (int i = top; i < bottom; i++)
			sum += locationGrid[x][i].getCurrentTile().getValue()
					* locationGrid[x][i].getLetterMultiplier();
		
		for (int i = top; i < bottom; i++)
			sum = locationGrid[x][i].getWordMultiplier() * sum;
		return sum;
	}

	public AbstractSquare[][] getLocationGrid() {
		return locationGrid;
	}
	
	
}
