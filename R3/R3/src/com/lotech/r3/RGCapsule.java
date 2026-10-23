package com.lotech.r3;

public class RGCapsule extends RenderGeometry
{
	float pos[] = {0,0,0};
	float rot[] = {0,0,0,1};
	float size[] = {1,1};
	int segs[] = {8,2,1};
	float uvscale[] = {1,1};

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
	
	public void SetSize(float isize[])
	{
		for(int i=0;i<2;i++)
			size[i]=isize[i];
	}
	
	public void SetSegs(int isegs[])
	{
		for(int i=0;i<3;i++)
			segs[i]=isegs[i];
	}
	
	public void SetUVScale(float iuvscale[])
	{
		for(int i=0;i<2;i++)
			uvscale[i]=iuvscale[i];
	}
	
	@Override
	public int GetNVerts()
	{
		return (segs[0]+1)*(2*segs[1]+segs[2]+1);
	}

	@Override
	public int GetNInds()
	{
		return 6*segs[0]*(2*segs[1]+segs[2]);
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
	    float tnorm[] = {0,0,1};
	    float theta,phi;
	    float totheight = 2*size[0]+size[1];
		for(int i=0;i<=segs[0];i++)
	    {
	        for(int j=0;j<=segs[1];j++)
	        {
	            toff = 0;
	            theta = 2*(float)Math.PI*i/(float)segs[0];
	            phi = (float)Math.PI-0.5f*(float)Math.PI*j/(float)segs[1];
	            tnorm [0] = (float)Math.cos(theta)*(float)Math.sin(phi);
	            tnorm [1] = (float)Math.sin(theta)*(float)Math.sin(phi);
	            tnorm[2] = (float)Math.cos(phi);
	            tvec[0] = size[0]*tnorm[0];
	            tvec[1] = size[0]*tnorm[1];
	            tvec[2] = size[0]*tnorm[2]-size[1]/2.0f;
	            Quaternion.QVRotate(tvec, rot, tvec);
	            tvec[0]+=pos[0];	tvec[1]+=pos[1];	tvec[2]+=pos[2];
	            ivertlist[tvo+(i+j*((int)segs[0]+1))*vlength+toff]=tvec[0];
	            ivertlist[tvo+(i+j*((int)segs[0]+1))*vlength+toff+1]=tvec[1];
	            ivertlist[tvo+(i+j*((int)segs[0]+1))*vlength+toff+2]=tvec[2];
	            toff+=3;
	            if(bnormals)
	            {
	                ivertlist[tvo+(i+j*((int)segs[0]+1))*vlength+toff] = tnorm[0];
	                ivertlist[tvo+(i+j*((int)segs[0]+1))*vlength+1+toff] = tnorm[1];
	                ivertlist[tvo+(i+j*((int)segs[0]+1))*vlength+2+toff] = tnorm[2];
	                toff+=3;
	            }
	            if(buvs)
	            {
	                ivertlist[tvo+(i+j*((int)segs[0]+1))*vlength+toff] = uvscale[0]*i/(float)(segs[0]);
	                ivertlist[tvo+(i+j*((int)segs[0]+1))*vlength+1+toff] = uvscale[1]*(j/(float)(segs[1]))*(size[0]/totheight);
	                toff+=2;
	            }
	        }
	    }
		tvo += (segs[0]+1)*(segs[1]+1)*vlength;
		for(int i=0;i<=segs[0];i++)
	    {
	        for(int j=0;j<segs[2];j++)
	        {
	            toff = 0;
	            theta = 2*(float)Math.PI*i/(float)segs[0];
	            tnorm [0] = (float)Math.cos(theta);
	            tnorm [1] = (float)Math.sin(theta);
	            tnorm[2] = 0;
	            tvec[0] = size[0]*tnorm[0];
	            tvec[1] = size[0]*tnorm[1];
	            tvec[2] = size[1]*(j+1)/(float)(segs[2])-size[1]/2.0f;
	            Quaternion.QVRotate(tvec, rot, tvec);
	            tvec[0]+=pos[0];	tvec[1]+=pos[1];	tvec[2]+=pos[2];
	            ivertlist[tvo+(i+j*(segs[0]+1))*vlength+toff]=tvec[0];
	            ivertlist[tvo+(i+j*(segs[0]+1))*vlength+toff+1]=tvec[1];
	            ivertlist[tvo+(i+j*(segs[0]+1))*vlength+toff+2]=tvec[2];
	            toff+=3;
	            if(bnormals)
	            {
		            Quaternion.QVRotate(tnorm, rot, tnorm);
	                ivertlist[tvo+(i+j*(segs[0]+1))*vlength+toff] = tnorm[0];
	                ivertlist[tvo+(i+j*(segs[0]+1))*vlength+1+toff] = tnorm[1];
	                ivertlist[tvo+(i+j*(segs[0]+1))*vlength+2+toff] = tnorm[2];
	                toff+=3;
	            }
	            if(buvs)
	            {
	                ivertlist[tvo+(i+j*(segs[0]+1))*vlength+toff] = uvscale[0]*i/(float)(segs[0]);
	                ivertlist[tvo+(i+j*(segs[0]+1))*vlength+1+toff] = uvscale[1]*(((j+1)/(float)(segs[2]))*(size[1]/totheight)+size[0]/totheight);
	                toff+=2;
	            }
	        }
	    }
		tvo += (segs[0]+1)*(segs[2])*vlength;
		for(int i=0;i<=segs[0];i++)
	    {
	        for(int j=0;j<segs[1];j++)
	        {
	            toff = 0;
	            theta = 2*(float)Math.PI*i/(float)segs[0];
	            phi = (float)Math.PI/2.0f-0.5f*(float)Math.PI*(j+1)/(float)segs[1];
	            tnorm [0] = (float)Math.cos(theta)*(float)Math.sin(phi);
	            tnorm [1] = (float)Math.sin(theta)*(float)Math.sin(phi);
	            tnorm[2] = (float)Math.cos(phi);
	            tvec[0] = size[0]*tnorm[0];
	            tvec[1] = size[0]*tnorm[1];
	            tvec[2] = size[0]*tnorm[2]+size[1]/2.0f;
	            Quaternion.QVRotate(tvec, rot, tvec);
	            tvec[0]+=pos[0];	tvec[1]+=pos[1];	tvec[2]+=pos[2];
	            ivertlist[tvo+(i+j*((int)segs[0]+1))*vlength+toff]=tvec[0];
	            ivertlist[tvo+(i+j*((int)segs[0]+1))*vlength+toff+1]=tvec[1];
	            ivertlist[tvo+(i+j*((int)segs[0]+1))*vlength+toff+2]=tvec[2];
	            toff+=3;
	            if(bnormals)
	            {
	                ivertlist[tvo+(i+j*((int)segs[0]+1))*vlength+toff] = tnorm[0];
	                ivertlist[tvo+(i+j*((int)segs[0]+1))*vlength+1+toff] = tnorm[1];
	                ivertlist[tvo+(i+j*((int)segs[0]+1))*vlength+2+toff] = tnorm[2];
	                toff+=3;
	            }
	            if(buvs)
	            {
	                ivertlist[tvo+(i+j*((int)segs[0]+1))*vlength+toff] = uvscale[0]*i/(float)(segs[0]);
	                ivertlist[tvo+(i+j*((int)segs[0]+1))*vlength+1+toff] = uvscale[1]*(((j+1)/(float)(segs[1]))*(size[0]/totheight)+(size[0]+size[1])/totheight);
	                toff+=2;
	            }
	        }
	    }
		tvo = ivertoffset*vlength;
	    if(bindices)
	    {
	        for(int i=0;i<segs[0];i++)
	        {
	            for(int j=0;j<2*segs[1]+segs[2];j++)
	            {
	                iindlist[iindoffset+6*(i+j*(int)segs[0])] = (short) (tvo/vlength+(i+j*((int)segs[0]+1)));
	                iindlist[iindoffset+6*(i+j*(int)segs[0])+1] = (short) (tvo/vlength+(i+1+j*((int)segs[0]+1)));
	                iindlist[iindoffset+6*(i+j*(int)segs[0])+2] = (short) (tvo/vlength+(i+(j+1)*((int)segs[0]+1)));
	                iindlist[iindoffset+6*(i+j*(int)segs[0])+3] = (short) (tvo/vlength+(i+1+j*((int)segs[0]+1)));
	                iindlist[iindoffset+6*(i+j*(int)segs[0])+4] = (short) (tvo/vlength+(i+1+(j+1)*((int)segs[0]+1)));
	                iindlist[iindoffset+6*(i+j*(int)segs[0])+5] = (short) (tvo/vlength+(i+(j+1)*((int)segs[0]+1)));
	            }
	        }
	    }
		return true;
	}

}
