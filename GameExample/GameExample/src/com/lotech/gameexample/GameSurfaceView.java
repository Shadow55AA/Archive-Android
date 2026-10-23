package com.lotech.gameexample;

import com.lotech.gameexample.GameEngine;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class GameSurfaceView extends GLSurfaceView
{
	private GameEngine gengine;
	
	public GameSurfaceView(Context context,GameEngine igengine)
	{
		super(context);
		setEGLContextClientVersion(2);
		gengine = igengine;
		setRenderer(gengine.glrenderer);
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}
	
	public GameSurfaceView(Context context,AttributeSet ias,GameEngine igengine)
	{
		super(context,ias);
		setEGLContextClientVersion(2);
		gengine = igengine;
		setRenderer(gengine.glrenderer);
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}
	
	public boolean onTouchEvent(MotionEvent e)
	{
		return gengine.onTouchEvent(e);
//		return super.onTouchEvent(e);
	}
}
