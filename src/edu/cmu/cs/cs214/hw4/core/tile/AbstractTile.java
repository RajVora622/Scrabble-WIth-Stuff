package edu.cmu.cs.cs214.hw4.core.tile;

/**
 * This class contains the basic functionality that a tile in Scrabble would
 * possess. A value and a letter associated with it.
 * 
 * @author rvora
 * 
 */
public abstract class AbstractTile {

	private char letter;
	private int value;

	public AbstractTile(char letter, int value) {
		this.setLetter(letter);
		this.setValue(value);
	}

	public AbstractTile(int value) {
		this.setValue(value);

	}

	public char getLetter() {
		return letter;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
