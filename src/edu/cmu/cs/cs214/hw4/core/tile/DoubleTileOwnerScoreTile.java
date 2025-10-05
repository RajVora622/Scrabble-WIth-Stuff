package edu.cmu.cs.cs214.hw4.core.tile;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.Location;

public class DoubleTileOwnerScoreTile extends SpecialTile
{
	private static final int PRICE = 2;
	private static final String name = "Double Score";
	public DoubleTileOwnerScoreTile(Player p) 
	{
		super(p,PRICE);
	}
	
	public String getName(){
		return name;
	}
	
	/**
	 * Doubles the score of the owner of the special tile
	 * @param loc 
	 * @param score
	 */
	@Override
	public int applyEffect(Game game, Location loc,int score)
	{
		getOwner().updateScore(-getOwner().getScore()+2*getOwner().getScore());
		return score;
	}
	
}
