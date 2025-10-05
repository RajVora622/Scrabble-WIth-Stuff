package edu.cmu.cs.cs214.hw4.core.tile;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.Location;

public abstract class SpecialTile {
	private Player owner;
	private int price;
	private boolean relocatable;
	public static final int NEGATIVEPOINT = 0;
	public static final int BOOMTILE = 1;
	public static final int REVERSEORDER = 2;
	public static final int DOUBLETILEOWNER = 3;
	public static final int EXTRATURN = 4;

	public SpecialTile(Player p, int price) {
		owner = p;
		relocatable = true;
		this.price = price;
	}

	public void setRelocatable(boolean b) {
		relocatable = b;
	}

	public abstract String getName();
	public boolean isRelocatble() {
		return relocatable;
	}

	public Player getOwner() {
		return owner;
	}

	/**
	 * Applies a certain effect on the game when tile played on special tile's
	 * location
	 * 
	 * @param game
	 * @param loc
	 * @param score
	 * @return
	 */
	public abstract int applyEffect(Game game, Location loc, int score);

	public int getPrice() {
		return price;
	}

}
