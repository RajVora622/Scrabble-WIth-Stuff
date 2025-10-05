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

import edu.cmu.cs.cs214.hw4.core.Location;
import edu.cmu.cs.cs214.hw4.core.square.*;
import edu.cmu.cs.cs214.hw4.core.tile.BlankTile;
import edu.cmu.cs.cs214.hw4.core.Game;

public class SquareGUI extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbstractSquare square;
	private int playerNo;
	public static final int LENGTH = 38;
	private Game game;

	public SquareGUI(Game game, int playerNo, AbstractSquare s) {
		this.playerNo = playerNo;
		square = s;
		this.game = game;
		setSize(LENGTH, LENGTH);
		actionListenerAddition();
	}

	public void actionListenerAddition() {
		this.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (game.isReadyToExchange())
					return;

				if (game.isTurnSubmitted())
				{
					if (game.getCurrentSpecialTile() == null)
					{
						game.takeSpecialTileAt(square.getLocation());
						

					} else {
						game.addSpecialTileAt(square.getLocation());
					}
					return;
				}

				if (game.getCurrentTile() == null) {
					if (square.isHasTile())
						game.takeTileAt(square.getLocation());
					else
						return;
				}

				else {

					game.addTileAt(square.getLocation());
				}

			}

		});
	}

	/**
	 * @return the position of this square
	 */
	public Location getLoc() {
		return square.getLocation();
	}

	@Override
	public void paint(Graphics g) {
		Image i = null;
		try {
			if (square.getLocation().getxPos() == 7
					&& square.getLocation().getyPos() == 7) {
				
				i = ImageIO.read(new File("assets/center.jpg"));
			}

			else if (square.getLetterMultiplier() == 1
					&& square.getWordMultiplier() == 1)
				i = ImageIO.read(new File("assets/normal.jpg"));
			else if (square.getLetterMultiplier() == 2)
				i = ImageIO.read(new File("assets/doubleletter.jpg"));
			else if (square.getLetterMultiplier() == 3)
				i = ImageIO.read(new File("assets/tripleletter.jpg"));
			else if (square.getWordMultiplier() == 2)
				i = ImageIO.read(new File("assets/doubleword.jpg"));
			else if (square.getWordMultiplier() == 3)
				i = ImageIO.read(new File("assets/tripleword.jpg"));

			g.drawImage(i, 0, 0, null);
			if (square.isHasTile())// draw tile
			{
				if(square.getCurrentTile() instanceof BlankTile)
				 {
				 g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 35));
				 g.setColor(Color.WHITE);
				 g.drawString(""+Character.toUpperCase(square.getCurrentTile().getLetter()),15,37);
				 }
				 else//normal tile
				 {
				String path = new String("assets/"+square.getCurrentTile().getLetter() + ".gif");
				i = ImageIO.read(new File(path));
				g.drawImage(i, 1, 1, null);
				}
				return;
			}
			if (square.isHasSpecialTile())
			{

				
				if ((playerNo + 1) == square.getSpecialTile().getOwner()
						.getPlayerNumber()) {

					String name = square.getCurrentSpecial().getName();

					g.drawString(name.substring(0, 1), 15, 37);
				}
			}
		} catch (IOException e) {
			System.err.println("File not found");
		}
	}
}
