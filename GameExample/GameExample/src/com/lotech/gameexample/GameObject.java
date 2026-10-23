package com.lotech.gameexample;

public class GameObject
{
	protected float pos[] = {0,0,0,0};
	protected float rot[] = {0,0,0,1};
	protected float linvel[] = {0,0,0,0};
	protected float angvel[] = {0,0,0,0};
	
	public void SetPos(float ipos[])
	{
		for(int i=0;i<3;i++)
			pos[i]=ipos[i];
	}
	
	public void SetRot(float irot[])
	{
		for(int i=0;i<4;i++)
			rot[i]=irot[i];
	}
	
	public void VRender(GameRenderer irenderer)
	{
		
	}
}
