package com.atl.ltc;


import android.opengl.GLES20;

public abstract class GLShaderBase
{	
	public static int LoadShader(int stype, String scode)
	{
		int shader = GLES20.glCreateShader(stype);
		
		GLES20.glShaderSource(shader, scode);
		GLES20.glCompileShader(shader);
		return shader;
	}
	
	public abstract void Destroy();
	
	public abstract void UseShader();
	
	public abstract void SetViewProj(float ivproj[]);
	
	public abstract void SetViewPos(float ivpos[]);
	
	public abstract void SetViewRot(float ivrot[]);
	
	public abstract void SetDrawPos(float idpos[]);
	
	public abstract void SetDrawRot(float idrot[]);
	
	public abstract void SetDrawColor(float idcolor[]);
	
	public abstract int GetProgram();
}