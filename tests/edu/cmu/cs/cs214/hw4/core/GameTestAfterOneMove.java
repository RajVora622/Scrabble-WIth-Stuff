package edu.cmu.cs.cs214.hw4.core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs.cs214.hw4.core.tile.*;

public class GameTestAfterOneMove {

	private Game game;

	@Before
	public void setUpBeforeClass() {
		game = new Game();
		game.addPlayers(2);

		for (int i = 0; i < 4; i++) {
			game.getCurrentPlayer().removeTile(
					game.getCurrentPlayer().getTileRack()[i]);
		}

		game.getCurrentPlayer().addTile(new RegularTile('z', 10));
		game.getCurrentPlayer().addTile(new RegularTile('e', 1));
		game.getCurrentPlayer().addTile(new RegularTile('r', 1));
		game.getCurrentPlayer().addTile(new RegularTile('o', 2));

		game.receiveTile(game.getCurrentPlayer().getTileRack()[0]);
		game.addTileAt(Board.CENTER);
		game.receiveTile(game.getCurrentPlayer().getTileRack()[1]);
		game.addTileAt(new Location(7, 8));
		game.receiveTile(game.getCurrentPlayer().getTileRack()[2]);
		game.addTileAt(new Location(7, 9));
		game.receiveTile(game.getCurrentPlayer().getTileRack()[3]);
		game.addTileAt(new Location(7, 10));

		assertTrue(game.submitMove());

	}

	@Test
	public void testAddingSpecialTileOnBoard() {
		assertTrue(game.purchase(1));
		game.receiveSpecialTile(game.getCurrentPlayer().getSpecialTiles()[0]);
		assertTrue(game.addSpecialTileAt(new Location(8, 10)));
	}

	@Test
	public void testPurchasingSpecialTileWithEnoughPoints() {
		assertTrue(game.submitMove());
		assertTrue(game.purchase(1));
	}

	@Test
	public void testEndMovePlayerChange() {
		assertTrue(game.getCurrentPlayer() == game.getPlayers()[0]);
		game.endTurn();
		assertTrue(game.getCurrentPlayer() == game.getPlayers()[1]);
	}

	@Test
	public void activatingNegativeTile() {

		game.purchase(0);
		game.receiveSpecialTile(game.getCurrentPlayer().getSpecialTiles()[0]);
		game.addSpecialTileAt(new Location(8, 10));
		game.endTurn();

		// Manipulating the players tile rack in order to have tiles that would
		// form a word

		for (int i = 0; i < 4; i++) {
			game.getCurrentPlayer().removeTile(
					game.getCurrentPlayer().getTileRack()[i]);
		}

		game.getCurrentPlayer().addTile(new RegularTile('w', 10));
		game.getCurrentPlayer().addTile(new RegularTile('o', 1));
		game.getCurrentPlayer().addTile(new RegularTile('r', 1));
		game.getCurrentPlayer().addTile(new RegularTile('d', 2));

		game.receiveTile(game.getCurrentPlayer().getTileRack()[0]);
		assertTrue(game.addTileAt(new Location(6, 10)));
		game.receiveTile(game.getCurrentPlayer().getTileRack()[2]);
		assertTrue(game.addTileAt(new Location(8, 10)));
		game.receiveTile(game.getCurrentPlayer().getTileRack()[3]);
		assertTrue(game.addTileAt(new Location(9, 10)));

		assertTrue(game.submitMove());

		assertTrue(game.getCurrentPlayer().getScore() < 0);
	}

