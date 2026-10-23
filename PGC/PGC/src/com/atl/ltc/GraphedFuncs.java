package com.atl.ltc;

import android.graphics.Color;

import com.atl.equation.EqFunction;

public class GraphedFuncs
{
	private int graphmode = 1;
	private EqFunction gfuncs[];
	private int ngfuncs;
	private int gfcolors[];
	private float gfrcolors[];
	
	public GraphedFuncs()
	{
		gfuncs = new EqFunction[5];
		for(int i=0;i<5;i++)
			gfuncs[i]=null;
		ngfuncs = 0;
		gfcolors = new int[5];
		gfcolors[0] = Color.parseColor("#ff000000");
		gfcolors[1] = Color.parseColor("#ffff0000");
		gfcolors[2] = Color.parseColor("#ff00ff00");
		gfcolors[3] = Color.parseColor("#ff0000ff");
		gfcolors[4] = Color.parseColor("#ffff00ff");
		
		gfrcolors = new float[5*3];
		gfrcolors[0] = 0f; gfrcolors[1] = 0f; gfrcolors[2] = 0f;
		gfrcolors[3] = 1f; gfrcolors[4] = 0f; gfrcolors[5] = 0f;
		gfrcolors[6] = 0f; gfrcolors[7] = 1f; gfrcolors[8] = 0f;
		gfrcolors[9] = 0f; gfrcolors[10] = 0f; gfrcolors[11] = 1f;
		gfrcolors[12] = 1f; gfrcolors[13] = 0f; gfrcolors[14] = 1f;
	}
	
	public int addFunction(EqFunction ifunc)
	{
		if(ifunc.GetNumVars()==graphmode)
		{
			if(graphmode==1)
			{
				if(ngfuncs<5)
				{
					for(int i=0;i<5;i++)
					{
						if(gfuncs[i]==null)
						{
							gfuncs[i]=ifunc;
							ngfuncs++;
							return i;
						}
					}
				}
				else
					return -1;
			}
			else
			{
				if(ngfuncs<1)
				{
					if(gfuncs[0]==null)
					{
						gfuncs[0]=ifunc;
						ngfuncs++;
						return 0;
					}
				}
				else
					return -1;
			}
		}
		else
		{
			if(ifunc.GetNumVars()==1||ifunc.GetNumVars()==2)
			{
				graphmode = ifunc.GetNumVars();
				for(int i=0;i<5;i++)
					gfuncs[i]=null;
				ngfuncs=0;
				return addFunction(ifunc);
			}
		}
		return -1;
	}
	
	public void removeFunction(EqFunction ifunc)
	{
		for(int i=0;i<5;i++)
		{
			if(gfuncs[i]==ifunc)
			{
				gfuncs[i]=null;
				ngfuncs--;
			}
		}
	}
	
	public boolean containsFunction(EqFunction ifunc)
	{
		for(int i=0;i<5;i++)
		{
			if(gfuncs[i]!=null&&gfuncs[i].GetName().equals(ifunc.GetName()))
				return true;
		}
		return false;
	}
	
	public void clearFuncs()
	{
		for(int i=0;i<5;i++)
		{
			gfuncs[i]=null;
			ngfuncs=0;
		}
	}
	
	public EqFunction getFunction(int i)
	{
		return gfuncs[i];
	}
	
	public int getColor(int iind)
	{
		return gfcolors[iind];
	}
	
	public float[] getRColor(int i)
	{
		float rcolor[] = {gfrcolors[i*3],gfrcolors[i*3+1],gfrcolors[i*3+2]};
		return rcolor;
	}
	
	public int getGraphMode()
	{
		return graphmode;
	}
	
	public int getNumFuncs()
	{
		return ngfuncs;
	}
}
