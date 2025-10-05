package edu.cmu.cs.cs214.hw4.core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs.cs214.hw4.core.tile.*;

public class GameTest {

	private Game game;

	@Before
	public void setUpBeforeClass() {
		game = new Game();
		game.addPlayers(2);
	}

	@Test
	public void testAddPlayers() {

		assertTrue(game.getPlayers()[0].getPlayerNumber() == 1);
		assertTrue(game.getPlayers()[1].getPlayerNumber() == 2);

	}

	@Test
	public void testGameStart() {

		assertEquals(game.getCurrentPlayer(), game.getPlayers()[0]);
	}

	@Test
	public void testRackContentsOnStart() {

		assertEquals(game.getCurrentPlayer().getTileCount(), 7);
	}

	@Test
	public void testReceieveTileFromPlayer() {
		assertTrue((game.getCurrentPlayer().getTileRack()[1].getLetter() >= 'a' && game
				.getCurrentPlayer().getTileRack()[1].getLetter() <= 'z')
				|| game.getCurrentPlayer().getTileRack()[1].getLetter() == ' ');
		game.receiveTile(game.getCurrentPlayer().getTileRack()[1]);
		assertEquals(game.getCurrentPlayer().getTileRack()[1], null);

	}

	@Test
	public void testPlacingFirstTileOnNonCenter() {

		game.receiveTile(game.getCurrentPlayer().getTileRack()[1]);
		assertFalse(game.addTileAt(new Location(6, 5)));

	}

	@Test
	public void testPlacingFirstTileOnCenter() {

		game.receiveTile(game.getCurrentPlayer().getTileRack()[1]);
		assertTrue(game.addTileAt(Board.CENTER));

	}

	@Test
	public void testPlacingOnIsolated() {
		game.receiveTile(game.getCurrentPlayer().getTileRack()[1]);
		game.addTileAt(Board.CENTER);
		game.receiveTile(game.getCurrentPlayer().getTileRack()[2]);
		assertFalse(game.addTileAt(new Location(4, 4)));

	}

	@Test
	public void testPlacingOnNonIsolated() {
		game.receiveTile(game.getCurrentPlayer().getTileRack()[1]);
		game.addTileAt(Board.CENTER);
		game.receiveTile(game.getCurrentPlayer().getTileRack()[2]);
		assertTrue(game.addTileAt(new Location(7, 6)));

	}

	@Test
	public void testTakingTileAndDropBack() {
		game.receiveTile(game.getCurrentPlayer().getTileRack()[1]);
		assertEquals(game.getCurrentPlayer().getTileRack()[1], null);
		game.dropCurrentTile();
		assertTrue((game.getCurrentPlayer().getTileRack()[1].getLetter() >= 'a' && game
				.getCurrentPlayer().getTileRack()[1].getLetter() <= 'z')
				|| game.getCurrentPlayer().getTileRack()[1].getLetter() == ' ');
	}

	@Test
	public void testFormingValidWordAndSubmitting() {

		for (int i = 0; i < 4; i++) {
			game.getCurrentPlayer().removeTile(
					game.getCurrentPlayer().getTileRack()[i]);
		}

		game.getCurrentPlayer().addTile(new RegularTile('w', 4));
		game.getCurrentPlayer().addTile(new RegularTile('o', 1));
		game.getCurrentPlayer().addTile(new RegularTile('r', 1));
		game.getCurrentPlayer().addTile(new RegularTile('d', 2));

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
	public void testFormingInvalidWordAndSubmitting() {

		for (int i = 0; i < 4; i++) {
			game.getCurrentPlayer().removeTile(
					game.getCurrentPlayer().getTileRack()[i]);
		}

		game.getCurrentPlayer().addTile(new RegularTile('w', 4));
		game.getCurrentPlayer().addTile(new RegularTile('x', 9));
		game.getCurrentPlayer().addTile(new RegularTile('r', 1));
		game.getCurrentPlayer().addTile(new RegularTile('d', 2));

		game.receiveTile(game.getCurrentPlayer().getTileRack()[0]);
		game.addTileAt(Board.CENTER);
		game.receiveTile(game.getCurrentPlayer().getTileRack()[1]);
		game.addTileAt(new Location(7, 8));
		game.receiveTile(game.getCurrentPlayer().getTileRack()[2]);
		game.addTileAt(new Location(7, 9));
		game.receiveTile(game.getCurrentPlayer().getTileRack()[3]);
		game.addTileAt(new Location(7, 10));

	}

	@Test
	public void testExchangingTiles() {
		AbstractTile[] list = new AbstractTile[4];
		for (int i = 0; i < 4; i++) {
			list[i] = game.getCurrentPlayer().getTileRack()[i];
		}
		assertTrue(game.exchangeTiles(list));
	}

	@Test
	public void purchaseSpecialTileWithoutEnoughPoints() {
		assertFalse(game.purchase(1));
	}

	@Test
	public void testScoreCalculation() {

		for (int i = 0; i < 4; i++) {
			game.getCurrentPlayer().removeTile(
					game.getCurrentPlayer().getTileRack()[i]);
		}

		game.getCurrentPlayer().addTile(new RegularTile('w', 4));
		game.getCurrentPlayer().addTile(new RegularTile('o', 1));
		game.getCurrentPlayer().addTile(new RegularTile('r', 1));
		game.getCurrentPlayer().addTile(new RegularTile('d', 2));

		game.receiveTile(game.getCurrentPlayer().getTileRack()[0]);
		game.addTileAt(Board.CENTER);
		game.receiveTile(game.getCurrentPlayer().getTileRack()[1]);
		game.addTileAt(new Location(7, 8));
		game.receiveTile(game.getCurrentPlayer().getTileRack()[2]);
		game.addTileAt(new Location(7, 9));
		game.receiveTile(game.getCurrentPlayer().getTileRack()[3]);
		game.addTileAt(new Location(7, 10));

		assertTrue(game.submitMove());

		assertEquals(game.getCurrentPlayer().getScore(),16);

	}

}
