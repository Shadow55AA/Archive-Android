package com.lotech.r3;

public class Quaternion
{
	static void QRot(float q[],float x,float y,float z,float angle)
	{
		q[0]=x*(float)Math.sin(angle/2);
		q[1]=y*(float)Math.sin(angle/2);
		q[2]=z*(float)Math.sin(angle/2);
	    q[3]=(float)Math.cos(angle/2);
	}
	
	static void QtoMatrix(float q[],float mat[])
	{
		float x=q[0],y=q[1],z=q[2],n=q[3];
		mat[0]=1-2*y*y-2*z*z;
		mat[1]=2*x*y-2*z*n;
		mat[2]=2*x*z+2*y*n;
		mat[3]=2*x*y+2*z*n;
		mat[4]=1-2*x*x-2*z*z;
		mat[5]=2*y*z-2*x*n;
		mat[6]=2*x*z-2*y*n;
		mat[7]=2*y*z+2*x*n;
		mat[8]=1-2*x*x-2*y*y;
	}
	
	static void QmulQ(float oq[],float lq[],float rq[])
	{
		float tq[] = {0,0,0,0};
		tq[0]=lq[3]*rq[0]+lq[0]*rq[3]+lq[1]*rq[2]-lq[2]*rq[1];
		tq[1]=lq[3]*rq[1]+lq[1]*rq[3]+lq[2]*rq[0]-lq[0]*rq[2];
		tq[2]=lq[3]*rq[2]+lq[2]*rq[3]+lq[0]*rq[1]-lq[1]*rq[0];
		tq[3]=lq[3]*rq[3]-lq[0]*rq[0]-lq[1]*rq[1]-lq[2]*rq[2];
		oq[0]=tq[0];
		oq[1]=tq[1];
		oq[2]=tq[2];
		oq[3]=tq[3];
	}
	
	static void QConj(float oq[],float iq[])
	{
		oq[0]=-iq[0];
		oq[1]=-iq[1];
		oq[2]=-iq[2];
		oq[3]=iq[3];
	}
	
	static void QVRotate(float ov[],float iq[],float iv[])
	{
		float tv[] = {iv[0],iv[1],iv[2],0};
		float cq[] = {0,0,0,1};
		QConj(cq,iq);
		QmulQ(tv,iq,tv);
		QmulQ(tv,tv,cq);
		for(int i=0;i<3;i++)
			ov[i]=tv[i];
	}
}
