package edu.cmu.cs.cs214.hw4.gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.tile.SpecialTile;

public class SpecialTileGUI extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2773015596337096653L;
	private SpecialTile tile;
	private boolean empty;
	private Game game;
	public SpecialTileGUI(Game game)
	{
		this.game=game;
		tile = null;
		empty = true;
		
		setSize(120,SquareGUI.LENGTH);
		setOpaque(true);
		actionListenerAddition();
		
		
	}
	
	public void actionListenerAddition(){
		this.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				if (!game.isTurnSubmitted())// turn not ended yet
					return;
				
				if (game.getCurrentSpecialTile()==null)// taking from special rack
				{
					if (isEmpty())
						return;
					
					game.receiveSpecialTile(removeSpecialTile());
			
				} else// putting back to special rack
				{
					
					if (!isEmpty())
						return;
					
					setSpecialTile(game.getCurrentSpecialTile());
					game.dropCurrentSpecialTile();
					
				}
			}
		});
	}
	/**
	 * get current special tile
	 * @return tile : its special tile
	 */
	public SpecialTile getSpecialTile()
	{
		return tile;
	}
	/**
	 * set special tile if currently empty
	 * @param t : special tile to be added
	 */
	public void setSpecialTile(SpecialTile t)
	{
		if(!empty)
			return;
		tile = t;
		setText(tile.getName());
		
		empty = false;
	}
	/**
	 * remove and return current special tile
	 * @return current special tile
	 */
	public SpecialTile removeSpecialTile()
	{
		SpecialTile temp = tile;
		setText("");
		tile = null;
		empty = true;
	
		return temp;
	}
	/**
	 * @return false if has special tile, false otherwise
	 */
	public boolean isEmpty()
	{
		return empty;
	}
}
