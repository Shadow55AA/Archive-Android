package com.lotech.r3;

import com.lotech.r3.GameEngine;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class GameSurfaceView extends GLSurfaceView
{
	private GameEngine gengine;
	
	public GameSurfaceView(Context context)
	{
		super(context);
		setEGLContextClientVersion(2);
		gengine = null;
	}
	
	public GameSurfaceView(Context context,GameEngine igengine)
	{
		super(context);
		setEGLContextClientVersion(2);
		gengine = igengine;
	}
	
	public GameSurfaceView(Context context,AttributeSet ias,GameEngine igengine)
	{
		super(context,ias);
		setEGLContextClientVersion(2);
		gengine = igengine;
	}
	
	public boolean onTouchEvent(MotionEvent e)
	{
		if(gengine!=null)
			return gengine.onTouchEvent(e);
		return super.onTouchEvent(e);
	}
}
