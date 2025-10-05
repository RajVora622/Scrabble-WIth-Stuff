package edu.cmu.cs.cs214.hw4.core.tile;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.Location;

public class BoomTile extends SpecialTile {
	private static final int PRICE = 1;
	private static final String name = "Boom Tile";
	public BoomTile(Player p) {
		super(p, PRICE);
	}
	
	public String getName(){
		return name;
	}

	/**
	 * Removes tiles and puts it into the tile bag in a 3 block radius from a
	 * particular location. Tiles at X positions from block B are removed
	 * 
	 * |X| |X| |X| 
	 * | |X|X|X| | 
	 * |X|X|B|X|X| 
	 * | |X|X|X| | 
	 * |X| |X| |X|
	 * 
	 * @param game
	 *            The game to which the effect needs to be applied
	 * @param loc
	 *            The location of the special tile
	 * @param score
	 *            The current score of the round
	 * @return The score of the round changed or unchanged
	 */
	@Override
	public int applyEffect(Game game, Location loc, int score) {
		AbstractTile[] temp = new AbstractTile[12];
		int count = 0;
		int x = loc.getxPos();
		int y = loc.getyPos();

		for (int i = 0; i <= 2; i++) {
			AbstractTile t = game.getBoard().removeTileFrom(
					new Location(x + i, y));
			if (t != null) {
				temp[count] = t;
				count++;
			}

			t = game.getBoard().removeTileFrom(new Location(x - i, y));
			if (t != null) {
				temp[count] = t;
				count++;
			}

			t = game.getBoard().removeTileFrom(new Location(x, y + i));
			if (t != null) {
				temp[count] = t;
				count++;
			}

			t = game.getBoard().removeTileFrom(new Location(x, y - i));
			if (t != null) {
				temp[count] = t;
				count++;
			}

			t = game.getBoard().removeTileFrom(new Location(x + i, y + i));
			if (t != null) {
				temp[count] = t;
				count++;
			}

			t = game.getBoard().removeTileFrom(new Location(x + i, y - i));
			if (t != null) {
				temp[count] = t;
				count++;
			}

			t = game.getBoard().removeTileFrom(new Location(x - i, y + i));
			if (t != null) {
				temp[count] = t;
				count++;
			}

			t = game.getBoard().removeTileFrom(new Location(x - i, y - i));
			if (t != null) {
				temp[count] = t;
				count++;
			}

		}

		AbstractTile[] tilesToReturn = new AbstractTile[count];// resize
		for (int i = 0; i < count; i++)
			tilesToReturn[i] = temp[i];
		game.getBag().recycleTiles(tilesToReturn);
		return 0;
	}

}
