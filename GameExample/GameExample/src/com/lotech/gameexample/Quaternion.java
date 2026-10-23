package com.lotech.gameexample;

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
}
