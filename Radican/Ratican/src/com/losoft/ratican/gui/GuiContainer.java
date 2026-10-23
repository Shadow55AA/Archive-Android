package com.losoft.ratican.gui;

import java.util.ArrayList;

import com.losoft.ratican.engine.GameEngine;
import com.losoft.ratican.engine.GameRenderer;

public class GuiContainer extends Gui
{
	ArrayList<Gui> guilist;
	
	public GuiContainer(GameEngine igengine)
	{
		super(igengine);
		guilist = new ArrayList<Gui>(10);
	}
	
	public void AddGui(Gui igui)
	{
		guilist.add(igui);
	}
	
	@Override
	public void render(GameRenderer irenderer)
	{
		if(!bvisible)
			return;
		for(int i=0;i<guilist.size();i++)
		{
			guilist.get(i).render(irenderer);
		}
	}

	
	@Override
	public void onPointerUp(float px,float py)
	{
		for(int i=0;i<guilist.size();i++)
		{
			guilist.get(i).onPointerUp(px,py);
		}
	}
	
	@Override
	public void onPointerMove(float ipointerpos[],int npointers)
	{
		for(int i=0;i<guilist.size();i++)
		{
			guilist.get(i).onPointerMove(ipointerpos,npointers);
		}
	}
	
	@Override
	public void onSurfaceChanged(int iwidth,int iheight)
	{
		for(int i=0;i<guilist.size();i++)
		{
			guilist.get(i).onSurfaceChanged(iwidth,iheight);
		}
	}
}
