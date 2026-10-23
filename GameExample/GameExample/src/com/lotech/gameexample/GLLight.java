package com.lotech.gameexample;

import android.opengl.GLES20;

public class GLLight
{
	private float pos[] = {0,-1,0,1};
	private float diffuse[] = {1,1,1,1};
	private float ambient[] = {0.1f,0.1f,0.1f,1};
	private float specular[] = {1,1,1,1};
	
	public void Set(int iprogram)
	{
		int sloc = GLES20.glGetUniformLocation(iprogram, "lpos");
		GLES20.glUniform4fv(sloc, 1, pos,0);
		sloc = GLES20.glGetUniformLocation(iprogram, "ldiff");
		GLES20.glUniform4fv(sloc, 1, diffuse,0);
		sloc = GLES20.glGetUniformLocation(iprogram, "lamb");
		GLES20.glUniform4fv(sloc, 1, ambient,0);
		sloc = GLES20.glGetUniformLocation(iprogram, "lspec");
		GLES20.glUniform4fv(sloc, 1, specular,0);
	}
}
