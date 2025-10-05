package edu.cmu.cs.cs214.hw4.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import edu.cmu.cs.cs214.hw4.core.Game;
import edu.cmu.cs.cs214.hw4.core.tile.RegularTile;
import edu.cmu.cs.cs214.hw4.core.tile.AbstractTile;

public class TileGUI extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbstractTile tile;
	private boolean empty;
	private Game game;

	public TileGUI(Game game) {
		this.game = game;
		tile = null;
		empty = true;
		setSize(SquareGUI.LENGTH, SquareGUI.LENGTH);
		this.setOpaque(true);
		actionListenerAddition();

	}

	public void actionListenerAddition() {
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (game.isTurnSubmitted())
					return;

				if (game.isReadyToExchange()) {

					if (isEmpty())
					{ 
						if (game.getExchangeTiles().size() != 0){
							setTile((game.removeFromExchangeTileList(game
									.getExchangeTiles().size() - 1)));}
						    
						else
							JOptionPane.showMessageDialog(null,
									"No more exchanging tiles");
					} else{
						
						game.addToExchangeTileList(removeTile());
					}

						
				}
				
				else{
				if (game.getCurrentTile() == null)
					game.receiveTile(removeTile());
				else {
					if (!isEmpty())
						return;
					else
						setTile(game.getCurrentTile());
					game.dropCurrentTile();
				}
				}
			}
		});
	}

	/**
	 * expose its tile
	 * 
	 * @return current tile
	 */
	public AbstractTile getTile() {
		return tile;
	}

	/**
	 * set tile
	 * 
	 * @param t
	 *            : tile to be added
	 */
	public void setTile(AbstractTile t) {
		tile = t;
		empty = false;
	}

	/**
	 * remove and return current tile
	 * 
	 * @return current tile
	 */
	public AbstractTile removeTile() {
		AbstractTile temp = tile;
		tile = null;
		empty = true;
		return temp;
	}

	/**
	 * @return false if no tile, true otherwise
	 */
	public boolean isEmpty() {
		return empty;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		setBackground(Color.WHITE);
		if (tile == null)// empty slot
		{
			setText("");
			return;
		}
		if (tile instanceof RegularTile) {
			Image i;
			try {
				String path = new String("assets/"+tile.getLetter() + ".gif");
				i = ImageIO.read(new File(path));
				g.drawImage(i, 1, 1, null);
			} catch (IOException e) {
				System.out.println("File Not Found");
			}

		} else// blank tile
		{
			setBackground(Color.GRAY);
									
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 35));
			g.drawString("" + Character.toUpperCase(tile.getLetter()), 15, 37);
		}
	}

}
