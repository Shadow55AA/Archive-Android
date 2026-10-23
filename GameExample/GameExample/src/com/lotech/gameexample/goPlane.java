package com.lotech.gameexample;

public class goPlane extends GameObject
{
	private float size[] = {1,1};
	
	void SetSize(float isize[])
	{
		for(int i=0;i<2;i++)
			size[i]=isize[i];
	}
	
	@Override
	public void VRender(GameRenderer irenderer)
	{
		irenderer.SetDrawPos(pos);
		irenderer.SetDrawRot(rot);
		irenderer.SetTexture(irenderer.GetDefaultTexture("fractalnoise"));
		irenderer.DrawPlane(size);
	}
}
