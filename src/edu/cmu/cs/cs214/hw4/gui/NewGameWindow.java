package edu.cmu.cs.cs214.hw4.gui;

import java.awt.BorderLayout;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;

import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.cmu.cs.cs214.hw4.core.Game;

public class NewGameWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Game g;
	private JButton addPlayer;
	private JTextField nPlayerInput;
	private GameBoardPanel[] playerWindows;

	public NewGameWindow()// a simple welcome UI
	{
		setTitle("Welcome to Scrabble!");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setLocation(500, 100);
		g = new Game();

		JPanel setup = new JPanel();
		

		JLabel countInput = new JLabel("How many people are playing?");
		setup.add(countInput);

		nPlayerInput = new JTextField(3);
		setup.add(nPlayerInput);

		addPlayer = new JButton("START GAME");
		setup.add(addPlayer);

		addPlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int c;
				try {
					c = Integer.parseInt(nPlayerInput.getText());
					if (c < 2 || c > 4) {
						JOptionPane.showMessageDialog(null,
								"Only 2-4 players allowed.", "Error",
								JOptionPane.PLAIN_MESSAGE);
						return;
					}
					setVisible(false);
					showPlayerWindow(c);

				} catch (NumberFormatException exp) {
					JOptionPane.showMessageDialog(null,
							"Please Input A Number !", "Error",
							JOptionPane.PLAIN_MESSAGE);
				}

				pack(); // Resizes the window
			}
		});

		add(setup, BorderLayout.NORTH);

		pack();
		setVisible(true);
	}

	public void showPlayerWindow(int count) {
		g.addPlayers(count);
		playerWindows = new GameBoardPanel[count];
		for (int i = 0; i < count; i++)
			// initialize 'count' GUIs
			playerWindows[i] = new GameBoardPanel(g, i);

		
		for (int i = 0; i < count; i++)
			playerWindows[i].givePlayerWindows(playerWindows);
			
		playerWindows[0].startTurn();
	}
	
	
}
