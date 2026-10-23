package com.lotech.tubesurfer;

// types: 0 Sphere, 1 Plane, 2 Box, 3 Capsule

public class CollisionHull
{
	private float pos[] = {0,0,0};
	private float rot[] = {0,0,0,1};
	private int type;
	private float params[] = {0,0,0,0,0,0,0,0};
	
	public void SetPos(float ipos[])
	{
		for(int i=0;i<3;i++)
			pos[i]=ipos[i];
	}
	
	public void GetPos(float opos[])
	{
		for(int i=0;i<3;i++)
			opos[i]=pos[i];
	}
	
	public void SetRot(float irot[])
	{
		for(int i=0;i<3;i++)
			rot[i]=irot[i];
	}
	
	public void GetRot(float orot[])
	{
		for(int i=0;i<3;i++)
			orot[i]=rot[i];
	}
	
	public void MakeSphere(float iradius)
	{
		type = 0;
		params[0]=iradius;
	}
	
	public void MakePlane(float iplanefunc[])
	{
		type = 1;
		for(int i=0;i<4;i++)
			params[i]=iplanefunc[i];
	}
	
	public void MakeBox(float isize[])
	{
		type = 2;
		for(int i=0;i<3;i++)
			params[i]=isize[i];
	}
	
	public void MakeCapsule(float isize[])
	{
		type = 3;
		params[0]=isize[0];
		params[1]=isize[1];
	}
	
	public boolean Collide(CollisionHull hullb,PhysicsContactList icontactlist)
	{
		switch(type)
		{
		case(0):	// Sphere
			if(hullb.type==0)	// vs Sphere
			{
				return CollideFuncs.CollideSphereSphere(pos, params[0], hullb.pos, hullb.params[0],icontactlist);
			}
			if(hullb.type==1)	// vs Plane
			{
				return CollideFuncs.CollideSpherePlane(pos, params[0], hullb.params,icontactlist);
			}
			break;
		case(3):	// Capsule
			if(hullb.type==1)	// vs plane
			{
				return CollideFuncs.CollideCapsulePlane(pos, rot, params, hullb.params, icontactlist);
			}
			break;
		}
		return false;
	}
}
