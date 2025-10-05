

package edu.cmu.cs.cs214.hw4.core.tile;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.Location;

public class ReverseOrderTile extends SpecialTile
{
	private static final int PRICE = 2;
	private static final String name = "Reverse Order";
	public ReverseOrderTile(Player p) 
	{
		super(p,PRICE);
	}
	
	public String getName(){
		return name;
	}
	/**
	 * Reverses the order of play
	 * @param loc Location on which the special tile is
	 * @param score
	 */
	@Override
	public int applyEffect(Game game, Location loc,int score)
	{
		Player[] reversed= new Player[game.getPlayers().length];
		
		for(int i=game.getPlayers().length-1; i>=0; i--){
			reversed[i]=game.getPlayers()[game.getPlayers().length-i-1];	
		}
		
		game.setPlayers(reversed);
		
		return score;
	}
	
}
