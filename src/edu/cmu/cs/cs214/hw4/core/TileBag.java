package edu.cmu.cs.cs214.hw4.core;

import java.util.Random;

import edu.cmu.cs.cs214.hw4.core.tile.*;

public class TileBag 
{
	private AbstractTile[] tiles;
	private static final int MAX = 100;
	private int tileCount;
	public TileBag()
	{
		tiles = new AbstractTile[MAX];
		tileCount = 0;
		init();
	}
	/**
	 * Creating a tile bag with the tiles
	 */
	private void init()
	{
		for(int i = 1;i <= 12;i++)
		{
			addTile(new RegularTile('e',1));
			if(i <= 9)
			{
				addTile(new RegularTile('a',1));
				addTile(new RegularTile('i',1));
			}
			if(i <= 8)
			{
				addTile(new RegularTile('o',1));
			}
			if(i <= 6)
			{
				addTile(new RegularTile('n',1));
				addTile(new RegularTile('r',1));
				addTile(new RegularTile('t',1));
			}
			if(i <= 4)
			{
				addTile(new RegularTile('l',1));
				addTile(new RegularTile('s',1));
				addTile(new RegularTile('u',1));
				addTile(new RegularTile('d',2));
			}
			if(i <= 3)
			{
				addTile(new RegularTile('g',2));
			}
			if(i <= 2)
			{
				addTile(new RegularTile('b',3));
				addTile(new RegularTile('c',3));
				addTile(new RegularTile('m',3));
				addTile(new RegularTile('p',3));
				addTile(new RegularTile('f',4));
				addTile(new RegularTile('h',4));
				addTile(new RegularTile('v',4));
				addTile(new RegularTile('w',4));
				addTile(new RegularTile('y',4));
				
			}
			if(i <= 1)
			{
				addTile(new RegularTile('k',5));
				addTile(new RegularTile('j',8));
				addTile(new RegularTile('x',8));
				addTile(new RegularTile('q',10));
				addTile(new RegularTile('z',10));
			}
		}
		randomize();
	}
	private void randomize()
	{
		int remaining = tileCount;
		AbstractTile[] newList = new AbstractTile[MAX];
		Random r = new Random();
		for(int i = 0; i < tileCount;i++)
		{
			int loc = r.nextInt(remaining);//take a random
			newList[i] = tiles[loc];
			for(int rest = loc;rest < remaining-1;rest++)
				tiles[rest] = tiles[rest+1];//move the rest up
			remaining--;
		}
		tiles = newList;
	}
	private void addTile(AbstractTile tileToBeAdded)
	{
		tiles[tileCount] = tileToBeAdded;
		tileCount++;		
	}
	
	/**
	 * Adds a list of tiles to the tile bag
	 * @param tList
	 */
	public void recycleTiles(AbstractTile[] tList)
	{
		for(AbstractTile t : tList)
			addTile(t);
		randomize();
	}
	/**
	 * Takes tiles from the bag
	 * @param numberOfTiles
	 * @return
	 */
	public AbstractTile[] takeTiles(int numberOfTiles)
	{
		if(numberOfTiles > tileCount)
			numberOfTiles = tileCount;
		AbstractTile[] ret = new AbstractTile[numberOfTiles];
		for(int i = numberOfTiles-1; i >= 0;i--)
		{
			tileCount--;
			ret[i] = tiles[tileCount];
		}
		return ret;
	}
	/**
	 * @return tileCount
	 */
	public int getTileCount()
	{
		return tileCount;
	}
}
