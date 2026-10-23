package com.lotech.r3;

public class RGTube extends RenderGeometry
{
	private float pos[] = {0,0,0};
	private float rot[] = {0,0,0,1};
	private float radii[] = {1,2};
	private float height = 1;
	private int rsegs = 8;
	private int hsegs = 1;
	private boolean bflat = true;
	
	public void SetPos(float ipos[])
	{
		for(int i=0;i<3;i++)
			pos[i]=ipos[i];
	}
	
	public void SetRot(float irot[])
	{
		for(int i=0;i<4;i++)
			rot[i]=irot[i];
	}
	
	public void SetRadii(float iradii[])
	{
		for(int i=0;i<2;i++)
			radii[i]=iradii[i];
	}
	
	public void SetHeight(float iheight)
	{
		height = iheight;
	}
	
	public void SetRSegs(int irsegs)
	{
		rsegs = irsegs;
	}
	
	public void SetHSegs(int ihsegs)
	{
		hsegs=ihsegs;
	}
	
	public void SetBFlat(boolean ibflat)
	{
		bflat = ibflat;
	}
	
	@Override
	public int GetNVerts()
	{
		int nvs = (rsegs+1)*(hsegs+1);
		if(!bflat)
		{
			nvs += nvs+2*(rsegs+1)*(2);
		}
		return nvs;
	}

	@Override
	public int GetNInds()
	{
		int nis = 6*rsegs*hsegs;
		if(!bflat)
		{
			nis += nis+12*rsegs;
		}
		return nis;
	}

	@Override
	public boolean GenVerts(float[] ivertlist, int ivertoffset,
			short[] iindlist, int iindoffset, boolean bindices,
			boolean bnormals, boolean buvs)
	{
		int vlength = 3;
		if(bnormals)
			vlength+=3;
		if(buvs)
			vlength+=2;
		int tvo = ivertoffset*vlength;
		int toff = 0;
	    float tvec[] = {0,0,0,0};
	    float tnorm[] = {0,0,0,0};
	    float theta=0;
		for(int i=0;i<=rsegs;i++)
	    {
	        for(int j=0;j<=hsegs;j++)
	        {
	            toff = 0;
	            theta = 2*(float)Math.PI*i/(float)rsegs;
	            tnorm [0] = (float)Math.cos(theta);
	            tnorm [1] = (float)Math.sin(theta);
	            tnorm[2] = 0;
	            tvec[0] = radii[0]*tnorm[0];
	            tvec[1] = radii[0]*tnorm[1];
	            tvec[2] = height*j/(float)(hsegs)-height/2.0f;
	            Quaternion.QVRotate(tvec, rot, tvec);
	            tvec[0]+=pos[0];	tvec[1]+=pos[1];	tvec[2]+=pos[2];
	            ivertlist[tvo+(i+j*((int)rsegs+1))*vlength+toff]=tvec[0];
	            ivertlist[tvo+(i+j*((int)rsegs+1))*vlength+toff+1]=tvec[1];
	            ivertlist[tvo+(i+j*((int)rsegs+1))*vlength+toff+2]=tvec[2];
	            toff+=3;
	            if(bnormals)
	            {
		            Quaternion.QVRotate(tnorm, rot, tnorm);
	                ivertlist[tvo+(i+j*((int)rsegs+1))*vlength+toff] = -tnorm[0];
	                ivertlist[tvo+(i+j*((int)rsegs+1))*vlength+1+toff] = -tnorm[1];
	                ivertlist[tvo+(i+j*((int)rsegs+1))*vlength+2+toff] = -tnorm[2];
	                toff+=3;
	            }
	            if(buvs)
	            {
	                ivertlist[tvo+(i+j*((int)rsegs+1))*vlength+toff] = i/(float)(rsegs);
	                ivertlist[tvo+(i+j*((int)rsegs+1))*vlength+1+toff] = j/(float)(hsegs);
	                toff+=2;
	            }
	        }
	    }
	    if(bindices)
	    {
	        for(int i=0;i<rsegs;i++)
	        {
	            for(int j=0;j<hsegs;j++)
	            {
	                iindlist[iindoffset+6*(i+j*(int)rsegs)] = (short) (tvo/vlength+(i+j*((int)rsegs+1)));
	                iindlist[iindoffset+6*(i+j*(int)rsegs)+1] = (short) (tvo/vlength+(i+(j+1)*((int)rsegs+1)));
	                iindlist[iindoffset+6*(i+j*(int)rsegs)+2] = (short) (tvo/vlength+(i+1+j*((int)rsegs+1)));
	                iindlist[iindoffset+6*(i+j*(int)rsegs)+3] = (short) (tvo/vlength+(i+1+j*((int)rsegs+1)));
	                iindlist[iindoffset+6*(i+j*(int)rsegs)+4] = (short) (tvo/vlength+(i+(j+1)*((int)rsegs+1)));
	                iindlist[iindoffset+6*(i+j*(int)rsegs)+5] = (short) (tvo/vlength+(i+1+(j+1)*((int)rsegs+1)));
	            }
	        }
	    }
		return true;
	}
}
