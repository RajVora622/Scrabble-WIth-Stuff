package edu.cmu.cs.cs214.hw4.core.square;

import edu.cmu.cs.cs214.hw4.core.Location;
public class RegularSquare extends AbstractSquare {
	
	private static final int LETTER_MULTIPLIER = 1;
	private static final int WORD_MULTIPLIER =1;
	
	public RegularSquare(Location location){
		super(location);
		this.setLetterMultiplier(LETTER_MULTIPLIER);
		this.setWordMultiplier(WORD_MULTIPLIER);
		
	}

}
