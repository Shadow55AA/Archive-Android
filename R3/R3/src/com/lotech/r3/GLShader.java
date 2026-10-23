package com.lotech.r3;

import android.opengl.GLES20;

public class GLShader
{
	private final String vertshadercode =
			"uniform mat4 vproj;" +
			"uniform vec4 vpos;" +
			"uniform mat3 vrot;" +
			"uniform vec4 dpos;" +
			"uniform mat3 drot;" +
			"attribute vec3 vertpos;" +
			"attribute vec3 vertnorm;" +
			"attribute vec4 vertuv;" +
			"varying vec3 fvertpos;" +
			"varying vec3 fvertnorm;" +
			"varying vec4 fvertuv;" +
			"void main() {" +
			"	fvertnorm = drot*vertnorm;" +
			"	fvertuv = vertuv;" +
			"	vec3 tpos = drot*vertpos+dpos.xyz;" +
			"	fvertpos = tpos;" +
			"	tpos = vrot*(tpos-vpos.xyz);" +
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
			"varying vec3 fvertnorm;" +
			"varying vec4 fvertuv;" +
			"void main() {" +
			"	vec4 fcolor = texture2D(difftex,fvertuv.xy)*lamb*mamb;" +
			"	vec3 ldir = lpos.xyz;" +
			"	float theta = dot(fvertnorm,-ldir);" +
			"	if(theta>0.0)" +
			"	{" +
			"		fcolor += texture2D(difftex,fvertuv.xy)*mdiff*ldiff*theta;" +
			"	}" +
			//"	fcolor = clamp(fcolor,0,1);" +
			"	gl_FragColor = fcolor;" +
			"}";
	
	private int program=0;
	
	GLShader()
	{
		int vertshader = LoadShader(GLES20.GL_VERTEX_SHADER, vertshadercode);
		int fragshader = LoadShader(GLES20.GL_FRAGMENT_SHADER, fragshadercode);
		
		program = GLES20.glCreateProgram();
		GLES20.glAttachShader(program, vertshader);
		GLES20.glAttachShader(program, fragshader);
		GLES20.glLinkProgram(program);
		GLES20.glDeleteShader(fragshader);
		GLES20.glDeleteShader(vertshader);
		
		float mat[] = {1,0,0,0,
					   0,1,0,0,
					   0,0,1,0,
					   0,0,0,1};
		float vec[] = {0,0,0,0};
		int uloc;
		uloc = GLES20.glGetUniformLocation(program, "vproj");
		GLES20.glUniformMatrix4fv(uloc, 1,false, mat,0);
		uloc = GLES20.glGetUniformLocation(program, "vpos");
		GLES20.glUniform4fv(uloc, 1, vec,0);
		uloc = GLES20.glGetUniformLocation(program, "dpos");
		vec[3]=1;
		GLES20.glUniform4fv(uloc, 1, vec,0);
		uloc = GLES20.glGetUniformLocation(program, "vrot");
		GLES20.glUniform4fv(uloc, 1, vec,0);
		uloc = GLES20.glGetUniformLocation(program, "drot");
		GLES20.glUniform4fv(uloc, 1, vec,0);
		uloc = GLES20.glGetUniformLocation(program, "vColor");
		GLES20.glUniform4fv(uloc, 1, vec,0);
	}
	
	public void Destroy()
	{
		if(program!=0)
			GLES20.glDeleteProgram(program);
	}
	
	public static int LoadShader(int stype, String scode)
	{
		int shader = GLES20.glCreateShader(stype);
		
		GLES20.glShaderSource(shader, scode);
		GLES20.glCompileShader(shader);
		return shader;
	}
	
	public void UseShader()
	{
		GLES20.glUseProgram(program);
	}
	
	public int GetProgram()
	{
		return program;
	}
}
