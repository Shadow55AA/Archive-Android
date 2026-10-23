package com.lotech.tubesurfer;

public class PhysicsObject
{
	public float pos[] = {0,0,0};
	public float rot[] = {0,0,0,1};
	public float linvel[] = {0,0,0};
	public float angvel[] = {0,0,0};
	public float mass = -1;
	public float inertia[] = {1,0,0,
							  0,1,0,
							  0,0,1};
	public float invinertia[] = {1,0,0,
								 0,1,0,
								 0,0,1};
	protected boolean bgencontacts = false;
	public CollisionObject cobj=null;
	public float contactbounce=0.4f;
	public float contactfriction=0.3f;
	public float contactspringstr=1000;
	
	public void SetInertia(float iinertia[])
	{
		for(int i=0;i<9;i++)
			inertia[i]=iinertia[i];
		LAMatrix.InvMat3(invinertia, inertia);
	}
	
	public void UpdateVelocity(float dt)
	{
		
	}
	
	public void UpdatePosition(float dt)
	{
		for(int i=0;i<3;i++)
			pos[i]+=linvel[i]*dt;
	}
	
	public boolean Collide(PhysicsObject objb,PhysicsContactList icontactlist)
	{
		if(objb!=null&&(cobj!=null&&objb.cobj!=null))
		{
			if(bgencontacts&&objb.bgencontacts)
			{
				int prevncs = icontactlist.GetNContacts();
				if(cobj.Collide(objb.cobj, icontactlist))
				{
					int ncs = icontactlist.GetNContacts();
					for(int i=prevncs;i<ncs;i++)
					{
						PhysicsContact cp = icontactlist.GetContact(i);
						cp.obja = this;
						cp.objb = objb;
					}
					return true;
				}
				return false;
			}
			else
				return cobj.Collide(objb.cobj,null);
		}
		return false;
	}

}
