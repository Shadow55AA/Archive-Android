package com.lotech.gameexample;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;

public class GLPlane
{
	private FloatBuffer vertbuffer;
	private ShortBuffer indbuffer;
	private int vertlength = 8;
	
	private float verts[] = {
		-0.5f, -0.5f, 0f,
		0,0,1,
		0,0,
		0.5f, -0.5f, 0f,
		0,0,1,
		1,0,
		-0.5f, 0.5f, 0f,
		0,0,1,
		0,1,
		0.5f, 0.5f, 0f,
		0,0,1,
		1,1};
	private short inds[] = {
		0,1,2,1,3,2
	};
	private int vbuff[] = {0,0};
	
	private static float color[] = {1,1,1,1};
	
	public GLPlane()
	{
		ByteBuffer bb = ByteBuffer.allocateDirect(4*verts.length);
		bb.order(ByteOrder.nativeOrder());
		vertbuffer = bb.asFloatBuffer();
		vertbuffer.put(verts);
		vertbuffer.position(0);
		ByteBuffer bb2 = ByteBuffer.allocateDirect(2*6);
		bb2.order(ByteOrder.nativeOrder());
		indbuffer = bb2.asShortBuffer();
		indbuffer.put(inds);
		indbuffer.position(0);
		
		GLES20.glGenBuffers(2, vbuff,0);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbuff[0]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, 4*verts.length, null, GLES20.GL_DYNAMIC_DRAW);
		GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, 0, 4*verts.length, vertbuffer);
		
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, vbuff[1]);
		GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, 2*6, indbuffer, GLES20.GL_STATIC_DRAW);
	}
	
	public void Destroy()
	{
		if(vbuff[0]!=0)
			GLES20.glDeleteBuffers(1, vbuff,0);
		if(vbuff[1]!=0)
			GLES20.glDeleteBuffers(1, vbuff,1);
	}
	
	public void SetSize(float isize[])
	{
		if(vbuff[0]==0)
			return;
		vertbuffer.position(0);
		for(int i=0;i<4;i++)
		{
			vertbuffer.put(i*vertlength,verts[i*vertlength]*isize[0]);
			vertbuffer.put(i*vertlength+1,verts[i*vertlength+1]*isize[1]);
			vertbuffer.put(i*vertlength+2,verts[i*vertlength+2]);
		}
		vertbuffer.position(0);
		
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbuff[0]);
		GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, 0, 4*verts.length, vertbuffer);
	}
	
	public void Draw(int iprogram)
	{
		if(vbuff[0]==0)
			return;
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbuff[0]);
		int spos = GLES20.glGetAttribLocation(iprogram, "vertpos");
		GLES20.glEnableVertexAttribArray(spos);
		GLES20.glVertexAttribPointer(spos,3,GLES20.GL_FLOAT,false,4*vertlength,0);
		int svnorm = GLES20.glGetAttribLocation(iprogram, "vertnorm");
		GLES20.glEnableVertexAttribArray(svnorm);
		GLES20.glVertexAttribPointer(svnorm,3,GLES20.GL_FLOAT,false,4*vertlength,12);
		int svtexuv = GLES20.glGetAttribLocation(iprogram, "vertuv");
		GLES20.glEnableVertexAttribArray(svtexuv);
		GLES20.glVertexAttribPointer(svtexuv,2,GLES20.GL_FLOAT,false,4*vertlength,24);
		
		int scolor = GLES20.glGetUniformLocation(iprogram, "vColor");
		GLES20.glUniform4fv(scolor, 1, color,0);

		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, vbuff[1]);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_SHORT, 0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
		GLES20.glDisableVertexAttribArray(svtexuv);
		GLES20.glDisableVertexAttribArray(svnorm);
		GLES20.glDisableVertexAttribArray(spos);
	}
}
