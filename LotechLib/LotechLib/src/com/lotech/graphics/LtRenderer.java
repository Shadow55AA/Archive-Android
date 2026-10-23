package com.lotech.graphics;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

public class LtRenderer implements GLSurfaceView.Renderer
{
	private LtShader shader;
	
	public LtRenderer(Context icontext)
	{
		super();
	}
	
	@Override
	public void finalize()
	{
		shader.Destroy();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		shader = new LtShader();
	}

	@Override
	public void onDrawFrame(GL10 gl)
	{
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		
	}
}
