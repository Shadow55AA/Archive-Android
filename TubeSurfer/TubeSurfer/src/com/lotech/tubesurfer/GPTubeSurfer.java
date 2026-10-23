package com.lotech.tubesurfer;

import android.view.MotionEvent;

public class GPTubeSurfer extends GamePlayer
{
	private float psize[] = {2,6};
	private float angle;
	private PhysicsObject poleftfoot;
	private PhysicsObject porightfoot;
	private PhysicsObject potrack;
	
	GPTubeSurfer()
	{
		pobj = new PhysicsObject();
		pobj.cobj = new CollisionObject();
		CollisionHull chull = new CollisionHull();
		float vec[] = {0.5f,1,0,0};
		float mat[] = {1,0,0, 0,1,0, 0,0,1};
		chull.MakeCapsule(vec);
		pobj.mass = 10;
		Physics.CalcInertiaCapsule(mat, vec, 10);
		pobj.SetInertia(mat);
		pobj.cobj.AddHull(chull);
		pobj.bgencontacts = true;
		pobj.pos[1]=4f;
		
		poleftfoot = new PhysicsObject();
		poleftfoot.cobj = new CollisionObject();
		chull = new CollisionHull();
		chull.MakeSphere(0.25f);
		poleftfoot.mass = 10;
		Physics.CalcInertiaSphere(mat, 0.25f, 10);
		poleftfoot.SetInertia(mat);
		poleftfoot.cobj.AddHull(chull);
		poleftfoot.bgencontacts = true;
		poleftfoot.pos[0]=-1f;
		poleftfoot.pos[1]=3.5f;
		poleftfoot.pos[2]=-0.75f;
		
		porightfoot = new PhysicsObject();
		porightfoot.cobj = new CollisionObject();
		chull = new CollisionHull();
		chull.MakeSphere(0.25f);
		porightfoot.mass = 10;
		Physics.CalcInertiaSphere(mat, 0.25f, 10);
		porightfoot.SetInertia(mat);
		porightfoot.cobj.AddHull(chull);
		porightfoot.bgencontacts = true;
		porightfoot.pos[0]=1f;
		porightfoot.pos[1]=3.5f;
		porightfoot.pos[2]=-0.75f;
		
		potrack = new PhysicsObject();
		potrack.cobj = new CollisionObject();
		chull = new CollisionHull();
		chull.MakeSphere(0.25f);
		potrack.mass = 10;
		Physics.CalcInertiaSphere(mat, 0.25f, 10);
		potrack.SetInertia(mat);
		potrack.cobj.AddHull(chull);
		potrack.bgencontacts = true;
		potrack.pos[0]=0f;
		potrack.pos[1]=3.5f;
		potrack.pos[2]=0.75f;
		
		
		pos[1]=pobj.pos[1];
		vpos[1]=6f;
		angle = 0;
	}
	
	@Override
	public void onCollide(GameObject objb)
	{
	}

	@Override
	public boolean Collide(GameObject objb,PhysicsContactList icontactlist)
	{
		boolean bhit = false;
		if(objb!=null&&(pobj!=null&&objb.pobj!=null))
		{
			if(pobj.Collide(objb.pobj, icontactlist))
				bhit = true;
		}
		if(objb!=null&&(poleftfoot!=null&&objb.pobj!=null))
		{
			if(poleftfoot.Collide(objb.pobj, icontactlist))
				bhit = true;
		}
		return bhit;
	}
	
	@Override
	public void Render(GameRenderer irenderer)
	{
		irenderer.SetDrawPos(pos);
		irenderer.SetDrawRot(rot);
		irenderer.SetMaterial(irenderer.GetDefaultMaterial("white"));
		irenderer.SetTexture(irenderer.GetDefaultTexture("fractalnoise"));
		if(robj!=null)
		{
			robj.SetRenderBuffer(0);
			irenderer.DrawObject(robj);
			robj.SetRenderBuffer(1);
			irenderer.SetDrawPos(poleftfoot.pos);
			irenderer.DrawObject(robj);
			irenderer.SetDrawPos(porightfoot.pos);
			irenderer.DrawObject(robj);
			irenderer.SetDrawPos(potrack.pos);
			irenderer.DrawObject(robj);
		}
		//irenderer.DrawPlane(psize);
	}

	@Override
	public void setView(GameRenderer irenderer)
	{
		irenderer.SetViewPos(vpos);
		irenderer.SetViewRot(vrot);
	}

	@Override
	public void physicsStep(float dt)
	{	
		poleftfoot.linvel[1]+=-9.81f*dt;
		//poleftfoot.UpdatePosition(dt);
		pobj.linvel[1]+=-9.81f*dt;
		//pobj.UpdatePosition(dt);
		for(int i=0;i<3;i++)
			pos[i]=pobj.pos[i];
		//float vdir[] = {(float)Math.cos(angle+(float)Math.PI/2.0f),0,(float)Math.sin(angle+(float)Math.PI/2.0f)};
		//pos[0]+=-10.0f*vdir[0]*dt;
		//pos[1]+=linvel[1]*dt;
		//pos[2]+=-10.0f*vdir[2]*dt;
		CollisionHull chull = pobj.cobj.GetHull(0);
		chull.SetPos(pos);
		chull = poleftfoot.cobj.GetHull(0);
		chull.SetPos(poleftfoot.pos);
	}

	@Override
	public void updateView()
	{
		float vdir[] = {(float)Math.cos(angle+(float)Math.PI/2.0f),0,(float)Math.sin(angle+(float)Math.PI/2.0f)};
		vpos[0] = 5f*vdir[0]+pos[0];
		vpos[1] = pos[1];
		vpos[2] = 5f*vdir[2]+pos[2];
	}

	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		return false;
	}

	@Override
	public void TiltUpdate(float[] itilt)
	{
		float m = LAVector.Vec3Magnitude(itilt);
		float da = 0;
		float vec[] = {0,0,0,1};
		if(m>0)
		{
			da = itilt[1]/m;
		}
		if(Math.abs(da)>0.1)
		{
			//angle+=0.1*da;
		}
		Quaternion.QRot(rot, 0, 1, 0, angle);
		Quaternion.QRot(vrot, 0, 1, 0, angle);
		//pos[0]=4*(float)Math.cos(angle-(float)Math.PI/2.0f);
		//pos[1]=4*(float)Math.sin(angle-(float)Math.PI/2.0f);
		//pos[0]=itilt[0];
		//pos[1]=itilt[1]-4;
		//pos[2]=itilt[2];
	}
}
