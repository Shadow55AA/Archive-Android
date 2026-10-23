package com.atl.ltc;


import android.opengl.GLES20;

public class GLShader3d extends GLShaderBase
{
	private final String vertshadercode =
			"uniform mat4 vproj;" +
			"uniform vec4 vpos;" +
			"uniform mat3 vrot;" +
			"uniform vec3 dpos;" +
			"uniform mat3 drot;" +
			"attribute vec3 vertpos;" +
			"attribute vec3 vertcolor;" +
			"varying vec3 fvertpos;" +
			"varying vec3 fvertcolor;" +
			"void main() {" +
			"	fvertcolor = vertcolor;" +
			"	vec3 tpos = drot*vertpos+dpos;" +
//			"	fvertpos = tpos;" +
//			"	tpos = vrot*(tpos-vpos.xyz);" +
//			"	gl_Position = vproj*vec4(vertpos+dpos,1);" +
			"	gl_Position = vproj*vec4(tpos,1);" +
			"}";
	
	private final String fragshadercode =
			"precision mediump float;" +
			"uniform vec4 vColor;" +
			"uniform vec4 lpos;" +
			"uniform vec4 lamb;" +
			"uniform vec4 ldiff;" +
			"uniform vec4 lspec;" +
			"uniform vec4 mamb;" +
			"uniform vec4 mdiff;" +
			"uniform vec4 mspec;" +
			"uniform sampler2D difftex;" +
			"varying vec3 fvertpos;" +
			"varying vec3 fvertcolor;" +
			"void main() {" +
//			"	vec4 fcolor = lamb*mamb;" +
//			"	vec3 ldir = lpos.xyz;" +
//			"	float theta = dot(fvertnorm,-ldir);" +
//			"	if(theta>0.0)" +
//			"	{" +
//			"		fcolor += mdiff*ldiff*theta;" +
//			"	}" +
			//"	fcolor = clamp(fcolor,0,1);" +
			"	gl_FragColor = vec4(fvertcolor,1);" +
			"}";
	
	private int program=0;
	private int uvproj,uvpos,uvrot,udpos,udrot,uvcolor;
	
	
	GLShader3d()
	{
		int vertshader = LoadShader(GLES20.GL_VERTEX_SHADER, vertshadercode);
		int fragshader = LoadShader(GLES20.GL_FRAGMENT_SHADER, fragshadercode);
		
		program = GLES20.glCreateProgram();
		GLES20.glAttachShader(program, vertshader);
		GLES20.glAttachShader(program, fragshader);
		GLES20.glLinkProgram(program);
		GLES20.glDeleteShader(fragshader);
		GLES20.glDeleteShader(vertshader);
		GLES20.glUseProgram(program);
		
		float mat[] = {1,0,0,0,
					   0,1,0,0,
					   0,0,1,0,
					   0,0,0,1};
		float vec[] = {0,0,0,0};
		int uloc;
		uvproj = GLES20.glGetUniformLocation(program, "vproj");
		GLES20.glUniformMatrix4fv(uvproj, 1,false, mat,0);
		uvpos = GLES20.glGetUniformLocation(program, "vpos");
		GLES20.glUniform4fv(uvpos, 1, vec,0);
		udpos = GLES20.glGetUniformLocation(program, "dpos");
		GLES20.glUniform3fv(udpos, 1, vec,0);
		uvrot = GLES20.glGetUniformLocation(program, "vrot");
		GLES20.glUniformMatrix3fv(uvrot, 1,false, mat,0);
		udrot = GLES20.glGetUniformLocation(program, "drot");
		GLES20.glUniformMatrix3fv(udrot, 1,false, mat,0);
		vec[3]=1;
		uvcolor = GLES20.glGetUniformLocation(program, "vColor");
		GLES20.glUniform4fv(uvcolor, 1, vec,0);
		GLES20.glUseProgram(0);
	}

	@Override
	public void Destroy()
	{
		if(program!=0)
			GLES20.glDeleteProgram(program);
	}

	@Override
	public void UseShader()
	{
		GLES20.glUseProgram(program);
	}
	
	@Override
	public void SetViewProj(float ivproj[])
	{
		GLES20.glUniformMatrix4fv(uvproj, 1,false, ivproj,0);
	}

	@Override
	public void SetViewPos(float ivpos[])
	{
		GLES20.glUniform4fv(uvpos, 1, ivpos,0);
	}

	@Override
	public void SetViewRot(float ivrot[])
	{
		GLES20.glUniform4fv(uvrot, 1, ivrot,0);
	}

	@Override
	public void SetDrawPos(float idpos[])
	{
		GLES20.glUniform3fv(udpos, 1, idpos,0);
	}

	@Override
	public void SetDrawRot(float idrot[])
	{
		GLES20.glUniformMatrix3fv(udrot, 1, false,idrot,0);
	}

	@Override
	public void SetDrawColor(float idcolor[])
	{
		float tcolor[] = {idcolor[0],idcolor[1],idcolor[2],1};
		GLES20.glUniform4fv(uvcolor, 1, tcolor,0);
	}

	@Override
	public int GetProgram()
	{
		return program;
	}
}