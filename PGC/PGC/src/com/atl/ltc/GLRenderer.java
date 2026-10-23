package com.atl.ltc;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.atl.equation.EqCalculator;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.View;

public class GLRenderer	implements GLSurfaceView.Renderer
{
	private EqCalculator calc;
	private GraphedFuncs gfuncs;
	private GLShaderBase shader,shader3d;
	private GLBox tbox;
	private PlotView plotview;
	private float projmat[] = {1,0,0,0,
					   0,1,0,0,
					   0,0,1,0,
					   0,0,0,1};
	private float oprojmat[] = {1,0,0,0,
						0,1,0,0,
						0,0,1,0,
						0,0,0,1};
	private int vwidth=1,vheight=1;
	private int plotsize;
	private boolean bgraph = false;
	private float view3dtheta = 0.1f, view3dphi = 0;
	private float view3dthetat = 0.1f, view3dphit = 0;
	private float plot3dmat[] = {1,0,0,
								0,1,0,
								0,0,1};
	private boolean btouch = false;
	private float touchpos[] = {0,0};
	private float touchmov[] = {0,0};
	
	public GLRenderer(Context icontext,EqCalculator icalc,GraphedFuncs igfuncs)
	{
		super();
		calc = icalc;
		gfuncs = igfuncs;
	}
	
	@Override
	public void finalize()
	{
		plotview.Destroy();
		tbox.Destroy();
		shader.Destroy();
	}
	
	public void generatePlot()
	{
		calc.GeneratePlot(plotview,gfuncs);
	}

	public void animatePlot()
	{	
		plot3dmat[0]=(float)Math.cos(view3dtheta+view3dthetat);
		plot3dmat[1]=(float)Math.sin(view3dtheta+view3dthetat)*(float)Math.sin(view3dphi+view3dphit);
		plot3dmat[2]=(float)Math.sin(view3dtheta+view3dthetat)*(float)Math.cos(view3dphi+view3dphit);
		plot3dmat[3]=0;
		plot3dmat[4]=(float)Math.cos(view3dphi+view3dphit);
		plot3dmat[5]=-(float)Math.sin(view3dphi+view3dphit);
		plot3dmat[6]=-(float)Math.sin(view3dtheta+view3dthetat);
		plot3dmat[7]=(float)Math.cos(view3dtheta+view3dthetat)*(float)Math.sin(view3dphi+view3dphit);
		plot3dmat[8]=(float)Math.cos(view3dtheta+view3dthetat)*(float)Math.cos(view3dphi+view3dphit);
		
		if(btouch)
		{
			view3dthetat=-(touchmov[0]-touchpos[0])*2*(float)Math.PI/plotsize;
			view3dphit=-(touchmov[1]-touchpos[1])*2*(float)Math.PI/plotsize;
		}
		else
			view3dtheta+=0.02f;
	}
	
	public void onTouchDown(float ix,float iy)
	{
		view3dthetat=0;
		view3dphit=0;
		touchpos[0]=ix;
		touchpos[1]=iy;
		touchmov[0]=ix;
		touchmov[1]=iy;
		btouch = true;
	}
	
	public void onTouchMove(float ix,float iy)
	{
		touchmov[0]=ix;
		touchmov[1]=iy;
	}
	
	public void onTouchUp(float ix,float iy)
	{
		btouch = false;
		view3dtheta+=view3dthetat;
		view3dthetat=0;
		view3dphi+=view3dphit;
		view3dphit=0;
	}
	
	@Override
	public void onDrawFrame(GL10 gl)
	{
		gl.glViewport(0, 0, vwidth, vheight);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
		
		if(bgraph)
		{
			gl.glViewport(vwidth/2-plotsize/2, (int)(vheight*0.05), plotsize, plotsize);
			if(plotview.getNumVars()==1)
			{
				shader.UseShader();
				shader.SetViewProj(oprojmat);
				plotview.render(shader);
			}
			else if(plotview.getNumVars()==2)
			{
				shader3d.UseShader();
				shader3d.SetViewProj(projmat);
				shader3d.SetDrawRot(plot3dmat);
				plotview.render(shader3d);
			}
		}
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		gl.glViewport(0, 0, width, height);
		vwidth=width;
		vheight=height;
		if(width>height)
			plotsize = (int)(height*0.9f);
		else
			plotsize = (int)(width*0.9f);
		BuildPerspectiveProjection(projmat,50.0f,1.0f,1,1000);
		float hps = plotsize/2.0f;
		buildOrthographicProjection(oprojmat,-hps,hps,hps,-hps,-1,1);
		plotview.resize(width, height);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		gl.glClearColor(1f, 1f, 1f, 1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		shader = new GLShader();
		shader3d = new GLShader3d();
		tbox = new GLBox();
		plotview = new PlotView();
	}
	
	public void setDrawGraph(boolean ibgraph)
	{
		bgraph = ibgraph;
	}
	
	public PlotView getPlotView()
	{
		return plotview;
	}
	
	public void BuildPerspectiveProjection(float imat[],float fov,float aspect,float n,float f)
	{
	    float h = 1.0f/(float)Math.tan(fov*3.14159/360.0);
	    float neg_depth = n-f;

	    imat[0] = h / aspect;
	    imat[1] = 0;
	    imat[2] = 0;
	    imat[3] = 0;

	    imat[4] = 0;
	    imat[5] = h;
	    imat[6] = 0;
	    imat[7] = 0;

	    imat[8] = 0;
	    imat[9] = 0;
	    imat[10] = (f + n)/neg_depth;
	    imat[11] = -1;

	    imat[12] = 0;
	    imat[13] = 0;
	    imat[14] = 2.0f*(n*f)/neg_depth;
	    imat[15] = 0;
	}
	
	private void buildOrthographicProjection(float imatbuf[],float l, float r,float t,float b,float n,float f)
	{
	    imatbuf[0] = 2/(r-l);
	    imatbuf[1] = 0;
	    imatbuf[2] = 0;
	    imatbuf[3] = -(r+l)/(r-l);

	    imatbuf[4] = 0;
	    imatbuf[5] = 2/(t-b);
	    imatbuf[6] = 0;
	    imatbuf[7] = -(t+b)/(t-b);

	    imatbuf[8] = 0;
	    imatbuf[9] = 0;
	    imatbuf[10] = -2/(f-n);
	    imatbuf[11] = -(f+n)/(f-n);

	    imatbuf[12] = 0;
	    imatbuf[13] = 0;
	    imatbuf[14] = 0;
	    imatbuf[15] = 1;
	}
	
	public void drawLine(float x1,float y1,float x2,float y2)
	{
		shader.UseShader();
	}

}
