package com.lotech.sudd;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.content.Context;

public class LtGLView extends GLSurfaceView
{
	SUDDCore suddcore;
	public LtGLView(Context context,SUDDCore icore)
	{
		super(context);
		setEGLContextClientVersion(2);
		suddcore = icore;
		setRenderer(suddcore.glrenderer);
	}
	
	public boolean onTouchEvent(MotionEvent e)
	{
		suddcore.onTouchEvent(e);
//		return true;
		return super.onTouchEvent(e);
	}
}