	@Test
	public void activatingBoomTile() {

		game.purchase(1);
		game.receiveSpecialTile(game.getCurrentPlayer().getSpecialTiles()[0]);
		game.addSpecialTileAt(new Location(8, 10));
		game.endTurn();

		for (int i = 0; i < 4; i++) {
			game.getCurrentPlayer().removeTile(
					game.getCurrentPlayer().getTileRack()[i]);
		}

		game.getCurrentPlayer().addTile(new RegularTile('w', 10));
		game.getCurrentPlayer().addTile(new RegularTile('o', 1));
		game.getCurrentPlayer().addTile(new RegularTile('r', 1));
		game.getCurrentPlayer().addTile(new RegularTile('d', 2));

		game.receiveTile(game.getCurrentPlayer().getTileRack()[0]);
		assertTrue(game.addTileAt(new Location(6, 10)));
		game.receiveTile(game.getCurrentPlayer().getTileRack()[2]);
		assertTrue(game.addTileAt(new Location(8, 10)));
		game.receiveTile(game.getCurrentPlayer().getTileRack()[3]);
		assertTrue(game.addTileAt(new Location(9, 10)));
		assertTrue(game.getBoard().getLocationGrid()[7][10].isHasTile());
		assertTrue(game.submitMove());

		assertFalse(game.getBoard().getLocationGrid()[7][10].isHasTile());
	}

	@Test
	public void testActivatingReverseOrderTile() {

		game.purchase(2);
		game.receiveSpecialTile(game.getCurrentPlayer().getSpecialTiles()[0]);
		game.addSpecialTileAt(new Location(8, 10));
		game.endTurn();

		for (int i = 0; i < 4; i++) {
			game.getCurrentPlayer().removeTile(
					game.getCurrentPlayer().getTileRack()[i]);
		}

		game.getCurrentPlayer().addTile(new RegularTile('w', 10));
		game.getCurrentPlayer().addTile(new RegularTile('o', 1));
		game.getCurrentPlayer().addTile(new RegularTile('r', 1));
		game.getCurrentPlayer().addTile(new RegularTile('d', 2));

		game.receiveTile(game.getCurrentPlayer().getTileRack()[0]);
		assertTrue(game.addTileAt(new Location(6, 10)));
		game.receiveTile(game.getCurrentPlayer().getTileRack()[2]);
		assertTrue(game.addTileAt(new Location(8, 10)));
		game.receiveTile(game.getCurrentPlayer().getTileRack()[3]);
		assertTrue(game.addTileAt(new Location(9, 10)));
		assertTrue(game.getBoard().getLocationGrid()[7][10].isHasTile());

		assertTrue(game.getPlayers()[0].getPlayerNumber() == 1);

		assertTrue(game.submitMove());

		assertTrue(game.getPlayers()[0].getPlayerNumber() == 2);

	}

	@Test
	public void testActivatingDoubleOwnerScore() {

		game.purchase(3);
		game.receiveSpecialTile(game.getCurrentPlayer().getSpecialTiles()[0]);
		game.addSpecialTileAt(new Location(8, 10));
		game.endTurn();

		for (int i = 0; i < 4; i++) {
			game.getCurrentPlayer().removeTile(
					game.getCurrentPlayer().getTileRack()[i]);
		}

		game.getCurrentPlayer().addTile(new RegularTile('w', 10));
		game.getCurrentPlayer().addTile(new RegularTile('o', 1));
		game.getCurrentPlayer().addTile(new RegularTile('r', 1));
		game.getCurrentPlayer().addTile(new RegularTile('d', 2));

		game.receiveTile(game.getCurrentPlayer().getTileRack()[0]);
		assertTrue(game.addTileAt(new Location(6, 10)));
		game.receiveTile(game.getCurrentPlayer().getTileRack()[2]);
		assertTrue(game.addTileAt(new Location(8, 10)));
		game.receiveTile(game.getCurrentPlayer().getTileRack()[3]);
		assertTrue(game.addTileAt(new Location(9, 10)));
		assertTrue(game.getBoard().getLocationGrid()[7][10].isHasTile());

		int Player1Score = game.getPlayers()[0].getScore();

		assertTrue(game.submitMove());

		assertTrue(game.getPlayers()[0].getScore() == 2 * Player1Score);

	}

}
