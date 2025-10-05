package edu.cmu.cs.cs214.hw4.core;

import edu.cmu.cs.cs214.hw4.core.Player;

public interface GameChangeListener {

    /**
     * Receives notifications any time a significant change is made to the board
     *
     * @param loc The location on which a move has been made
     */
    public void moveMade();

   

    /**
     * Called when the game ends, announcing the winner.
     *
     * @param winner The winner of the game.
     */
    public void gameEnded(Player winner);

}