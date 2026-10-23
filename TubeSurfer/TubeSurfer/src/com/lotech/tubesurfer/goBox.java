package com.lotech.tubesurfer;

public class goBox extends GameObject
{
	private float size[] = {1,1,1};
	
	void SetSize(float isize[])
	{
		for(int i=0;i<3;i++)
			size[i]=isize[i];
	}
	
	@Override
	public void Render(GameRenderer irenderer)
	{
		irenderer.SetDrawPos(pos);
		irenderer.SetDrawRot(rot);
		irenderer.DrawBox(size);
	}
}
