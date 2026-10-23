package com.losoft.ratican.render;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import android.opengl.GLES20;

import com.losoft.ratican.engine.GameRenderer;

public class RenderObject
{
	private FloatBuffer vertbuffer,normbuffer,uvbuffer;
	private ShortBuffer indbuffer;
	private ArrayList<RenderElement> elements;
	
	private int nverts = 0,ninds = 0;
	private int glbuffs[] = {0,0};
	private boolean binds = false,bnorms = false,buvs = false,bstatic = false;
	
	public RenderObject()
	{
		elements = new ArrayList<RenderElement>();
	}
	
	public void setFlagStatic(boolean ibstatic)
	{
		bstatic = ibstatic;
	}
	
	public void setFlagNorms(boolean ibnorms)
	{
		bnorms = ibnorms;
	}
	
	public void setFlagUvs(boolean ibuvs)
	{
		buvs = ibuvs;
	}
	
	public void setFlagInds(boolean ibinds)
	{
		binds = ibinds;
	}
	
	public void setNVerts(int inverts)
	{
		nverts = inverts;
	}
	
	public void setVBuff(int ivbuff)
	{
		glbuffs[0]=ivbuff;
	}
	
	public void setNInds(int ininds)
	{
		ninds = ininds;
	}
	
	public void setIBuff(int iibuff)
	{
		glbuffs[1]=iibuff;
	}
	
	public void render(int iprogram)
	{
		if(glbuffs[0]==0)
			return;
		int vertlength = 3;
		if(bnorms)
			vertlength += 3;
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, glbuffs[0]);
		int spos = GLES20.glGetAttribLocation(iprogram, "vertpos");
		GLES20.glEnableVertexAttribArray(spos);
		GLES20.glVertexAttribPointer(spos,3,GLES20.GL_FLOAT,false,4*vertlength,0);
		int svnorm = GLES20.glGetAttribLocation(iprogram, "vertnorm");
		if(bnorms)
		{
			GLES20.glEnableVertexAttribArray(svnorm);
			GLES20.glVertexAttribPointer(svnorm,3,GLES20.GL_FLOAT,false,4*vertlength,12);
		}
		int svtexuv = GLES20.glGetAttribLocation(iprogram, "vertuv");
		if(buvs)
		{
			GLES20.glEnableVertexAttribArray(svtexuv);
			GLES20.glVertexAttribPointer(svtexuv,2,GLES20.GL_FLOAT,false,4*vertlength,24); // <- not fully correct
		}
		//int scolor = GLES20.glGetUniformLocation(iprogram, "vColor");
		//GLES20.glUniform4fv(scolor, 1, color,0);

		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, glbuffs[1]);
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, ninds, GLES20.GL_UNSIGNED_SHORT, 0);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
		if(buvs)
			GLES20.glDisableVertexAttribArray(svtexuv);
		if(bnorms)
			GLES20.glDisableVertexAttribArray(svnorm);
		GLES20.glDisableVertexAttribArray(spos);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
	}

}
