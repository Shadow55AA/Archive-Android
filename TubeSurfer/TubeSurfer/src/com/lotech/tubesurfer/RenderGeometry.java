package com.lotech.tubesurfer;

public abstract class RenderGeometry
{
	public abstract int GetNVerts();
	public abstract int GetNInds();
	public abstract boolean GenVerts(float ivertlist[],int ivertoffset,short iindlist[],int iindoffset,
		boolean bindices,boolean bnormals,boolean buvs);
}
