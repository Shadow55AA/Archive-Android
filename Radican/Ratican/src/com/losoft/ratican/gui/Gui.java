package com.losoft.ratican.gui;

import com.losoft.ratican.engine.GameEngine;
import com.losoft.ratican.engine.GameRenderer;

public abstract class Gui
{
	protected GameEngine gengine;
	protected boolean bvisible;
	
	public Gui(GameEngine igengine)
	{
		gengine = igengine;
		bvisible = true;
	}
	
	public abstract void render(GameRenderer irenderer);
	
	public void onPointerUp(float px,float py)
	{
		
	}
	
	public void onPointerMove(float ipointerpos[],int npointers)
	{
		
	}
	
	public void onSurfaceChanged(int iwidth, int iheight)
	{
		
	}
	
	public void setVisible(boolean ibvisible)
	{
		bvisible = ibvisible;
	}
}
