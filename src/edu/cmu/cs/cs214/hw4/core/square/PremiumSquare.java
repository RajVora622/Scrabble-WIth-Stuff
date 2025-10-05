package edu.cmu.cs.cs214.hw4.core.square;

import edu.cmu.cs.cs214.hw4.core.Location;

public class PremiumSquare extends AbstractSquare {

	
	public PremiumSquare(Location location,int x, int y){
		super(location);
		super.setLetterMultiplier(x);
		super.setWordMultiplier(y);
		}
}
