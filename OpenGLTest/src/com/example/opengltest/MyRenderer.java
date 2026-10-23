package com.example.opengltest;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

public class MyRenderer implements GLSurfaceView.Renderer
{
	Triangle mtri;
	Square msquare;
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		GLES20.glClearColor(0,0,0,1.0f);
		
		mtri = new Triangle();
		msquare = new Square();
	}

	public void onDrawFrame(GL10 gl)
	{
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		mtri.draw();
	}

	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		GLES20.glViewport(0,0,width,height);
	}


}
