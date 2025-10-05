package edu.cmu.cs.cs214.hw4.gui;

import java.awt.Color;



import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.tile.SpecialTile;

public class SpecialRackGUI extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SpecialTileGUI[] tiles;
	
	public SpecialRackGUI(Game game)
	{
		
		setLayout(null);
		tiles = new SpecialTileGUI[Player.MAX_SPECIAL_TILES];
		JTextField name = new JTextField("SpecialTiles");
		name.setLocation(0,0);
		name.setSize(ButtonGUI.WIDTH,SquareGUI.LENGTH);
		//name.setBackground(Color.YELLOW);
		add(name);
		//initialize speical rack
		for(int i = 0;i < tiles.length;i++)
		{
			SpecialTileGUI t = new SpecialTileGUI(game);
			
			t.setLocation(0,SquareGUI.LENGTH+i*SquareGUI.LENGTH);
			tiles[i] = t;
			add(t);
		}
		setBackground(Color.LIGHT_GRAY);
		setSize(BoardGUI.LENGTH,2*(1+SquareGUI.LENGTH*Player.MAX_SPECIAL_TILES));
	}
	/**
	 * refresh the rack with given list
	 * @param tList : new list of special tiles
	 */
	public void resetSpecialTiles(SpecialTile[] tList)
	{
		//fill the rack as much as possible
		for(int i = 0;i < tList.length;i++)
			tiles[i].setSpecialTile(tList[i]);
		//clean the rest
		for(int i = tList.length;i < Player.MAX_SPECIAL_TILES;i++)
			tiles[i].removeSpecialTile();
	}
}
