package com.lotech.r3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;

public class RenderBuffer
{
	private int vertlength;
	private int nverts,ninds;
	private int glbuffers[] = {0,0};
	private boolean bindices,bnormals,buvs;
	
	public void Destroy()
	{
		if(glbuffers[0]!=0)
			GLES20.glDeleteBuffers(1, glbuffers,0);
		if(glbuffers[1]!=0)
			GLES20.glDeleteBuffers(1, glbuffers,1);
	}
	
	public void Generate(float iverts[],int inverts,short iinds[],int ininds,
		boolean ibindices,boolean ibnormals,boolean ibuvs)
	{
		nverts = inverts;
		ninds = ininds;
		bindices = ibindices;
		bnormals = ibnormals;
		buvs = ibuvs;
		vertlength = 3;
		if(bnormals)
			vertlength+=3;
		if(buvs)
			vertlength+=2;
		ByteBuffer vbb = ByteBuffer.allocateDirect(nverts*vertlength*4);
		vbb.order(ByteOrder.nativeOrder());
		FloatBuffer fvbuff = vbb.asFloatBuffer();
		fvbuff.put(iverts,0,nverts*vertlength);
		fvbuff.position(0);
		
		GLES20.glGenBuffers(1, glbuffers,0);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,glbuffers[0]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, nverts*vertlength*4, fvbuff, GLES20.GL_STATIC_DRAW);
		if(bindices)
		{
			ByteBuffer ibb = ByteBuffer.allocateDirect(ninds*2);
			ibb.order(ByteOrder.nativeOrder());
			ShortBuffer sibuff = ibb.asShortBuffer();
			sibuff.put(iinds,0,ninds);
			sibuff.position(0);
			
			GLES20.glGenBuffers(1,glbuffers,1);
			GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, glbuffers[1]);
			GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, ninds*2, sibuff, GLES20.GL_STATIC_DRAW);
		}
	}
	
	public boolean Render(int iprogram)
	{
		if(glbuffers[0]==0||glbuffers[1]==0)
			return false;
		int voff = 0;
		int svnorm=0,svtexuv=0;
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, glbuffers[0]);
		int spos = GLES20.glGetAttribLocation(iprogram, "vertpos");
		GLES20.glEnableVertexAttribArray(spos);
		GLES20.glVertexAttribPointer(spos,3,GLES20.GL_FLOAT,false,4*vertlength,voff);
		voff=12;
		if(bnormals)
		{
			svnorm = GLES20.glGetAttribLocation(iprogram, "vertnorm");
			GLES20.glEnableVertexAttribArray(svnorm);
			GLES20.glVertexAttribPointer(svnorm,3,GLES20.GL_FLOAT,false,4*vertlength,voff);
			voff+=12;
		}
		if(buvs)
		{
			svtexuv = GLES20.glGetAttribLocation(iprogram, "vertuv");
			GLES20.glEnableVertexAttribArray(svtexuv);
			GLES20.glVertexAttribPointer(svtexuv,2,GLES20.GL_FLOAT,false,4*vertlength,voff);
			voff+=8;
		}
		
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, glbuffers[1]);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, ninds, GLES20.GL_UNSIGNED_SHORT, 0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
		if(buvs)
			GLES20.glDisableVertexAttribArray(svtexuv);
		if(bnormals)
			GLES20.glDisableVertexAttribArray(svnorm);
		GLES20.glDisableVertexAttribArray(spos);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
		return true;
	}
};
