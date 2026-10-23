package com.lotech.r3;

public class CollisionObject
{
	private int nhulls=0,maxnhulls=10;
	private CollisionHull[] hulls;
	
	CollisionObject()
	{
		hulls = new CollisionHull[maxnhulls];
	}
	
	public void AddHull(CollisionHull ihull)
	{
		if(nhulls<maxnhulls)
			hulls[nhulls++]=ihull;
	}
	
	public int GetNHulls()
	{
		return nhulls;
	}
	
	public CollisionHull GetHull(int ind)
	{
		if(ind<0||ind>=nhulls)
			return null;
		return hulls[ind];
	}
	
	public boolean Collide(CollisionObject cobjb,PhysicsContactList icontactlist)
	{
		boolean bhit = false;
		for(int i=0;i<nhulls;i++)
		{
			for(int j=0;j<cobjb.nhulls;j++)
			{
				if(hulls[i].Collide(cobjb.hulls[j], icontactlist))
					bhit = true;
			}
		}
		return bhit;
	}
}
