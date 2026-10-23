package com.lotech.sudd;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.view.MotionEvent;

public class SUDDCore
{
	public LtGLView glview;
	public LtGLRenderer glrenderer;
	
	private float pmx = 0,pmy = 0;
	
	LtShader shader;
	LtTriangle tri;
	public float color[] = {1,0,0,0};
	
	public SUDDCore(Context icontext)
	{
		// Renderer Must be created before View
		glrenderer = new LtGLRenderer(this);
		glview = new LtGLView(icontext,this);
	}
	
	// Renderer call
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		GLES20.glClearColor(0,0,0,1.0f);
		
		shader = new LtShader();
		tri = new LtTriangle();
	}

	// Renderer call
	public void onDrawFrame(GL10 gl)
	{
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		shader.UseShader();
		int scolor = GLES20.glGetUniformLocation(shader.GetProgram(), "vColor");
		GLES20.glUniform4fv(scolor, 1, color,0);
		tri.Draw(shader.GetProgram());
	}

	// LtGLRenderer call
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		GLES20.glViewport(0,0,width,height);
	}

	// LtGLView call
	public boolean onTouchEvent(MotionEvent e)
	{
		float mx = e.getX();
		float my = e.getY();
		switch(e.getAction())
		{
		case MotionEvent.ACTION_MOVE:
			float dx = mx - pmx;
			float dy = my - pmy;
			color[0] += dx / 100.0f;
			if(color[0]>1.0f)
				color[0]=1.0f;
			if(color[0]<0.0f)
				color[0]=0.0f;
			color[1] += dy / 100.0f;
			if(color[1]>1.0f)
				color[1]=1.0f;
			if(color[1]<0.0f)
				color[1]=0.0f;
			break;
		}
		
		pmx = mx;
		pmy = my;
		return true;
	}
}
