package com.lotech.tubesurfer;

import android.opengl.GLES20;

public class GLMaterial
{
	private float diffuse[] = {1,1,1,1};
	private float ambient[] = {1,1,1,1};
	private float specular[] = {1,1,1,1};
	
	void Set(int iprogram)
	{
		int sloc = GLES20.glGetUniformLocation(iprogram, "mdiff");
		GLES20.glUniform4fv(sloc, 1, diffuse,0);
		sloc = GLES20.glGetUniformLocation(iprogram, "mamb");
		GLES20.glUniform4fv(sloc, 1, ambient,0);
		sloc = GLES20.glGetUniformLocation(iprogram, "mspec");
		GLES20.glUniform4fv(sloc, 1, specular,0);
	}
	
	void SetDiffuse(float idiff[])
	{
		for(int i=0;i<4;i++)
			diffuse[i]=idiff[i];
	}
	
	void SetAmbient(float iamb[])
	{
		for(int i=0;i<4;i++)
			ambient[i]=iamb[i];
	}
	
	void SetSpecular(float ispec[])
	{
		for(int i=0;i<4;i++)
			specular[i]=ispec[i];
	}
}
