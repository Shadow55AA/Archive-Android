package com.lotech.sudd;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

public class LtGLRenderer implements GLSurfaceView.Renderer
{
	SUDDCore suddcore;
	
	public LtGLRenderer(SUDDCore icore)
	{
		super();
		suddcore = icore;
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		// TODO Auto-generated method stub
		suddcore.onSurfaceCreated(gl,config);
	}
	
	public void onDrawFrame(GL10 gl)
	{
		suddcore.onDrawFrame(gl);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		suddcore.onSurfaceChanged(gl, width, height);
	}

}
