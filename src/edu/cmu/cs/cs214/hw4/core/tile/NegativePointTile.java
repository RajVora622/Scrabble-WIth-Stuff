package edu.cmu.cs.cs214.hw4.core.tile;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.Location;

public class NegativePointTile extends SpecialTile
{
	private static final int PRICE = 2;
	private static final String name = "Negative Point";
	
	public String getName(){
		return name;
	}
	public NegativePointTile(Player p) 
	{
		super(p,PRICE);
	}
	
	/**
	 * Makes the entire score of the move negative of the original score
	 * @param loc
	 * @param score
	 */
	@Override
	public int applyEffect(Game game, Location loc,int score)
	{
		if(score < 0)
			return score;
		else
			return -score;
	}
	
}
