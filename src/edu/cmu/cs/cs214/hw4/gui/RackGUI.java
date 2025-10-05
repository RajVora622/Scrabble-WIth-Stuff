package edu.cmu.cs.cs214.hw4.gui;


import javax.swing.JPanel;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.tile.AbstractTile;

public class RackGUI extends JPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5376452010355685562L;
	public static final int HEIGHT = SquareGUI.LENGTH;
	TileGUI[] tiles;
	Game game;
	public RackGUI(Game game)
	{
		this.game=game;
		setLayout(null);
		tiles = new TileGUI[Player.MAX_TILES];
		//initialize 7 tiles
		for(int i = 0;i < tiles.length;i++)
		{
			TileGUI t=new TileGUI(game);
			
			t.setLocation(SquareGUI.LENGTH*(i),0);
			tiles[i] = t;
			add(t);
		}
		setSize(SquareGUI.LENGTH*7,HEIGHT);
	}
	
	/**
	 * refresh the rack with given list
	 * @param tList : new tile list
	 */
	public void resetTiles(AbstractTile[] tList)
	{
		//set all tiles
		for(int i = 0;i < tList.length;i++)
			tiles[i].setTile(tList[i]);
		//clear the rest
		for(int i = tList.length;i < Player.MAX_TILES;i++)
			tiles[i].removeTile();
	}
	
	
}
