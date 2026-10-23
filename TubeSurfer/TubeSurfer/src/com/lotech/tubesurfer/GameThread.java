package com.lotech.tubesurfer;

import com.lotech.tubesurfer.GameEngine;

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
