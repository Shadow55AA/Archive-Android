package com.losoft.ratican.engine;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class GameSurfaceView extends GLSurfaceView
{
	public GameSurfaceView(Context context,GameEngine igengine)
	{
		super(context);
		setEGLContextClientVersion(2);
	}
	
	@Override
	public boolean performClick()
	{
		return super.performClick();
	}
}
