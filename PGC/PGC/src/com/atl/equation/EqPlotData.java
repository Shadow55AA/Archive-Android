package com.atl.equation;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class EqPlotData
{
	
	public int nvars;
	public int maxnfloats;
	public FloatBuffer plotfloats;
	public float domainx[];
	public float domainy[];
	public float rangey[];
	public int stepsx,stepsy;
	
	public EqPlotData()
	{
		nvars = 0;
		maxnfloats = 10000;
		ByteBuffer bb = ByteBuffer.allocateDirect(10000*4);
		bb.order(ByteOrder.nativeOrder());
		plotfloats = bb.asFloatBuffer();
		domainx = new float[2];
		domainy = new float[2];
		rangey = new float[2];
		
		domainx[0]=-10;
		domainx[1]=10;
		domainy[0]=-10;
		domainy[1]=10;
		rangey[0]=-10;
		rangey[1]=10;
		
		stepsx=100;
		stepsy=100;
	}
}
