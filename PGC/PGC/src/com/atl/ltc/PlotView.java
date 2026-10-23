package com.atl.ltc;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES10;
import android.opengl.GLES20;
import android.util.Log;

public class PlotView
{
	private int nvars = 1;
	private float domainx[] = {-10,10};
	private float domainy[] = {-10,10};
	private float rangey[] = {-10,10};
	// 250^2,40^3,16^4
	private float plotsize;
	private int linesteps = 100;
	private int planesteps = 20;	// Must be 2 or higher
	private int nfuncs;
	
	private FloatBuffer vertbuffer;
	private FloatBuffer colorbuffer;
	private int maxnverts = 2000;
	private float fcolors[];
	
	// Line graph box
	private FloatBuffer box2dvertbuffer;
	
	// Plane graph box
	private float box3dverts[] = {-5,-5,5,
								  0,0,0,
								  5,-5,5,
								  0,0,0,
								  5,5,5,
								  0,0,0,
								  -5,5,5,
								  0,0,0,
								  -5,-5,-5,
								  0,0,0,
								  5,-5,-5,
								  0,0,0,
								  5,5,-5,
								  0,0,0,
								  -5,5,-5,
								  0,0,0};
	private short box3dinds[] = {0,1,1,2,2,3,3,0,
								 4,5,5,6,6,7,7,4,
								 0,4,1,5,2,6,3,7};
	private int box3dvbuff[] = {0};
	private int box3dibuff[] = {0};
	private ShortBuffer indbuffer;
	
	private int planeibuff[] = {0};
	
	// Plane graph axes
	private float pgaxes[] = {0,0,0,
							  0,1,0,
							  1,0,0,
							  0,1,0,
							  0,0,0,
							  0,0,1,
							  0,1,0,
							  0,0,1,
							  0,0,0,
							  1,0,0,
							  0,0,1,
							  1,0,0};
	private int pgaxesvbuff[] = {0};
	
	public PlotView()
	{
		ByteBuffer bb = ByteBuffer.allocateDirect(4*3*maxnverts);
		bb.order(ByteOrder.nativeOrder());
		vertbuffer = bb.asFloatBuffer();
		for(int i=0;i<maxnverts*3;i++)
		{
			vertbuffer.put(0f);
		}
		vertbuffer.position(0);
		
		bb = ByteBuffer.allocateDirect(4*3*maxnverts);
		bb.order(ByteOrder.nativeOrder());
		colorbuffer = bb.asFloatBuffer();
		for(int i=0;i<maxnverts*3;i++)
		{
			colorbuffer.put(0f);
		}
		colorbuffer.position(0);
		
		// Plane Graph box
		vertbuffer.put(box3dverts);
		vertbuffer.position(0);
		
		GLES20.glGenBuffers(1, box3dvbuff,0);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, box3dvbuff[0]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, 4*6*8, vertbuffer, GLES20.GL_STATIC_DRAW);
		
		// Plane Graph Axes
		vertbuffer.put(pgaxes);
		vertbuffer.position(0);
		
