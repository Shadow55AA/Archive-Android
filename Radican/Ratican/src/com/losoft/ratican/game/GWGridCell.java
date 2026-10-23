package com.losoft.ratican.game;

import java.util.ArrayList;

import com.losoft.ratican.game.obj.GOEnemy;

public class GWGridCell
{
	private ArrayList<GOEnemy> enemies;
	
	public GWGridCell()
	{
		enemies = new ArrayList<GOEnemy>(4);
	}
}
