package com.losoft.ratican.gui;

import com.losoft.ratican.engine.GameEngine;

public class GuiPanel extends GuiContainer
{
	private float posrel[] = {0,0}, size[] = {0,0};
	
	public GuiPanel(GameEngine igengine)
	{
		super(igengine);
	}
	
	public GuiPanel(GameEngine igengine,float iposrel[],float isize[])
	{
		super(igengine);
		for(int i=0;i<2;i++)
		{
			posrel[i]=iposrel[i];
			size[i]=isize[i];
		}
	}
}
