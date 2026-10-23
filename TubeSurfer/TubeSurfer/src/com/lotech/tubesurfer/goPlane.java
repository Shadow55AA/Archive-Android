package com.lotech.tubesurfer;

public class goPlane extends GameObject
{
	private float size[] = {1,1};
	
	void SetSize(float isize[])
	{
		for(int i=0;i<2;i++)
			size[i]=isize[i];
	}
	
	@Override
	public void Render(GameRenderer irenderer)
	{
		irenderer.SetDrawPos(pos);
		irenderer.SetDrawRot(rot);
		irenderer.SetMaterial(irenderer.GetDefaultMaterial("white"));
		irenderer.SetTexture(irenderer.GetDefaultTexture("fractalnoise"));
		irenderer.DrawPlane(size);
	}
}
