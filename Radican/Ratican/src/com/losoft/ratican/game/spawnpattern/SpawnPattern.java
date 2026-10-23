package com.losoft.ratican.game.spawnpattern;

import com.losoft.ratican.engine.GameEngine;

public class SpawnPattern
{
	protected GameEngine gengine;
	protected float maxtopspeed, spawnduration;
	protected boolean bdead;
	protected float timecounter;
	
	public SpawnPattern(GameEngine igengine)
	{
		gengine = igengine;
		maxtopspeed=5;
		spawnduration=2;
		timecounter = 0;
		bdead = false;
	}
	
	public void spawnStep(float dt)
	{
		timecounter+=dt;
		if(timecounter>spawnduration)
			bdead=true;
	}
	
	public boolean isDead()
	{
		return bdead;
	}
}
