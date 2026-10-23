package com.losoft.ratican.gui;

import com.losoft.ratican.engine.GameEngine;
import com.losoft.ratican.engine.GameRenderer;

public class GuiButton extends Gui
{
	private float pos[] = {0,0,0},size[] = {10,10,1};
	private float posrel[] = {0,0};
	public GuiButton(GameEngine igengine)
	{
		super(igengine);
	}
	
	public GuiButton(GameEngine igengine,float iposrel[],float isize[])
	{
		super(igengine);
		for(int i=0;i<2;i++)
		{
			posrel[i]=iposrel[i];
			size[i]=isize[i];
		}
	}
	
	@Override
	public void onSurfaceChanged(int iwidth,int iheight)
	{
		pos[0]=posrel[0]*iwidth;
		pos[1]=posrel[1]*iheight;
	}

	@Override
	public void render(GameRenderer irenderer)
	{
		if(!bvisible)
			return;
		irenderer.setDrawPos(pos);
		irenderer.drawRect(size);
	}
	
	@Override
	public void onPointerUp(float px,float py)
	{
		float hx=size[0]/2.0f;
		float hy=size[1]/2.0f;
		if(pos[0]-hx<=px&&px<=pos[0]+hx)
		{
			if(pos[1]-hy<=py&&py<=pos[1]+hy)
			{
				gengine.changeGameState(2);
			}
		}
	}
}
