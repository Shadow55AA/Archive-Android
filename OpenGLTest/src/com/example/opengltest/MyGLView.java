package com.example.opengltest;

import android.opengl.GLSurfaceView;
import android.content.Context;

public class MyGLView extends GLSurfaceView
{
	public MyGLView(Context context)
	{
		super(context);
		setEGLContextClientVersion(2);
		setRenderer(new MyRenderer());
	}
}
