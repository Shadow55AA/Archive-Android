package com.lotech.r3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class GLTexture
{
	private int texture[] = {0};
	Bitmap resbmp = null;
	
	public boolean Set(int iprogram)
	{
		if(texture[0]==0)
			return false;
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		//GLES20.glEnable(GLES20.GL_TEXTURE_2D);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0]);
		int sloc = GLES20.glGetUniformLocation(iprogram, "difftex");
		GLES20.glUniform1i(sloc, 0);
		return true;
	}
	
	public void LoadTextureResource(Resources ires,int texresid)
	{
		BitmapFactory.Options bfopts = new BitmapFactory.Options();
		bfopts.inScaled = false;
		Bitmap tmpbmp = BitmapFactory.decodeResource(ires, texresid, bfopts);
		Matrix tm = new Matrix();
		tm.setScale(1, -1);
		resbmp = Bitmap.createBitmap(tmpbmp,0,0,tmpbmp.getWidth(),tmpbmp.getHeight(),tm,false);
	}
	
	public void GenTexture()
	{
		GLES20.glGenTextures(1, texture,0);
		if(texture[0]!=0)
		{
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0]);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_NEAREST);
			GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR_MIPMAP_NEAREST);
			//GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
			//GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
			
			ByteBuffer bb = ByteBuffer.allocateDirect(64*64*3);
			bb.order(ByteOrder.nativeOrder());
			for(int i=0;i<64*64*3;i++)
			{
				bb.put((byte) 0xff);
			}
			bb.position(0);
			if(resbmp!=null)
				GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, resbmp, 0);
			else
				GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGB, 64, 64, 0, GLES20.GL_RGB, GLES20.GL_UNSIGNED_BYTE, bb);
			GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
		}
		resbmp = null;
	}
	
	public void Destroy()
	{
		if(texture[0]!=0)
			GLES20.glDeleteTextures(1, texture,0);
	}
}
