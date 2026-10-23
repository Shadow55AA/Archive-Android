package com.lotech.gameexample;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.lotech.gameexample.R;
import com.lotech.gameexample.R.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES20;
import android.view.MotionEvent;

public class GameEngine
{
	public GameSurfaceView glsview;
	public GameRenderer glrenderer = null;
	public GameThread gthread;
	private float[] viewproj = {1,0,0,0,
								0,1,0,0,
								0,0,1,0,
								0,0,0,1};
	private int nobjs,maxnobjs = 32;
	private GameObject objs[];
	private GLLight sunlight;
	private GLMaterial whitemat;
	private GLTexture tex01;
	
	public GameEngine(Context icontext)
	{
		// Renderer must be created before view
		glrenderer = new GameRenderer(icontext,this);
		glsview = new GameSurfaceView(icontext,this);
		gthread = new GameThread(this);
		nobjs = 0;
		tex01 = new GLTexture();
		tex01.LoadTextureResource(icontext.getResources(), R.drawable.getex);
	}
			
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		float fvec[] = {0,0,0,0};
		GLES20.glClearColor(0,0,0,1.0f);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glDepthFunc(GLES20.GL_LEQUAL);
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		//GLES20.glDisable(GLES20.GL_CULL_FACE);
		gthread.SetRunning(true);
		gthread.start();
		objs = new GameObject[maxnobjs];
		goPlane tplane = new goPlane();
		fvec[0]=5;	fvec[1]=5f;
		tplane.SetSize(fvec);
		Quaternion.QRot(fvec, 1, 0, 0, (float)Math.PI/2.0f);
		tplane.SetRot(fvec);
		objs[0] = tplane;
		/*goBox tbox = new goBox();
		fvec[0]=1;	fvec[1]=1f;	fvec[2]=1f;
		tbox.SetSize(fvec);
		fvec[0]=0;	fvec[1]=1f;	fvec[2]=0f;
		tbox.SetPos(fvec);
		objs[1] = tbox;*/
		nobjs = 1;
		sunlight = new GLLight();
		whitemat = new GLMaterial();
		tex01.GenTexture();
	}
	
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		GLES20.glViewport(0,0,width,height);
		glrenderer.BuildPerspectiveProjection(viewproj, 50, (float)width/(float)height, 1f, 1000);
	}
	
	public void Run()
	{
		glsview.requestRender();
		try
		{
			GameThread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onDrawFrame(GL10 gl)
	{
		float tvec[] = {1,1,1,1};
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
		glrenderer.SetViewProjection(viewproj);
		float vpos[] = {0,1,4,0};
		glrenderer.SetViewPos(vpos);
		float vrot[] = {0,0,0,1};
		glrenderer.SetViewRot(vrot);
		glrenderer.SetLight(sunlight);
		whitemat.SetAmbient(tvec);
		if(!glrenderer.SetTexture(tex01))
		{
			tvec[1] = 0;
			tvec[2] = 0;
			whitemat.SetAmbient(tvec);
		}
		glrenderer.SetMaterial(whitemat);
		for(int i=0;i<nobjs;i++)
		{
			objs[i].VRender(glrenderer);
		}
	}
	
	public boolean onTouchEvent(MotionEvent e)
	{
		return false;
	}
}
