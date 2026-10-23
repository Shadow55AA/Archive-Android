package com.lotech.tubesurfer;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class RGConstructor
{
	private float verts[] = null;
	private short inds[] = null;
	private FloatBuffer vertbuffer;
	private ShortBuffer indbuffer;
	private int nverts = 0,ninds=0;
	private int maxnverts = 0,maxninds=0;
	private int maxvertbufflength;
	private boolean bindices,bnormals,buvs;
	
	public void Init(int imaxnverts,int imaxninds,
		boolean ibindices,boolean ibnormals,boolean ibuvs)
	{
		maxnverts = imaxnverts;
		maxninds = imaxninds;
		bindices = ibindices;
		bnormals = ibnormals;
		buvs = ibuvs;
		if(maxnverts<3)
			maxnverts = 3;
		if(maxninds<3)
			maxninds = 3;
		int vertlength = 3;
		if(bnormals)
			vertlength+=3;
		if(buvs)
			vertlength+=2;
		maxvertbufflength = maxnverts*vertlength;
		verts = new float[maxnverts*vertlength];
		inds = new short[maxninds];
		vertbuffer = FloatBuffer.allocate(maxnverts*vertlength);
		vertbuffer.position(0);
		indbuffer = ShortBuffer.allocate(maxninds);
		indbuffer.position(0);
	}
	
	public void Clear()
	{
		nverts = 0;
		ninds = 0;
	}
	
	public void SetParams(boolean ibindices,boolean ibnormals,boolean ibuvs)
	{
		bindices = ibindices;
		bnormals = ibnormals;
		buvs = ibuvs;
		int vertlength = 3;
		if(bnormals)
			vertlength+=3;
		if(buvs)
			vertlength+=2;
		maxnverts = maxvertbufflength/vertlength;
	}
	
	public boolean AddGeometry(RenderGeometry igeo)
	{
	    int tnv = igeo.GetNVerts();
	    int tni = igeo.GetNInds();
	    if(nverts+tnv>maxnverts ||(bindices && ninds+tni>maxninds))
	        return false;
	    igeo.GenVerts(verts,nverts,inds,ninds,bindices,bnormals,buvs);
	    nverts+=tnv;
	    if(bindices)
	        ninds+=tni;
	    return true;
	}
	
	public RenderBuffer GenerateBuffer()
	{
		if(nverts>0)
		{
			RenderBuffer rbuff = new RenderBuffer();
			rbuff.Generate(verts, nverts, inds, ninds, bindices, bnormals, buvs);
			return rbuff;
		}
		return null;
	}
}
