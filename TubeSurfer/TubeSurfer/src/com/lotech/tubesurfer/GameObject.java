package com.lotech.tubesurfer;

public abstract class GameObject
{
	protected float pos[] = {0,0,0,0};
	protected float rot[] = {0,0,0,1};
	protected float linvel[] = {0,0,0,0};
	protected float angvel[] = {0,0,0,0};
	protected RenderObject robj = null;
	protected PhysicsObject pobj = null;
	protected float mass = -1;
	
	public void SetPos(float ipos[])
	{
		for(int i=0;i<3;i++)
			pos[i]=ipos[i];
	}
	
	public float GetPos(int coord)
	{
		if(coord<0||coord>2)
			return 0;
		return pos[coord];
	}
	
	public void SetRot(float irot[])
	{
		for(int i=0;i<4;i++)
			rot[i]=irot[i];
	}
	
	public float GetLinVel(int coord)
	{
		if(coord<0||coord>2)
			return 0;
		return linvel[coord];
	}
	
	public void SetLinVel(float ilinvel[])
	{
		for(int i=0;i<3;i++)
			linvel[i]=ilinvel[i];
	}
	
	public void SetRenderObject(RenderObject irobj)
	{
		robj = irobj;
	}
	
	public abstract void Render(GameRenderer irenderer);
	
	public void onCollide(GameObject objb)
	{
		
	}
	
	public boolean Collide(GameObject objb,PhysicsContactList icontactlist)
	{
		if(objb!=null&&(pobj!=null&&objb.pobj!=null))
		{
			return pobj.Collide(objb.pobj, icontactlist);
		}
		return false;
	}
}
