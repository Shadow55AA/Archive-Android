package com.lotech.r3;

public class LAVector
{
	static float Vec3Magnitude(float vec[])
	{
		return (float)Math.sqrt(vec[0]*vec[0]+vec[1]*vec[1]+vec[2]*vec[2]);
	}
	
	static void Vec3Normalize(float vec[])
	{
		float m = vec[0]*vec[0]+vec[1]*vec[1]+vec[2]*vec[2];
		if(m==0)
			m=1;
		m = (float)Math.sqrt(m);
		vec[0]/=m;	vec[1]/=m;	vec[2]/=m;
	}
	
	static float Vec3Dot(float va[],float vb[])
	{
		return va[0]*vb[0]+va[1]*vb[1]+va[2]*vb[2];
	}
	
	static void Vec3Cross(float ov[],float va[],float vb[])
	{
		float v[] = {0,0,0};
		v[0]=va[1]*vb[2]-va[2]*vb[1];
		v[1]=va[2]*vb[0]-va[0]*vb[2];
		v[2]=va[0]*vb[1]-va[1]*vb[0];
		ov[0]=v[0];
		ov[1]=v[1];
		ov[2]=v[2];
	}
}
