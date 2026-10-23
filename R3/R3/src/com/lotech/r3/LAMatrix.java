package com.lotech.r3;

public class LAMatrix
{
	public static void InvMat3(float omat[],float imat[])
	{
		float d = imat[0]*imat[4]*imat[8] -
				  imat[0]*imat[7]*imat[5] +
				  imat[3]*imat[7]*imat[2] -
				  imat[3]*imat[1]*imat[8] +
				  imat[6]*imat[1]*imat[5] -
				  imat[6]*imat[4]*imat[2];
		if(d==0)
			d=1;
		omat[0]=(imat[4]*imat[8]-imat[5]*imat[7])/d;
		omat[1]=-(imat[1]*imat[8]-imat[2]*imat[7])/d;
		omat[2]=(imat[1]*imat[5]-imat[2]*imat[4])/d;
		omat[3]=-(imat[3]*imat[8]-imat[5]*imat[6])/d;
		omat[4]=(imat[0]*imat[8]-imat[2]*imat[6])/d;
		omat[5]=-(imat[0]*imat[5]-imat[2]*imat[3])/d;
		omat[6]=(imat[3]*imat[7]-imat[4]*imat[6])/d;
		omat[7]=-(imat[0]*imat[7]-imat[1]*imat[6])/d;
		omat[8]=(imat[0]*imat[4]-imat[1]*imat[3])/d;
	}
	
	public static void Transpose3(float omat[],float imat[])
	{
		float mat[] = {1,0,0, 0,1,0, 0,0,1};
		mat[0]=imat[3];
		mat[1]=imat[1];
		mat[2]=imat[6];
		mat[3]=imat[1];
		mat[4]=imat[4];
		mat[5]=imat[7];
		mat[6]=imat[2];
		mat[7]=imat[5];
		mat[8]=imat[8];
		for(int i=0;i<9;i++)
			omat[i]=mat[i];
	}
	
	public static void VmulM3(float ov[],float va[],float matb[])
	{
		float v[] = {0,0,0};
		v[0] = va[0]*matb[0]+va[1]*matb[3]+va[2]*matb[6];
		v[1] = va[0]*matb[1]+va[1]*matb[4]+va[2]*matb[7];
		v[2] = va[0]*matb[2]+va[1]*matb[5]+va[2]*matb[8];
		ov[0]=v[0];
		ov[1]=v[1];
		ov[2]=v[2];
	}
	
	public static void MmulM3(float omat[],float mata[],float matb[])
	{
		float mat[] ={0,0,0, 0,0,0, 0,0,0};
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				mat[j+i*3]=0;
				for(int k=0;k<3;k++)
				{
					mat[j+i*3]+=mata[k+i*3]*matb[j+k*3];
				}
			}
		}
		for(int i=0;i<9;i++)
			omat[i]=mat[i];
	}
	
	public static void MMRotate3(float omat[],float imat[], float rot[])
	{
		float tmat[] = {0,0,0, 0,0,0, 0,0,0};
		float trot[] = {0,0,0, 0,0,0, 0,0,0};
		MmulM3(tmat,rot,imat);
		Transpose3(trot,rot);
		MmulM3(tmat,tmat,trot);
		for(int i=0;i<9;i++)
			omat[i]=tmat[i];
	}
}
