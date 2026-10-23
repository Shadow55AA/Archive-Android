package com.lotech.tubesurfer;

public class CollideFuncs
{
	static public boolean CollideSphereSphere(float sposa[],float sradiusa,float sposb[],float sradiusb,PhysicsContactList icontactlist)
	{
		float vec[] = {0,0,0};
		for(int i=0;i<3;i++)
			vec[i] = sposa[i]-sposb[i];
	    double d = vec[0]*vec[0]+vec[1]*vec[1]+vec[2]*vec[2];
	    double r = sradiusa+sradiusb;
	    if(d<1.0||r<1.0)
	        d = Math.sqrt(d);
	    else
	        r = r*r;
	    if(d<r)
	    {
	    	if(icontactlist==null)
	            return true;
	        PhysicsContact cp = icontactlist.GetNewContact();
	        if(cp==null)
	        	return true;
	        for(int i=0;i<3;i++)
	        	cp.norm[i] = vec[i];
	        double dist = LAVector.Vec3Magnitude(vec);
	        if(dist==0)
	        	dist=1;
	        for(int i=0;i<3;i++)
	            cp.norm[i]/=dist;
	        for(int i=0;i<3;i++)
	        {
	        	cp.point[i] = sposa[i]+cp.norm[i]*sradiusa;
	        }
	        cp.pdepth = (float)dist-(sradiusa+sradiusb);
	        return true;
	    }
		return false;
	}
	
	static public boolean CollideSpherePlane(float spos[],float sradius,float planefunc[],PhysicsContactList icontactlist)
	{
		float projpos = spos[0]*planefunc[0]+spos[1]*planefunc[1]+spos[2]*planefunc[2]-planefunc[3];
		if(projpos<sradius)
		{
			if(icontactlist==null)
				return true;
			PhysicsContact cp = icontactlist.GetNewContact();
			if(cp==null)
				return true;
			for(int i=0;i<3;i++)
				cp.norm[i]=planefunc[i];
			for(int i=0;i<3;i++)
				cp.point[i] = spos[i]-sradius*planefunc[i];
			cp.pdepth = projpos-sradius;
			return true;
		}
		return false;
	}
	
	static public boolean CollideCapsulePlane(float cpos[],float crot[],float csize[],float planefunc[],PhysicsContactList icontactlist)
	{
		float p1[] = {0,0,-csize[1]/2.0f};
		float p2[] = {0,0,csize[1]/2.0f};
		Quaternion.QVRotate(p1, crot, p1);
		Quaternion.QVRotate(p2, crot, p2);
		for(int i=0;i<3;i++)
		{
			p1[i]+=cpos[i];
			p2[i]+=cpos[i];
		}
		float projp1 = LAVector.Vec3Dot(p1,planefunc)-planefunc[3];
		float projp2 = LAVector.Vec3Dot(p2,planefunc)-planefunc[3];
		if(projp1<projp2)
		{
			if(projp1<csize[0])
			{
				if(icontactlist==null)
					return true;
				PhysicsContact cp = icontactlist.GetNewContact();
				if(cp==null)
					return true;
				for(int i=0;i<3;i++)
					cp.norm[i]=planefunc[i];
				for(int i=0;i<3;i++)
					cp.point[i] = p1[i]-csize[i]*planefunc[i];
				cp.pdepth = projp1-csize[0];
				return true;
			}
		}
		else
		{
			if(projp2<csize[0])
			{
				if(icontactlist==null)
					return true;
				PhysicsContact cp = icontactlist.GetNewContact();
				if(cp==null)
					return true;
				for(int i=0;i<3;i++)
					cp.norm[i]=planefunc[i];
				for(int i=0;i<3;i++)
					cp.point[i] = p2[i]-csize[i]*planefunc[i];
				cp.pdepth = projp2-csize[0];
				return true;
			}
		}
		return false;
	}
}
