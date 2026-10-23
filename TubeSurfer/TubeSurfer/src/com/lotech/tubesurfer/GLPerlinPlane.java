package com.lotech.tubesurfer;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class GLPerlinPlane
{
	private FloatBuffer vertbuffer;
	private ShortBuffer indbuffer;
	private int vertlength = 8;
	
	private float verts[];
	private short inds[];
	
	private int segs[] = {10,10};
	private int vbuff[] = {0,0};
	
	GLPerlinPlane()
	{
		verts = new float[(segs[0]+1)*(segs[1]+1)*vertlength];
		inds = new short[segs[0]*segs[1]];
	}
}