		GLES20.glGenBuffers(1, pgaxesvbuff,0);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, pgaxesvbuff[0]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, 4*6*6, vertbuffer, GLES20.GL_STATIC_DRAW);
		
		bb = ByteBuffer.allocateDirect(2*6*(planesteps)*(planesteps));
		bb.order(ByteOrder.nativeOrder());
		indbuffer = bb.asShortBuffer();
		indbuffer.put(box3dinds);
		indbuffer.position(0);
		
		GLES20.glGenBuffers(1, box3dibuff,0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, box3dibuff[0]);
		GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, 2*24, indbuffer, GLES20.GL_STATIC_DRAW);
		
		for(int j=0;j<planesteps;j++)
		{
			for(int i=0;i<planesteps;i++)
			{
				indbuffer.put((short)(i+j*(planesteps+1)));
				indbuffer.put((short)(i+1+j*(planesteps+1)));
				indbuffer.put((short)(i+(j+1)*(planesteps+1)));
				indbuffer.put((short)(i+(j+1)*(planesteps+1)));
				indbuffer.put((short)(i+1+j*(planesteps+1)));
				indbuffer.put((short)(i+1+(j+1)*(planesteps+1)));
			}
		}
		indbuffer.position(0);

		GLES20.glGenBuffers(1, planeibuff,0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, planeibuff[0]);
		GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, 2*6*planesteps*planesteps, indbuffer, GLES20.GL_STATIC_DRAW);
		
		fcolors = new float[3*10];
		for(int i=0;i<10*3;i++)
			fcolors[i]=1f;
		
		// Line graph box
		bb = ByteBuffer.allocateDirect(4*3*8);
		bb.order(ByteOrder.nativeOrder());
		box2dvertbuffer = bb.asFloatBuffer();
		for(int i=0;i<24;i++)
			box2dvertbuffer.put(0f);
		box2dvertbuffer.position(0);
	}
	
	public void Destroy()
	{
		GLES20.glDeleteBuffers(1, planeibuff,0);
		GLES20.glDeleteBuffers(1, box3dibuff,0);
		GLES20.glDeleteBuffers(1, box3dvbuff,0);
	}
	
	public void setNumVars(int invars)
	{
		nvars = invars;
	}
	
	public int getNumVars()
	{
		return nvars;
	}
	
	public float[] getDomainX()
	{
		return domainx;
	}
	
	public void setDomainX(float ixmin,float ixmax)
	{
		domainx[0] = ixmin;
		domainx[1] = ixmax;
	}
	
	public float[] getDomainY()
	{
		return domainy;
	}
	
	public void setDomainY(float iymin,float iymax)
	{
		domainy[0] = iymin;
		domainy[1] = iymax;
	}
	
	public int getLineSteps()
	{
		return linesteps;
	}
	
	public int getPlaneSteps()
	{
		return planesteps;
	}
	
	public void resetVerts()
	{
		vertbuffer.position(0);
	}
	
	public void inputVert(int ind,float vert,int fnum)
	{
		if(nvars==1)
		{
			float vx = 0;
			vx = domainx[0]+ind*(domainx[1]-domainx[0])/(linesteps);
			vertbuffer.put(ind*3+fnum*(linesteps+1)*3,vx);
			vertbuffer.put(ind*3+1+fnum*(linesteps+1)*3,vert);
			vertbuffer.put(ind*3+2+fnum*(linesteps+1)*3,0);
		}
	}
	
	public void inputVert(int i,int j,float x,float y,float vert)
	{
		if(nvars==2)
		{
			int ind = j*(planesteps+1)*3+i*3;
			vertbuffer.put(ind,x);
			vertbuffer.put(ind+1,vert);
			vertbuffer.put(ind+2,y);
		}
	}
	
	public void setFuncColor(int fnum,float color[])
	{
		fcolors[fnum*3]=color[0];
		fcolors[fnum*3+1]=color[1];
		fcolors[fnum*3+2]=color[2];
	}
	
	public void setRangeY(float imin,float imax)
	{
		rangey[0]=imin;
		rangey[1]=imax;
	}
	
	public float[] getRangeY()
	{
		return rangey;
	}
	
	public void scalePlot()
	{
		if(nvars==1)
		{
			for(int j=0;j<nfuncs;j++)
			{
				float vx = 0;
				float vy = 0;
				float dxscale = plotsize/(domainx[1]-domainx[0]);
				float ryscale = plotsize/(rangey[1]-rangey[0]);
				for(int i=0;i<=linesteps;i++)
				{
					vx = vertbuffer.get(i*3+j*(linesteps+1)*3);
					vertbuffer.put(i*3+j*(linesteps+1)*3,(vx-domainx[0])*dxscale);
					vy = vertbuffer.get(i*3+1+j*(linesteps+1)*3);
					vertbuffer.put(i*3+1+j*(linesteps+1)*3,(vy-rangey[0])*ryscale);
				}
			}
		}
		else if(nvars==2)
		{
			float midpoint[] = {(domainx[0]+domainx[1])/2f,(rangey[0]+rangey[1])/2f,(domainy[0]+domainy[1])/2f};
			int ind = 0;
			float vx = 0, vy = 0, vz = 0, k = 0;
			float dxscale = 10/(domainx[1]-domainx[0]);
			float dyscale = 10/(rangey[1]-rangey[0]);
			float dzscale = 10/(domainy[1]-domainy[0]);
			for(int j=0;j<=planesteps;j++)
			{
				for(int i=0;i<=planesteps;i++)
				{
					ind = j*(planesteps+1)*3+i*3;
					vx = vertbuffer.get(ind);
					vertbuffer.put(ind,(vx-midpoint[0])*dxscale);
					vy = vertbuffer.get(ind+1);
					vertbuffer.put(ind+1,(vy-midpoint[1])*dyscale);
					vz = vertbuffer.get(ind+2);
					vertbuffer.put(ind+2,(vz-midpoint[2])*dzscale);
					k = (vy-rangey[0])/(rangey[1]-rangey[0]);
					colorbuffer.put(ind,0*k+1*(1-k));
					colorbuffer.put(ind+1,1*k+0*(1-k));
					colorbuffer.put(ind+2,0);
				}
			}
		}
	}
	
	public void setNumFuncs(int infuncs)
	{
		nfuncs = infuncs;
	}
	
	public void resize(int iwidth,int iheight)
	{
		if(iwidth<iheight)
			plotsize=iwidth*0.9f;
		else
			plotsize=iheight*0.9f;
		linesteps=(int)plotsize/2;
		
		box2dvertbuffer.put(0,-plotsize/2);
		box2dvertbuffer.put(1,-plotsize/2);
		box2dvertbuffer.put(2,0);
		box2dvertbuffer.put(3,plotsize/2);
		box2dvertbuffer.put(4,-plotsize/2);
		box2dvertbuffer.put(5,0);
		box2dvertbuffer.put(6,-plotsize/2);
		box2dvertbuffer.put(7,plotsize/2-1);
		box2dvertbuffer.put(8,0);
		box2dvertbuffer.put(9,plotsize/2);
		box2dvertbuffer.put(10,plotsize/2-1);
		box2dvertbuffer.put(11,0);
		
		box2dvertbuffer.put(12,-plotsize/2+1);
		box2dvertbuffer.put(13,-plotsize/2);
		box2dvertbuffer.put(14,0);
		box2dvertbuffer.put(15,-plotsize/2+1);
		box2dvertbuffer.put(16,plotsize/2);
		box2dvertbuffer.put(17,0);
		box2dvertbuffer.put(18,plotsize/2);
		box2dvertbuffer.put(19,-plotsize/2);
		box2dvertbuffer.put(20,0);
		box2dvertbuffer.put(21,plotsize/2);
		box2dvertbuffer.put(22,plotsize/2);
		box2dvertbuffer.put(23,0);
	}
	
	public void render(GLShaderBase ishader)
	{
		if(nvars==1)
		{
			drawLineGraphBox(ishader);
			float dpos[] ={-plotsize/2,-plotsize/2,0};
			ishader.SetDrawPos(dpos);
			
			GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
			int spos = GLES20.glGetAttribLocation(ishader.GetProgram(), "vertpos");
			GLES20.glEnableVertexAttribArray(spos);
			GLES20.glVertexAttribPointer(spos,3,GLES20.GL_FLOAT,false,12,vertbuffer);
			
			float tcolor[] = {0,0,0};
			
			for(int i=0;i<nfuncs;i++)
			{
				tcolor[0] = fcolors[i*3]; tcolor[1] = fcolors[i*3+1]; tcolor[2] = fcolors[i*3+2];
				ishader.SetDrawColor(tcolor);
				
				GLES20.glDrawArrays(GL10.GL_LINE_STRIP, i*(linesteps+1), (linesteps+1));
			}
			
			GLES20.glDisableVertexAttribArray(spos);
		}
		else if(nvars==2)
		{
			float dpos[] = {-6,-6,-19};
			ishader.SetDrawPos(dpos);
			GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, pgaxesvbuff[0]);
			int spos = GLES20.glGetAttribLocation(ishader.GetProgram(), "vertpos");
			int scol = GLES20.glGetAttribLocation(ishader.GetProgram(), "vertcolor");
			GLES20.glEnableVertexAttribArray(spos);
			GLES20.glEnableVertexAttribArray(scol);
			GLES20.glVertexAttribPointer(spos,3,GLES20.GL_FLOAT,false,24,0);
			GLES20.glVertexAttribPointer(scol, 3, GLES20.GL_FLOAT, false, 24, 12);
			GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
			GLES20.glDrawArrays(GL10.GL_LINES, 0, 6);
			
			dpos[0] = 0; dpos[1] = 0; dpos[2] = -25;
			ishader.SetDrawPos(dpos);
			
			GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, box3dvbuff[0]);
			GLES20.glVertexAttribPointer(spos,3,GLES20.GL_FLOAT,false,24,0);
			GLES20.glVertexAttribPointer(scol, 3, GLES20.GL_FLOAT, false, 24, 12);
			GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, box3dibuff[0]);

			GLES20.glDrawElements(GL10.GL_LINES, 24, GLES10.GL_UNSIGNED_SHORT, 0);
			
			GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
			GLES20.glVertexAttribPointer(spos, 3, GLES20.GL_FLOAT, false, 12, vertbuffer);
			GLES20.glVertexAttribPointer(scol, 3, GLES20.GL_FLOAT, false, 12, colorbuffer);
			GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, planeibuff[0]);
			
			GLES20.glDrawElements(GLES10.GL_TRIANGLES, 6*(planesteps)*(planesteps), GLES10.GL_UNSIGNED_SHORT, 0);

			GLES20.glDisableVertexAttribArray(scol);
			GLES20.glDisableVertexAttribArray(spos);
		}
	}
	
	public void drawLineGraphBox(GLShaderBase ishader)
	{
		float dpos[] ={0,0,0};
		ishader.SetDrawPos(dpos);
		float tcolor[] = {0,1,0};
		ishader.SetDrawColor(tcolor);
		
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		int spos = GLES20.glGetAttribLocation(ishader.GetProgram(), "vertpos");
		GLES20.glEnableVertexAttribArray(spos);
		GLES20.glVertexAttribPointer(spos,3,GLES20.GL_FLOAT,false,12,box2dvertbuffer);
		GLES20.glDrawArrays(GL10.GL_LINES, 0, 4);
		tcolor[1] = 0f;
		tcolor[0] = 1f;
		ishader.SetDrawColor(tcolor);
		GLES20.glDrawArrays(GL10.GL_LINES, 4, 4);
		
		GLES20.glDisableVertexAttribArray(spos);
	}
}