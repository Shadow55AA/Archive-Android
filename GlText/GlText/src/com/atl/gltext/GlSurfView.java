package com.atl.gltext;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class GlSurfView extends GLSurfaceView
{

	public GlSurfView(Context context)
	{
		super(context);
		
		setRenderer(new GlRenderer(context));
	}

}
