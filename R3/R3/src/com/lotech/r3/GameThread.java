package com.lotech.r3;

import com.lotech.r3.GameEngine;

public class GameThread extends Thread
{
	private boolean brunning;
	private GameEngine gengine;
	
	public GameThread(GameEngine igengine)
	{
		brunning = false;
		gengine = igengine;
	}
	
	public void SetRunning(boolean ibrunning)
	{
		brunning = ibrunning;
	}
	
	@Override
	public void run()
	{
		while(brunning)
		{
			gengine.Run();
		}
	}
}
