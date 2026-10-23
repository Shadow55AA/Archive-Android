package com.lotech.r3;

public class GOLevel1Track extends GOTubeTrack
{
	private class Node
	{
		public float pos[] = {0,0,0};
		public float angle = 0;
	}
	
	int curnode,ncount;
	Node nodes[];
	
	public GOLevel1Track()
	{
		pos[1] = 1;
		pos[2] = -30;
		pobj = new PhysicsObject();
		pobj.cobj = new CollisionObject();
		CollisionHull chull = new CollisionHull();
		chull.SetPos(pos);
		chull.MakeSphere(2);
		pobj.cobj.AddHull(chull);
		nodes = new Node[5];
		for(int i=0;i<5;i++)
			nodes[i] = new Node();
		nodes[0].pos[0]=0;
		nodes[0].pos[1]=0;
		nodes[0].pos[2]=-30;
		nodes[0].angle=-(float)Math.PI/2;
		for(int i=1;i<5;i++)
		{
			double da = Noise.Noisei(52666+i*97)*Math.PI/4;
			nodes[i].angle = nodes[i-1].angle+(float)da;
			nodes[i].pos[0] = nodes[i-1].pos[0]+30*(float)Math.cos(nodes[i].angle);
			nodes[i].pos[1] = nodes[i-1].pos[1];
			nodes[i].pos[2] = nodes[i-1].pos[2]+30*(float)Math.sin(nodes[i].angle);
		}
		curnode = 0;
		ncount = 5;
	}
	
	@Override
	public void onCollide(GameObject objb)
	{
		pos[2]-=15;
		double da = Noise.Noisei(52666+(ncount++)*97)*Math.PI/4;
		int prevn = (curnode-1);
		if(prevn<0)	prevn=4;
		nodes[curnode].angle = nodes[prevn].angle+(float)da;
		nodes[curnode].pos[0] = nodes[prevn].pos[0]+30*(float)Math.cos(nodes[curnode].angle);
		nodes[curnode].pos[1] = nodes[prevn].pos[1];
		nodes[curnode].pos[2] = nodes[prevn].pos[2]+30*(float)Math.sin(nodes[curnode].angle);
		curnode = (curnode+1)%5;
		CollisionHull chull = pobj.cobj.GetHull(0);
		chull.SetPos(nodes[curnode].pos);
	}
	
	@Override
	public void Render(GameRenderer irenderer)
	{
		irenderer.SetMaterial(irenderer.GetDefaultMaterial("white"));
		irenderer.SetTexture(irenderer.GetDefaultTexture("fractalnoise"));
		for(int i=0;i<5;i++)
		{
			irenderer.SetDrawPos(nodes[i].pos);
			Quaternion.QRot(rot, 0, 1, 0, nodes[i].angle+(float)Math.PI/2);
			irenderer.SetDrawRot(rot);
			irenderer.DrawObject(robj);
		}
	}
}
