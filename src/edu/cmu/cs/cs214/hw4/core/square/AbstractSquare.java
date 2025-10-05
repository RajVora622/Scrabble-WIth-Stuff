package edu.cmu.cs.cs214.hw4.core.square;

import edu.cmu.cs.cs214.hw4.core.Location;
import edu.cmu.cs.cs214.hw4.core.tile.AbstractTile;
import edu.cmu.cs.cs214.hw4.core.tile.SpecialTile;
public abstract class AbstractSquare {
	private Location location;
	private int wordMultiplier;
	private int letterMultiplier;
	private boolean hasTile;
	private boolean hasSpecialTile;
	private AbstractTile currentTile;
	private SpecialTile currentSpecial;
	private boolean used;

	
	public AbstractSquare(Location location){
		this.setHasTile(false);
		this.setHasSpecialTile(false);
		this.setCurrentSpecial(null);
		this.setCurrentTile(null);
		this.setLocation(location);
		this.setUsed(false);
		
		
	}
	/**
	 * @return the wordMultiplier
	 */
	public int getWordMultiplier() {
		
			return wordMultiplier;
	}
	/**
	 * @param wordMultiplier the wordMultiplier to set
	 */
	public void setWordMultiplier(int wordMultiplier) {
		this.wordMultiplier = wordMultiplier;
	}
	/**
	 * @return the letterMultiplier
	 */
	public int getLetterMultiplier() {
		
		
			return letterMultiplier;
	}
	/**
	 * @param letterMultiplier the letterMultiplier to set
	 */
	public void setLetterMultiplier(int letterMultiplier) {
		this.letterMultiplier = letterMultiplier;
	}
	/**
	 * @return the currentTile
	 */
	public AbstractTile getCurrentTile() {
		return currentTile;
	}
	/**
	 * @param currentTile the currentTile to set
	 */
	public void setCurrentTile(AbstractTile currentTile) {
		this.currentTile = currentTile;
	}
	/**
	 * @return the currentSpecial
	 */
	public SpecialTile getCurrentSpecial() {
		return currentSpecial;
	}
	/**
	 * @param currentSpecial the currentSpecial to set
	 */
	public void setCurrentSpecial(SpecialTile currentSpecial) {
		this.currentSpecial = currentSpecial;
	}
	/**
	 * @return the hasSpecialTile
	 */
	public boolean isHasSpecialTile() {
		return hasSpecialTile;
	}
	/**
	 * @param hasSpecialTile the hasSpecialTile to set
	 */
	public void setHasSpecialTile(boolean hasSpecialTile) {
		this.hasSpecialTile = hasSpecialTile;
	}
	/**
	 * @return the hasTile
	 */
	public boolean isHasTile() {
		return hasTile;
	}
	/**
	 * @param hasTile the hasTile to set
	 */
	public void setHasTile(boolean hasTile) {
		this.hasTile = hasTile;
	}
	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	
	/**
	 * Sets the special tile on this square
	 * @param The tile that has to be set 
	 * @return True on success, false otherwise
	 */
	public boolean setSpecialTile(SpecialTile tileToBeSet)
	{
		
		if(this.isHasSpecialTile() && currentSpecial.getOwner() == tileToBeSet.getOwner())
			return false;
		currentSpecial = tileToBeSet;
		this.setHasSpecialTile(true);
		return true;		
	}
	/**
	 * Same as special tile
	 * @param tileToBeSet
	 * @return
	 */
	public boolean setTile(AbstractTile tileToBeSet) 
	{
		if(this.isHasTile())
			return false;
		currentTile = tileToBeSet;
		this.setHasTile(true);
		return true;
	}
	
	
	public SpecialTile getSpecialTile()
	{
		return currentSpecial;
	}
	/**
	 * Removes the special tile from the square
	 * @return current special tile
	 */
	public SpecialTile removeSpecialTile()
	{
		SpecialTile t = currentSpecial;
		currentSpecial = null;
		this.setHasSpecialTile(false);
		return t;
	}
	/**
	 * Removes the current tile from the square
	 * @return current tile
	 */
	public AbstractTile removeTile()
	{
		if(!this.hasTile)
			return null;
		AbstractTile tile = currentTile;
		currentTile = null;
		this.setHasTile(false);
		return tile;
	}
	/**
	 * @return the used
	 */
	public boolean isUsed() {
		return used;
	}
	/**
	 * @param used the used to set
	 */
	public void setUsed(boolean used) {
		this.used = used;
	}
	
}
