package com.losoft.ratican.render;

import javax.microedition.khronos.opengles.GL10;

public class RenderElement
{
	public int start = 0,end = 0;
	public float color[] = {1,1,1,1};
	
	public RenderElement()
	{
		
	}
	
	public RenderElement(float icolor[])
	{
		for(int i=0;i<4;i++)
		{
			color[i]=icolor[i];
		}
	}
	
	public void setColor(float icolor[])
	{
		for(int i=0;i<4;i++)
		{
			color[i]=icolor[i];
		}
	}
	
	public void setStart(int istart)
	{
		start = istart;
	}
	
	public void setEnd(int iend)
	{
		end = iend;
	}
}
