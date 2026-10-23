package com.lotech.r3;

public class GOLevel1Terrain extends GameObject
{
	public GOLevel1Terrain()
	{
		Quaternion.QRot(rot, 1, 0, 0, (float)Math.PI/2);
		pobj = new PhysicsObject();
		pobj.cobj = new CollisionObject();
		CollisionHull chull = new CollisionHull();
		float pfunc[] = {0,1,0,0};
		chull.MakePlane(pfunc);
		pobj.cobj.AddHull(chull);
		pobj.bgencontacts = true;
		pobj.contactbounce = 0.0f;
		pobj.contactfriction = 0.0f;
		pobj.contactspringstr = 1000;
	}
	
	@Override
	public void Render(GameRenderer irenderer)
	{
		float tvec[] = {0,0,0,0};
		irenderer.GetViewPos(tvec);
		int ivx = (int)tvec[0];
		ivx-=ivx%50;
		int ivz = (int)tvec[2];
		ivz-=ivz%50;
		tvec[0]=(float)(ivx);
		tvec[1]=pos[1];
		tvec[2]=(float)(ivz);
		irenderer.SetDrawRot(rot);
		irenderer.SetMaterial(irenderer.GetDefaultMaterial("white"));
		irenderer.SetTexture(irenderer.GetDefaultTexture("fractalnoise"));
		for(int i=-2;i<=2;i++)
		{
			for(int j=-2;j<=2;j++)
			{
				tvec[0]=(float)ivx+50*i;
				tvec[2]=(float)ivz+50*j;
				irenderer.SetDrawPos(tvec);
				irenderer.DrawObject(robj);
			}
		}
	}

}
