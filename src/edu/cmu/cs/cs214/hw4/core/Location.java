package edu.cmu.cs.cs214.hw4.core;


public class Location {
	private int xPos;
	private int yPos;

	public Location(int x, int y) {

		this.setxPos(x);
		this.setyPos(y);
	}

	/**
	 * @return the xPos
	 */
	public int getxPos() {
		return xPos;
	}

	/**
	 * @param xPos
	 *            the xPos to set
	 */
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	/**
	 * @return the yPos
	 */
	public int getyPos() {
		return yPos;
	}

	/**
	 * @param yPos
	 *            the yPos to set
	 */
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	@Override
	public boolean equals(Object O) {
		Location loc = (Location) O;
		if(O==null)
			return false;
		if (O instanceof Location) {
			
			if (loc.xPos == this.xPos && loc.yPos == this.yPos)
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.xPos + this.yPos;
	}

}
