package com.lotech.sudd;

import android.opengl.GLES20;

public class LtShader
{
	private final String vertshadercode =
			"attribute vec4 vPosition;" +
			"void main() {" +
			"	gl_Position = vPosition;" +
			"}";
	
	private final String fragshadercode =
			"precision mediump float;" +
			"uniform vec4 vColor;" +
			"void main() {" +
			"	gl_FragColor = vColor;" +
			"}";
	
	private int program;
	
	LtShader()
	{
		int vertshader = LoadShader(GLES20.GL_VERTEX_SHADER, vertshadercode);
		int fragshader = LoadShader(GLES20.GL_FRAGMENT_SHADER, fragshadercode);
		
		program = GLES20.glCreateProgram();
		GLES20.glAttachShader(program, vertshader);
		GLES20.glAttachShader(program, fragshader);
		GLES20.glLinkProgram(program);
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
