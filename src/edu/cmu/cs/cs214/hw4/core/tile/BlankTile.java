package edu.cmu.cs.cs214.hw4.core.tile;

public class BlankTile extends AbstractTile
{
	private boolean hasLetter;
	public BlankTile()
	{
		super(0);
		hasLetter = false;
		super.setLetter(' ');
	}
	public boolean hasLetter()
	{
		return hasLetter;
	}
	
	@Override
	public void setLetter(char l)
	{
		super.setLetter(l);
		hasLetter = true;
	}
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof BlankTile;
	}
	@Override
	public int hashCode()
	{
		return getValue();
	}
}
