package com.lotech.r3;

public class RGTriangle extends RenderGeometry
{
	private float verts[] = {0,0,0, 0,0,0, 0,0,0};
	private float norms[] = {0,0,0,	0,0,0, 0,0,0};
	private float uvs[] = {0,0, 0,0, 0,0};
	
	@Override
	public int GetNVerts()
	{
		return 3;
	}

	@Override
	public int GetNInds()
	{
		return 3;
	}

	@Override
	public boolean GenVerts(float ivertlist[],int ivertoffset,short iindlist[],int iindoffset,
		boolean bindices,boolean bnormals,boolean buvs)
	{
		int vlength = 3;
		if(bnormals)
			vlength+=3;
		if(buvs)
			vlength+=2;
		int tvo = ivertoffset*vlength;
		int toff = 0;
		for(int i=0;i<3;i++)
		{
			ivertlist[tvo+toff] = verts[i*3];
	        ivertlist[tvo+1+toff] = verts[i*3+1];
	        ivertlist[tvo+2+toff] = verts[i*3+2];
	        toff+=3;
	        if(bnormals)
	        {
	            ivertlist[tvo+toff] = norms[i*3];
	            ivertlist[tvo+1+toff] = norms[i*3+1];
	            ivertlist[tvo+2+toff] = norms[i*3+2];
	            toff+=3;
	        }
	        if(buvs)
	        {
	            ivertlist[tvo+toff] = uvs[i*2];
	            ivertlist[tvo+1+toff] = uvs[i*2+1];
	            toff+=2;
	        }
		}
		if(bindices)
		{
			iindlist[iindoffset] = (short) (tvo/vlength);
		    iindlist[iindoffset+1] = (short) (tvo/vlength+1);
		    iindlist[iindoffset+2] = (short) (tvo/vlength+2);
		}
		return true;
	}

}
