package edu.cmu.cs.cs214.hw4.gui;


import javax.swing.JButton;

public class ButtonGUI extends JButton
{
	/**
	 * Setting up features of a button for re-use
	 */
	private static final long serialVersionUID = -3194037499704113360L;
	public static final int WIDTH = 80;
	public static final int HEIGHT = 38;
	
	public ButtonGUI(String text)
	{
		super(text);
		setSize(WIDTH,HEIGHT);
		
	}
}
